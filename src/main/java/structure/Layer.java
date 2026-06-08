package structure;

import activation.HiddenActivation;
import regularization.AlphaDropout;
import regularization.Regulator;

import java.io.Serializable;
import java.util.stream.IntStream;

public class Layer implements Serializable
{
    private final float [][] weight;
    private final float [][] weightGrad;
    private final float [] bias;
    private final float [] biasGrad;
    private final float [] activationInput;
    private final Neuron [] output;
    private final HiddenActivation activation;
    private final Regulator dropout;

    private Layer(Builder builder, HiddenActivation activation) {
        int inputSize = builder.input;
        int outputSize = builder.output;

        weight = new float[outputSize][inputSize];
        weightGrad = new float[outputSize][inputSize];
        bias = new float[outputSize];
        biasGrad = new float[outputSize];
        activationInput = new float[outputSize];
        output = new Neuron[outputSize];

        this.activation = activation;

        for (int i = 0; i < weight.length; i++) {
            output[i] = new Neuron();
            bias[i] = 0.0f;
        }

        dropout = new AlphaDropout(outputSize, builder.dropout);
    }

    public static class Builder {
        private final int input;
        private final int output;
        private float dropout;

        public Builder(int input, int output) {
            this.input = input;
            this.output = output;
        }

        public Builder dropout(float dropout) {
            this.dropout = dropout;
            return this;
        }

        public Layer build(HiddenActivation activation) {
            return new Layer(this, activation);
        }
    }

    public Neuron [] forward(float [] input, boolean training) {

        if(training) dropout.regulate(output);

        if(output.length >= 512 || input.length >= 512) {
            IntStream.range(0, output.length).parallel().forEach(i -> {
                activationInput[i] = bias[i];

                for (int j = 0; j < input.length; j++) {
                    activationInput[i] += weight[i][j] * input[j];
                }

                float value = activation.activate(activationInput[i]);
                output[i].setValue(value);
            });
        }

        else {
            for(int i = 0; i < output.length; i++) {
                activationInput[i] = bias[i];

                for (int j = 0; j < input.length; j++) {
                    activationInput[i] += weight[i][j] * input[j];
                }

                float value = activation.activate(activationInput[i]);
                output[i].setValue(value);
            }
        }

        return output;
    }

    public Neuron [] forward(Neuron [] input, boolean training) {

        if(training) dropout.regulate(output);
        if(output.length >= 512 || input.length >= 512) {
            java.util.stream.IntStream.range(0, output.length).parallel().forEach(i -> {
                activationInput[i] = bias[i];

                for (int j = 0; j < input.length; j++) {
                    activationInput[i] += weight[i][j] * input[j].value;
                }

                float value = activation.activate(activationInput[i]);
                output[i].setValue(value);
            });
        }

        else {
            for (int i = 0; i < output.length; i++) {
                activationInput[i] = bias[i];

                for (int j = 0; j < input.length; j++) {
                    activationInput[i] += weight[i][j] * input[j].value;
                }

                float value = activation.activate(activationInput[i]);
                output[i].setValue(value);
            }
        }

       return output;
    }

    public void backward(Neuron [] input) {
        for(int i = 0; i < output.length; i++) {
            //dropout
            output[i].multiplyGrad(dropout.derive(i));

            float delta = output[i].grad * activation.derive(activationInput[i]);
            float[] weightGradRow = weightGrad[i];

            for(int j = 0; j < input.length; j++) {
                weightGradRow[j] += input[j].value * delta;
                input[j].grad += weight[i][j] * delta;
            }

            biasGrad[i] += delta;
        }
    }

    public void backward(float [] input) {
        for(int i = 0; i < output.length; i++) {
            //dropout
            output[i].multiplyGrad(dropout.derive(i));

            float delta = output[i].grad * activation.derive(activationInput[i]);
            float[] weightGradRow = weightGrad[i];

            for(int j = 0; j < input.length; j++) {
                weightGradRow[j] += input[j] * delta;
            }

            biasGrad[i] += delta;
        }
    }

    public float [] computeInputGradient(float [] input) {
        float [] inputGrad = new float[input.length];

        for(int i = 0; i < output.length; i++) {
            //alphaDropout
            output[i].multiplyGrad(dropout.derive(i));

            float delta = output[i].grad * activation.derive(activationInput[i]);

            for(int j = 0; j < input.length; j++) {
                weightGrad[i][j] += input[j] * delta;

                inputGrad[j] += weight[i][j] * delta;
            }

            biasGrad[i] += delta;
        }

        return inputGrad;
    }

    public float [] getDoubleOutput() {
        float [] result = new float[output.length];

        for(int i = 0; i < result.length; i++) {
            result[i] = output[i].getValue();
        }

        return result;
    }

    public int getOutputSize() { return weight.length; }
    public int getInputSize() { return weight[0].length; }
    public float [][] getWeight() { return weight; }
    public float [][] getWeightGrad() { return weightGrad; }
    public float [] getBias() { return bias; }
    public float [] getBiasGrad() { return biasGrad; }
    public float [] getActivationInput() { return activationInput; }
    public Neuron [] getOutput() { return output; }
    public HiddenActivation getActivation() { return activation; }
}
