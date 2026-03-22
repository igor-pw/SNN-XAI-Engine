package activation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReluActivationTest extends ActivationFuncTest
{
    private final ReluActivation relu = new ReluActivation();

    @Test
    public void shouldUpdateToZero_whenValueIsNegative() {
        //given
        initInput(-5.3);

        //when
        relu.activate(input);
        double result = input[0].getValue();

        //then
        assertEquals(0.0, result);
    }

    @Test
    public void shouldUpdateToZero_whenValueIsZero() {
        //given
        initInput(0.0);

        //when
        relu.activate(input);
        double result = input[0].getValue();

        //then
        assertEquals(0.0,result);
    }

    @Test
    public void shouldUpdateToPositive_whenValueIsPositive() {
        //given
        initInput(3.76474);

        //when
        relu.activate(input);
        double result = input[0].getValue();

        //then
        assertTrue(result > 0.0);
    }

    @Test
    public void shouldUpdateToX_whenValueIsPositive() {
        //given
        initInput(4.841);

        //when
        relu.activate(input);
        double result = input[0].getValue();

        //then
        assertEquals(4.841, result);
    }

    @Test
    public void shouldUpdateToX_whenTwoValuesArePositive() {
        //given
        initInput(3.215, 13.421);
        double [] expected = {3.215, 13.421};

        //when
        relu.activate(input);
        double [] result = getResult(input);

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i], 1e-12);
        }
    }

    @Test
    public void shouldUpdateToX_whenTwoValuesAreNegative() {
        //given
        initInput(-42.1, -5.1584);
        double expected = 0.0;

        //when
        relu.activate(input);
        double [] result = getResult(input);

        //then
        for(double value : result) {
            assertEquals(expected, value);
        }
    }

    @Test
    public void shouldUpdateToX_whenValuesAreMixed() {
        //given
        initInput(-4.391, 0.0, 5.952, 9.2, -9.291521);
        double [] expected = {0.0, 0.0, 5.952, 9.2, 0.0};

        //when
        relu.activate(input);
        double [] result = getResult(input);

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i], 1e-12);
        }
    }

    @Test
    public void shouldThrowException_whenInputIsEmpty() {
        //given
        initInput();

        //then
        assertThrows(IllegalArgumentException.class, () -> relu.activate(input));
    }
}
