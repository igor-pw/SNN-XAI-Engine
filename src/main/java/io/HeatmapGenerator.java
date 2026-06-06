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
        int whiteLineColor = (255 << 16) | (255 << 8) | 255;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;

                double origVal = originalInput[index];
                int baseGray = (int) ((origVal / maxOrig) * 255);
                double val = input[index] / maxLrp;

                int grayRgb = (baseGray << 16) | (baseGray << 8) | baseGray;
                smallPanel.setRGB(x, y, grayRgb);

                int lrpRgb = 0;
                if (val > threshold) {
                    int intensity = (int) (val * 255);
                    lrpRgb = (intensity << 16);
                } else if (val < -threshold) {
                    int intensity = (int) (Math.abs(val) * 255);
                    lrpRgb = intensity;
                }
                smallPanel.setRGB(x + width + 1, y, lrpRgb);

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

                smallPanel.setRGB(x + (width * 2) + 2, y, overlayRgb);
            }

            smallPanel.setRGB(width, y, whiteLineColor);
            smallPanel.setRGB((width * 2) + 1, y, whiteLineColor);
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
            System.err.println("Error saving heatmap: " + e.getMessage());
        }
    }

    public void saveGeneratedPatternAsImage(double[] input, int width, int height, String fileName) {
        double minVal = Double.MAX_VALUE;
        double maxVal = -Double.MAX_VALUE;
        for (int i = 0; i < input.length; i++) {
            if (input[i] < minVal) minVal = input[i];
            if (input[i] > maxVal) maxVal = input[i];
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                double value = input[index];

                if (maxVal > minVal) {
                    value = (value - minVal) / (maxVal - minVal);
                }

                int gray = (int) (value * 255);

                if (gray < 0) gray = 0;
                if (gray > 255) gray = 255;

                // Składamy kolor RGB
                int rgb = (gray << 16) | (gray << 8) | gray;

                image.setRGB(x, y, rgb);
            }
        }

        try {
            String fullPath = System.getProperty("user.dir") + File.separator + fileName;
            ImageIO.write(image, "png", new File(fullPath));
        } catch (IOException e) {
            System.err.println("Error saving image: " + e.getMessage());
        }
    }
}
