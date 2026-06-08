package normalization;

import java.io.Serializable;

public interface Normalizer extends Serializable
{
    float [][] normalize(float [][] data);
    float [][] normalizePredict(float [][] data);
}
