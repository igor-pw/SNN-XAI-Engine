package activation;

import structure.Neuron;

import java.awt.*;
import java.io.Serializable;

public interface OutputActivation extends Serializable
{
    public static double EPSILON = 1e-15;

    void activate(Neuron[] input);
    void derive(Neuron [] predicted, double [] target);
}
