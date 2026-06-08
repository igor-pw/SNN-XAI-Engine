package normalization;

public class ZScoreNormalizer implements Normalizer
{
    private float [] mean;
    private float [] std;

    @Override
    public float [][] normalize(float [][] data) {
        int rows = data.length;
        int cols = data[0].length;

        float [][] result = new float[rows][cols];
        mean = new float[cols];
        std = new float[cols];

        for(int i = 0; i < cols; i++) {
            float mean = 0.0f;
            float std = 0.0f;

            for(int j = 0; j < rows; j++) {
                mean += data[j][i];
            }

            mean /= rows;

            for(int j = 0; j < rows; j++) {
                std += (float)Math.pow((data[j][i] - mean), 2);
            }

            std /= rows;
            std = (float)Math.sqrt(std);

            for(int j = 0; j < rows; j++) {
                result [j][i] = (data[j][i] - mean) / (std + 1e-15f);
            }

            this.mean[i] = mean;
            this.std[i] = std;
        }

        return result;
    }

    public float [][] normalizePredict(float [][] data) {
        int rows = data.length;
        int cols = data[0].length;

        float [][] result = new float[rows][cols];
        for(int i = 0; i < cols; i++) {
            for(int j = 0; j < rows; j++) {
                result[j][i] = (data[j][i] - mean[i]) / (std[i] + 1e-15f);
            }
        }

        return result;
    }
}
