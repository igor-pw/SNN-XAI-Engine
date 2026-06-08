package integration;

import com.opencsv.CSVReader;
import execution.Explainer;
import io.HeatmapGenerator;
import io.NeuralNetworkIO;
import normalization.Normalizer;
import org.junit.jupiter.api.Test;
import structure.NeuralNetwork;

import java.io.FileReader;

public class MnistLayerwiseRelevancePropagationTest {

    @Test
    public void shouldPerformLayerwiseRelevancePropagation() {
        float alpha = 1.0f;
        float beta = 0.0f;

        Object [] model = NeuralNetworkIO.load("src/model/MNIST_98.16");
        NeuralNetwork neuralNetwork = (NeuralNetwork) model[0];
        Normalizer zScore = (Normalizer) model[1];
        HeatmapGenerator heatmapGenerator = new HeatmapGenerator();

        int predictSize = 100;
        int [] expected = new int[predictSize];
        float [][] input = new float[predictSize][];
        try (CSVReader reader = new CSVReader(new FileReader("src/test/resources/MNIST/mnist_test.csv"))) {
            String[] nextLine = reader.readNext();
            for(int i = 0; i < predictSize; i++) {
                nextLine = reader.readNext();

                input[i] = new float[nextLine.length - 1];
                for (int j = 0; j < nextLine.length - 1; j++) {
                    input[i][j] = Float.parseFloat(nextLine[j]);
                }
                expected[i] = Integer.parseInt(nextLine[nextLine.length - 1]);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading line: " + e.getMessage());
        }

        float [][] normalizedInput = zScore.normalizePredict(input);

        Explainer explainer = new Explainer(neuralNetwork, alpha, beta, input[0].length);

        for(int i = 0; i < predictSize; i++) {
            float[] relevance = explainer.performLayerwiseRelevancePropagation(normalizedInput[i], neuralNetwork);
            heatmapGenerator.saveHeatmapPanel(input[i], relevance, 28, 28, "src/model/MNIST_" + expected[i] + "_" + explainer.getPredictionIndex() + "_Heatmap" + i + ".png");
        }
    }
}
