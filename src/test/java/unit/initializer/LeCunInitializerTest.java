package unit.initializer;

import activation.ActivationFunc;
import activation.LinearActivation;
import initializer.LeCunInitializer;
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

        double meanExpected = 0.0;
        double stdExpected = Math.sqrt(1.0 / (double)inputSize);

        //when
        double meanResult = 0.0;
        double stdResult = 0.0;
        lecun.initialize(layer);

        //then
        for(int i = 0; i < outputSize; i++) {
            for(int j = 0; j < inputSize; j++) {
                meanResult += weight[i][j].getValue();
            }
        }

        meanResult /= outputSize*inputSize;

        for(int i = 0; i < outputSize; i++) {
            for(int j = 0; j < inputSize; j++) {
                stdResult += Math.pow((weight[i][j].getValue() - meanResult), 2);
            }
        }

        stdResult /= outputSize*inputSize;
        stdResult = Math.sqrt(stdResult);

        assertEquals(meanExpected, meanResult, 1e-3);
        assertEquals(stdExpected, stdResult, 1e-3);
    }
}
