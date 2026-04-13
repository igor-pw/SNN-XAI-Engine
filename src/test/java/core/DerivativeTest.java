package core;

import activation.ActivationFunc;
import activation.SeluActivation;
import core.Operator;
import org.junit.jupiter.api.Test;
import structure.Scalar;
import utils.TestGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DerivativeTest
{
    @Test
    public void shouldUpdatePatentsGradToPositive_whenAddIsUsed() {
        //given
        Scalar parent1 = new Scalar();
        Scalar parent2 = new Scalar();

        //when
        Scalar y = Operator.add(parent1, parent2);
        y.setGrad(2.5);

        y.propagateGrad.run();

        //then
        assertTrue(parent1.getGrad() > 0.0);
        assertTrue(parent2.getGrad() > 0.0);
    }

    @Test
    public void shouldUpdateToNegativeParentsGradToNegative_whenAddIsUsed() {
        //given
        Scalar parent1 = new Scalar();
        Scalar parent2 = new Scalar();

        //when
        Scalar x = Operator.add(parent1, parent2);
        x.setGrad(-0.275);

        x.propagateGrad.run();

        //then
        assertTrue(parent1.getGrad() < 0.0);
        assertTrue(parent2.getGrad() < 0.0);
    }

    @Test
    public void shouldUpdateParentsGradToX_whenAddIsUsed() {
        //given
        Scalar x1 = new Scalar();
        Scalar x2 = new Scalar();
        Scalar y1 = new Scalar();

        double expected = 0.5;

        //when
        Scalar y2 = Operator.add(x1, x2);
        Scalar z = Operator.add(y1, y2);
        z.setGrad(0.5);

        z.propagateGrad.run();
        y2.propagateGrad.run();

        //then
        assertEquals(expected, y1.getGrad());
        assertEquals(expected, y2.getGrad());
        assertEquals(expected, x1.getGrad());
        assertEquals(expected, x2.getGrad());
    }

    @Test
    public void shouldUpdateParentsGradToPositive_whenMultiplyIsUsed() {
        //given
        Scalar x1 = new Scalar(3.0);
        Scalar x2 = new Scalar(2.0);

        //when
        Scalar y = Operator.multiply(x1, x2);
        y.setGrad(1.125);

        y.propagateGrad.run();

        //then
        assertTrue(x1.getGrad() > 0.0);
        assertTrue(x2.getGrad() > 0.0);
    }

    @Test
    public void shouldUpdateParentsToNegative_when_MultiplyIsUsed() {
        //given
        Scalar x1 = new Scalar(3.0);
        Scalar x2 = new Scalar(2.0);

        //when
        Scalar y = Operator.multiply(x1, x2);
        y.setGrad(-0.625);

        y.propagateGrad.run();

        //then
        assertTrue(x1.getGrad() < 0.0);
        assertTrue(x2.getGrad() < 0.0);
    }

    @Test
    public void shouldUpdateParentsToPositiveAndNegative_whenMultiplyIsUsed() {
        //given
        Scalar x1 = new Scalar(3.0);
        Scalar x2 = new Scalar(-2.0);

        //when
        Scalar y = Operator.multiply(x1, x2);
        y.setGrad(-0.625);

        y.propagateGrad.run();

        //then
        assertTrue(x1.getGrad() > 0.0);
        assertTrue(x2.getGrad() < 0.0);
    }

    @Test
    public void shouldUpdateParentsGradToX_whenMultiplyIsUsed() {
        //given
        Scalar x1 = new Scalar(2.0);
        Scalar x2 = new Scalar(3.0);
        Scalar y1 = new Scalar(4.5);

        double expected1 = 6.0;
        double expected2 = 4.5;
        double expected3 = 13.5;
        double expected4 = 9.0;

        //when
        Scalar y2 = Operator.multiply(x1, x2);
        Scalar z = Operator.multiply(y1, y2);
        z.setGrad(1.0);

        z.propagateGrad.run();
        y2.propagateGrad.run();

        //then
        assertEquals(expected1, y1.getGrad());
        assertEquals(expected2, y2.getGrad());
        assertEquals(expected3, x1.getGrad());
        assertEquals(expected4, x2.getGrad());
    }

    @Test
    public void shouldUpdateParentGradToPositive_whenActivateIsUsed() {
        //given
        ActivationFunc selu = new SeluActivation();
        Scalar x = new Scalar(3.37541);

        //when
        Scalar [] y = Operator.activate(new Scalar[]{x}, selu);
        y[0].setGrad(1.7312);

        y[0].propagateGrad.run();

        //then
        assertTrue(x.getGrad() > 0.0);
    }

    @Test
    public void shouldUpdateParentGradToX_whenActivateIsUsed() {
        //given
        ActivationFunc selu = new SeluActivation();
        Scalar x = new Scalar(-3.25);

        double expected = -0.006816890923082;

        //when
        Scalar [] y = Operator.activate(new Scalar[]{x}, selu);
        y[0].setGrad(-0.1);

        y[0].propagateGrad.run();
        double result = x.getGrad();

        //then
        assertEquals(expected, result, 1e-15);
    }
}

