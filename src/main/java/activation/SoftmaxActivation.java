package activation;

import structure.Neuron;

public class SoftmaxActivation implements OutputActivation
{
    @Override
    public void activate(Neuron[] input) {
        int size = input.length;
        double result;
        double denominator = 0.0;

        for(Neuron neuron : input) {
            denominator += Math.exp(neuron.getValue());
        }

        for(int i = 0; i < size; i++) {
            result = Math.exp(input[i].getValue()) / denominator + EPSILON;
            input[i].setValue(result);
            }
    }

    @Override
    public void derive(Neuron [] predicted, double [] target) {
        return;
    }
}

