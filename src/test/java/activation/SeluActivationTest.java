package activation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeluActivationTest
{
    private SeluActivation selu;

    @BeforeEach
    public void setUp() {
        selu = new SeluActivation();
    }

    @Test
    public void shouldReturnNegative_whenValueIsNegative() {
        //given
        double input = -5.0;

        //when
        double result = selu.activate(input);

        //then
        assertTrue(result < 0);
    }

    @Test
    public void shouldReturnPositive_whenValueIsPositive() {
       //given
       double input = 5.0;

       //when
        double result = selu.activate(input);

        //then
        assertTrue(result > 0);
    }

    @Test
    public void shouldReturnZero_whenValueIsZero() {
        //given
        double input = 0.0;

        //when
        double result = selu.activate(input);

        //then
        assertEquals(0.0, result);
    }

    @Test
    public void shouldReturnX_whenValueIsPositive() {
        //given
        double input = 3.57;

        //when
        double result = selu.activate(input);

        //then
        assertEquals(3.751002524859065, result, 1e-12);
    }

    @Test
    public void shouldReturnX_whenValueIsNegative() {
        //given
        double input = -5.67;

        //when
        double result = selu.activate(input);

        //then
        assertEquals(-1.75203765117812, result, 1e-12);
    }
}
