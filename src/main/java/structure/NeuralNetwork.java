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
    private float cost = 0.0f;

    private NeuralNetwork(Builder builder) {
        int layerSize = builder.layerBuilder.size();
        this.layer = new Layer[layerSize];

        for(int i = 0; i < layerSize; i++) {
            Layer.Builder layerBuilder = builder.layerBuilder.get(i);

            if(i == layerSize - 1) {
                this.layer[i] = layerBuilder.build(new LinearActivation());
            }
            else {
                this.layer[i] = layerBuilder.build(new SeluActivation());
            }
        }

        this.lossFunc = builder.lossFunc;
        this.outputActivation = builder.outputActivation;
        this.adamOptimizer = builder.adamOptimizer;
        this.adamOptimizer.init(layer);
        this.gradientNormClipping = builder.gradientNormClipping;
    }

    public static class Builder {
        private final List<Layer.Builder> layerBuilder = new ArrayList<>();
        private AbstractLossFunc lossFunc = new MseLoss();
        private OutputActivation outputActivation = new ReluActivation();
        private Optimizer adamOptimizer = new NAdam.Builder().build();
        private GradientNormClipping gradientNormClipping = new GradientNormClipping(1.0f);

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

    public void initializeWeights(Initializer initializer) {
        initializer.initialize(layer);
    }

    public Neuron [] forward(float [] input, boolean training) {
        Neuron [] predicted = layer[0].forward(input, training);

        for(int i = 1; i < layer.length; i++) {
            predicted = layer[i].forward(predicted, training);
        }

        outputActivation.activate(predicted);
        return predicted;
    }

    public void backward(float [] target, float [] input) {
        Neuron [] predicted = layer[layer.length - 1].getOutput();
        cost = lossFunc.compute(predicted, target);

        prepareGrads(predicted, target);

        for(int i = layer.length - 1; i >= 1; i--) {
            layer[i].backward(layer[i-1].getOutput());
        }

        layer[0].backward(input);
    }

    public void backwardWithTargetGrad(float [] target, float [] input) {
        Neuron [] predicted = layer[layer.length - 1].getOutput();

        for(int i = 0; i < predicted.length; i++) {
            predicted[i].grad = target[i];
        }

        for(int i = layer.length - 1; i >= 1; i--) {
            layer[i].backward(layer[i-1].getOutput());
        }

        layer[0].backward(input);
    }

    private void prepareGrads(Neuron [] predicted, float [] target) {
        lossFunc.derive(predicted, target);
        outputActivation.derive(predicted, target);
    }

    public void updateNetwork(float learningRate, int batch) {
        gradientNormClipping.optimize(layer);
        adamOptimizer.optimize(layer, learningRate, batch);
        clearGraph();
    }

    private void clearGraph() {
        for(Layer nLayer : layer) {
            for(Neuron neuron : nLayer.getOutput()) {
                neuron.setValue(0.0f);
                neuron.setGrad(0.0f);
            }
        }
    }

    public float calculateLoss(float [] target) {
        Neuron [] predicted = layer[layer.length - 1].getOutput();
        return lossFunc.compute(predicted, target);
    }

    public Layer [] getLayer() { return layer; }
    public Layer getLayer(int i)  { return layer[i]; }
    public float getCost() { return cost; }
}
