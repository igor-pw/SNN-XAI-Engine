package io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HeatmapGenerator
{
    public void saveHeatmapPanel(double[] originalInput, double[] input, int width, int height, String fileName) {
        double maxLrp = 1e-9;
        for (double r : input) {
            if (Math.abs(r) > maxLrp) maxLrp = Math.abs(r);
        }

        double maxOrig = 1e-9;
        for (double v : originalInput) {
            if (v > maxOrig) maxOrig = v;
        }

        int totalWidth = (width * 3) + 2;
        BufferedImage smallPanel = new BufferedImage(totalWidth, height, BufferedImage.TYPE_INT_RGB);

        double threshold = 0.15;
        int whiteLineColor = (255 << 16) | (255 << 8) | 255; // Biała kreska

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;

                double origVal = originalInput[index];
                int baseGray = (int) ((origVal / maxOrig) * 255);
                double val = input[index] / maxLrp;

                int grayRgb = (baseGray << 16) | (baseGray << 8) | baseGray;
                smallPanel.setRGB(x, y, grayRgb);

                int lrpRgb = 0; // domyślnie czarne tło
                if (val > threshold) {
                    int intensity = (int) (val * 255);
                    lrpRgb = (intensity << 16);
                } else if (val < -threshold) {
                    int intensity = (int) (Math.abs(val) * 255);
                    lrpRgb = intensity;
                }
                smallPanel.setRGB(x + width + 1, y, lrpRgb); // +1 za pierwszą kreskę

                int targetR = baseGray;
                int targetG = baseGray;
                int targetB = baseGray;
                double alpha = 0.0;

                if (val > threshold) {
                    targetR = 255;
                    targetG = 0;
                    targetB = 0;
                    alpha = val;
                } else if (val < -threshold) {
                    targetR = 0;
                    targetG = 0;
                    targetB = 255;
                    alpha = Math.abs(val);
                }

                int r = (int) ((1.0 - alpha) * baseGray + alpha * targetR);
                int g = (int) ((1.0 - alpha) * baseGray + alpha * targetG);
                int b = (int) ((1.0 - alpha) * baseGray + alpha * targetB);
                int overlayRgb = (r << 16) | (g << 8) | b;

                smallPanel.setRGB(x + (width * 2) + 2, y, overlayRgb); // +2 za obie kreski
            }

            smallPanel.setRGB(width, y, whiteLineColor);             // Kreska między sekcją 1 a 2
            smallPanel.setRGB((width * 2) + 1, y, whiteLineColor);     // Kreska między sekcją 2 a 3
        }

        int scale = 10;
        int scaledWidth = totalWidth * scale;
        int scaledHeight = height * scale;

        java.awt.Image smoothedImage = smallPanel.getScaledInstance(scaledWidth, scaledHeight, java.awt.Image.SCALE_SMOOTH);

        BufferedImage finalPanel = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D g2d = finalPanel.createGraphics();
        g2d.drawImage(smoothedImage, 0, 0, null);
        g2d.dispose();

        try {
            String fullPath = System.getProperty("user.dir") + File.separator + fileName;
            ImageIO.write(finalPanel, "png", new File(fullPath));
        } catch (IOException e) {
            System.err.println("[XAI-Panel] Błąd zapisu heatmapy: " + e.getMessage());
        }
    }
}
