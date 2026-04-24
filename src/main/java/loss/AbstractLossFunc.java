package loss;

import structure.Neuron;
import structure.Scalar;

public abstract class AbstractLossFunc
{
    public static double EPSILON = 1e-15;

    public abstract double compute(Neuron [] predicted, double [] target);
    public abstract void derive(Neuron [] predicted, double [] target);

    protected void validate(Neuron[] predicted, double [] target) {
        if(predicted.length != target.length) {
            throw new IllegalArgumentException("Mismatched array sizes");
        }
    }
}
