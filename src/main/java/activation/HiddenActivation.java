package activation;

import java.io.Serializable;

public interface HiddenActivation extends Serializable
{
    double activate(double input);
    double derive(double input);
}
