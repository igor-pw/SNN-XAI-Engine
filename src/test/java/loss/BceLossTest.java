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
                Arguments.of( "No difference", TestGenerator.initDefinedScalarVector(1.0, 0.0, 1.0, 0.0), new double[]{1.0, 0.0, 1.0, 0.0}, 0.0, 1e-15),
                Arguments.of( "With difference", TestGenerator.initDefinedScalarVector(0.753, 0.213, 0.353, 0.832), new double[]{1.0, 0.0, 0.0, 1.0}, 0.285637226097, 1e-9)
        );
    };
}
