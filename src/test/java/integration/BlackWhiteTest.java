package integration;

import activation.OutputActivation;
import activation.SigmoidActivation;
import com.opencsv.CSVReader;
import execution.Trainer;
import initialization.Initializer;
import initialization.LeCunInitializer;
import loss.AbstractLossFunc;
import loss.BceLoss;
import normalization.Normalizer;
import normalization.ZScoreNormalizer;
import org.junit.jupiter.api.Test;

import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlackWhiteTest {

    @Test
    public void  shouldCorrectlyPerformFullLearningProcess_andPredictOutput() {
        double learningRate = 0.01;
        int epoch = 20;
        int batch = 2;
        long seed = 42;
        double threshold = 0.1;
        String pathName = "src/test/resources/MNIST/black_and_white.csv";

        int[] structure = {784, 4, 1};
        OutputActivation sigmoid = new SigmoidActivation();
        AbstractLossFunc bce = new BceLoss();
        Initializer lecun = new LeCunInitializer(seed);
        Normalizer zScore = new ZScoreNormalizer();

        double [] expected = {0.0, 1.0};

        //when
        Trainer trainer = new Trainer(learningRate, epoch, batch);

        trainer.readData(pathName, 1);
        trainer.normalizeData(zScore);
        trainer.initNeuralNetwork(structure, bce, sigmoid, lecun);

        trainer.fit();

        int predictSize = 2;
        double [][] input = new double[predictSize][];
        try (
                CSVReader reader = new CSVReader(new FileReader("src/test/resources/MNIST/black_and_white.csv"))) {
            String[] nextLine = reader.readNext();
            for (int i = 0; i < predictSize; i++) {
                nextLine = reader.readNext();

                input[i] = new double[nextLine.length - 1];
                for (int j = 0; j < nextLine.length - 1; j++) {
                    input[i][j] = Double.parseDouble(nextLine[j]);
                }
            }
        } catch(Exception e){
            throw new RuntimeException("Error reading line: " + e.getMessage());
        }

        double[][] normalizedInput = zScore.normalizePredict(input);

        double [] result = new double[2];

        for (int i = 0; i < predictSize; i++) {
            result[i] = trainer.predict(normalizedInput[i])[0];
        }

        for(int i = 0; i < predictSize; i++) {
            System.out.println(result[i]);
            assertEquals(expected[i], result[i], threshold);
        }
    }
}