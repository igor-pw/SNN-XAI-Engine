package integration;

import activation.*;
import data.Dataset;
import execution.Trainer;
import initialization.Initializer;
import initialization.LeCunInitializer;
import io.CsvReader;
import io.DataReader;
import loss.BceLoss;
import normalization.Normalizer;
import normalization.ZScoreNormalizer;
import optimization.optimizer.NAdam;
import org.junit.jupiter.api.Test;
import structure.Layer;
import structure.NeuralNetwork;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class XorTest
{
    @Test
    public void shouldCorrectlyPerformFullLearningProcess_andPredictOutput() {
        //given
        long seed = 42;
        String pathName = "src/test/resources/Xor_Dataset.csv";

        DataReader reader = new CsvReader();

        Dataset dataset = reader.read(pathName, 1);

        Initializer lecun = new LeCunInitializer(seed);
        Normalizer zScore = new ZScoreNormalizer();

        zScore.normalize(dataset);

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

        trainer.initNeuralNetwork(lecun);
        trainer.loadData(dataset, dataset);

        trainer.fit();

        //then
        assertTrue(trainer.getValAccuracy() > 99.00);
    }
}
