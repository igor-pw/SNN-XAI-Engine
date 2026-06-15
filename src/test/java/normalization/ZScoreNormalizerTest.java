package normalization;

import data.Dataset;
import org.junit.jupiter.api.Test;
import utils.TestGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ZScoreNormalizerTest
{
    private final Normalizer zScore = new ZScoreNormalizer();

    @Test
    public void shouldReturnMatrix_whenNormalizeIsUsed() {
        //given
        float [][] data = TestGenerator.generateRandomMatrix(2, 6);
        Dataset dataset = new Dataset(data, null);

        //when
        zScore.normalize(dataset);
        float [][] result = dataset.getFeatures();

        //then
        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[0].length; j++)
                assertNotEquals(data[i][j], result[i][j]);
        }
    }

    @Test
    public void shouldReturnXSizeMatrix_whenNormalizeIsUsed() {
        //given
        int rows = 4;
        int cols = 3;

        float [][] data = TestGenerator.generateRandomMatrix(rows, cols);
        Dataset dataset = new Dataset(data, null);

        int expectedRows = 4;
        int expectedCols = 3;

        //when
        zScore.normalize(dataset);

        //then
        assertEquals(expectedRows, rows);
        assertEquals(expectedCols, cols);
    }

    @Test
    public void shouldReturnStandardizedMatrix_whenNormalizeIsUsed() {
        //given
        int rows = 100;
        int cols = 20;
        float [][] data = TestGenerator.generateRandomMatrix(rows, cols);
        Dataset dataset = new Dataset(data, null);

        float expectedMean = 0.0f;
        float expectedStd = 1.0f;

        //when
        zScore.normalize(dataset);
        float [][] result = dataset.getFeatures();
        float resultMean = 0.0f;
        float resultStd = 0.0f;
        int n = rows*cols;

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                resultMean += result[i][j];
            }
        }

        resultMean /= n;

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                resultStd += (float)Math.pow((result[i][j] - resultMean), 2);
            }
        }

        resultStd /= n;
        resultStd = (float)Math.sqrt(resultStd);

        //then
        assertEquals(expectedMean, resultMean, 1e-6);
        assertEquals(expectedStd, resultStd, 1e-6);
    }


}
