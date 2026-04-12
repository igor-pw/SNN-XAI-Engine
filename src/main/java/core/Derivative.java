package core;

import activation.SeluActivation;
import structure.Scalar;

public class Derivative
{
    public static Runnable add(Scalar scalar) {
        return () -> {
            Scalar x1 = scalar.getParent()[0];
            Scalar x2 = scalar.getParent()[1];

            double grad = scalar.getGrad();

            x1.addGrad(grad);
            x2.addGrad(grad);
        };
    }

    public static Runnable multiply(Scalar scalar) {
        return () -> {
            Scalar x1 = scalar.getParent()[0];
            Scalar x2 = scalar.getParent()[1];

            double grad = scalar.getGrad();

            x1.addGrad(x2.getValue() * grad);
            x2.addGrad(x1.getValue() * grad);
        };
    }

    public static Runnable activate(Scalar scalar) {
        return () -> {
            Scalar x = scalar.getParent()[0];

            double grad = scalar.getGrad();
            double derivative = x.getValue() > 0.0 ? SeluActivation.lambda : SeluActivation.lambda * SeluActivation.alfa * Math.exp(x.getValue());

            x.addGrad(derivative * grad);
        };
    }
}
