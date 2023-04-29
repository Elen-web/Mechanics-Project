import java.util.Arrays;

public abstract class Converter {
    protected Spring[] springs;
    protected double[] freqAmps;

    protected abstract Spring[] bitSequenceToSprings(boolean[] bits);

    public void connectMass(double x0, double v0) {
        double t = 1.0; // time of oscillations in seconds
        double dt = 0.001; // time step in seconds
        double[] coords = new Spring(1).move(t, dt, x0, v0);
        for (int i = 0; i < springs.length; i++) {
            springs[i].setX(coords[i]);
        }
    }

    public void computeFreqAmps() {
        int numSamples = 1000;
        double[] timeVals = new double[numSamples];
        double tStep = 1.0 / numSamples;
        for (int i = 0; i < numSamples; i++) {
            timeVals[i] = i * tStep;
        }
        double[] yVals = new double[numSamples];
        for (int i = 0; i < numSamples; i++) {
            double[] coords = new double[springs.length];
            for (int j = 0; j < springs.length; j++) {
                coords[j] = springs[j].getX();
            }
            yVals[i] = FT.amplitude(coords, timeVals, i);
        }
        freqAmps = FT.transform(yVals);
    }

    public abstract double evaluate(boolean[] bits);
}
