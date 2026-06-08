package regularization;

import structure.Neuron;

import java.io.Serializable;

public interface Regulator extends Serializable
{
    void regulate(Neuron [] neuron);
    float derive(int i);
}
