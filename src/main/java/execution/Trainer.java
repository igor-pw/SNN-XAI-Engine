package execution;

import java.util.Locale;
import core.Dataset;
import initialization.Initializer;
import io.CsvReader;
import io.DataReader;
import normalization.Normalizer;
import regularization.L2Regularization;
import regularization.LabelSmoothing;
import structure.NeuralNetwork;
import structure.Neuron;

public class Trainer
{
    private float learningRate;
    private final int epoch;
    private final int batch;
    private final LabelSmoothing labelSmoothing;
    private final L2Regularization l2Regularization;
    private Dataset trainingDataset;
    private Dataset testDataset;
    private float testAccuracy = 0.0f;
    private final NeuralNetwork neuralNetwork;

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
        private float learningRate = 0.001f;
        private int epoch = 10;
        private int batch = 32;
        private float smoothing = 0.0f;
        private float decay = 0.0f;

        public Builder(NeuralNetwork neuralNetwork) {
            this.neuralNetwork = neuralNetwork;
        }

        public Builder learningRate(float learningRate) {
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

        public Builder smoothing(float smoothing) {
            this.smoothing = smoothing;
            return this;
        }

        public Builder decay(float decay) {
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
            long startTime = System.nanoTime();
            float loss = 0.0f;

            int epochNumber = i + 1;
            System.out.print("Epoch: " + epochNumber + " | ");
            trainingDataset.shuffle();

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

            float totalLoss = loss/trainingDatasetSize;

            int counter = 0;
            float testLoss = 0.0f;
            for(int j = 0; j < testDatasetSize; j++) {
                float [] target = testDataset.getTarget(j);
                float [] prediction = predict(testDataset.getFeatures(j));

                testLoss += neuralNetwork.calculateLoss(target);

                int predictIndex;

                if (prediction.length == 1) {
                    predictIndex = (prediction[0] >= 0.5) ? 1 : 0;

                    if ((int) target[0] == predictIndex) {
                        counter++;
                    }
                }
                else {
                    predictIndex = argMax(prediction);
                    if(target[predictIndex] == 1.0) {
                        counter++;
                    }
                }
            }

            long endTime = System.nanoTime();

            float epochTime = (endTime - startTime) / 1_000_000_000.0f;
            testAccuracy = (100.0f * counter) / testDatasetSize;
            float valLoss = testLoss / testDatasetSize;

            System.out.printf(Locale.US, "loss: %.5f | val_loss: %.5f | test_acc: %.2f%% | time: %.3fs\n", totalLoss, valLoss, testAccuracy, epochTime);

            learningRate *= 0.98f;
        }
    }

    public float [] predict(float [] input) {
        Neuron[] scalarResult = neuralNetwork.forward(input, false);

        int size = scalarResult.length;

        float [] result = new float[size];

        for(int i = 0; i < size; i++) {
            result[i] = scalarResult[i].getValue();
        }

        return result;
    }

    public int argMax(float [] output) {
        int bestIndex = 0;

        for(int i = 0; i < output.length; i++) {
            if(output[i] > output[bestIndex]) {
                bestIndex = i;
            }
        }

        return bestIndex;
    }

    public NeuralNetwork getNeuralNetwork() { return neuralNetwork; }
    public float getTestAccuracy() { return testAccuracy; }
}
