import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HorizontalStadiumBilliard {
    private static final double RADIUS = 1.0;

    public static void main(String[] args) {
        int numReflections = 100; // Number of reflections
        double L = 2.0; // Length of the straight lines connecting the semicircles
        int M = 10; // Number of bins for testing uniformity

        List<double[]> reflectionPoints = simulateHorizontalBilliard(numReflections, L);
        testUniformity(reflectionPoints, M);
    }

    private static List<double[]> simulateHorizontalBilliard(int numReflections, double L) {
        List<double[]> reflectionPoints = new ArrayList<>();

        double x0 = getRandomNumber(-RADIUS, RADIUS); // Initial x-coordinate (-RADIUS to RADIUS)
        double y0 = getRandomNumber(-RADIUS, RADIUS); // Initial y-coordinate (-RADIUS to RADIUS)
        double magnitude = 1.0; // Magnitude of the initial momentum

        // Normalize the initial momentum vector
        double px0 = getRandomNumber(-1, 1) / magnitude;
        double py0 = getRandomNumber(-1, 1) / magnitude;

        double x = x0;
        double y = y0;
        double px = px0;
        double py = py0;

        for (int i = 0; i < numReflections; i++) {
            // Determine which side the particle will hit
            boolean hitTop = (y + RADIUS) >= (RADIUS - L) && py > 0;
            boolean hitBottom = (y - RADIUS) <= (L - RADIUS) && py < 0;

            if (hitTop || hitBottom) {
                // Reflection off the top or bottom line segments
                px = px;
                py = -py;
            } else {
                // Reflection off the left or right semicircles
                double xc = (x > 0) ? L : 0; // x-coordinate of the center of the semicircle

                // Calculate the next position of the particle
                double a = py * py - (x - xc) * (x - xc);
                double b = 2 * x * py - 2 * (x - xc) * y;
                double c = x * x - y * y + (x - xc) * (x - xc) - RADIUS * RADIUS;

                double discriminant = b * b - 4 * a * c;

                if (discriminant >= 0) {
                    double t = (-b + Math.sqrt(discriminant)) / (2 * a);
                    double nextX = x + px * t;
                    double nextY = y + py * t;

                    // Update the position and momentum
                    x = nextX;
                    y = nextY;

                    // Calculate the new momentum after reflection
                    double newPx = (y * y - (x - xc) * (x - xc)) * px - 2 * (x - xc) * y * py;
                    double newPy = -2 * (x - xc) * y * px + ((x - xc) * (x - xc) - y * y) * py;

                    px = newPx;
                    py = newPy;
                }
            }

            // Save the reflection point
            reflectionPoints.add(new double[]{x, y});
        }

        return reflectionPoints;
    }

    private static void testUniformity(List<double[]> reflectionPoints, int numBins) {
        int[] binCounts = new int[numBins];

        for (double[] point : reflectionPoints) {
            double x = point[0];
            double L = point[1] + RADIUS;

            // Map x/L to the interval [0, 1]
            double mappedValue = (x + L / 2) / L;

            // Determine the bin index
            int binIndex = (int) (mappedValue * numBins);

            // Increment the bin count
            binCounts[binIndex]++;
        }

        // Compute the mean and variance of the number of entries in the bins
        int totalCount = reflectionPoints.size();
        double mean = (double) totalCount / numBins;
        double variance = 0.0;

        for (int count : binCounts) {
            variance += Math.pow(count - mean, 2);
        }

        variance /= numBins;

        // Check if the bin counts are approximately equal
        boolean isApproximatelyEqual = true;
        double threshold = 0.1;

        for (int count : binCounts) {
            double deviation = Math.abs(count - mean);

            if (deviation > threshold) {
                isApproximatelyEqual = false;
                break;
            }
        }

        // Print results
        System.out.println("Number of Reflections: " + reflectionPoints.size());
        System.out.println("Mean: " + mean);
        System.out.println("Variance: " + variance);
        System.out.println("Bin Counts: " + java.util.Arrays.toString(binCounts));
        System.out.println("Approximately Equal: " + isApproximatelyEqual);
    }

    private static double getRandomNumber(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }
}
