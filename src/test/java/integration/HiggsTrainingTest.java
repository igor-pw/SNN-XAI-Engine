package integration;

import activation.SigmoidActivation;
import activation.SoftmaxActivation;
import execution.Trainer;
import initialization.LeCunInitializer;
import io.NeuralNetworkIO;
import loss.BceLoss;
import loss.CceLoss;
import normalization.ZScoreNormalizer;
import optimization.NAdam;
import org.junit.jupiter.api.Test;
import structure.Layer;
import structure.NeuralNetwork;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HiggsTrainingTest
{
    @Test
    public void shouldCorrectlyPerformFullHiggsTraining(){

        String trainingFilePath = "src/test/resources/Higgs/higgs_train.csv";
        String testFilePath = "src/test/resources/Higgs/higgs_test.csv";

        NeuralNetwork neuralNetwork = new NeuralNetwork.Builder()
                .addLayer(new Layer.Builder(30, 128))
                .addLayer(new Layer.Builder(128, 128).dropout(0.2f))
                .addLayer(new Layer.Builder(128, 128).dropout(0.2f))
                .addLayer(new Layer.Builder(128, 1))
                .outputActivation(new SigmoidActivation())
                .optimizer(new NAdam.Builder().build())
                .lossFunction(new BceLoss())
                .build();

        Trainer trainer = new Trainer.Builder(neuralNetwork)
                .epoch(10)
                .batch(4096)
                .learningRate(0.001f)
                .decay(1e-4f)
                .build();

        ZScoreNormalizer zScore = new ZScoreNormalizer();

        trainer.readTrainingData(trainingFilePath, 1);
        trainer.readTestData(testFilePath, 1);
        trainer.normalizeData(zScore);
        trainer.initNeuralNetwork(new LeCunInitializer(42));

        trainer.fit();

        String pathName = String.format(Locale.US, "src/model/higgs_%.2f", trainer.getTestAccuracy());
        //NeuralNetworkIO.save(neuralNetwork, zScore, pathName);

        assertTrue(trainer.getTestAccuracy() > 82.00);
    }

}
