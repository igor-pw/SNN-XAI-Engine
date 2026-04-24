package activation;

import operator.SoftmaxOperator;
import structure.Scalar;

public class SoftmaxActivation implements OutputActivation
{
    @Override
    public double [] activate(double [] input) {
        int size = input.length;
        double [] result = new double[size];
        double denominator = 0.0;

        for(double value : input) {
            denominator += Math.exp(value);
        }

        for(int i = 0; i < size; i++) {
            result[i] = Math.exp(input[i]) / denominator;
        }

        return result;
    }

    @Override
    public double [] derive(double [] input, double [] output) {
        int size = input.length;
        double [] result = new double[size];

        for(int i = 0; i < size; i++) {
            result[i] = output[i] - input[i];
        }

        return result;
    }
}

