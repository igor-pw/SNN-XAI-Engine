package structure;

import activation.HiddenActivation;
import activation.LinearActivation;
import activation.OutputActivation;
import activation.SeluActivation;
import core.AutoGradEngine;
import core.ComputationalGraph;
import initialization.Initializer;
import loss.AbstractLossFunc;
import optimization.NAdam;
import optimization.Optimizer;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork
{
    private final Layer[] layer;
    private final List<Scalar> parameter = new ArrayList<>();
    private final AbstractLossFunc lossFunc;
    private final OutputActivation outputActivation;
    private final ComputationalGraph computationalGraph = new ComputationalGraph();
    private final Optimizer adamOptimizer;
    private double cost = 0.0;

    public NeuralNetwork(int[] structure, AbstractLossFunc lossFunc, OutputActivation outputActivation) {
        this.outputActivation = outputActivation;
        int layerNumber = structure.length - 1;
        layer = new Layer[layerNumber];
        this.lossFunc = lossFunc;

        HiddenActivation selu = new SeluActivation();
        HiddenActivation linear = new LinearActivation();

        for(int i = 0; i < layerNumber - 1; i++) {
            layer[i] = new Layer(structure[i], structure[i+1], selu);
        }

        layer[layerNumber - 1] = new Layer(structure[layerNumber - 1], structure[layerNumber], linear);

        for(Layer nLayer : layer) {
            Scalar [][] weight = nLayer.getWeight();
            Scalar [] bias = nLayer.getBias();

            for(Scalar [] scalarList : weight) {
                parameter.addAll(List.of(scalarList));
            }

            parameter.addAll(List.of(bias));
        }

        adamOptimizer = new NAdam(parameter.size());
    }

    public void initializeWeights(Initializer initializer) {
        initializer.initialize(layer);
    }

    public void prepareForward(double probability) {
        computationalGraph.buildComputationalGraph(layer, layer[0].getInputSize(), probability);
    }

    public Neuron [] forward(double [] input) {
        computationalGraph.forward(input);
        Neuron [] predicted = layer[layer.length - 1].getOutput();
        outputActivation.activate(predicted);
        return predicted;
    }

    public void backward(double [] target) {
        Neuron [] predicted = layer[layer.length - 1].getOutput();
        cost = lossFunc.compute(predicted, target);
        AutoGradEngine.backward(computationalGraph.getGraph(), computationalGraph.getDropout(), predicted, target, lossFunc, outputActivation);
    }

    public void updateNetwork(double learningRate, int batch) {
        /*for (Scalar scalar : parameter) {
            double oldWeight = scalar.getValue();
            double newWeight = oldWeight - learningRate*scalar.getGrad()/batch;

            scalar.updateValue(newWeight);
        }*/
        adamOptimizer.optimize(parameter, learningRate, batch);
        computationalGraph.clearGraph();
    }

    public Layer [] getLayer() { return layer; }
    public double getCost() { return cost; }

}
