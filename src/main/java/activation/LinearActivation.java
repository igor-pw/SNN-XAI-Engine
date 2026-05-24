package activation;

import structure.Scalar;

public class LinearActivation implements HiddenActivation
{
    @Override
    public double activate(double input) {
        return input;
    }

    @Override
    public double derive(double input) {
        return 1.0;
    }
}
