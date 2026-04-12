package activation;

import structure.Scalar;

public class SeluActivation implements ActivationFunc
{
    public final static double lambda = 1.0507009873554804934193349852946;
    public final static double alfa = 1.6732632423543772848170429916717;

    @Override
    public void activate(Scalar[] input) {
        if(input.length == 0) {
            throw new IllegalArgumentException("Empty input");
        }

        for(Scalar scalar : input) {
            double currentValue = scalar.getValue();
            double newValue;

            newValue = currentValue > 0.0 ? lambda * currentValue : alfa * lambda * (Math.exp(currentValue) - 1);
            scalar.setValue(newValue);
        }
    }
}