package normalization;

public interface Normalizer
{
    double [][] normalize(double [][] data);
    double [][] normalizePredict(double [][] data);
}
