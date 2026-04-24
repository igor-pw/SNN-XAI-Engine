package activation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SeluActivationTest extends ActivationFuncTest
{
    private final SeluActivation selu = new SeluActivation();

    @Override
    protected HiddenActivation getActivation() { return selu; }

    @Override
    protected Stream<Arguments> provideUpdateToXTestData() {
        return Stream.of(
                Arguments.of("Negative input", -5.67, -1.75203765117812),
                Arguments.of("Zero input", 0.0, 0.0),
                Arguments.of("Positive input", 3.57, 3.751002524859065)
        );
    };

    @Override
    protected Stream<Arguments> provideUpdateToVectorXTestData() {
        return Stream.of(
                Arguments.of("Negative input",
                        new double[]{-0.91, -3.14192},
                        new double[]{-1.050421767898739, -1.682149845496720}),
                Arguments.of("Positive input",
                        new double[]{8.24, 0.48},
                        new double[]{8.657776135809159, 0.504336473930630}),
                Arguments.of("Mixed input",
                        new double[]{-7.213, 4.321, -1.04, -5.12, 0.12},
                        new double[]{-1.756803721832739, 4.540078966363031, -1.136690897476226, -1.747592898934778, 0.126084118482658})
        );
    }

    @Test
    public void shouldUpdateToNegative_whenValueIsNegative() {
        //given
        initInput(-5.3291);

        //when
        selu.activate(input);
        double result = input[0].getValue();

        //then
        assertTrue(result < 0);
    }

    @Test
    public void shouldUpdateToPositive_whenValueIsPositive() {
       //given
        initInput(3.3251);

       //when
        selu.activate(input);
        double result = input[0].getValue();

        //then
        assertTrue(result > 0);
    }
}
