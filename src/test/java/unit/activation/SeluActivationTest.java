package unit.activation;

import activation.SeluActivation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SeluActivationTest extends ActivationFuncTest
{
    private final SeluActivation selu = new SeluActivation();

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

    @Test
    public void shouldReturnZero_whenValueIsZero() {
        //given
        initInput(0.0);

        //when
        selu.activate(input);
        double result = input[0].getValue();

        //then
        assertEquals(0.0, result);
    }

    @Test
    public void shouldUpdateToX_whenValueIsPositive() {
        //given
        initInput(3.57);

        //when
        selu.activate(input);
        double result = input[0].getValue();

        //then
        assertEquals(3.751002524859065, result, 1e-12);
    }

    @Test
    public void shouldUpdateTwoValuesToX_whenValuesArePositive() {
        //given
        initInput(8.24, 0.48);
        double [] expected = {8.657776135809159, 0.504336473930630};

        //when
        selu.activate(input);
        double [] result = getResult(input);

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i], 1e-12);
        }
    }

    @Test
    public void shouldUpdateToX_whenValueIsNegative() {
        //given
        initInput(-5.67);
        double expected = -1.75203765117812;

        //when
        selu.activate(input);
        double result = input[0].getValue();

        //then
        assertEquals(expected, result, 1e-12);
    }

    @Test
    public void shouldUpdateTwoValuesToX_whenValuesAreNegative() {
        //given
        initInput(-0.91, -3.14192);
        double [] expected = {-1.050421767898739, -1.682149845496720};

        //when
        selu.activate(input);
        double [] result = getResult(input);

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i], 1e-12);
        }
    }

    @Test
    public void shouldUpdateTwoValuesToX_whenValuesAreMixed() {
        //given
        initInput(-7.213, 4.321, -1.04, -5.12, 0.12);
        double [] expected = {-1.756803721832739, 4.540078966363031, -1.136690897476226, -1.747592898934778, 0.126084118482658};

        //when
        selu.activate(input);
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
        assertThrows(IllegalArgumentException.class, () -> selu.activate(input));
    }
}
