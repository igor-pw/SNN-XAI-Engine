package activation;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LinearActivationTest extends ActivationFuncTest
{
    private final HiddenActivation linear = new LinearActivation();

    @Override
    protected HiddenActivation getActivation() { return linear; }

    @Override
    protected Stream<Arguments> provideUpdateToXTestData() {
        return Stream.of(
                Arguments.of("Negative input", -5.67, -5.67),
                Arguments.of("Zero input", 0.0, 0.0),
                Arguments.of("Positive input", 3.57, 3.57)
        );
    };

    @Override
    protected Stream<Arguments> provideUpdateToVectorXTestData() {
        return Stream.of(
                Arguments.of("Negative input",
                        new double[]{-0.91, -3.14192},
                        new double[]{-0.91, -3.14192}),
                Arguments.of("Positive Input",
                        new double[]{8.24, 0.48},
                        new double[]{8.24, 0.48}),
                Arguments.of("Mixed input",
                        new double[]{-7.213, 4.321, -1.04, -5.12, 0.12},
                        new double[]{-7.213, 4.321, -1.04, -5.12, 0.12})
        );
    };
}
