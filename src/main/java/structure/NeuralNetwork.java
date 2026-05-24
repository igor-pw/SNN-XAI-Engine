package structure;

import activation.HiddenActivation;
import activation.LinearActivation;
import activation.OutputActivation;
import activation.SeluActivation;
import initialization.Initializer;
import loss.AbstractLossFunc;
import optimization.NAdam;
import optimization.Optimizer;
import regularization.AlphaDropout;
import regularization.Regulator;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork
{
    private final Layer[] layer;
    private final AbstractLossFunc lossFunc;
    private final OutputActivation outputActivation;
    private final Optimizer adamOptimizer;
    private double cost = 0.0;

    public NeuralNetwork(int[] structure, AbstractLossFunc lossFunc, OutputActivation outputActivation, double dropout) {
        this.outputActivation = outputActivation;
        int layerNumber = structure.length - 1;
        layer = new Layer[layerNumber];
        this.lossFunc = lossFunc;

        HiddenActivation selu = new SeluActivation();
        HiddenActivation linear = new LinearActivation();

        for(int i = 0; i < layerNumber - 1; i++) {
            layer[i] = new Layer(structure[i], structure[i+1], selu, dropout);
        }

        layer[layerNumber - 1] = new Layer(structure[layerNumber - 1], structure[layerNumber], linear, 0.0);

        int weightSize = 0;
        int biasSize = 0;

        for(int i = 0; i < structure.length - 1; i++) {
            weightSize += structure[i]*structure[i+1];
            biasSize += structure[i+1];
        }

        adamOptimizer = new NAdam(weightSize, biasSize);
    }

    public void initializeWeights(Initializer initializer) {
        initializer.initialize(layer);
    }

    public Neuron [] forward(double [] input, boolean training) {
        Neuron [] predicted = layer[0].forward(input, training);

        for(int i = 1; i < layer.length; i++) {
            predicted = layer[i].forward(predicted, training);
        }

        outputActivation.activate(predicted);
        return predicted;
    }

    public void backward(double [] target, double [] input) {
        Neuron [] predicted = layer[layer.length - 1].getOutput();
        cost = lossFunc.compute(predicted, target);

        prepareGrads(predicted, target);

        for(int i = layer.length - 1; i >= 1; i--) {
            layer[i].backward(layer[i-1].getOutput());
        }

        layer[0].backward(input);
    }

    private void prepareGrads(Neuron [] predicted, double [] target) {
        lossFunc.derive(predicted, target);
        outputActivation.derive(predicted, target);
    }

    public void updateNetwork(double learningRate, int batch) {
        adamOptimizer.optimize(layer, learningRate, batch);
        clearGraph();
    }

    private void clearGraph() {
        for(Layer nLayer : layer) {
            for(Neuron neuron : nLayer.getOutput()) {
                neuron.setValue(0.0);
                neuron.setGrad(0.0);
            }
        }
    }

    public double getCost() { return cost; }
}
