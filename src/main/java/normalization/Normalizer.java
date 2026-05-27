package normalization;

import java.io.Serializable;

public interface Normalizer extends Serializable
{
    double [][] normalize(double [][] data);
    double [][] normalizePredict(double [][] data);
}
