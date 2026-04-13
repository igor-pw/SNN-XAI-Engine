package loss;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import structure.Scalar;
import utils.TestGenerator;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class LossFuncTest
{
    protected abstract AbstractLossFunc getLossFunc();
    protected abstract Stream<Arguments> provideReturnXCostTestData();

    @Test
    public void shouldReturnPositiveCost_whenComputeIsUsed() {
        //given
        Scalar [] predicted = TestGenerator.initRandomScalarVector(20, 1.0);
        double [] target = TestGenerator.generateRandomVector(20, 1.0);

        //when
        double result = getLossFunc().compute(predicted, target);

        //then
        assertTrue(result > 0.0);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideReturnXCostTestData")
    public void shouldReturnXCost_whenComputeIsUsed(String description, Scalar[] predicted, double [] target, double expected, double delta) {
        //given

        //when
        double result = getLossFunc().compute(predicted, target);

        //then
        assertEquals(expected, result, delta);
    }

    @Test
    public void shouldThrowException_whenInputSizesAreDifferent() {
        //given
        Scalar [] predicted = TestGenerator.initRandomScalarVector(5, 1.2);
        double [] target = TestGenerator.generateRandomVector(6, 1.2);

        //then
        assertThrows(IllegalArgumentException.class, () -> getLossFunc().compute(predicted, target));
    }

    //derive tests

}
