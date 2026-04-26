package activation;

import structure.Neuron;

public interface OutputActivation
{
    public static double EPSILON = 1e-15;

    void activate(Neuron[] input);
    double [] derive(double [] input, double [] output);
}
