package activation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ReluActivationTest extends ActivationFuncTest
{
    private final ReluActivation relu = new ReluActivation();

    @Override
    protected HiddenActivation getActivation() { return relu; }

    @Override
    protected Stream<Arguments> provideUpdateToXTestData() {
        return Stream.of(
                Arguments.of("Negative input", -5.3, 0.0),
                Arguments.of("Zero input", 0.0, 0.0),
                Arguments.of("Positive input", 3.76474, 3.76474)
        );
    };

    @Override
    protected Stream<Arguments> provideUpdateToVectorXTestData() {
        return Stream.of(
                Arguments.of("Negative input",
                        new double[]{-42.1, -5.1584},
                        new double[]{0.0, 0.0}),
                Arguments.of("Positive input",
                        new double[]{3.215, 13.421},
                        new double[]{3.215, 13.421}),
                Arguments.of("Mixed input",
                        new double[]{-4.391, 0.0, 5.952, 9.2, -9.291521},
                        new double[]{0.0, 0.0, 5.952, 9.2, 0.0})
        );
    };

    @Test
    public void shouldUpdateToPositive_whenValueIsPositive() {
        //given
        initInput(3.76474);

        //when
        relu.activate(input);
        double result = input[0].getValue();

        //then
        assertTrue(result > 0.0);
    }
}
