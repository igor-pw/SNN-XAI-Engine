package core;

import activation.HiddenActivation;
import structure.Layer;
import structure.Neuron;
import structure.Scalar;

public class ComputationalGraph
{
    private Neuron [] computationalGraph;
    private Neuron [] graphInput;

    public void buildComputationalGraph(Layer[] layer, int size)
    {
        int graphSize = 0;
        for(Layer nLayer : layer) {
            graphSize += nLayer.getOutputSize();
        }

        computationalGraph = new Neuron[graphSize];
        graphInput = new Neuron[size];

        Neuron [] input = new Neuron[size];

        for(int i = 0; i < size; i++) {
            input[i] = new Neuron();
        }

        System.arraycopy(input, 0, graphInput, 0, size);

        int counter = 0;

        for(Layer nLayer : layer) {
            Scalar [][] weight = nLayer.getWeight();
            Scalar [] bias = nLayer.getBias();
            Neuron [] output = nLayer.getOutput();
            HiddenActivation activationFunc = nLayer.getActivation();

            int outputSize = output.length;

            for(int i = 0; i < outputSize; i++) {
                output[i] = new Neuron(weight[i], input, bias[i], activationFunc);
                computationalGraph[counter++] = output[i];
            }

            input = new Neuron[outputSize];

            for(int i = 0; i < outputSize; i++) {
                input[i] = computationalGraph[counter - outputSize + i];
            }
        }
    }

    public void forward(double [] input) {
        for(int i = 0; i < input.length; i++) {
            graphInput[i].setValue(input[i]);
        }

        for(Neuron neuron : computationalGraph) {
            neuron.forward();
        }
    }

    public void clearGraph() {
        for(Neuron neuron : computationalGraph) {
            neuron.setValue(0.0);
            neuron.setGrad(0.0);
        }
    }

    public int getSize() { return computationalGraph.length; }
    public Neuron [] getGraph() { return computationalGraph; }
}
