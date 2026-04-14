package structure;

public class Scalar
{
    private double value;
    private double grad;
    private double hessian;
    private Scalar[] parent;

    public Runnable propagateGrad = null;
    public Runnable propagateHessian = null;

    public Scalar() {
        value = 0.0;
        grad = 0.0;
        hessian = 0.0;
        parent = null;
    }

    public Scalar(double value) {
        this.value = value;
        grad = 0.0;
        hessian = 0.0;
        parent = null;
    }

    public Scalar(double value, Scalar... parent) {
        this.value = value;
        grad = 0.0;
        hessian = 0.0;
        this.parent = new Scalar[parent.length];

        System.arraycopy(parent, 0, this.parent, 0, parent.length);
    }

    public Scalar(Scalar scalar) {
        value = scalar.value;
        grad = scalar.grad;
        hessian = scalar.hessian;
        parent = new Scalar[1];

        parent[0] = scalar;
    }

    public static Scalar [] toScalarArray(double [] value) {
        int size = value.length;
        Scalar [] scalar = new Scalar[size];

        for(int i = 0; i < size; i++) {
            scalar[i] = new Scalar(value[i]);
        }

        return scalar;
    }

    public void setValue(double value) { this.value = value; }
    public double getValue() { return value; }
    public Scalar[] getParent() { return parent; }
    public void setParent(Scalar [] parent) { this.parent = parent; }
    public void setGrad(double grad) { this.grad = grad; }
    public double getGrad() { return grad; }
    public void addGrad(double grad) { this.grad += grad; }
}
