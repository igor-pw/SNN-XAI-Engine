package integration;

import activation.ActivationFunc;
import activation.SeluActivation;
import activation.SoftmaxActivation;
import org.junit.jupiter.api.Test;
import structure.NeuralNetwork;
import structure.Scalar;
import utils.TestGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ForwardPassTest
{
    @Test
    public void shouldPerformFullForwardPass() {
        //given
        TestGenerator generator = new TestGenerator();

        int[] structure = {784, 1024, 512, 256, 128, 64, 32, 16, 10};
        int layerNumber = structure.length - 1;
        ActivationFunc outputActivation = new SoftmaxActivation();
        NeuralNetwork neuralNetwork = generator.initRandomNeuralNetwork(structure, outputActivation);

        Scalar[] input = generator.initRandomScalarVector(structure[0], 5.0);

        int expectedSize = structure[layerNumber];
        double expectedSum = 1.0;
        double [] expectedRange = {0.0, 1.0};

        //when
        neuralNetwork.forward(input);
        Scalar [] output = neuralNetwork.getLayer()[layerNumber - 1].getOutput();

        int resultSize = output.length;
        double resultSum = 0.0;

        for(Scalar scalar : output) {
            resultSum += scalar.getValue();
        }

        //then
        assertEquals(expectedSize, resultSize);
        assertEquals(expectedSum, resultSum, 1e-12);

        for(Scalar scalar : output) {
            double value = scalar.getValue();
            assertTrue(value >= expectedRange[0] && value <= expectedRange[1]);
        }
    }
}
