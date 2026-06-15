package io;

import data.Dataset;
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
        float [][] data = dataset.getFeatures();
        float [][] target = dataset.getTarget();

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
        float [][] data = dataset.getFeatures();
        float [][] target = dataset.getTarget();

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

        float [][] expectedData = {{0.52f,1.34f,-0.87f,2.11f,0.03f},
                                    {-1.20f,0.76f,1.45f,-0.33f,1.89f},
                                    {0.88f,-0.54f,0.21f,1.67f,-1.02f},
                                    {1.73f,2.05f,-1.34f,0.09f,0.55f},
                                    {-0.41f,1.11f,0.93f,-1.78f,0.34f},
                                    {0.07f,-1.63f,2.30f,0.42f,-0.91f},
                                    {1.29f,0.38f,-0.66f,1.05f,1.77f},
                                    {-0.95f,1.82f,0.14f,-0.57f,-1.23f},
                                    {0.63f,-0.29f,1.01f,2.44f,0.18f},
                                    {-1.47f,0.55f,-1.10f,0.81f,1.04f}};
        float [] expectedTarget = {1.00f, 0.00f, 1.00f, 0.00f, 1.00f, 0.00f, 1.00f, 1.00f, 0.00f, 0.00f};

        //when
        Dataset dataset = reader.read(fileName, skipLines);
        float [][] data = dataset.getFeatures();
        float [][] target = dataset.getTarget();

       //then
       for(int i = 0; i < data.length; i++) {
           for(int j = 0; j < data[0].length; j++) {
               assertEquals(expectedData[i][j], data[i][j]);
           }

           assertEquals(expectedTarget[i], target[i][0]);
       }
    }
}
