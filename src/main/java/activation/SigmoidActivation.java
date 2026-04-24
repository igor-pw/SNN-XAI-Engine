package activation;

import structure.Scalar;

public class SigmoidActivation implements OutputActivation
{
    @Override
    public double [] activate(double [] input) {
        int size = input.length;
        double [] result = new double[size];

        for(int i = 0; i < size; i++) {
            result[i] = 1 / (1 + Math.exp(-input[i]));
        }

        return result;
    }

    public double [] derive(double [] input, double [] output) {
        int size = input.length;
        double [] result = new double[size];

        for(int i = 0 ; i < size; i++) {
            result[i] = output[i] * (1 - output[i]);
        }

        return result;
    }
}
