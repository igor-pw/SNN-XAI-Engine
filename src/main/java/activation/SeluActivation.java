package activation;

public class SeluActivation implements ActivationFunc {

    private final double lambda = 1.0507009873554804934193349852946;
    private final double alfa = 1.6732632423543772848170429916717;

    @Override
    public double activate(double value) {
        return value > 0 ? lambda * value : alfa * lambda * (Math.exp(value) - 1.0);
    }
}