package io;

import core.Dataset;
import io.CsvReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CsvReaderTest
{
    private CsvReader reader;

    @BeforeEach
    public void setUp() {
        reader = new CsvReader();
    }

    @Test
    public void shouldStoreAnyData_whenReadCVSIsUsed() {
        //given
        String fileName = "src/test/resources/data.csv";
        int skipLines = 1;

        //when
        Dataset dataset = reader.read(fileName, skipLines);
        double [][] data = dataset.getFeatures();
        double [][] target = dataset.getTarget();

        //then
        assertNotEquals(null, data);
        assertNotEquals(null, data);
        assertTrue(data.length > 0);
        assertTrue(target[0].length > 0);
    }

    @Test
    public void shouldStoreXSizeData_whenReadCSVIsUsed() {
        //given
        String fileName = "src/test/resources/data.csv";
        int skipLines = 1;

        int [] expectedDataSize = {10, 5};
        int expectedTargetSize = 10;

        //when
        Dataset dataset = reader.read(fileName, skipLines);
        double [][] data = dataset.getFeatures();
        double [][] target = dataset.getTarget();

        //then
        assertEquals(expectedDataSize[0], data.length);
        assertEquals(expectedDataSize[1], data[0].length);
        assertEquals(expectedTargetSize, target.length);
    }

    @Test
    public void shouldStoreXData_whenReadCSVIsUsed() {
        //given
        String fileName = "src/test/resources/data.csv";
        int skipLines = 1;

        double [][] expectedData = {{0.52,1.34,-0.87,2.11,0.03},
                                    {-1.20,0.76,1.45,-0.33,1.89},
                                    {0.88,-0.54,0.21,1.67,-1.02},
                                    {1.73,2.05,-1.34,0.09,0.55},
                                    {-0.41,1.11,0.93,-1.78,0.34},
                                    {0.07,-1.63,2.30,0.42,-0.91},
                                    {1.29,0.38,-0.66,1.05,1.77},
                                    {-0.95,1.82,0.14,-0.57,-1.23},
                                    {0.63,-0.29,1.01,2.44,0.18},
                                    {-1.47,0.55,-1.10,0.81,1.04}};
        double [] expectedTarget = {1.00, 0.00, 1.00, 0.00, 1.00, 0.00, 1.00, 1.00, 0.00, 0.00};

        //when
        Dataset dataset = reader.read(fileName, skipLines);
        double [][] data = dataset.getFeatures();
        double [][] target = dataset.getTarget();

       //then
       for(int i = 0; i < data.length; i++) {
           for(int j = 0; j < data[0].length; j++) {
               assertEquals(expectedData[i][j], data[i][j]);
           }

           assertEquals(expectedTarget[i], target[i][0]);
       }
    }
}
