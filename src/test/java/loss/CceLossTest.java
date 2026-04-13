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
                Arguments.of( "No difference", TestGenerator.initDefinedScalarVector(0.0, 0.0, 0.0, 1.0, 0.0), new double[]{0.0, 0.0, 0.0, 1.0, 0.0}, 0.0, 1e-15),
                Arguments.of( "With difference", TestGenerator.initDefinedScalarVector(0.253, 0.532, 0.821, 0.024), new double[]{1.0, 0.0, 0.0, 0.0}, 1.37436579025, 1e-9)
        );
    };
}
