package operator;

import structure.Scalar;

public class SoftmaxOperator
{
    private final Scalar [] input;
    int counter = 0;

    public SoftmaxOperator(Scalar [] input) {
        int size = input.length;
        this.input = new Scalar[size];

        System.arraycopy(input, 0, this.input, 0, size);
    }

    public void increment() {
        if(counter++ >= this.input.length) {
            counter = 0;
            this.activate();
        }
    }

    private void activate() {
        double denominator = 0.0;

        for(Scalar scalar : input) {
            denominator += Math.exp(scalar.getValue());
        }

        for(Scalar scalar : input) {
            double currentValue = scalar.getValue();
            double newValue = Math.exp(currentValue) / denominator;
            scalar.setValue(newValue);
        }
    }
}
