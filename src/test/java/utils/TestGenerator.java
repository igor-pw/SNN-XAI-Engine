package utils;

import activation.HiddenActivation;
import initialization.Initializer;
import initialization.LeCunInitializer;
import loss.AbstractLossFunc;
import loss.MseLoss;
import structure.Layer;
import structure.Neuron;

import java.util.Arrays;
import java.util.Random;

public class TestGenerator
{
    private static final long SEED = 337609;
    private final static Initializer lecun = new LeCunInitializer(337609);
    private final static AbstractLossFunc mse = new MseLoss();
    private final static Random random = new Random(SEED);

    //new / refactored
    public static Neuron [] initRandomNeuronVector(int size, float bound) {
        Neuron [] result = new Neuron[size];
        float value;

        for(int i = 0; i < size; i++) {
            value = random.nextFloat(bound);
            result[i] = new Neuron();
            result[i].setValue(value);

        }

        return result;
    }

    public static Neuron [] initDefinedNeuronVector(float... value) {
        int size = value.length;
        Neuron [] result = new Neuron[size];

        for(int i = 0; i < size; i++) {
            result[i] = new Neuron();
            result[i].setValue(value[i]);
        }

        return result;
    }

    public static Neuron [] initOneValueNeuronVector(int size, float value) {
        Neuron [] result = new Neuron[size];

        for(int i = 0; i < size; i++) {
            result[i] = new Neuron();
            result[i].setValue(value);
        }

        return result;
    }

    public static Layer initDefinedLayer(float [][] weight, float [] bias, HiddenActivation activationFunc) {
        int inputSize = weight[0].length;
        int outputSize = weight.length;
        Layer layer = new Layer.Builder(inputSize, outputSize).build(activationFunc);

        for(int i = 0; i < outputSize; i++) {
            for(int j = 0; j < inputSize; j++) {
                layer.getWeight()[i][j] = weight[i][j];
            }

            layer.getBias()[i] = bias[i];
        }

        return layer;
    }

    public static Layer initEqualWeightsLayer(int inputSize, int outputSize, float value, HiddenActivation activationFunc) {
        Layer layer = new Layer.Builder(inputSize, outputSize).build(activationFunc);

        for(int i = 0; i < outputSize; i++) {
            for(int j = 0; j < inputSize; j++) {
                layer.getWeight()[i][j] = value;
            }

            layer.getBias()[i] = value;
        }

        return layer;
    }

    public static Layer initRandomLayer(int inputSize, int outputSize, HiddenActivation activationFunc) {
        Layer layer = new Layer.Builder(inputSize, outputSize).build(activationFunc);
        lecun.initialize(layer);

        return layer;
    }

    public static float [][] generateRandomMatrix(int rows, int cols) {
        float [][] output = new float[rows][cols];

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                output[i][j] = random.nextFloat();
            }
        }

        return output;
    }

    public static float [] generateRandomVector(int size, float bound) {
        float [] output = new float[size];

        for(int i = 0; i < size; i++) {
            output[i] = random.nextFloat(bound);
        }

        return output;
    }

    public static float [] generateOneValueVector(int size, float value) {
        float [] output = new float[size];

        Arrays.fill(output, value);

        return output;
    }
}
