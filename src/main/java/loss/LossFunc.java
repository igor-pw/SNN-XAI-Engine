package loss;

import structure.Neuron;

public interface LossFunc
{
    float compute(Neuron [] predicted, float [] target);
    void derive(Neuron [] predicted, float [] target);
}