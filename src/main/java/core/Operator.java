package core;

import activation.ActivationFunc;
import structure.Scalar;

public class Operator
{
    public Scalar add(Scalar x1, Scalar x2) {
        double x1_value = x1.getValue();
        double x2_value = x2.getValue();

        Scalar result = new Scalar(x1_value + x2_value, x1, x2);
        result.propagateGrad = Derivative.add(result);

        return result;
    }

    public Scalar multiply(Scalar x1, Scalar x2) {
        double x1_value = x1.getValue();
        double x2_value = x2.getValue();

        Scalar result = new Scalar(x1_value * x2_value, x1, x2);
        result.propagateGrad = Derivative.multiply(result);

        return result;
    }

    public Scalar [] activate(Scalar [] input, ActivationFunc activationFunc) {
        Scalar [] output = new Scalar[input.length];

        for(int i = 0; i < input.length; i++) {
            output[i] = new Scalar(input[i]);
            output[i].propagateGrad = Derivative.activate(output[i]);
        }

        activationFunc.activate(output);

        return output;
    }
}
