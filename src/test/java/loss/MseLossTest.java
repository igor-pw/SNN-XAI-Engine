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
                Arguments.of( "No difference", TestGenerator.initOneValueScalarVector(50, 0.75), TestGenerator.generateOneValueVector(50, 0.75), 0.0, 0.0),
                Arguments.of( "With difference", TestGenerator.initDefinedScalarVector(1.0, 2.0, 3.5, -2.5), new double[]{1.5, 3.0, 2.5, 3.0}, 8.125, 0.0)
        );
    };
}
