package activation;

import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

public class LinearActivationTest extends HiddenActivationFuncTest
{
    private final HiddenActivation linear = new LinearActivation();

    @Override
    protected HiddenActivation getActivation() { return linear; }

    @Override
    protected Stream<Arguments> provideUpdateToXTestData() {
        return Stream.of(
                Arguments.of("Negative input", -5.67f, -5.67f),
                Arguments.of("Zero input", 0.0f, 0.0f),
                Arguments.of("Positive input", 3.57f, 3.57f)
        );
    };

    @Override
    protected Stream<Arguments> provideUpdateToVectorXTestData() {
        return Stream.of(
                Arguments.of("Negative input",
                        new float[]{-0.91f, -3.14192f},
                        new float[]{-0.91f, -3.14192f}),
                Arguments.of("Positive Input",
                        new float[]{8.24f, 0.48f},
                        new float[]{8.24f, 0.48f}),
                Arguments.of("Mixed input",
                        new float[]{-7.213f, 4.321f, -1.04f, -5.12f, 0.12f},
                        new float[]{-7.213f, 4.321f, -1.04f, -5.12f, 0.12f})
        );
    };
}
