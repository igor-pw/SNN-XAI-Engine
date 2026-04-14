package execution;

import activation.ActivationFunc;
import core.Dataset;
import initialization.Initializer;
import io.CsvReader;
import io.DataReader;
import loss.AbstractLossFunc;
import normalization.Normalizer;
import structure.NeuralNetwork;
import structure.Scalar;

import java.util.function.IntPredicate;

public class Trainer
{
    private double learningRate = 0.001;
    private int epoch = 10;
    private Dataset dataset;
    private NeuralNetwork neuralNetwork;

    public Trainer(double learningRate, int epoch) {
        this.learningRate = learningRate;
        this.epoch = epoch;
    }

    public void readData(String pathName, int skipLines) {
        DataReader reader = new CsvReader();

        dataset = reader.read(pathName, skipLines);
    }

    public void initNeuralNetwork(int [] structure, AbstractLossFunc lossFunc, ActivationFunc outputActivation, Initializer initializer) {
        neuralNetwork = new NeuralNetwork(structure, lossFunc, outputActivation);
        neuralNetwork.initializeWeights(initializer);
    }

    public void normalizeData(Normalizer normalizer) {
        dataset.normalize(normalizer);
    }

    public void fit() {
        int datasetSize = dataset.getTarget().length;

        for(int i = 0; i < epoch; i++) {
            //System.out.println("Epoch: " + i);
            //dataset.shuffle();

            for(int j = 0; j < datasetSize; j++) {
                neuralNetwork.forward(Scalar.toScalarArray(dataset.getFeatures()[j]));
                neuralNetwork.backward(new double[]{dataset.getTarget()[j]});
                neuralNetwork.updateNetwork(learningRate);
                neuralNetwork.clearNetwork();
            }
        }
    }

    public double [] predict(double [] input) {
        Scalar [] scalarResult = neuralNetwork.forward(Scalar.toScalarArray(input));
        int size = scalarResult.length;

        double [] result = new double[size];

        for(int i = 0; i < size; i++) {
            result[i] = scalarResult[i].getValue();
        }

        return result;
    }
}
