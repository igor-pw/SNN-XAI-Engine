package unit.structure;

import org.junit.jupiter.api.Test;
import structure.Layer;
import structure.NeuralNetwork;
import structure.Scalar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestUtils.getResult;

public class NeuralNetworkTest extends StructureTest
{
    @Test
    public void shouldReturnXSizeVectors_whenForwardIsUsed() {
        //given
        int [] structure = {10, 16, 8, 4};
        int layerNumber = structure.length - 1;
        NeuralNetwork neuralNetwork = generator.initEqualsWeightsNeuralNetwork(structure, 0.5, linear);
        Layer[] layer = neuralNetwork.getLayer();

        Scalar[] input = generator.initRandomScalarVector(structure[0], 1.0);
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
        int layerNumber = structure.length - 1;
        NeuralNetwork neuralNetwork = generator.initEqualsWeightsNeuralNetwork(structure, 0.5, linear);
        Layer outputLayer = neuralNetwork.getLayer()[layerNumber - 1];

        Scalar [] input = generator.initDefinedScalarVector(0.25, 0.75);
        double [] expected = {2.5, 2.5};

        //when
        neuralNetwork.forward(input);
        double [] result = getResult(outputLayer.getOutput());

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i], 1e-12);
        }
    }
}
