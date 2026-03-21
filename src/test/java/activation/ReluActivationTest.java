package activation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReluActivationTest
{
    private ReluActivation relu;

    @BeforeEach
    public void setUp() {
        relu = new ReluActivation();
    }

    /*@Test
    public void shouldReturnZero_whenValueIsNegative() {
        //given
        double value = -5.3;

        //when
        double result = relu.activate(value);

        //then
        assertEquals(0.0, result);
    }

    @Test
    public void shouldReturnZero_whenValueIsZero() {
        //given
        double value = 0.0;

        //when
        double result = relu.activate(value);

        //then
        assertEquals(0.0,result);
    }

    @Test
    public void shouldReturnPositive_whenValueIsPositive() {
        //given
        double value = 3.76474;

        //when
        double result = relu.activate(value);

        //then
        assertTrue(result > 0.0);
    }

    @Test
    public void shouldReturnX_whenValueIsPositive() {
        //given
        double value = 4.841;

        //when
        double result = relu.activate(value);

        //then
        assertEquals(4.841, result);
    }*/
}
