package activation;

import org.junit.jupiter.api.Test;
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
                        initDefinedNeuronVector(-42.1, -5.1584),
                        new double[]{0.0, 0.0}),
                Arguments.of("Positive input",
                        initDefinedNeuronVector(3.215, 13.421),
                        new double[]{3.215, 13.421}),
                Arguments.of("Mixed input",
                        initDefinedNeuronVector(-4.391, 0.0, 5.952, 9.2, -9.291521),
                        new double[]{0.0, 0.0, 5.952, 9.2, 0.0})
        );
    };
}
