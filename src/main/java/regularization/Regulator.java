package regularization;

import structure.Neuron;

public interface Regulator
{
    boolean regulate(Neuron neuron);
    double derive(Neuron neuron);
}
