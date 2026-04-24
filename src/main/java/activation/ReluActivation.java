package activation;

import structure.Scalar;

public class ReluActivation implements OutputActivation
{
    @Override
    public double [] activate(double [] input) {
        int size = input.length;
        double [] result = new double[size];

        for(int i = 0; i < size; i++) {
            result[i] = Math.max(input[i], 0.0);
        }

        return result;
    }

    @Override
    public double [] derive(double [] input, double [] output) {
        int size = input.length;
        double [] result = new double[size];

        for(int i = 0; i < size; i++) {
            result[i] = input[i] > 0.0 ? 1.0 : 0.0;
        }

        return result;
    }
}
