package structure;

import java.io.Serializable;

public class Neuron implements Serializable
{
    float value = 0.0f;
    float grad = 0.0f;

    public float getGrad() { return grad; }
    public void setGrad(float grad) {this.grad = grad; }
    public void multiplyGrad(float grad) { this.grad *= grad; }
    public float getValue() { return value; }
    public void setValue(float value) { this.value = value; }
}
