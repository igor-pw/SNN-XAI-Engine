package structure;

import activation.*;
import initialization.Initializer;
import loss.AbstractLossFunc;
import loss.MseLoss;
import optimization.GradientNormClipping;
import optimization.NAdam;
import optimization.Optimizer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork implements Serializable
{
    private final Layer[] layer;
    private final AbstractLossFunc lossFunc;
    private final OutputActivation outputActivation;
    private final Optimizer adamOptimizer;
    private final GradientNormClipping gradientNormClipping;
    private double cost = 0.0;

    private NeuralNetwork(Builder builder) {
        int layerSize = builder.layerBuilder.size();
        this.layer = new Layer[layerSize];

        int weightSize = 0;
        int biasSize = 0;

        for(int i = 0; i < layerSize; i++) {
            Layer.Builder layerBuilder = builder.layerBuilder.get(i);

            if(i == layerSize - 1) {
                this.layer[i] = layerBuilder.build(new LinearActivation());
            }
            else {
                this.layer[i] = layerBuilder.build(new SeluActivation());
            }

            weightSize += layer[i].getInputSize() * layer[i].getOutputSize();
            biasSize += layer[i].getOutputSize();
        }

        this.lossFunc = builder.lossFunc;
        this.outputActivation = builder.outputActivation;
        this.adamOptimizer = builder.adamOptimizer;
        this.adamOptimizer.init(weightSize, biasSize);
        this.gradientNormClipping = builder.gradientNormClipping;
    }

    public static class Builder {
        private final List<Layer.Builder> layerBuilder = new ArrayList<>();
        private AbstractLossFunc lossFunc = new MseLoss();
        private OutputActivation outputActivation = new ReluActivation();
        private Optimizer adamOptimizer = new NAdam.Builder().build();
        private GradientNormClipping gradientNormClipping = new GradientNormClipping(1.0);

        public Builder addLayer(Layer.Builder layerBuilder) {
            this.layerBuilder.add(layerBuilder);
            return this;
        }

        public Builder lossFunction(AbstractLossFunc lossFunc) {
            this.lossFunc = lossFunc;
            return this;
        }

        public Builder outputActivation(OutputActivation outputActivation) {
            this.outputActivation = outputActivation;
            return this;
        }

        public Builder optimizer(Optimizer optimizer) {
            this.adamOptimizer = optimizer;
            return this;
        }

        public Builder gradientClipping(GradientNormClipping gradientNormClipping) {
            this.gradientNormClipping = gradientNormClipping;
            return this;
        }

        public NeuralNetwork build() {
            return new NeuralNetwork(this);
        }
    }

    /*public NeuralNetwork(int[] structure, AbstractLossFunc lossFunc, OutputActivation outputActivation, double dropout, double maxNorm) {
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
        gradientNormClipping = new GradientNormClipping(maxNorm);
    }*/

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

    public void backwardWithTargetGrad(double [] target, double [] input) {
        Neuron [] predicted = layer[layer.length - 1].getOutput();

        for(int i = 0; i < predicted.length; i++) {
            predicted[i].grad = target[i];
        }

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
        gradientNormClipping.optimize(layer);
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

    public Layer [] getLayer() { return layer; }
    public Layer getLayer(int i)  { return layer[i]; }
    public double getCost() { return cost; }
}
