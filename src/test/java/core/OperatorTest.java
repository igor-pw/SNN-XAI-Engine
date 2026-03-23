package core;

import org.junit.jupiter.api.Test;
import structure.Scalar;

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
}
