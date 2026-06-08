package activation;

import java.io.Serializable;

public interface HiddenActivation extends Serializable
{
    float activate(float input);
    float derive(float input);
}
