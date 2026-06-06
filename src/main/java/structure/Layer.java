package structure;

import activation.HiddenActivation;
import activation.SeluActivation;
import regularization.AlphaDropout;
import regularization.Regulator;

import java.io.Serializable;
import java.util.stream.IntStream;

public class Layer implements Serializable
{
    private final double [][] weight;
    private final double [][] weightGrad;
    private final double [] bias;
    private final double [] biasGrad;
    private final double [] activationInput;
    private final Neuron [] output;
    private final HiddenActivation activation;
    private final Regulator dropout;

    private Layer(Builder builder, HiddenActivation activation) {
        int inputSize = builder.input;
        int outputSize = builder.output;

        weight = new double[outputSize][inputSize];
        weightGrad = new double[outputSize][inputSize];
        bias = new double[outputSize];
        biasGrad = new double[outputSize];
        activationInput = new double[outputSize];
        output = new Neuron[outputSize];

        this.activation = activation;

        for (int i = 0; i < weight.length; i++) {
            output[i] = new Neuron();
            bias[i] = 0.1;
        }

        dropout = new AlphaDropout(outputSize, builder.dropout);
    }

    public static class Builder {
        private final int input;
        private final int output;
        private double dropout;

        public Builder(int input, int output) {
            this.input = input;
            this.output = output;
        }

        public Builder dropout(double dropout) {
            this.dropout = dropout;
            return this;
        }

        public Layer build(HiddenActivation activation) {
            return new Layer(this, activation);
        }
    }

    public Layer(int prevSize, int currentSize, HiddenActivation activation, double probability) {
        weight = new double[currentSize][prevSize];
        weightGrad = new double[currentSize][prevSize];
        bias = new double[currentSize];
        biasGrad = new double[currentSize];
        activationInput = new double[currentSize];
        output = new Neuron[currentSize];

        this.activation = activation;

        for (int i = 0; i < weight.length; i++) {
            output[i] = new Neuron();
            bias[i] = 0.1;
        }

        dropout = new AlphaDropout(currentSize, probability);
    }

    public Neuron [] forward(double [] input, boolean training) {

        if(training) dropout.regulate(output);

        IntStream.range(0, output.length).parallel().forEach(i -> {
            activationInput[i] = bias[i];

            for(int j = 0; j < input.length; j++) {
                activationInput[i] += weight[i][j]*input[j];
            }

            double value = activation.activate(activationInput[i]);
            output[i].setValue(value);
        });

        return output;
    }

    public Neuron [] forward(Neuron [] input, boolean training) {

        if(training) dropout.regulate(output);

        IntStream.range(0, output.length).parallel().forEach(i -> {
        //for(int i = 0; i < output.length; i++) {
            activationInput[i] = bias[i];

            for(int j = 0; j < input.length; j++) {
                activationInput[i] += weight[i][j]*input[j].value;
            }

            double value = activation.activate(activationInput[i]);
            output[i].setValue(value);
       });

       return output;
    }

    public void backward(Neuron [] input) {
        for(int i = 0; i < output.length; i++) {
            //alphaDropout
            output[i].multiplyGrad(dropout.derive(i));

            double delta = output[i].grad * activation.derive(activationInput[i]);

            for(int j = 0; j < input.length; j++) {
                weightGrad[i][j] += input[j].value * delta;

                input[j].grad += weight[i][j] * delta;
            }

            biasGrad[i] += delta;
        }
    }

    public void backward(double [] input) {
        for(int i = 0; i < output.length; i++) {
            //alphaDropout
            output[i].multiplyGrad(dropout.derive(i));

            double delta = output[i].grad * activation.derive(activationInput[i]);

            for(int j = 0; j < input.length; j++) {
                weightGrad[i][j] += input[j] * delta;
            }

            biasGrad[i] += delta;
        }
    }

    public double [] computeInputGradient(double [] input) {
        double [] inputGrad = new double[input.length];

        for(int i = 0; i < output.length; i++) {
            //alphaDropout
            output[i].multiplyGrad(dropout.derive(i));

            double delta = output[i].grad * activation.derive(activationInput[i]);

            for(int j = 0; j < input.length; j++) {
                weightGrad[i][j] += input[j] * delta;

                inputGrad[j] += weight[i][j] * delta;
            }

            biasGrad[i] += delta;
        }

        return inputGrad;
    }

    public double [] getDoubleOutput() {
        double [] result = new double[output.length];

        for(int i = 0; i < result.length; i++) {
            result[i] = output[i].getValue();
        }

        return result;
    }

    public int getOutputSize() { return weight.length; }
    public int getInputSize() { return weight[0].length; }
    public double [][] getWeight() { return weight; }
    public double [][] getWeightGrad() { return weightGrad; }
    public double [] getBias() { return bias; }
    public double [] getBiasGrad() { return biasGrad; }
    public double [] getActivationInput() { return activationInput; }
    public Neuron [] getOutput() { return output; }
    public HiddenActivation getActivation() { return activation; }
}
