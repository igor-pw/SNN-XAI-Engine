package unit.initializer;

import activation.ActivationFunc;
import activation.LinearActivation;
import initializer.LeCunInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structure.Layer;
import structure.Scalar;
import utils.TestGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class LeCunInitializerTest
{
    private final TestGenerator generator = new TestGenerator();
    private final ActivationFunc linear = new LinearActivation();
    private final LeCunInitializer lecun = new LeCunInitializer();

    @Test
    public void shouldInitializeNonZeroValues_whenInitializeIsUsed() {
        //given
        int outputSize = 50;
        int inputSize = 150;

        Layer layer = new Layer(inputSize, outputSize, linear);
        Scalar [][] weight = layer.getWeight();

        double notExpected = 0.0;

        //when
        lecun.initialize(layer);
        double result;

        //then
        for(int i = 0; i < outputSize; i++) {
            for(int j = 0; j < inputSize; j++) {
                result = weight[i][j].getValue();
                assertNotEquals(notExpected, result);
            }
        }
    }

    @Test
    public void shouldInitializeValuesFromNormalDistribution_whenInitializeIsUsed() {
        //given
        int outputSize = 100;
        int inputSize = 200;

        Layer layer = new Layer(inputSize, outputSize, linear);
        Scalar[][] weight = layer.getWeight();

        double expectedMean = 0.0;
        double expectedStd = Math.sqrt(1.0 / (double)inputSize);

        //when
        double resultMean = 0.0;
        double resultStd = 0.0;
        lecun.initialize(layer);

        //then
        for(int i = 0; i < outputSize; i++) {
            for(int j = 0; j < inputSize; j++) {
                resultMean += weight[i][j].getValue();
            }
        }

        resultMean /= outputSize*inputSize;

        for(int i = 0; i < outputSize; i++) {
            for(int j = 0; j < inputSize; j++) {
                resultStd += Math.pow((weight[i][j].getValue() - resultMean), 2);
            }
        }

        resultStd /= outputSize*inputSize;
        resultStd = Math.sqrt(resultStd);

        assertEquals(expectedMean, resultMean, 1e-3);
        assertEquals(expectedStd, resultStd, 1e-3);
    }
}
