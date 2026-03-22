package activation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structure.Scalar;

import static org.junit.jupiter.api.Assertions.*;

public class SoftmaxActivationTest extends ActivationFuncTest {

    private final SoftmaxActivation softmax = new SoftmaxActivation();

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
    public void shouldUpdateToPositive_whenValueIsPositive() {
        //given
        initInput(3.941);

        //when
        softmax.activate(input);
        double result = input[0].getValue();

        //then
        assertTrue(result > 0);
    }

    @Test
    public void shouldUpdateToX_whenValueIsNegative() {
        //given
        initInput(-8.4012);
        double expected = 1.0;

        //when
        softmax.activate(input);
        double result = input[0].getValue();

        //then
        assertEquals(expected, result, 1e-12);
    }

    @Test
    public void shouldUpdateToX_whenValueIsPositive() {
        //given
        initInput(32.981);
        double expected = 1.0;

        //when
        softmax.activate(input);
        double result = input[0].getValue();

        //then
        assertEquals(expected, result, 1e-12);
    }

    @Test
    public void shouldUpdateToX_whenValuesAreNegative() {
        //given
        initInput(-4.612, -5.129);
        double [] expected = {0.6264459988, 0.3735540012};

        //when
        softmax.activate(input);
        double [] result = getResult(input);

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i], 1e-9);
        }
    }

    @Test
    public void shouldUpdateToX_whenValuesArePositive() {
        //given
        initInput(3.90123, 1.4512, 3.5121);
        double [] expected = {0.566913617, 0.0489195416, 0.3841668414};

        //when
        softmax.activate(input);
        double [] result = getResult(input);

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i], 1e-9);
        }
    }

    @Test
    public void shouldUpdateToX_whenValuesAreMixed() {
        //given
        initInput(6.9452, -12.4521, 4.1518, -9.3215, -3.9781);
        double [] expected = {0.9423020364, 0.0000000035, 0.0576808862, 0.0000000812, 0.0000169927};

        //when
        softmax.activate(input);
        double [] result = getResult(input);

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i], 1e-9);
        }
    }

    @Test
    public void shouldThrowException_whenInputIsEmpty() {
        //given
        initInput();

        //then
        assertThrows(IllegalArgumentException.class, () -> softmax.activate(input));
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
