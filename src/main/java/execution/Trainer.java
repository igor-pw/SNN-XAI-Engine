package execution;

import activation.HiddenActivation;
import activation.OutputActivation;
import core.Dataset;
import initialization.Initializer;
import io.CsvReader;
import io.DataReader;
import loss.AbstractLossFunc;
import normalization.Normalizer;
import structure.NeuralNetwork;
import structure.Neuron;
import structure.Scalar;

public class Trainer
{
    private double learningRate = 0.001;
    private int epoch = 10;
    private int batch = 1;
    private Dataset dataset;
    private NeuralNetwork neuralNetwork;

    public Trainer(double learningRate, int epoch) {
        this.learningRate = learningRate;
        this.epoch = epoch;
    }

    public Trainer(double learningRate, int epoch, int batch) {
        this.learningRate = learningRate;
        this.epoch = epoch;
        this.batch = batch;
    }

    public void readData(String pathName, int skipLines) {
        DataReader reader = new CsvReader();

        dataset = reader.read(pathName, skipLines);
    }

    public void initNeuralNetwork(int [] structure, AbstractLossFunc lossFunc, OutputActivation outputActivation, Initializer initializer) {
        neuralNetwork = new NeuralNetwork(structure, lossFunc, outputActivation);
        neuralNetwork.initializeWeights(initializer);
        neuralNetwork.prepareForward();
    }

    public void normalizeData(Normalizer normalizer) {
        dataset.normalize(normalizer);
    }

    public void toOneHotEncoding(int size) {
        dataset.toOneHotEncoding(size);
    }

    public void fit() {
        int datasetSize = dataset.getTarget().length;

        for(int i = 0; i < epoch; i++) {
            int epochNumber = i + 1;
            System.out.print("Epoch: " + epochNumber + ", ");
            dataset.shuffle();

            for(int j = 0; j < datasetSize; j++) {
                neuralNetwork.forward(dataset.getFeatures(j));
                neuralNetwork.backward(dataset.getTarget(j));
                //Mini-Batch Gradient Descent
                if(((j+1) % batch == 0) || (j == datasetSize-1)) {
                    neuralNetwork.updateNetwork(learningRate, batch);
                }
            }

            System.out.println("loss: " + neuralNetwork.getCost());
        }
    }

    public double [] predict(double [] input) {
        Neuron[] scalarResult = neuralNetwork.forward(input);

        int size = scalarResult.length;

        double [] result = new double[size];

        for(int i = 0; i < size; i++) {
            result[i] = scalarResult[i].getValue();
        }

        return result;
    }
}
