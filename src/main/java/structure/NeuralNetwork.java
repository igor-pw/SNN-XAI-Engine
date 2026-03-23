package structure;

import activation.ActivationFunc;
import activation.SeluActivation;
import initializer.Initializer;
import loss.LossFunc;
import core.ScalarPool;

public class NeuralNetwork
{
    private Layer[] layer;
    private Scalar[] parameter;
    private final LossFunc loss;
    private double cost = 0.0;
    private final ScalarPool scalarPool;

    public NeuralNetwork(ScalarPool scalarPool, int[] structure, LossFunc loss, ActivationFunc outputActivation) {
        this.scalarPool = scalarPool;
        layer = new Layer[structure.length];
        this.loss = loss;

        SeluActivation hiddenActivation = new SeluActivation();

        for(int i = 0; i < structure.length-1; i++) {
            layer[i] = new Layer(structure[i], structure[i+1], scalarPool, parameter, hiddenActivation);
        }
        layer[layer.length-1] = new Layer(structure[structure.length-1], 0, scalarPool, parameter, outputActivation);
    }

    public void initializeWeights(Initializer initializer) {
        initializer.initialize(layer);
    }
}
