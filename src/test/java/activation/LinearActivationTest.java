package activation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LinearActivationTest extends ActivationFuncTest
{
    private final LinearActivation linear = new LinearActivation();

    @Test
    public void shouldThrowException_whenInputIsNull() {
        //given
        initInput();

        //then
        assertThrows(IllegalArgumentException.class, () -> linear.activate(input));
    }

}
