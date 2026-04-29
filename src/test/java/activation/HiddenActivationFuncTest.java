package activation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import structure.Scalar;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.TestUtils.getResult;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class HiddenActivationFuncTest
{
    protected abstract HiddenActivation getActivation();
    protected abstract Stream<Arguments> provideUpdateToXTestData();
    protected abstract Stream<Arguments> provideUpdateToVectorXTestData();

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideUpdateToXTestData")
    public void shouldUpdateToX_whenActivateIsUsed(String description, double input, double expected) {
        //given

        //when
        double result = getActivation().activate(input);

        //then
        assertEquals(expected, result, 1e-9);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideUpdateToVectorXTestData")
    public void shouldUpdateToVectorX_whenActivateIsUsed(String description, double [] input, double [] expected) {
        //given

        //when
        double [] result = new double[input.length];
        for(int i = 0; i < input.length; i++) {
            result[i] = getActivation().activate(input[i]);
        }

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i], 1e-9);
        }
    }
}
