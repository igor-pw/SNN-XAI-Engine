package activation;

import structure.Neuron;
import structure.Scalar;

public class SigmoidActivation implements OutputActivation
{
    @Override
    public void activate(Neuron[] input) {
        int size = input.length;
        double result;

        for(int i = 0; i < size; i++) {
            result = 1 / (1 + Math.exp(-input[i].getValue()));
            input[i].setValue(result);
        }
    }

    @Override
    public double [] derive(double [] input, double [] output) {
        int size = input.length;
        double [] result = new double[size];

        for(int i = 0 ; i < size; i++) {
            result[i] = output[i] * (1 - output[i]);
        }

        return result;
    }
}
