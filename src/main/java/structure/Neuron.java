package structure;

import activation.HiddenActivation;
import regularization.AlphaDropout;
import regularization.Regulator;

public class Neuron
{
    double value = 0.0;
    double grad = 0.0;

    public void setGrad(double grad) {this.grad = grad; }
    public void multiplyGrad(double grad) { this.grad *= grad; }
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
}
