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
    public double [] derive(double [] input, double [] output) {
        int size = input.length;
        double [] result = new double[size];

        for(int i = 0; i < size; i++) {
            result[i] = input[i] > 0.0 ? 1.0 : 0.0;
        }

        return result;
    }
}
