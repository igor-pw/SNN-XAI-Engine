package activation;

import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

import static utils.TestGenerator.initDefinedNeuronVector;

public class SigmoidActivationTest extends OutputActivationFuncTest
{
    private final SigmoidActivation sigmoid = new SigmoidActivation();

    @Override
    protected OutputActivation getActivation() { return sigmoid; }

    @Override
    protected Stream<Arguments> provideUpdateToVectorXTestData() {
        return Stream.of(
                Arguments.of("Negative input",
                        initDefinedNeuronVector(-5.12f, -0.421f),
                        new float[]{0.005940522198340f, 0.396277483698283f}),
                Arguments.of("Positive input",
                        initDefinedNeuronVector(0.215f, 0.621f),
                        new float[]{0.553543903151118f, 0.650445948782812f}),
                Arguments.of("Mixed input",
                        initDefinedNeuronVector(0.532f, 2.5421f, -1.5421f, 0.0f, -3.04591f),
                        new float[]{0.629949459291170f, 0.927040989600918f, 0.176230203918291f, 0.5f, 0.045394379263260f})
        );
    }
}
