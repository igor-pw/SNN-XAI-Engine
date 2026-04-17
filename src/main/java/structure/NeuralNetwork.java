package structure;

import activation.ActivationFunc;
import activation.SeluActivation;
import core.AutoGradEngine;
import initialization.Initializer;
import loss.AbstractLossFunc;
import loss.LossFunc;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork
{
    private final Layer[] layer;
    private final List<Scalar> parameter = new ArrayList<>();
    private final AbstractLossFunc lossFunc;
    private double cost = 0.0;

    public NeuralNetwork(int[] structure, AbstractLossFunc lossFunc, ActivationFunc outputActivation) {
        int layerNumber = structure.length - 1;
        layer = new Layer[layerNumber];
        this.lossFunc = lossFunc;

        SeluActivation hiddenActivation = new SeluActivation();

        for(int i = 0; i < layerNumber - 1; i++) {
            layer[i] = new Layer(structure[i], structure[i+1], hiddenActivation);
        }

        layer[layerNumber - 1] = new Layer(structure[layerNumber - 1], structure[layerNumber], outputActivation);

        for(Layer nLayer : layer) {
            Scalar [][] weight = nLayer.getWeight();
            Scalar [] bias = nLayer.getBias();

            for(Scalar [] scalarList : weight) {
                parameter.addAll(List.of(scalarList));
            }

            parameter.addAll(List.of(bias));
        }
    }

    public void initializeWeights(Initializer initializer) {
        initializer.initialize(layer);
    }

    public Scalar [] forward(Scalar [] input) {
        Scalar [] output = layer[0].forward(input);
        for(int i = 1; i < layer.length; i++) {
            output = layer[i].forward(output);
        }

        return output;
    }

    public void backward(double [] target) {
        Scalar [] predicted = layer[layer.length - 1].getOutput();
        cost = lossFunc.compute(predicted, target);
        AutoGradEngine.backward(predicted, target, lossFunc);
     }

    public void updateNetwork(double learningRate) {
        for (Scalar scalar : parameter) {
            double oldWeight = scalar.getValue();
            double newWeight = oldWeight - scalar.getGrad() * learningRate;

            scalar.setValue(newWeight);
        }
    }

    public void clearNetwork() {
        for(Scalar scalar : parameter) {
            scalar.setGrad(0.0);
            scalar.setParent(null);
        }

        for(Layer nLayer : layer) {
            Scalar [] output = nLayer.getOutput();

            for(Scalar scalar : output) {
                scalar.setParent(null);
            }
        }
    }

    public Layer [] getLayer() { return layer; }
    public double getCost() { return cost; }

}
