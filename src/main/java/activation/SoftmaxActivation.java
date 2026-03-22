package activation;

import structure.Scalar;

public class SoftmaxActivation implements ActivationFunc
{
    @Override
    public void activate(Scalar[] input) {
        double denominator = 0.0;

        if(input.length == 0) {
            throw new IllegalArgumentException("Empty input");
        }

         for(Scalar scalar : input) {
             denominator += Math.exp(scalar.getValue());
         }

         for(Scalar scalar : input) {
             double currentValue = scalar.getValue();
             double newValue = Math.exp(currentValue) / denominator;
             scalar.setValue(newValue);
         }
    }
}
