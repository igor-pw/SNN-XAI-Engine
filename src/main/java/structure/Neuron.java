package structure;

import activation.HiddenActivation;
import regularization.AlphaDropout;
import regularization.Regulator;

public class Neuron
{
    private boolean isGraphInput = false;
    double value = 0.0;
    double grad = 0.0;
    boolean dropoutMask = false;

    //new / refactored

    //public void addGrad(double grad) { this.grad += grad; }
    public void setGrad(double grad) {this.grad = grad; }
    public void multiplyGrad(double grad) { this.grad *= grad; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
    public boolean getDropoutMask() { return dropoutMask; }
    public void setDropoutMask(boolean dropoutMask) { this.dropoutMask = dropoutMask; }
}
