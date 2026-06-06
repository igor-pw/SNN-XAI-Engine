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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import structure.Layer;
import structure.NeuralNetwork;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XorTest
{
    @Disabled
    public void shouldCorrectlyPerformFullLearningProcess_andPredictOutput() {
        //given
        double threshold = 0.01;
        long seed = 67;
        String pathName = "src/test/resources/Xor_Dataset.csv";

        Initializer lecun = new LeCunInitializer(seed);
        Normalizer zScore = new ZScoreNormalizer();

        double [] expected = {0.0, 1.0, 1.0, 0.0};

        NeuralNetwork neuralNetwork = new NeuralNetwork.Builder()
                .addLayer(new Layer.Builder(2, 2))
                .addLayer(new Layer.Builder(2, 1))
                .build();

        //when
        Trainer trainer = new Trainer.Builder(neuralNetwork)
                .learningRate(0.02)
                .epoch(1)
                .batch(4)
                .build();

        trainer.readTrainingData(pathName, 1);
        trainer.normalizeData(zScore);
        trainer.initNeuralNetwork(lecun);

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
