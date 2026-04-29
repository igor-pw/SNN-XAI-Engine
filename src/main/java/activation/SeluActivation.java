package activation;

import structure.Scalar;

public class SeluActivation implements HiddenActivation
{
    public final static double lambda = 1.0507009873554804934193349852946;
    public final static double alfa = 1.6732632423543772848170429916717;

    @Override
    public double activate(double input) {
        return input > 0.0 ? lambda * input : lambda * alfa * (Math.exp(input) - 1);
    }

    @Override
    public double derive(double input, double output) {
        return input > 0.0 ? lambda : lambda * alfa * Math.exp(input);
    }
}