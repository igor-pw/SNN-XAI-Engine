public class Layer
{
    private Scalar[][] weights;
    private final Scalar[] bias;
    private final Scalar[] output;
    private final ActivationFunc activation;

    public Layer(int currentSize, int nextSize, Pool scalarPool, Scalar[] parameters) {
        weights = new Scalar[currentSize][nextSize];
        bias = new Scalar[currentSize];
        output = new Scalar[currentSize];

        if(nextSize == 0){
            weights = null;
        }

        activation = new ActivationFunc();

        if(weights != null) {
            for(int i = 0; i < weights.length; i++) {
                for(int j = 0; j < weights[i].length; j++) {
                    weights[i][j] = scalarPool.borrow();
                }

                bias[i] = scalarPool.borrow();
                output[i] = scalarPool.borrow();
            }
        }
    }
}
