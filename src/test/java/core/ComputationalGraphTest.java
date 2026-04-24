package core;

import activation.HiddenActivation;
import activation.LinearActivation;
import org.junit.jupiter.api.Test;
import structure.NeuralNetwork;
import structure.Scalar;
import utils.TestGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComputationalGraphTest
{
    @Test
    public void shouldReturnSizeX_whenBuildComputationalGraphIsUsed() {
        //given
        int [] structure = {2, 3, 2, 1};
        HiddenActivation linear = new LinearActivation();
        Scalar [] input = TestGenerator.initOneValueScalarVector(2, 0.0);
        NeuralNetwork neuralNetwork = TestGenerator.initRandomNeuralNetwork(structure, linear);
        ComputationalGraph computationalGraph = new ComputationalGraph();
        int expected = 26;

        //when
        computationalGraph.buildComputationalGraph(neuralNetwork.getLayer(), 2);
        int result = computationalGraph.getSize();

        //then
        System.out.println(result);
        assertEquals(expected, result);
    }
}
