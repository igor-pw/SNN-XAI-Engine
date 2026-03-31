package structure;

import activation.ActivationFunc;
import activation.ReluActivation;
import loss.LossFunc;
import loss.mseLoss;
import org.junit.jupiter.api.Test;
import utils.TestGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NeuralNetworkTest
{
    private TestGenerator generator = new TestGenerator();
    private LossFunc mse = new mseLoss();
    private ActivationFunc relu = new ReluActivation();

    @Test
    public void shouldReturnXSizeVector_whenForwardIsUsed() {
        //given
        int [] structure = {10, 16, 8, 4};
        int layerNumber = structure.length - 1;
        NeuralNetwork neuralNetwork = new NeuralNetwork(structure, mse, relu);
        Layer [] layer = neuralNetwork.getLayer();

        for(int i = 0 ; i  < layerNumber; i++) {
            layer[i] = generator.initRandomLayer(structure[i], structure[i+1]);
        }

        Scalar [] input = generator.initRandomScalarVector(structure[0], 1.0);
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
        //To Do
    }
}
