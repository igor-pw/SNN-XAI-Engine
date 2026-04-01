package structure;

import activation.ActivationFunc;
import activation.SeluActivation;
import initializer.Initializer;
import loss.LossFunc;

public class NeuralNetwork
{
    private Layer[] layer;
    private Scalar[] parameter;
    private final LossFunc loss;
    private double cost = 0.0;

    public NeuralNetwork(int[] structure, LossFunc loss, ActivationFunc outputActivation) {
        int layerNumber = structure.length - 1;
        layer = new Layer[layerNumber];
        this.loss = loss;

        SeluActivation hiddenActivation = new SeluActivation();

        for(int i = 0; i < layerNumber - 1; i++) {
            layer[i] = new Layer(structure[i], structure[i+1], hiddenActivation);
        }

        layer[layerNumber - 1] = new Layer(structure[layerNumber - 1], structure[layerNumber], outputActivation);

    }

    public void initializeWeights(Initializer initializer) {
        initializer.initialize(layer);
    }

    public void forward(Scalar [] input) {
        Scalar [] output = layer[0].forward(input);
        for(int i = 1; i < layer.length; i++) {
            output = layer[i].forward(output);
        }
    }

    public Layer [] getLayer() { return layer; }
}
