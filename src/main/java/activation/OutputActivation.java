package activation;

public interface OutputActivation
{
    double [] activate(double [] input);
    double [] derive(double [] input, double [] output);
}
