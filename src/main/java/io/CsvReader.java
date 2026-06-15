package io;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import data.Dataset;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader implements DataReader
{
    @Override
    public Dataset read(String filePath, int skipLines) {
        CSVReader reader = initCSVReader(filePath, skipLines);

        List<float[]> featuresList = new ArrayList<>();
        List<float[]> targetList = new ArrayList<>();

        try {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                float[] rowFeatures = new float[nextLine.length - 1];

                for (int j = 0; j < nextLine.length - 1; j++) {
                    rowFeatures[j] = Float.parseFloat(nextLine[j]);
                }

                float[] rowTarget = { Float.parseFloat(nextLine[nextLine.length - 1]) };

                featuresList.add(rowFeatures);
                targetList.add(rowTarget);

            }
            reader.close();
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException("Error reading line: " + e.getMessage());
        }

        return new Dataset(
                featuresList.toArray(new float[0][]),
                targetList.toArray(new float[0][])
        );
    }

    private CSVReader initCSVReader(String filePath, int skipLines) {
        try {
            CSVReaderBuilder builder = new CSVReaderBuilder(new java.io.FileReader(filePath));
            builder.withSkipLines(skipLines);
            return builder.build();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to open file: " + filePath);
        }
    }
}
