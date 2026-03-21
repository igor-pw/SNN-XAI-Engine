package activation;

import structure.Scalar;

public class SeluActivation implements ActivationFunc {

    private final double lambda = 1.0507009873554804934193349852946;
    private final double alfa = 1.6732632423543772848170429916717;

    @Override
    public void activate(Scalar[] input) {
        for(Scalar scalar : input) {
            double currentValue = scalar.getValue();
            double newValue = 0.0;

            newValue = currentValue > 0 ? lambda * currentValue : alfa * lambda * (Math.exp(currentValue) - 1);
            scalar.setValue(newValue);
        }
    }
}