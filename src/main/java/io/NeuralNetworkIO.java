package io;

import java.io.*;
import normalization.Normalizer;
import structure.NeuralNetwork;

public class NeuralNetworkIO
{
    public static class Model {
        private final NeuralNetwork neuralNetwork;
        private final Normalizer normalizer;

        private Model(NeuralNetwork neuralNetwork, Normalizer normalizer)  {
            this.neuralNetwork = neuralNetwork;
            this.normalizer = normalizer;
        }

        public NeuralNetwork getNeuralNetwork() { return neuralNetwork; }
        public Normalizer getNormalizer() { return normalizer; }
    }

    public static void save(NeuralNetwork neuralNetwork, Normalizer normalizer, String filePath) {
        String fullPath = System.getProperty("user.dir") + File.separator + filePath;

        File outputFile = new File(fullPath);
        if (outputFile.getParentFile() != null) {
            outputFile.getParentFile().mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile))) {

            oos.writeObject(neuralNetwork);
            oos.writeObject(normalizer);

            System.out.println("Model exported to: " + filePath);

        } catch (IOException e) {
            System.err.println("Error saving: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Model load(String filePath) {
        String fullPath = System.getProperty("user.dir") + File.separator + filePath;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullPath))) {

            NeuralNetwork neuralNetwork = (NeuralNetwork) ois.readObject();
            Normalizer normalizer = (Normalizer) ois.readObject();

            System.out.println("Model restored from: " + filePath);
            return new Model(neuralNetwork, normalizer);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error writing: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
