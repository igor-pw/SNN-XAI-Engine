package loss;

import org.junit.jupiter.params.provider.Arguments;
import utils.TestGenerator;

import java.util.stream.Stream;

public class MseLossTest extends LossFuncTest
{
    private AbstractLossFunc mse = new MseLoss();

    @Override
    protected AbstractLossFunc getLossFunc() { return mse; }

    @Override
    protected Stream<Arguments> provideReturnXCostTestData() {
        return Stream.of(
                Arguments.of( "No difference", TestGenerator.initOneValueNeuronVector(50, 0.75f), TestGenerator.generateOneValueVector(50, 0.75f), 0.0f, 0.0f),
                Arguments.of( "With difference", TestGenerator.initDefinedNeuronVector(1.0f, 2.0f, 3.5f, -2.5f), new float[]{1.5f, 3.0f, 2.5f, 3.0f}, 8.125f, 0.0f)
        );
    };
}
