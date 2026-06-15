package data;

import structure.Layer;
import structure.NeuralNetwork;

public class ModelTracker
{
    private final float [][] bestWeight;
    private final float [][] bestBias;
    private float bestValLoss = Float.MAX_VALUE;
    private float valAcc = 0.0f;

    public ModelTracker(Layer [] layer) {
        bestWeight = new float[layer.length][];
        bestBias = new float[layer.length][];

        for(int i = 0; i < layer.length; i++) {
            int outputSize = layer[i].getOutputSize();
            int inputSize = layer[i].getInputSize();

            bestBias[i] = new float[outputSize];
            bestWeight[i] = new float[outputSize*inputSize];
        }
    }

    public void track(NeuralNetwork neuralNetwork, float valLoss, float valAcc) {
        if(valLoss < bestValLoss) {
            bestValLoss = valLoss;
            this.valAcc = valAcc;
            saveModel(neuralNetwork);
        }
    }

    private void saveModel(NeuralNetwork neuralNetwork) {
        for(int i = 0; i < bestBias.length; i++) {
            Layer layer = neuralNetwork.getLayer(i);
            float [][] weight = layer.getWeight();
            float [] bias = layer.getBias();

            System.arraycopy(bias, 0, bestBias[i], 0, bias.length);

            int inputSize = layer.getInputSize();
            for(int j = 0; j < layer.getOutputSize(); j++) {
                System.arraycopy(weight[j], 0, bestWeight[i], j*inputSize, inputSize);
            }
        }
    }

    public void loadBestModel(NeuralNetwork neuralNetwork) {
        for(int i = 0; i < bestBias.length; i++) {
            Layer layer = neuralNetwork.getLayer(i);
            float [][] weight = layer.getWeight();
            float [] bias = layer.getBias();

            System.arraycopy(bestBias[i], 0, bias, 0, bias.length);

            int inputSize = layer.getInputSize();
            for(int j = 0; j < layer.getOutputSize(); j++) {
                System.arraycopy(bestWeight[i], j*inputSize, weight[j], 0, inputSize);
            }
        }
    }

    public float getValAcc() { return valAcc; }
}
