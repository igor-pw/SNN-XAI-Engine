package regularization;

import structure.Neuron;

public interface Regulator
{
    void regulate(Neuron [] neuron);
    double derive(int i);
}
