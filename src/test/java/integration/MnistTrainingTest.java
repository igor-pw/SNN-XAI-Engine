package integration;

import activation.OutputActivation;
import activation.SoftmaxActivation;
import com.opencsv.CSVReader;
import execution.Trainer;
import initialization.Initializer;
import initialization.LeCunInitializer;
import io.NeuralNetworkIO;
import loss.AbstractLossFunc;
import loss.CceLoss;
import normalization.Normalizer;
import normalization.ZScoreNormalizer;
import optimization.GradientNormClipping;
import optimization.NAdam;
import org.junit.jupiter.api.Test;
import structure.Layer;
import structure.NeuralNetwork;

import java.io.FileReader;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MnistTrainingTest {

    @Test
    public void  shouldCorrectlyPerformFullLearningProcess_andPredictOutputWithXAccuracy() {
        //given
        int oneHotSize = 10;
        long seed = 42;
        String trainingPathName = "src/test/resources/MNIST/mnist_train.csv";
        String testPathName = "src/test/resources/MNIST/mnist_test.csv";

        Initializer lecun = new LeCunInitializer(seed);
        Normalizer zScore = new ZScoreNormalizer();

        NeuralNetwork neuralNetwork = new NeuralNetwork.Builder()
                .addLayer(new Layer.Builder(784, 512).dropout(0.15f))
                .addLayer(new Layer.Builder(512 , 10))
                .outputActivation(new SoftmaxActivation())
                .lossFunction(new CceLoss())
                .optimizer(new NAdam.Builder()
                        .epsilon(1e-8f)
                        .build())
                .gradientClipping(new GradientNormClipping(5.0f))
                .build();

        Trainer trainer = new Trainer.Builder(neuralNetwork)
                .learningRate(0.0009f)
                .epoch(25)
                .batch(64)
                .smoothing(0.02f)
                .decay(1e-4f)
                .build();

        //when
        trainer.readTrainingData(trainingPathName, 1);
        trainer.readTestData(testPathName, 1);
        trainer.toOneHotEncoding(oneHotSize);
        trainer.normalizeData(zScore);
        trainer.initNeuralNetwork(lecun);

        trainer.fit();

        String pathName = String.format(Locale.US, "src/model/MNIST_%.2f", trainer.getTestAccuracy());
        //NeuralNetworkIO.save(trainer.getNeuralNetwork(), zScore, pathName);

        assertTrue(trainer.getTestAccuracy() > 95.00);
    }
}
