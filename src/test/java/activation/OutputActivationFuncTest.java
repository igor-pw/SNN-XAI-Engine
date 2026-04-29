package activation;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import structure.Neuron;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class OutputActivationFuncTest
{
    protected abstract OutputActivation getActivation();
    protected abstract Stream<Arguments> provideUpdateToVectorXTestData();

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideUpdateToVectorXTestData")
    public void shouldUpdateToVectorX_whenActivateIsUsed(String description, Neuron [] input, double [] expected) {
        //given

        //when
        getActivation().activate(input);

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], input[i].getValue(), 1e-9);
        }
    }
}
