package activation;

import structure.Neuron;

public class SigmoidActivation implements OutputActivation
{
    @Override
    public void activate(Neuron[] input) {
        int size = input.length;
        float result;

        for(int i = 0; i < size; i++) {
            float x = input[i].getValue();
            if(x < -88.0f) x = -88.0f;
            if(x > 88.0f) x = 88.0f;

            result = 1 / (1 + (float)Math.exp(-x));
            input[i].setValue(result);
        }
    }

    @Override
    public void derive(Neuron [] predicted, float [] target) {
        return;
    }
}
