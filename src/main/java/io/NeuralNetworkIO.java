package io;

import java.io.*;
import normalization.Normalizer;
import structure.NeuralNetwork;

public class NeuralNetworkIO
{
    public static void save(NeuralNetwork neuralNetwork, Normalizer normalizer, String filePath) {
        String fullPath = System.getProperty("user.dir") + File.separator + filePath;

        File outputFile = new File(fullPath);
        if (outputFile.getParentFile() != null) {
            outputFile.getParentFile().mkdirs(); // Tworzy np. src/test/resources jeśli ich nie ma
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile))) {

            oos.writeObject(neuralNetwork);
            oos.writeObject(normalizer);

        } catch (IOException e) {
            System.err.println("[IO] Błąd podczas zapisu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Object[] load(String filePath) {
        String fullPath = System.getProperty("user.dir") + File.separator + filePath;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullPath))) {

            NeuralNetwork neuralNetwork = (NeuralNetwork) ois.readObject();
            Normalizer normalizer = (Normalizer) ois.readObject();

            return new Object[] { neuralNetwork, normalizer };

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("podczas wczytywania: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
