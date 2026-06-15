package regularization.dropout;

import structure.Neuron;

import java.io.Serializable;

public interface Dropout extends Serializable
{
    void regulate(Neuron [] neuron);
    float derive(int i);
}
