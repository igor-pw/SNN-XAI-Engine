package activation;

public interface HiddenActivation
{
    double activate(double input);
    double derive(double input, double output);
}
