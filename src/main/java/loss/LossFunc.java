package loss;

import structure.Neuron;

public interface LossFunc
{
    double compute(Neuron [] predicted, double [] target);
    void derive(Neuron [] predicted, double [] target);
}
