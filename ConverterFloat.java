import java.util.Arrays;

public class ConverterFloat extends Converter {
    
    private int[] intBits;
    private int[] fracBits;
    private int intLength;
    private int fracLength;
    
    public ConverterFloat(int[] intBits, int[] fracBits) {
        this.intBits = intBits;
        this.fracBits = fracBits;
        this.intLength = intBits.length;
        this.fracLength = fracBits.length;
    }
    
    @Override
    protected Spring[] bitsToSprings(int[] bits) {
        int numSprings = bits.length;
        Spring[] springs = new Spring[numSprings];
        for (int i = 0; i < numSprings; i++) {
            if (bits[i] == 1) {
                springs[i] = new Spring(1);
            } else {
                springs[i] = new Spring(0);
            }
        }
        return springs;
    }
    
    @Override
    public void connectSprings() {
        Spring[] intSprings = bitsToSprings(intBits);
        Spring[] fracSprings = bitsToSprings(fracBits);
        Spring[] combinedSprings = new Spring[intLength + fracLength];
        System.arraycopy(intSprings, 0, combinedSprings, 0, intLength);
        System.arraycopy(fracSprings, 0, combinedSprings, intLength, fracLength);
        for (int i = 0; i < combinedSprings.length - 1; i++) {
            Spring s1 = combinedSprings[i];
            Spring s2 = combinedSprings[i+1];
            s1.inSeries(s2);
        }
        this.springs = combinedSprings;
    }
    
    @Override
    public double evaluate() {
        double[] freqAmps = computeFrequencyAmplitudes();
        int intVal = binaryToInt(intBits);
        double fracVal = binaryToFrac(fracBits);
        double value = intVal + fracVal;
        double factor = 2 * Math.PI / springs.length;
        for (int i = 0; i < freqAmps.length; i++) {
            value += freqAmps[i] * Math.sin((i+1) * factor * time);
        }
        return value;
    }
    
    private double binaryToFrac(int[] bits) {
        double frac = 0;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] == 1) {
                frac += Math.pow(2, -(i+1));
            }
        }
        return frac;
    }
    
    private double[] computeFrequencyAmplitudes() {
        double[] freqAmps = new double[springs.length];
        double[] displacements = computeDisplacements();
        for (int i = 0; i < springs.length; i++) {
            double springConstant = springs[i].getStiffness();
            double amplitude = 2 * Math.abs(displacements[i]) / (springConstant * springs.length);
            freqAmps[i] = amplitude;
        }
        return freqAmps;
    }
    
    @Override
    public String toString() {
        return "ConverterFloat [intBits=" + Arrays.toString(intBits) + ", fracBits=" + Arrays.toString(fracBits) + "]";
    }
    
}
