package execution;

import activation.OutputActivation;
import core.Dataset;
import initialization.Initializer;
import io.CsvReader;
import io.DataReader;
import loss.AbstractLossFunc;
import normalization.Normalizer;
import regularization.L2Regularization;
import regularization.LabelSmoothing;
import structure.NeuralNetwork;
import structure.Neuron;

public class Trainer
{
    private double learningRate;
    private final int epoch;
    private final int batch;
    private final LabelSmoothing labelSmoothing;
    private final L2Regularization l2Regularization;
    private Dataset trainingDataset;
    private Dataset testDataset;
    private double testAccuracy = 0.0;
    private NeuralNetwork neuralNetwork;

    private Trainer(Builder builder) {
        this.neuralNetwork = builder.neuralNetwork;
        this.learningRate = builder.learningRate;
        this.epoch = builder.epoch;
        this.batch = builder.batch;
        labelSmoothing = new LabelSmoothing(builder.smoothing);
        l2Regularization = new L2Regularization(builder.decay);
    }

    public static class Builder {
        private final NeuralNetwork neuralNetwork;
        private double learningRate = 0.001;
        private int epoch = 10;
        private int batch = 32;
        private double smoothing = 0.0;
        private double decay = 0.0;

        public Builder(NeuralNetwork neuralNetwork) {
            this.neuralNetwork = neuralNetwork;
        }

        public Builder learningRate(double learningRate) {
            this.learningRate = learningRate;
            return this;
        }

        public Builder epoch(int epoch) {
            this.epoch = epoch;
            return this;
        }

        public Builder batch(int batch) {
            this.batch = batch;
            return this;
        }

        public Builder smoothing(double smoothing) {
            this.smoothing = smoothing;
            return this;
        }

        public Builder decay(double decay) {
            this.decay = decay;
            return this;
        }

        public Trainer build() {
            return new Trainer(this);
        }
    }

    public void readTrainingData(String pathName, int skipLines) {
        DataReader reader = new CsvReader();
        trainingDataset = reader.read(pathName, skipLines);
    }

    public void readTestData(String pathName, int skipLines) {
        DataReader reader = new CsvReader();
        testDataset = reader.read(pathName, skipLines);
    }

    public void initNeuralNetwork(Initializer initializer) {
        neuralNetwork.initializeWeights(initializer);
    }

    public void normalizeData(Normalizer normalizer) {
        trainingDataset.normalize(normalizer);
        testDataset.normalize(normalizer);
    }

    public void toOneHotEncoding(int size) {
        trainingDataset.toOneHotEncoding(size);
        testDataset.toOneHotEncoding(size);
    }

    public void fit() {
        int trainingDatasetSize = trainingDataset.getTarget().length;
        int testDatasetSize = testDataset.getTarget().length;
        labelSmoothing.regulate(trainingDataset.getTarget());

        for(int i = 0; i < epoch; i++) {
            double loss = 0.0;

            int epochNumber = i + 1;
            System.out.print("Epoch: " + epochNumber + ", ");
            trainingDataset.shuffle();

            if(epochNumber == 10) {
                learningRate *= 0.5;
            }

            if(epochNumber == 20) {
                learningRate *= 0.5;
            }

            for(int j = 0; j < trainingDatasetSize; j++) {
                neuralNetwork.forward(trainingDataset.getFeatures(j), true);
                neuralNetwork.backward(trainingDataset.getTarget(j), trainingDataset.getFeatures(j));
                loss += neuralNetwork.getCost();

                //Mini-Batch Gradient Descent
                if(((j+1) % batch == 0) || (j == trainingDatasetSize-1)) {
                    l2Regularization.regulate(neuralNetwork.getLayer());
                    neuralNetwork.updateNetwork(learningRate, batch);
                }
            }

            double totalLoss = loss/trainingDatasetSize;
            System.out.print("loss: " + totalLoss + " | ");

            int counter = 0;
            for(int j = 0; j < testDatasetSize; j++) {
                double [] target = testDataset.getTarget(j);
               int predict = argMax(predict(testDataset.getFeatures(j)));
               if(target[predict] == 1.0) {
                   counter++;
               }
            }

            testAccuracy = (100.0*counter)/testDatasetSize;
            System.out.println("test_acc: " + testAccuracy + "%");
        }
    }

    public double [] predict(double [] input) {
        Neuron[] scalarResult = neuralNetwork.forward(input, true);

        int size = scalarResult.length;

        double [] result = new double[size];

        for(int i = 0; i < size; i++) {
            result[i] = scalarResult[i].getValue();
        }

        return result;
    }

    public int argMax(double [] output) {
        int bestIndex = 0;

        for(int i = 0; i < output.length; i++) {
            if(output[i] > output[bestIndex]) {
                bestIndex = i;
            }
        }

        return bestIndex;
    }

    public NeuralNetwork getNeuralNetwork() { return neuralNetwork; }
    public double getTestAccuracy() { return testAccuracy; }
}
