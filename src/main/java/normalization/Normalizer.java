package normalization;

import data.Dataset;

import java.io.Serializable;

public interface Normalizer extends Serializable
{
    void normalize(Dataset data);
    void transform(Dataset data);
}
