import java.util.ArrayList;

public class ConverterInt extends Converter {

    private Spring root;

    public ConverterInt(int[] binaryWeights) {
        root = buildTree(binaryWeights, 0, binaryWeights.length - 1);
    }

    private Spring buildTree(int[] binaryWeights, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = (start + end) / 2;
        Spring node = new Spring(binaryWeights[mid]);
        node.left = buildTree(binaryWeights, start, mid - 1);
        node.right = buildTree(binaryWeights, mid + 1, end);
        return node;
    }

    @Override
    public double[] convert(String binary) {
        int n = binary.length();
        double[] amplitudes = new double[n];
        for (int i = 0; i < n; i++) {
            char c = binary.charAt(i);
            if (c == '1') {
                amplitudes[i] = root.move(1, 0.01, 0, 0)[0];
            }
        }
        return amplitudes;
    }

    @Override
    public double evaluate(double[] amplitudes) {
        int n = amplitudes.length;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += amplitudes[i] * root.weightAt(i, n);
        }
        return sum;
    }
}

class Spring {

    double k;
    Spring left;
    Spring right;

    public Spring(double k) {
        this.k = k;
    }

    public double[] move(double t, double dt, double x0, double v0) {
        double omega = Math.sqrt(k);
        double A = x0;
        double B = v0 / omega;
        double x = A * Math.cos(omega * t) + B * Math.sin(omega * t);
        double v = -A * omega * Math.sin(omega * t) + B * omega * Math.cos(omega * t);
        return new double[] {x, v};
    }

    public double weightAt(int i, int n) {
        double weight = 1;
        Spring node = this;
        while (node != null) {
            if (i >= n / 2) {
                weight *= node.k;
                node = node.right;
                i -= n / 2;
            } else {
                node = node.left;
                n /= 2;
            }
       
