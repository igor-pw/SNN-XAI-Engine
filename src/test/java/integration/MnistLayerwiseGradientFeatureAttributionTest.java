package integration;

import com.opencsv.CSVReader;
import execution.Explainer;
import io.HeatmapGenerator;
import io.NeuralNetworkIO;
import normalization.Normalizer;
import org.junit.jupiter.api.Test;
import structure.NeuralNetwork;

import java.io.FileReader;

public class MnistLayerwiseGradientFeatureAttributionTest
{
    @Test
    public void shouldPerformLayerwiseRelevancePropagation() {
        double alpha = 0.5;
        double beta = 0.5;

        Object [] model = NeuralNetworkIO.load("src/model/MNIST_98.05");
        NeuralNetwork neuralNetwork = (NeuralNetwork) model[0];
        Normalizer zScore = (Normalizer) model[1];
        HeatmapGenerator heatmapGenerator = new HeatmapGenerator();

        int predictSize = 100;
        int [] expected = new int[predictSize];
        double [][] input = new double[predictSize][];
        try (CSVReader reader = new CSVReader(new FileReader("src/test/resources/MNIST/mnist_test.csv"))) {
            String[] nextLine = reader.readNext();
            for(int i = 0; i < predictSize; i++) {
                nextLine = reader.readNext();

                input[i] = new double[nextLine.length - 1];
                for (int j = 0; j < nextLine.length - 1; j++) {
                    input[i][j] = Double.parseDouble(nextLine[j]);
                }
                expected[i] = Integer.parseInt(nextLine[nextLine.length - 1]);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading line: " + e.getMessage());
        }

        double [][] normalizedInput = zScore.normalizePredict(input);

        Explainer explainer = new Explainer(neuralNetwork, alpha, beta, input[0].length);

        for(int i = 0; i < predictSize; i++) {
            double [] target = new double[neuralNetwork.getLayer(neuralNetwork.getLayer().length - 1).getOutputSize()];
            target[expected[i]] = 1.0;
            double [] featureAttribution = explainer.performLayerwiseGradientFeatureAttribution(target, normalizedInput[i],  neuralNetwork);

            heatmapGenerator.saveHeatmapPanel(input[i], featureAttribution, 28, 28, "src/model/MNIST_" + expected[i] + "_Heatmap" + i + ".png");
        }
    }
}
