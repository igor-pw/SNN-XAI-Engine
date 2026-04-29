package activation;

import org.junit.jupiter.api.Test;
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
                        initDefinedNeuronVector(-5.12, -0.421),
                        new double[]{0.005940522198340, 0.396277483698283}),
                Arguments.of("Positive input",
                        initDefinedNeuronVector(.215, 0.621),
                        new double[]{0.553543903151118, 0.650445948782812}),
                Arguments.of("Mixed input",
                        initDefinedNeuronVector(0.532, 2.5421, -1.5421, 0.0, -3.04591),
                        new double[]{0.629949459291170, 0.927040989600918, 0.176230203918291, 0.5, 0.045394379263260})
        );
    }
}
