package loss;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import structure.Neuron;
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
        Neuron[] predicted = TestGenerator.initRandomNeuronVector(20, 1.0f);
        float [] target = TestGenerator.generateRandomVector(20, 1.0f);

        //when
        float result = getLossFunc().compute(predicted, target);

        //then
        assertTrue(result > 0.0f);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideReturnXCostTestData")
    public void shouldReturnXCost_whenComputeIsUsed(String description, Neuron [] predicted, float [] target, float expected, float delta) {
        //given

        //when
        float result = getLossFunc().compute(predicted, target);

        //then
        assertEquals(expected, result, delta);
    }

    @Test
    public void shouldThrowException_whenInputSizesAreDifferent() {
        //given
        Neuron [] predicted = TestGenerator.initRandomNeuronVector(5, 1.2f);
        float [] target = TestGenerator.generateRandomVector(6, 1.2f);

        //then
        assertThrows(IllegalArgumentException.class, () -> getLossFunc().compute(predicted, target));
    }
}
