package activation;
import structure.Neuron;
import structure.Scalar;

public class ReluActivation implements OutputActivation
{
    @Override
    public void activate(Neuron[] input) {
        int size = input.length;
        double result;

        for(int i = 0; i < size; i++) {
            result = Math.max(input[i].getValue(), 0.0);
            input[i].setValue(result);
        }
    }

    @Override
    public void derive(Neuron [] input, double [] output) {
        int size = input.length;

        for(int i = 0; i < size; i++) {
            input[i].multiplyGrad(input[i].getValue() > 0.0 ? 1.0 : 0.0);
        }
    }
}
