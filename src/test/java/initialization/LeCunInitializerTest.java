package initialization;

import activation.HiddenActivation;
import activation.LinearActivation;
import org.junit.jupiter.api.Test;
import structure.Layer;
import structure.NeuralNetwork;

import static org.junit.jupiter.api.Assertions.*;

public class LeCunInitializerTest
{
    private final HiddenActivation linear = new LinearActivation();
    private final LeCunInitializer lecun = new LeCunInitializer(337609);

    @Test
    public void shouldInitializeNonZeroValues_whenInitializeIsUsed() {
        //given
        int outputSize = 50;
        int inputSize = 150;

        Layer layer = new Layer.Builder(inputSize, outputSize).build(linear);
        float [][] weight = layer.getWeight();

        double notExpected = 0.0;

        //when
        lecun.initialize(layer);
        double result;

        //then
        for(int i = 0; i < outputSize; i++) {
            for(int j = 0; j < inputSize; j++) {
                result = weight[i][j];
                assertNotEquals(notExpected, result);
            }
        }
    }

    @Test
    public void shouldInitializeValuesFromNormalDistribution_whenInitializeIsUsed() {
        //given
        int outputSize = 100;
        int inputSize = 200;

        Layer layer = new Layer.Builder(inputSize, outputSize).build(linear);
        float [][] weight = layer.getWeight();

        float expectedMean = 0.0f;
        float expectedStd = (float)Math.sqrt(1.0 / (float)inputSize);

        //when
        float resultMean = 0.0f;
        float resultStd = 0.0f;
        lecun.initialize(layer);

        //then
        for(int i = 0; i < outputSize; i++) {
            for(int j = 0; j < inputSize; j++) {
                resultMean += weight[i][j];
            }
        }

        resultMean /= outputSize*inputSize;

        for(int i = 0; i < outputSize; i++) {
            for(int j = 0; j < inputSize; j++) {
                resultStd += (float)Math.pow((weight[i][j] - resultMean), 2);
            }
        }

        resultStd /= (float)outputSize*inputSize;
        resultStd = (float)Math.sqrt(resultStd);

        assertEquals(expectedMean, resultMean, 1e-3);
        assertEquals(expectedStd, resultStd, 1e-3);
    }
}
