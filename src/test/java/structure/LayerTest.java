package structure;

import org.junit.jupiter.api.Test;
import utils.TestGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestUtils.getResult;

public class LayerTest extends StructureTest
{
    @Test
    public void shouldReturnXSizeVector_whenForwardIsUsed() {
        //given
        int size = 1;
        Layer layer = TestGenerator.initRandomLayer(size, size+1, linear);

        Scalar [] input = TestGenerator.initDefinedScalarVector(0.25);
        int expected = 2;

        //when
        Scalar[] output = layer.forward(input);
        int result = output.length;

        //then
        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnVectorX_whenForwardIsUsed() {
        //given
        double [][] weight = {{0.25, 0.75}, {0.125, 0.5}, {0.8, 0.375}};
        double [] bias = {1.0, 1.5, 2.0};
        Layer layer = TestGenerator.initDefinedLayer(weight, bias, linear);

        Scalar [] input =  TestGenerator.initDefinedScalarVector(0.25, 0.75);
        double [] expected = {1.625, 1.90625, 2.48125};

        //when
        Scalar [] output = layer.forward(input);
        double [] result = getResult(output);

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i], 1e-12);
        }
    }

    //depth test
}
