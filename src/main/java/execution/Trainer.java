package execution;

import java.util.Locale;
import data.Dataset;
import data.ModelTracker;
import initialization.Initializer;
import optimization.scheduler.NoOpScheduler;
import optimization.scheduler.Scheduler;
import optimization.stopping.NoOpStopping;
import optimization.stopping.Stopping;
import regularization.smoothing.NoOpSmoothing;
import regularization.smoothing.Smoothing;
import regularization.weight.NoOpWeightRegularizer;
import regularization.weight.WeightRegularizer;
import structure.NeuralNetwork;
import structure.Neuron;

public class Trainer
{
    private float learningRate;
    private final int epoch;
    private final int batch;
    private final Smoothing smoothing;
    private final WeightRegularizer weightRegularizer;
    private final Stopping earlyStopping;
    private final Scheduler scheduler;
    private Dataset trainingDataset;
    private Dataset validationDataset;
    private float accuracy = 0.0f;
    private final NeuralNetwork neuralNetwork;
    private final ModelTracker tracker;

    private Trainer(Builder builder) {
        this.neuralNetwork = builder.neuralNetwork;
        this.learningRate = (float)builder.learningRate;
        this.epoch = builder.epoch;
        this.batch = builder.batch;
        this.smoothing = builder.smoothing;
        this.weightRegularizer = builder.weightRegularizer;
        this.earlyStopping = builder.earlyStopping;
        this.scheduler = builder.scheduler;
        this.tracker = new ModelTracker(neuralNetwork.getLayer());
    }

    public static class Builder {
        private final NeuralNetwork neuralNetwork;
        private Smoothing smoothing = new NoOpSmoothing();
        private Scheduler scheduler = new NoOpScheduler();
        private WeightRegularizer weightRegularizer = new NoOpWeightRegularizer();
        private Stopping earlyStopping = new NoOpStopping();
        private double learningRate = 0.001;
        private int epoch = 10;
        private int batch = 32;

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

        public Builder smoothing(Smoothing smoothing) {
            this.smoothing = smoothing;
            return this;
        }

        public Builder weightRegularizer(WeightRegularizer weightRegularizer) {
            this.weightRegularizer = weightRegularizer;
            return this;
        }

        public Builder earlyStopping(Stopping earlyStopping) {
            this.earlyStopping = earlyStopping;
            return this;
        }

        public Builder scheduler(Scheduler scheduler) {
            this.scheduler = scheduler;
            return this;
        }

        public Trainer build() {
            return new Trainer(this);
        }
    }

    public void loadData(Dataset trainingDataset, Dataset validationDataset) {
        this.trainingDataset = trainingDataset;
        this.validationDataset = validationDataset;
    }

    public void initNeuralNetwork(Initializer initializer) {
        neuralNetwork.initializeWeights(initializer);
    }

    public void fit() {
        int trainingDatasetSize = trainingDataset.getTarget().length;
        smoothing.regulate(trainingDataset.getTarget());

        System.out.println("");

        for(int i = 0; i < epoch; i++) {
            long startTime = System.nanoTime();
            float loss = 0.0f;

            int epochNumber = i + 1;
            trainingDataset.shuffle();

            for(int j = 0; j < trainingDatasetSize; j++) {
                neuralNetwork.forward(trainingDataset.getFeatures(j), true);
                neuralNetwork.backward(trainingDataset.getTarget(j), trainingDataset.getFeatures(j));
                loss += neuralNetwork.getCost();

                //Mini-Batch Gradient Descent
                if(((j+1) % batch == 0) || (j == trainingDatasetSize-1)) {
                    weightRegularizer.regulate(neuralNetwork.getLayer());
                    neuralNetwork.updateNetwork(learningRate, batch);
                }
            }

            float trainingLoss = loss/trainingDatasetSize;

            float valLoss = validate();
            long endTime = System.nanoTime();

            float epochTime = (endTime - startTime) / 1_000_000_000.0f;

            System.out.printf(Locale.US, "Epoch: %d | loss: %.5f | val_loss: %.5f | val_acc: %.2f%% | time: %.3fs\n", epochNumber, trainingLoss, valLoss, accuracy, epochTime);

            if(earlyStopping.shouldStop(valLoss)) {
                System.out.println("Training stopped");
                break;
            }

            learningRate = scheduler.step(learningRate, epochNumber);
            tracker.track(neuralNetwork, valLoss, accuracy);
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

    public float validate() {
        int validationDatasetSize = validationDataset.getTarget().length;

        float loss = 0.0f;
        int counter = 0;

        for(int i = 0; i < validationDatasetSize; i++) {
            float [] target = validationDataset.getTarget(i);
            float [] prediction = predict(validationDataset.getFeatures(i));

            loss += neuralNetwork.calculateLoss(target);

            int predictIndex;
            int targetClass;

            if (prediction.length == 1) {
                predictIndex = (prediction[0] >= 0.5) ? 1 : 0;

                if ((int) target[0] == predictIndex) {
                    counter++;
                }
            }
            else {
                predictIndex = argMax(prediction);
                targetClass = argMax(target);
                if(targetClass == predictIndex) {
                    counter++;
                }
            }

            accuracy = (100.0f * counter) / validationDatasetSize;
        }

        return loss / validationDatasetSize;
    }

    public void restoreBestModel() {
        tracker.loadBestModel(neuralNetwork);
        accuracy = tracker.getValAcc();
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
    public float getValAccuracy() { return accuracy; }
}
