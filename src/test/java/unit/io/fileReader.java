package unit.io;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class fileReader
{
    private CSVReader reader;
    private double [][] data;
    private double [] target;

    public void readCSV(String file_name) {
        try {
            CSVReaderBuilder builder = new CSVReaderBuilder(new FileReader(file_name));
            builder.withSkipLines(1);
            reader = builder.build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String[]> line = null;

        try {
            line = reader.readAll();
        } catch (CsvException | IOException e) {
            e.printStackTrace();
        }

        assert line != null;
        data = new double[line.size()][line.get(0).length - 1];
        target = new double[line.size()];

        for(int i = 0; i < line.size(); i++) {
            for(int j = 0; j < line.get(i).length - 1; j++) {
                data[i][j] = Double.parseDouble(line.get(i)[j]);
            }

            target[i] = Double.parseDouble(line.get(i)[line.get(i).length - 1]);
        }
    }

    public double [][] getData() { return data; }
    public double [] getTarget() { return target; }
}
