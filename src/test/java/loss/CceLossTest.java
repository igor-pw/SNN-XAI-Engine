package loss;

import org.junit.jupiter.params.provider.Arguments;
import utils.TestGenerator;

import java.util.stream.Stream;

public class CceLossTest extends LossFuncTest {

    private AbstractLossFunc cce = new CceLoss();

    @Override
    protected AbstractLossFunc getLossFunc() { return cce; }

    @Override
    protected Stream<Arguments> provideReturnXCostTestData() {
        return Stream.of(
                Arguments.of( "No difference", TestGenerator.initDefinedNeuronVector(0.0f, 0.0f, 0.0f, 1.0f, 0.0f), new float[]{0.0f, 0.0f, 0.0f, 1.0f, 0.0f}, 0.0f, 1e-6f),
                Arguments.of( "With difference", TestGenerator.initDefinedNeuronVector(0.253f, 0.532f, 0.821f, 0.024f), new float[]{1.0f, 0.0f, 0.0f, 0.0f}, 1.37436579025f, 1e-6f)
        );
    };
}
