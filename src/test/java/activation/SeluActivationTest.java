package activation;

import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

public class SeluActivationTest extends HiddenActivationFuncTest
{
    private final SeluActivation selu = new SeluActivation();

    @Override
    protected HiddenActivation getActivation() { return selu; }

    @Override
    protected Stream<Arguments> provideUpdateToXTestData() {
        return Stream.of(
                Arguments.of("Negative input", -5.67f, -1.75203765117812f),
                Arguments.of("Zero input", 0.0f, 0.0f),
                Arguments.of("Positive input", 3.57f, 3.751002524859065f)
        );
    };

    @Override
    protected Stream<Arguments> provideUpdateToVectorXTestData() {
        return Stream.of(
                Arguments.of("Negative input",
                        new float[]{-0.91f, -3.14192f},
                        new float[]{-1.050421767898739f, -1.682149845496720f}),
                Arguments.of("Positive input",
                        new float[]{8.24f, 0.48f},
                        new float[]{8.657776135809159f, 0.504336473930630f}),
                Arguments.of("Mixed input",
                        new float[]{-7.213f, 4.321f, -1.04f, -5.12f, 0.12f},
                        new float[]{-1.756803721832739f, 4.540078966363031f, -1.136690897476226f, -1.747592898934778f, 0.126084118482658f})
        );
    }
}
