package activation;

import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

import static utils.TestGenerator.initDefinedNeuronVector;

public class ReluActivationTest extends OutputActivationFuncTest
{
    private final ReluActivation relu = new ReluActivation();

    @Override
    protected OutputActivation getActivation() { return relu; }

    @Override
    protected Stream<Arguments> provideUpdateToVectorXTestData() {
        return Stream.of(
                Arguments.of("Negative input",
                        initDefinedNeuronVector(-42.1f, -5.1584f),
                        new float[]{0.0f, 0.0f}),
                Arguments.of("Positive input",
                        initDefinedNeuronVector(3.215f, 13.421f),
                        new float[]{3.215f, 13.421f}),
                Arguments.of("Mixed input",
                        initDefinedNeuronVector(-4.391f, 0.0f, 5.952f, 9.2f, -9.291521f),
                        new float[]{0.0f, 0.0f, 5.952f, 9.2f, 0.0f})
        );
    };
}
