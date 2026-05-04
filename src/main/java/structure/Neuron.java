package structure;

import activation.HiddenActivation;

public class Neuron
{
    private boolean isGraphInput = false;
    private final Scalar [] weight;
    private final Neuron [] input;
    private final Scalar bias;
    private final HiddenActivation activationFunc;
    double value = 0.0;
    private double activationInput = 0.0;
    double grad = 0.0;
    double hessian = 0.0;

    //new / refactored
    public Neuron() {
        weight = null;
        input = null;
        bias = null;
        activationFunc = null;
    }

    public Neuron(boolean isGraphInput) {
        weight = null;
        input = null;
        bias = null;
        activationFunc = null;
        this.isGraphInput = isGraphInput;
    }
    public Neuron(double value) {
        this();

        this.value = value;
    }

    public Neuron(Scalar [] weight, Neuron [] input, Scalar bias, HiddenActivation activationFunc) {
        this.weight = weight;
        this.input = input;
        this.bias = bias;
        this.activationFunc = activationFunc;
    }

    public void forward() {
        int size = weight.length;

        //no need to clear output before every forward
        activationInput = bias.value;

        for(int i = 0; i < size; i ++) {
            activationInput += weight[i].value*input[i].value;
        }

        value = activationFunc.activate(activationInput);
    }

    public void backward() {
        int size = weight.length;
        double delta = grad * activationFunc.derive(activationInput, value);

        for(int i = 0; i < size; i++) {
            if(input[i].value != 0.0) {
                weight[i].grad += input[i].value * delta;
            }

            //skips computing gradient for network input
            if(!isGraphInput) {
                input[i].grad += weight[i].value * delta;
            }
        }

        bias.grad += delta;
    }

    //public void addGrad(double grad) { this.grad += grad; }
    public void setGrad(double grad) {this.grad = grad; }
    public void multiplyGrad(double grad) { this.grad *= grad; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public Scalar [] getWeight() { return weight; }
    public Neuron [] getInput() { return input; }
    public Scalar getBias() { return bias; }
}
