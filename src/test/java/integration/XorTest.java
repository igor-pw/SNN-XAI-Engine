package integration;

import activation.ActivationFunc;
import activation.SigmoidActivation;
import execution.Trainer;
import initialization.Initializer;
import initialization.LeCunInitializer;
import loss.AbstractLossFunc;
import loss.BceLoss;
import loss.MseLoss;
import normalization.Normalizer;
import normalization.ZScoreNormalizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class XorTest
{
    @Test
    public void shouldCorrectlyPerformFullLearningProcess_andPredictOutput() {
        //given
        double learningRate = 0.1;
        int epoch = 100;
        long seed = 4125;
        String pathName = "src/test/resources/Xor_Dataset.csv";

        int [] structure = {2, 4, 1};
        ActivationFunc sigmoid = new SigmoidActivation();
        AbstractLossFunc mse = new MseLoss();
        Initializer lecun = new LeCunInitializer(seed);
        Normalizer zScore = new ZScoreNormalizer();

        double threshold = 0.015;

        //when
        Trainer trainer = new Trainer(learningRate, epoch);

        trainer.readData(pathName, 1);
        trainer.normalizeData(zScore);
        trainer.initNeuralNetwork(structure, mse, sigmoid, lecun);

        trainer.fit();
        double [] result1 = trainer.predict(new double[]{0.0, 0.0});
        double [] result2 = trainer.predict(new double[]{1.0, 0.0});
        double [] result3 = trainer.predict(new double[]{0.0, 1.0});
        double [] result4 = trainer.predict(new double[]{1.0, 1.0});

        System.out.println(result1[0]);
        System.out.println(result2[0]);
        System.out.println(result3[0]);
        System.out.println(result4[0]);

        //then
        assertTrue(result1[0] >= 0.0 && result1[0] <= 0.0 + threshold);
        assertTrue(result2[0] <= 1.0 && result2[0] >= 1.0 - threshold);
        assertTrue(result3[0] <= 1.0 && result3[0] >= 1.0 - threshold);
        assertTrue(result4[0] >= 0.0 && result4[0] <= 0.0 + threshold);
    }
}
