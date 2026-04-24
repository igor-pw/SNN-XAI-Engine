package structure;

import org.junit.jupiter.api.Test;
import utils.TestGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestUtils.getResult;

public class NeuralNetworkTest extends StructureTest
{
    @Test
    public void shouldReturnXSizeVectors_whenForwardIsUsed() {
        //given
        int [] structure = {10, 16, 8, 4};
        int layerNumber = structure.length - 1;
        NeuralNetwork neuralNetwork = TestGenerator.initEqualsWeightsNeuralNetwork(structure, 0.5, linear);
        Layer[] layer = neuralNetwork.getLayer();

        double [] input = TestGenerator.generateRandomVector(structure[0], 1.0);
        int [] expected = {16, 8, 4};

        //when
        neuralNetwork.forward(input);
        int [] result = new int[layerNumber];
        for(int i = 0; i < layerNumber; i++) {
            result[i] = layer[i].getOutput().length;
        }

        //then
        for(int i = 0; i < layerNumber; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    @Test
    public void shouldReturnVectorX_whenForwardIsUsed() {
        //given
        int [] structure = {2, 4, 2};
        NeuralNetwork neuralNetwork = TestGenerator.initEqualsWeightsNeuralNetwork(structure, 0.5, linear);

        double [] input = {0.25, 0.75};
        double [] expected = {2.5, 2.5};

        //when
        Scalar [] output = neuralNetwork.forward(input);
        double [] result = getResult(output);

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i], 1e-12);
        }
    }
}
