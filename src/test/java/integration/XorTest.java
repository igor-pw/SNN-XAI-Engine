package integration;

import activation.*;
import execution.Trainer;
import initialization.Initializer;
import initialization.LeCunInitializer;
import loss.AbstractLossFunc;
import loss.BceLoss;
import loss.MseLoss;
import normalization.Normalizer;
import normalization.ZScoreNormalizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XorTest
{
    @Test
    public void shouldCorrectlyPerformFullLearningProcess_andPredictOutput() {
        //given
        double learningRate = 0.02;
        double threshold = 0.01;
        int epoch = 1;
        int batch = 4;
        long seed = 67;
        String pathName = "src/test/resources/Xor_Dataset.csv";

        int [] structure = {2, 2, 1};
        OutputActivation sigmoid = new SigmoidActivation();
        AbstractLossFunc mse = new BceLoss();
        Initializer lecun = new LeCunInitializer(seed);
        Normalizer zScore = new ZScoreNormalizer();

        double [] expected = {0.0, 1.0, 1.0, 0.0};

        //when
        Trainer trainer = new Trainer(learningRate, epoch, batch);

        trainer.readData(pathName, 1);
        trainer.normalizeData(zScore);
        trainer.initNeuralNetwork(structure, mse, sigmoid, lecun);

        trainer.fit();
        double [][] input = {{0.0, 0.0}, {0.0, 1.0}, {1.0, 0.0}, {1.0, 1.0}};

        double [][] normalizedInput = zScore.normalizePredict(input);
        double [] predicted = new double[expected.length];

        for(int i = 0; i < normalizedInput.length; i++) {
            predicted[i] = trainer.predict(normalizedInput[i])[0];
        }

        //then
        for(int i = 0; i < expected.length; i++) {
            System.out.println(predicted[i]);
            assertEquals(expected[i], predicted[i], threshold);
        }
    }
}
