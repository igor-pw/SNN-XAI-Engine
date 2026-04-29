package structure;

import activation.HiddenActivation;

public class Neuron
{
    private final Scalar [] weight;
    private final Neuron [] input;
    private final Scalar bias;
    private final HiddenActivation activationFunc;
    private double value = 0.0;
    private double activationInput = 0.0;
    private double grad = 0.0;
    private double hessian = 0.0;

    //new / refactored
    public Neuron() {
        weight = null;
        input = null;
        bias = null;
        activationFunc = null;
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
        activationInput = bias.getValue();

        for(int i = 0; i < size; i++) {
            activationInput += weight[i].getValue() * input[i].getValue();
        }

        value = activationFunc.activate(activationInput);
    }

    public void backward() {
        int size = weight.length;
        double delta = grad * activationFunc.derive(activationInput, value);

        for(int i = 0; i < size; i++) {
            double weightValue = weight[i].getValue();
            double inputValue = input[i].getValue();

            weight[i].addGrad(inputValue * delta);
            input[i].addGrad(weightValue * delta);
        }

        bias.addGrad(delta);
    }

    public void addGrad(double grad) { this.grad += grad; }
    public void setGrad(double grad) {this.grad = grad; }
    public void multiplyGrad(double grad) { this.grad *= grad; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public Scalar [] getWeight() { return weight; }
    public Neuron [] getInput() { return input; }
    public Scalar getBias() { return bias; }
}
