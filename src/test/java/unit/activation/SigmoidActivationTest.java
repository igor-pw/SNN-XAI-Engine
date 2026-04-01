package unit.activation;

import activation.SigmoidActivation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SigmoidActivationTest extends ActivationFuncTest
{
    private final SigmoidActivation sigmoid = new SigmoidActivation();

    @Test
    public void shouldUpdateToPositive_whenValueIsNegative() {
        //given
        initInput(-9.154);

        //when
        sigmoid.activate(input);
        double result = input[0].getValue();

        //then
        assertTrue(result > 0);
    }

    @Test
    public void shouldUpdateToPositive_whenValueIsPositive() {
        //given
        initInput(2.957);

        //when
        sigmoid.activate(input);
        double result = input[0].getValue();

        //then
        assertTrue(result > 0.0);
    }

    @Test
    public void shouldUpdateToX_whenValueIsNegative() {
        //given
        initInput(-8.5105);
        double expected = 0.000201302597281;

        //when
        sigmoid.activate(input);
        double result = input[0].getValue();

        //then
        assertEquals(expected, result, 1e-12);
    }

    @Test
    public void shouldUpdateToX_whenValueIsPositive() {
        //given
        initInput(2.7198);
        double expected = 0.938184935960464;

        //when
        sigmoid.activate(input);
        double result = input[0].getValue();

        //then
        assertEquals(expected, result, 1e-12);
    }

    @Test
    public void shouldUpdateToX_whenValuesAreNegative() {
        //given
        initInput(-5.12, -0.421);
        double [] expected = {0.005940522198340, 0.396277483698283};

        //when
        sigmoid.activate(input);
        double [] result = getResult(input);

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i], 1e-12);
        }
    }

    @Test
    public void shouldUpdateToX_whenValuesArePositive() {
        //given
        initInput(0.215, 0.621);
        double [] expected = {0.553543903151118, 0.650445948782812};

        //when
        sigmoid.activate(input);
        double [] result = getResult(input);

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i], 1e-12);
        }
    }

    @Test
    public void shouldUpdateToX_whenValuesAreMixed() {
        initInput(0.532, 2.5421, -1.5421, 0.0, -3.04591);
        double [] expected = {0.629949459291170, 0.927040989600918, 0.176230203918291, 0.5, 0.045394379263260};

        //when
        sigmoid.activate(input);
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
        assertThrows(IllegalArgumentException.class, () -> sigmoid.activate(input));
    }
}
