package core;

import activation.SeluActivation;
import org.junit.jupiter.api.Test;
import structure.Scalar;

import java.util.Deque;

import static org.junit.jupiter.api.Assertions.*;

public class AutoGradEngineTest
{
    @Test
    public void shouldReturnXSizedQueue_whenTopologicalSortIsUsed() {
        //given
        Scalar x = new Scalar();
        Scalar w = new Scalar();
        Scalar b = new Scalar();

        Scalar m = Operator.multiply(w, x);
        Scalar n = Operator.multiply(w, x);
        Scalar s = Operator.add(m, n);
        Scalar y = Operator.add(s, b);

        int expected = 7;

        //when
        Deque<Scalar> queue = AutoGradEngine.topologicalSort(new Scalar[]{y});

        //then
        assertEquals(expected, queue.size());
    }

    @Test
    public void  shouldReturnTopologicallySortedQueue_whenTopologicalSortIsUsed() {
       //given
       Scalar x = new Scalar();
       Scalar w = new Scalar();
       Scalar b = new Scalar();

       Scalar m = Operator.multiply(w, x);
       Scalar y = Operator.add(m, b);
       Scalar [] output = Operator.activate(new Scalar[]{y}, new SeluActivation());

       Scalar [] expectedVector = {output[0], y, m, w, x, b};

       //when
       Deque<Scalar> queue = AutoGradEngine.topologicalSort(output);

       //then
        for(Scalar expected : expectedVector) {
            assertSame(expected, queue.pop());
        }
    }

    @Test
    public void shouldUpdateGradsFromZero_whenBackwardIsUsed() {
        //given
        Scalar w1 = new Scalar(0.5);
        Scalar x1 = new Scalar(2.0);
        Scalar w2 = new Scalar(1.0);
        Scalar x2 = new Scalar(3.0);
        Scalar b = new Scalar(1.0);

        Scalar m1 = Operator.multiply(w1, x1);
        Scalar m2 = Operator.multiply(w2, x2);
        Scalar s = Operator.add(m1, m2);
        Scalar y = Operator.add(s, b);
        Scalar [] output = Operator.activate(new Scalar[]{y}, new SeluActivation());
        output[0].setGrad(2.0);

        //when
        Scalar [] result = {w1, x1, w2, x2, b, m1, m2, s, y};
        AutoGradEngine.backward(output);

        //then
        for(Scalar scalar : result) {
            assertTrue(scalar.getGrad() != 0.0);
        }
    }

    @Test
    public void shouldUpdateGradsToX_whenBackwardIsUsed() {
        //given
        Scalar x = new Scalar(3.0);
        Scalar w = new Scalar(2.0);
        Scalar b = new Scalar(1.0);
        Scalar m = Operator.multiply(w, x);
        Scalar y = Operator.add(m ,b);
        y.setGrad(1.0);

        double [] expected = {3.0, 2.0, 1.0, 1.0, 1.0};

        //when
        AutoGradEngine.backward(new Scalar[]{y});
        Scalar [] result = {w, x, b, m, y};

        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i].getGrad());
        }
    }
}
