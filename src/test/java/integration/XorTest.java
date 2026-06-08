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
import optimization.NAdam;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import structure.Layer;
import structure.NeuralNetwork;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XorTest
{
    @Test
    public void shouldCorrectlyPerformFullLearningProcess_andPredictOutput() {
        //given
        long seed = 42;
        String pathName = "src/test/resources/Xor_Dataset.csv";

        Initializer lecun = new LeCunInitializer(seed);
        Normalizer zScore = new ZScoreNormalizer();

        NeuralNetwork neuralNetwork = new NeuralNetwork.Builder()
                .addLayer(new Layer.Builder(4, 2))
                .addLayer(new Layer.Builder(2, 1))
                .outputActivation(new SigmoidActivation())
                .lossFunction(new BceLoss())
                .optimizer(new NAdam.Builder().build())
                .build();

        //when
        Trainer trainer = new Trainer.Builder(neuralNetwork)
                .learningRate(0.001f)
                .epoch(5)
                .batch(4)
                .build();

        trainer.readTrainingData(pathName, 1);
        trainer.readTestData(pathName, 1);
        trainer.normalizeData(zScore);
        trainer.initNeuralNetwork(lecun);

        trainer.fit();


        //then
        assertTrue(trainer.getTestAccuracy() > 99.00);
    }
}
