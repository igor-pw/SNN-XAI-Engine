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
                        initDefinedNeuronVector(-4.612f, -5.129f),
                        new float[]{0.6264459988f, 0.3735540012f}),
                Arguments.of("Positive input",
                        initDefinedNeuronVector(3.90123f, 1.4512f, 3.5121f),
                        new float[]{0.566913617f, 0.0489195416f, 0.3841668414f}),
                Arguments.of("Mixed input",
                        initDefinedNeuronVector(6.9452f, -12.4521f, 4.1518f, -9.3215f, -3.9781f),
                        new float[]{0.9423020364f, 0.0000000035f, 0.0576808862f, 0.0000000812f, 0.0000169927f})
        );
    }
}
