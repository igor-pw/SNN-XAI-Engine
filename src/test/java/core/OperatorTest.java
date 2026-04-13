package core;

import activation.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentAccessException;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import structure.Scalar;

import java.lang.reflect.AccessibleObject;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class OperatorTest
{
    private final Operator operator = new Operator();

    @Test
    public void shouldReturnScalarWithValueX_whenAddIsUsed() {
        //given
        Scalar x1 = new Scalar(3.2);
        Scalar x2 = new Scalar(5.3);
        double expected = 8.5;

        //when
        Scalar y = operator.add(x1, x2);
        double result = y.getValue();

        //then
        assertEquals(expected, result, 1e-12);
    }

    @Test
    public void shouldHaveAssignedParents_whenAddIsUsed() {
        //given
        Scalar x1 = new Scalar(-4.54);
        Scalar x2 = new Scalar(3.151);

        //when
        Scalar y = operator.add(x1, x2);
        Scalar [] parent = y.getParent();

        //then
        for(Scalar scalar : parent) {
            assertNotEquals(null, scalar);
        }
    }

    @Test
    public void shouldReturnCorrectValuesFromParents_whenAddIsUsed() {
        //given
        Scalar x1 = new Scalar(5.12);
        Scalar x2 = new Scalar(0.4421);
        double [] expected = {5.12, 0.4421};

        //when
        Scalar y = operator.add(x1, x2);
        Scalar [] parent = y.getParent();

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], parent[i].getValue());
        }
    }

    @Test
    public void shouldReturnScalarWithValueX_whenMultiplyIsUsed() {
        //given
        Scalar x1 = new Scalar(3.412);
        Scalar x2 = new Scalar(-2.541);
        double expected = -8.669892;

        //when
        Scalar y = operator.multiply(x1, x2);
        double result = y.getValue();

        //then
        assertEquals(expected, result, 1e-12);
    }

    @Test
    public void shouldHaveAssignedParents_whenMultiplyIsUsed() {
        //given
        Scalar x1 = new Scalar(0.94121);
        Scalar x2 = new Scalar(1.00091);

        //when
        Scalar y = operator.multiply(x1, x2);
        Scalar [] parent = y.getParent();

        //then
        for(Scalar scalar : parent) {
            assertNotEquals(null, scalar);
        }
    }

    @Test
    public void shouldCorrectlyReturnValuesFromParents_whenMultiplyIsUsed() {
        //given
        Scalar x1 = new Scalar(521.42165);
        Scalar x2 = new Scalar(-0.0000198);
        double [] expected = {521.42165, -0.0000198};

        //when
        Scalar y = operator.multiply(x1, x2);
        Scalar [] parent = y.getParent();

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], parent[i].getValue());
        }
    }

    static Stream<Arguments> allActivation() {
        return Stream.of(
                Arguments.of(new SeluActivation(), new double[]{-1.590598464273987, 0.0, 3.299201100296208}, 1e-12) ,
                Arguments.of(new ReluActivation(), new double[]{0.0, 0.0, 3.14}, 0.0),
                Arguments.of(new SigmoidActivation(), new double[]{0.086986319931842, 0.5, 0.958512880698304}, 1e-12),
                Arguments.of(new SoftmaxActivation(), new double[]{0.0039370754, 0.0413237814, 0.9547391432}, 1e-9),
                Arguments.of(new LinearActivation(), new double[]{-2.351, 0.0, 3.14}, 0.0)
        );
    }

    @ParameterizedTest
    @MethodSource("allActivation")
    public void shouldCorrectlyReturnScalarWithXValue_whenActivateIsUsed(ActivationFunc activationFunc, double [] expected, double delta) {
        //given
        Scalar [] x = {new Scalar(-2.351), new Scalar(0.0), new Scalar(3.14)};

        //when
        Scalar [] result = operator.activate(x, activationFunc);

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i].getValue(), delta);
        }
    }


}
