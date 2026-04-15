package normalization;

public class ZScoreNormalizer implements Normalizer
{
    private double [] mean;
    private double [] std;

    @Override
    public double [][] normalize(double [][] data) {
        int rows = data.length;
        int cols = data[0].length;

        double [][] result = new double[rows][cols];
        mean = new double[cols];
        std = new double[cols];

        for(int i = 0; i < cols; i++) {
            double mean = 0.0;
            double std = 0.0;

            for(int j = 0; j < rows; j++) {
                mean += data[j][i];
            }

            mean /= rows;

            for(int j = 0; j < rows; j++) {
                std += Math.pow((data[j][i] - mean), 2);
            }

            std /= rows;
            std = Math.sqrt(std);

            for(int j = 0; j < rows; j++) {
                result [j][i] = (data[j][i] - mean) / (std);
            }

            this.mean[i] = mean;
            this.std[i] = std;
        }

        return result;
    }

    public double [][] normalizePredict(double [][] data) {
        int rows = data.length;
        int cols = data[0].length;

        double [][] result = new double[rows][cols];
        for(int i = 0; i < cols; i++) {
            for(int j = 0; j < rows; j++) {
                result[j][i] = (data[j][i] - mean[i]) / (std[i] + 1e-15);
            }
        }

        return result;
    }
}
