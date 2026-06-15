package loss;

import org.junit.jupiter.params.provider.Arguments;
import utils.TestGenerator;

import java.util.stream.Stream;

public class BceLossTest extends LossFuncTest
{
    private AbstractLossFunc bce = new BceLoss();

    @Override
    protected AbstractLossFunc getLossFunc() { return bce; }

    @Override
    protected Stream<Arguments> provideReturnXCostTestData() {
        return Stream.of(
                Arguments.of( "No difference", TestGenerator.initDefinedNeuronVector(1.0f, 0.0f, 1.0f, 0.0f), new float[]{1.0f, 0.0f, 1.0f, 0.0f}, 0.0f, 1e-6f),
                Arguments.of( "With difference", TestGenerator.initDefinedNeuronVector(0.753f, 0.213f, 0.353f, 0.832f), new float[]{1.0f, 0.0f, 0.0f, 1.0f}, 0.285637226097f, 1e-6f)
        );
    };
}
