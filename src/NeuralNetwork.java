public class NeuralNetwork
{
    private Layer[] layers;
    private Scalar[] parameters;
    private final LossFunc loss;
    private double cost = 0.0;
    private final Pool scalarPool;

    public NeuralNetwork(Pool scalarPool, int[] structure, LossFunc loss) {
        this.scalarPool = scalarPool;
        layers = new Layer[structure.length];
        this.loss = loss;

        for(int i = 0; i < structure.length-1; i++) {
            layers[i] = new Layer(structure[i], structure[i+1], scalarPool, parameters);
        }
        layers[layers.length-1] = new Layer(structure[structure.length-1], 0, scalarPool, parameters);
    }
}
