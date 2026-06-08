package activation;
import structure.Neuron;

public class ReluActivation implements OutputActivation
{
    @Override
    public void activate(Neuron[] input) {
        int size = input.length;
        float result;

        for(int i = 0; i < size; i++) {
            result = Math.max(input[i].getValue(), 0.0f);
            input[i].setValue(result);
        }
    }

    @Override
    public void derive(Neuron [] input, float [] output) {
        int size = input.length;

        for(int i = 0; i < size; i++) {
            input[i].multiplyGrad(input[i].getValue() > 0.0f ? 1.0f : 0.0f);
        }
    }
}
