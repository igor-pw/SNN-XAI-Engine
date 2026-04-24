package integration;

import activation.HiddenActivation;
import activation.LinearActivation;
import activation.OutputActivation;
import activation.SoftmaxActivation;
import com.opencsv.CSVReader;
import execution.Trainer;
import initialization.Initializer;
import initialization.LeCunInitializer;
import loss.AbstractLossFunc;
import loss.CceLoss;
import normalization.Normalizer;
import normalization.ZScoreNormalizer;
import org.junit.jupiter.api.Test;

import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MnistTest {

    @Test
    public void shouldPass() {
        //given
        double learningRate = 0.00025;

        int epoch = 10;
        int oneHotSize = 10;
        long seed = 42;
        String pathName = "src/test/resources/MNIST/mnist_train.csv";

        int[] structure = {784, 32, 10};
        activation.HiddenActivation linear = new LinearActivation();
        AbstractLossFunc cce = new CceLoss();
        Initializer lecun = new LeCunInitializer(seed);
        Normalizer zScore = new ZScoreNormalizer();

        double threshold = 0.5;

        //when
        Trainer trainer = new Trainer(learningRate, epoch);

        trainer.readData(pathName, 1);
        trainer.toOneHotEncoding(oneHotSize);
        trainer.normalizeData(zScore);
        trainer.initNeuralNetwork(structure, cce, linear, lecun);

        trainer.fit();

        //when
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

        for(int i = 0; i < predictSize; i++) {
            double[] result = trainer.predict(normalizedInput[i]);
            //then
            /*for (int j = 0; j < result.length; j++) {
                System.out.print(result[j] + ", ");
            }
            System.out.println();*/

            System.out.println(result[expected[i]]);
            //assertTrue(result[expected[i]] <= 1.0 && result[expected[i]] >= 1.0 - threshold);
        }
    }
}
