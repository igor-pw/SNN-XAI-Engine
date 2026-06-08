package loss;

import structure.Neuron;

import java.io.Serializable;

public abstract class AbstractLossFunc implements Serializable
{
    public static float EPSILON = 1e-15f;

    public abstract float compute(Neuron [] predicted, float [] target);
    public abstract void derive(Neuron [] predicted, float [] target);

    protected void validate(Neuron[] predicted, float [] target) {
        if(predicted.length != target.length) {
            throw new IllegalArgumentException("Mismatched array sizes");
        }
    }
}
