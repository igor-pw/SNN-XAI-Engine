package activation;

import structure.Neuron;

public class SoftmaxActivation implements OutputActivation
{
    @Override
    public void activate(Neuron[] input) {
        int size = input.length;

        float maxValue = -Float.MAX_VALUE;
        for(Neuron neuron : input) {
            maxValue = Math.max(maxValue, neuron.getValue());
        }

        float denominator = 0.0f;

        for(Neuron neuron : input) {
            denominator += (float)Math.exp(neuron.getValue() - maxValue);
        }

        for(int i = 0; i < size; i++) {
            float result = (float)Math.exp(input[i].getValue() - maxValue) / denominator; // + EPSILON;
            input[i].setValue(result);
            }
    }

    @Override
    public void derive(Neuron [] predicted, float [] target) {
        return;
    }
}

