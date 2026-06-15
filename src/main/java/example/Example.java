package example;

import activation.SoftmaxActivation;
import data.Dataset;
import execution.Explainer;
import execution.Trainer;
import initialization.Initializer;
import initialization.LeCunInitializer;
import io.CsvReader;
import io.DataReader;
import io.HeatmapGenerator;
import io.NeuralNetworkIO;
import loss.CceLoss;
import normalization.Normalizer;
import normalization.ZScoreNormalizer;
import optimization.clipping.GradientNormClipping;
import optimization.optimizer.NAdam;
import optimization.scheduler.StepLR;
import optimization.stopping.EarlyStopping;
import regularization.smoothing.LabelSmoothing;
import regularization.weight.L2Regularizer;
import structure.Layer;
import structure.NeuralNetwork;

public class Example
{
    public static void main(String [] args)
    {
        //number of classes (MNIST example)
        int oneHotSize = 10;

        //path to training and validation data
        String trainingPathName = "path/to/training/data";
        String validationPathName = "path/to/validation/data";

        //parse data
        DataReader dataReader = new CsvReader();
        Dataset trainingData = dataReader.read(trainingPathName, 1);    //skipping first line
        Dataset validationData = dataReader.read(validationPathName,1);

        //transforming targets to one-hot encoding
        trainingData.toOneHotEncoding(oneHotSize);
        validationData.toOneHotEncoding(oneHotSize);

        //weight and bias initializer with a fixed seed
        Initializer lecun = new LeCunInitializer(42);

        //data normalizer
        Normalizer zScore = new ZScoreNormalizer();

        //data normalization
        zScore.normalize(trainingData);
        zScore.transform(validationData);

        //neural network set-up
        NeuralNetwork neuralNetwork = new NeuralNetwork.Builder()
                .addLayer(new Layer.Builder(784, 128).dropout(0.15))
                .addLayer(new Layer.Builder(128, 10))
                .outputActivation(new SoftmaxActivation())
                .lossFunction(new CceLoss())
                .optimizer(new NAdam.Builder()
                        .epsilon(1e-7f)
                        .build())
                .gradientClipping(new GradientNormClipping(10.0))
                .build();

        //trainer set-up
        Trainer trainer = new Trainer.Builder(neuralNetwork)
                .scheduler(new StepLR.Builder()
                        .gamma(0.95)
                        .epochStep(4)
                        .build())
                .earlyStopping(new EarlyStopping(3))
                .learningRate(0.0008)
                .epoch(20)
                .batch(64)
                .smoothing(new LabelSmoothing(0.03))
                .weightRegularizer(new L2Regularizer(1e-3))
                .build();

        //loading normalized data into trainer
        trainer.loadData(trainingData, validationData);

        //neural network initialization
        trainer.initNeuralNetwork(lecun);

        //training process
        trainer.fit();

        //restoring weights and biases of the best model
        trainer.restoreBestModel();

        //saving model
        NeuralNetworkIO.save(trainer.getNeuralNetwork(), zScore, "saved/model/path");

        //loading saved model
        NeuralNetworkIO.Model model = NeuralNetworkIO.load("path/to/saved/model");

        //extracting neural network and normalizer
        NeuralNetwork savedNeuralNetwork = model.getNeuralNetwork();

        //required to apply the exact same normalization to new/inference data
        Normalizer normalizer = model.getNormalizer();

        //input size of the neural network (28*28 for MNIST)
        int inputSize = 28*28;

        Explainer explainer = new Explainer.Builder()
                .alpha(2.0)
                .beta(1.0)
                .build(savedNeuralNetwork, inputSize);

        //extracting features and target of the first example
        float [] input = validationData.getFeatures(0);
        float [] target = validationData.getTarget(0);

        //computing feature importance / relevance for the given input
        float [] relevance = explainer.performLayerwiseRelevancePropagation(input);
        float [] attribution = explainer.performLayerwiseGradientFeatureAttribution(target, input);

        //raw data is required to generate the heatmap panel
        Dataset rawValidationData = dataReader.read("path/to/raw/validation/data", 1);
        float [] rawInput = rawValidationData.getFeatures(0);

        //width and height of image (MNIST example)
        int width = 28;
        int height = 28;

        //generating heatmaps (recommended for image data)
        HeatmapGenerator.saveHeatmapPanel(rawInput, relevance, width, height, "saved/relevance/heatmap/path");
        HeatmapGenerator.saveHeatmapPanel(rawInput, attribution, width, height, "saved/attribution/heatmap/path");
    }
}
