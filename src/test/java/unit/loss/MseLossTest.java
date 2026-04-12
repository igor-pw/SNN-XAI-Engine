package unit.loss;

import loss.AbstractLossFunc;
import loss.LossFunc;
import loss.MseLoss;
import org.junit.jupiter.params.provider.Arguments;

import java.util.AbstractCollection;
import java.util.stream.Stream;

public class MseLossTest extends LossFuncTest
{
    private AbstractLossFunc mse = new MseLoss();

    @Override
    protected AbstractLossFunc getLossFunc() { return mse; }

    @Override
    protected Stream<Arguments> provideReturnXCostTestData() {
        return Stream.of(
                Arguments.of( "No difference", generator.initOneValueScalarVector(50, 0.75), generator.generateOneValueVector(50, 0.75), 0.0, 0.0),
                Arguments.of( "With difference", generator.initDefinedScalarVector(1.0, 2.0, 3.5, -2.5), new double[]{1.5, 3.0, 2.5, 3.0}, 8.125, 0.0)
        );
    };
}
