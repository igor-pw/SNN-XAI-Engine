package utils;

import activation.HiddenActivation;
import activation.OutputActivation;
import initialization.Initializer;
import initialization.LeCunInitializer;
import loss.AbstractLossFunc;
import loss.MseLoss;
import structure.Layer;
import structure.NeuralNetwork;
import structure.Scalar;

import java.util.Arrays;
import java.util.Random;

public class TestGenerator
{
    private static final long SEED = 337609;
    private static Initializer lecun = new LeCunInitializer(337609);
    private static AbstractLossFunc mse = new MseLoss();
    private static Random random = new Random(SEED);

    public static Layer initDefinedLayer(double [][] weight, double [] bias, HiddenActivation activationFunc) {
        int inputSize = weight[0].length;
        int outputSize = weight.length;
        Layer layer = new Layer(inputSize, outputSize, activationFunc);

        for(int i = 0; i < outputSize; i++) {
            for(int j = 0; j < inputSize; j++) {
                layer.getWeight()[i][j].setValue(weight[i][j]);
            }

            layer.getBias()[i].setValue(bias[i]);
        }

        return layer;
    }

    public static Layer initEqualWeightsLayer(int inputSize, int outputSize, double value, HiddenActivation activationFunc) {
        Layer layer = new Layer(inputSize, outputSize, activationFunc);

        for(int i = 0; i < outputSize; i++) {
            for(int j = 0; j < inputSize; j++) {
                layer.getWeight()[i][j].setValue(value);
            }

            layer.getBias()[i].setValue(value);
        }

        return layer;
    }

    public static Layer initRandomLayer(int inputSize, int outputSize, HiddenActivation activationFunc) {
        Layer layer = new Layer(inputSize, outputSize, activationFunc);
        lecun.initialize(layer);

        return layer;
    }

    public static Scalar [] initDefinedScalarVector(double... value) {
        int size = value.length;
        Scalar [] result = new Scalar[size];

        for(int i = 0; i < size; i++) {
            result[i] = new Scalar(value[i]);
        }

        return result;
    }

    public static Scalar [] initOneValueScalarVector(int size, double value) {
        Scalar [] result = new Scalar[size];

        for(int i = 0; i < size; i++) {
            result[i] = new Scalar(value);
        }

        return result;
    }

    public static Scalar [] initRandomScalarVector(int size, double bound) {
        Scalar [] result = new Scalar[size];
        double value;

        for(int i = 0; i < size; i++) {
            value = random.nextDouble(bound);
            result[i] = new Scalar(value);
        }

        return result;
    }


    /*public static NeuralNetwork initDefinedNeuralNetwork(int [] structure, HiddenActivation activationFunc, double [][] bias, double [][] ... weight) {
        int layerNumber = structure.length;
        NeuralNetwork neuralNetwork = new NeuralNetwork(structure, mse, activationFunc);

        Layer [] layer = neuralNetwork.getLayer();

        for(int i = 0; i < layerNumber; i++) {
            layer[i] = initDefinedLayer(weight[i], bias[i], activationFunc);
        }

        return neuralNetwork;
    }

    public static NeuralNetwork initRandomNeuralNetwork(int [] structure, HiddenActivation activationFunc) {
        NeuralNetwork neuralNetwork = new NeuralNetwork(structure, mse, activationFunc);
        neuralNetwork.initializeWeights(lecun);

        return neuralNetwork;
    }

    public static NeuralNetwork initEqualsWeightsNeuralNetwork(int [] structure, double value, HiddenActivation activationFunc) {
        int layerNumber = structure.length - 1;
        NeuralNetwork neuralNetwork = new NeuralNetwork(structure, mse, activationFunc);

        Layer [] layer = neuralNetwork.getLayer();

        for(int i = 0; i < layerNumber; i++) {
            layer[i] = initEqualWeightsLayer(structure[i], structure[i+1], value, activationFunc);
        }

        return neuralNetwork;
    }*/

    public static double [][] generateRandomMatrix(int rows, int cols) {
        double [][] output = new double[rows][cols];

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                output[i][j] = random.nextDouble();
            }
        }

        return output;
    }

    public static double [] generateRandomVector(int size, double bound) {
        double [] output = new double[size];

        for(int i = 0; i < size; i++) {
            output[i] = random.nextDouble(bound);
        }

        return output;
    }

    public static double [] generateOneValueVector(int size, double value) {
        double [] output = new double[size];

        Arrays.fill(output, value);

        return output;
    }
}
