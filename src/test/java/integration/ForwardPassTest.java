package integration;

import activation.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import structure.NeuralNetwork;
import structure.Neuron;
import structure.Scalar;
import utils.TestGenerator;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.TestUtils.depthFirstSearch;

public class ForwardPassTest
{
    static Stream<Arguments> allActivation() {
        return Stream.of(
            Arguments.of(new SoftmaxActivation(), new double[]{0.0, 1.0}),
            Arguments.of(new SigmoidActivation(), new double[]{0.0, 1.0}),
            Arguments.of(new ReluActivation(), new double[]{0.0, Double.MAX_VALUE}),
            Arguments.of(new LinearActivation(), new double[]{-Double.MAX_VALUE, Double.MAX_VALUE})
        );
    }

    @ParameterizedTest
    @MethodSource("allActivation")
    public void shouldPerformFullForwardPass(HiddenActivation outputActivation, double [] expectedRange) {
        //given
        int[] structure = {784, 1024, 512, 256, 128, 64, 32, 16, 10};
        int layerNumber = structure.length - 1;
        NeuralNetwork neuralNetwork = TestGenerator.initRandomNeuralNetwork(structure, outputActivation);

        double[] input = TestGenerator.generateRandomVector(structure[0], 5.0);

        int expectedSize = structure[layerNumber];
        double expectedSum = 1.0;

        //when
        Neuron [] output = neuralNetwork.forward(input);

        int resultSize = output.length;
        double resultSum = 0.0;

        for(Neuron neuron : output) {
            resultSum += neuron.getValue();
        }

        //then
        assertEquals(expectedSize, resultSize);

        for(Neuron neuron : output) {
            double value = neuron.getValue();
            assertTrue(value >= expectedRange[0] && value <= expectedRange[1]);
        }
    }

    /*@Test
    public void shouldBuildComputationalGraphDuringForwardPass() {
        //given
        int[] structure = {10, 16, 8, 4};
        int layerNumber = structure.length - 1;
        HiddenActivation sigmoid = new SigmoidActivation();
        NeuralNetwork neuralNetwork = TestGenerator.initRandomNeuralNetwork(structure, sigmoid);

        double [] input = TestGenerator.generateRandomVector(structure[0], 5.0);
        double expected = 11876;

        //when
        Neuron [] output = neuralNetwork.forward(input);

        int result = depthFirstSearch(output);

        //then
        assertEquals(expected, result);
    }*/
}
