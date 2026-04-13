package activation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static utils.TestUtils.getResult;

public class SoftmaxActivationTest extends ActivationFuncTest {

    private final SoftmaxActivation softmax = new SoftmaxActivation();

    @Override
    protected ActivationFunc getActivation() { return softmax; }

    @Override
    protected Stream<Arguments> provideUpdateToXTestData() {
        return Stream.of(
                Arguments.of("Negative input", -8.4012, 1.0),
                Arguments.of("Zero input", 0.0, 1.0),
                Arguments.of("Positive input", 32.981, 1.0)
        );
    };

    @Override
    protected Stream<Arguments> provideUpdateToVectorXTestData() {
        return Stream.of(
                Arguments.of("Negative input",
                        new double[]{-4.612, -5.129},
                        new double[]{0.6264459988, 0.3735540012}),
                Arguments.of("Positive input",
                        new double[]{3.90123, 1.4512, 3.5121},
                        new double[]{0.566913617, 0.0489195416, 0.3841668414}),
                Arguments.of("Mixed input",
                        new double[]{6.9452, -12.4521, 4.1518, -9.3215, -3.9781},
                        new double[]{0.9423020364, 0.0000000035, 0.0576808862, 0.0000000812, 0.0000169927})
        );
    }

    @Test
    public void shouldUpdateToPositive_whenValueIsNegative() {
        //given
        initInput(-5.912);

        //when
        softmax.activate(input);
        double result = input[0].getValue();

        //then
        assertTrue(result > 0);
    }

    @Test
    public void shouldSumToX_whenValuesAreMixed() {
        //given
        initInput(-5.132, 3.125, 2.612, -0.5312);
        double expected = 1.0;

        //when
        softmax.activate(input);
        double [] result = getResult(input);

        //then
        double sum = 0.0;

        for(double value : result) {
            sum += value;
        }

        assertEquals(expected, sum);
    }
}
