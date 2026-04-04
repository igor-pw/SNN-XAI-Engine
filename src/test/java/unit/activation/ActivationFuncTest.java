package unit.activation;

import activation.ActivationFunc;
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
public abstract class ActivationFuncTest
{
    protected abstract ActivationFunc getActivation();
    protected abstract Stream<Arguments> provideUpdateToXTestData();
    protected abstract Stream<Arguments> provideUpdateToVectorXTestData();

    protected Scalar[] input;

    protected void initInput(double... values) {
        input = new Scalar[values.length];

        for(int i = 0; i < values.length; i++) {
            input[i] = new Scalar(values[i]);
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideUpdateToXTestData")
    public void shouldUpdateToX_whenActivateIsUsed(String description, double input, double expected) {
        //given
        initInput(input);

        //when
        getActivation().activate(this.input);
        double result = getResult(this.input)[0];

        //then
        assertEquals(expected, result, 1e-9);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideUpdateToVectorXTestData")
    public void shouldUpdateToVectorX_whenActivateIsUsed(String description, double [] input, double [] expected) {
        //given
        initInput(input);

        //when
        getActivation().activate(this.input);
        double [] result = getResult(this.input);

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i], 1e-9);
        }
    }

    @Test
    public void shouldThrowException_whenInputIsEmpty() {
        //given
        initInput();

        //then
        assertThrows(IllegalArgumentException.class, () -> getActivation().activate(input));
    }
}
