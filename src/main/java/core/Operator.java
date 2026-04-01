package core;

import activation.ActivationFunc;
import structure.Scalar;

public class Operator
{
    public Scalar add(Scalar x1, Scalar x2) {
        double x1_value = x1.getValue();
        double x2_value = x2.getValue();

        return new Scalar(x1_value + x2_value, x1, x2);
    }

    public Scalar multiply(Scalar x1, Scalar x2) {
        double x1_value = x1.getValue();
        double x2_value = x2.getValue();

        return new Scalar(x1_value * x2_value, x1, x2);
    }

    public Scalar [] activate(Scalar [] input, ActivationFunc activationFunc) {
        Scalar [] output = new Scalar[input.length];

        for(int i = 0; i < input.length; i++) {
            output[i] = new Scalar(input[i]);
        }

        activationFunc.activate(output);

        return output;
    }
}
