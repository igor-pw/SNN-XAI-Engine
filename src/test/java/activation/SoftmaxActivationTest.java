package activation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static utils.TestGenerator.initDefinedNeuronVector;

public class SoftmaxActivationTest extends OutputActivationFuncTest {

    private final SoftmaxActivation softmax = new SoftmaxActivation();

    @Override
    protected OutputActivation getActivation() { return softmax; }

    @Override
    protected Stream<Arguments> provideUpdateToVectorXTestData() {
        return Stream.of(
                Arguments.of("Negative input",
                        initDefinedNeuronVector(-4.612, -5.129),
                        new double[]{0.6264459988, 0.3735540012}),
                Arguments.of("Positive input",
                        initDefinedNeuronVector(3.90123, 1.4512, 3.5121),
                        new double[]{0.566913617, 0.0489195416, 0.3841668414}),
                Arguments.of("Mixed input",
                        initDefinedNeuronVector(6.9452, -12.4521, 4.1518, -9.3215, -3.9781),
                        new double[]{0.9423020364, 0.0000000035, 0.0576808862, 0.0000000812, 0.0000169927})
        );
    }
}
