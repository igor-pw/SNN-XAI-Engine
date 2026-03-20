import activation.ActivationFunc;
import activation.SigmoidActivation;
import core.Pool;
import initializer.Initializer;
import initializer.LeCunInitializer;
import loss.LossFunc;
import loss.mseLoss;
import structure.NeuralNetwork;

public class Main {

    public static void main(String [] args) {
        Pool scalarPool = new Pool(512);
        LossFunc lossFunc = new mseLoss();
        ActivationFunc outputActivation = new SigmoidActivation();
        Initializer initializer = new LeCunInitializer();

        int [] structure = {4, 8, 4, 2};

        NeuralNetwork neuralNetwork = new NeuralNetwork(scalarPool, structure, lossFunc, outputActivation);
        System.out.println(scalarPool.getPointer());

        neuralNetwork.initializeWeights(initializer);
    }
}
