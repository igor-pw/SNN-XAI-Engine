package activation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SigmoidActivationTest extends ActivationFuncTest
{
    private final SigmoidActivation sigmoid = new SigmoidActivation();

    @Override
    protected HiddenActivation getActivation() { return sigmoid; }

    @Override
    protected Stream<Arguments> provideUpdateToXTestData() {
        return Stream.of(
                Arguments.of("Negative input", -8.5105, 0.000201302597281),
                Arguments.of("Zero input", 0.0, 0.5),
                Arguments.of("Positive input", 2.7198, 0.938184935960464)
        );
    };

    @Override
    protected Stream<Arguments> provideUpdateToVectorXTestData() {
        return Stream.of(
                Arguments.of("Negative input",
                        new double[]{-5.12, -0.421},
                        new double[]{0.005940522198340, 0.396277483698283}),
                Arguments.of("Positive input",
                        new double[]{0.215, 0.621},
                        new double[]{0.553543903151118, 0.650445948782812}),
                Arguments.of("Mixed input",
                        new double[]{0.532, 2.5421, -1.5421, 0.0, -3.04591},
                        new double[]{0.629949459291170, 0.927040989600918, 0.176230203918291, 0.5, 0.045394379263260})
        );
    }

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
}
