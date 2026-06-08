package activation;

import structure.Neuron;

import java.awt.*;
import java.io.Serializable;

public interface OutputActivation extends Serializable
{
    public static float EPSILON = 1e-12f;

    void activate(Neuron[] input);
    void derive(Neuron [] predicted, float [] target);
}
