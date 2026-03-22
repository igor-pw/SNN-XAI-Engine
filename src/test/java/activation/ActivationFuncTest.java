package activation;

import structure.Scalar;

public abstract class ActivationFuncTest
{
    protected Scalar[] input;

    protected void initInput(double... values) {
        input = new Scalar[values.length];

        for(int i = 0; i < values.length; i++) {
            input[i] = new Scalar(values[i]);
        }
    }

    protected double [] getResult (Scalar [] input) {
        double [] result = new double[input.length];

        for(int i = 0; i < input.length; i++) {
            result[i] = input[i].getValue();
        }

        return result;
    }
}
