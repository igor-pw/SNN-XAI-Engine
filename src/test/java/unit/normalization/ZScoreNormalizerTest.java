package unit.normalization;

import normalization.Normalizer;
import normalization.ZScoreNormalizer;
import org.junit.jupiter.api.Test;
import utils.TestGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ZScoreNormalizerTest
{
    private final Normalizer zScore = new ZScoreNormalizer();
    private final TestGenerator generator = new TestGenerator();

    @Test
    public void shouldReturnMatrix_whenNormalizeIsUsed() {
        //given
        double [][] data = generator.generateRandomMatrix(2, 6);

        //when
        double [][] result = zScore.normalize(data);

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

        double [][] data = generator.generateRandomMatrix(rows, cols);

        int expectedRows = 4;
        int expectedCols = 3;

        //when
        double [][] result = zScore.normalize(data);

        //then
        assertEquals(expectedRows, rows);
        assertEquals(expectedCols, cols);
    }

    @Test
    public void shouldReturnStandardizedMatrix_whenNormalizeIsUsed() {
        //given
        int rows = 100;
        int cols = 20;
        double [][] data = generator.generateRandomMatrix(rows, cols);

        double expectedMean = 0.0;
        double expectedStd = 1.0;

        //when
        double [][] result = zScore.normalize(data);
        double resultMean = 0.0;
        double resultStd = 0.0;
        int n = rows*cols;

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                resultMean += result[i][j];
            }
        }

        resultMean /= n;

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                resultStd += Math.pow((result[i][j] - resultMean), 2);
            }
        }

        resultStd /= n;
        resultStd = Math.sqrt(resultStd);

        //then
        assertEquals(expectedMean, resultMean, 1e-12);
        assertEquals(expectedStd, resultStd, 1e-12);
    }


}
