package io;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import core.Dataset;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class CsvReader implements DataReader
{
    @Override
    public Dataset read(String filePath, int skipLines) {
        CSVReader reader = initCSVReader(filePath, skipLines);

        List<String[]> line;

        try {
            line = reader.readAll();
        } catch (CsvException | IOException e) {
            throw new RuntimeException("Failed to read data stream from: " + filePath);
        }

        assert line != null;
        double [][] features = new double[line.size()][line.get(0).length - 1];
        double [] target = new double[line.size()];

        for(int i = 0; i < line.size(); i++) {
            for(int j = 0; j < line.get(i).length - 1; j++) {
                features[i][j] = Double.parseDouble(line.get(i)[j]);
            }

            target[i] = Double.parseDouble(line.get(i)[line.get(i).length - 1]);
        }

        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close file: " + filePath);
        }
        return new Dataset(features, target);
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
