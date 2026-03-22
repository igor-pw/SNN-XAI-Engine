package activation;

import structure.Scalar;

public class LinearActivation implements ActivationFunc
{
    @Override
    public void activate(Scalar[] input) {
        if(input.length == 0) {
            throw new IllegalArgumentException("Empty input");
        }
    }
}
