package activation;

import structure.Scalar;

public class SigmoidActivation implements ActivationFunc
{
    @Override
    public void activate(Scalar[] input) {
        if(input.length == 0) {
            throw new IllegalArgumentException("Empty input");
        }

        for(Scalar scalar : input) {
            double currentValue = scalar.getValue();
            double newValue = 1/(1+Math.exp(-currentValue));
            scalar.setValue(newValue);
        }
    }
}
