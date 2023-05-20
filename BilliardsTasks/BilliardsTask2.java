import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VerticalCircularBilliard {
    private static final double GRAVITY = 10.0;
    private static final double RADIUS = 1.0;

    public static void main(String[] args) {
        int numReflections = 100;  // Number of reflections to simulate
        double x0 = getRandomNumber(-1, 1);  // Initial x-coordinate (-1 to 1)
        double y0 = getRandomNumber(-1, 1);  // Initial y-coordinate (-1 to 1)
        double magnitude = getRandomNumber(5, 10);  // Magnitude of the initial momentum

        // Normalize the initial momentum vector
        double px0 = getRandomNumber(-1, 1) / magnitude;
        double py0 = getRandomNumber(-1, 1) / magnitude;

        // Simulate vertical circular billiard
        List<double[]> reflectionPoints = simulateVerticalBilliard(numReflections, x0, y0, px0, py0);

        // Test reversibility
        double delta = 0.001;  // Small delta for deviation threshold
        int deviationPoint = testReversibility(reflectionPoints, numReflections, delta);

        // Print results
        System.out.println("Number of reflections: " + numReflections);
        System.out.println("Initial position: (" + x0 + ", " + y0 + ")");
        System.out.println("Initial momentum: (" + px0 + ", " + py0 + ")");
        System.out.println("Reflection points: " + reflectionPoints);
        System.out.println("Reversibility test:");
        if (deviationPoint == -1) {
            System.out.println("The reversed path coincides with the straight one.");
        } else {
            System.out.println("The paths deviate after " + deviationPoint + " reflections.");
        }
    }

    private static List<double[]> simulateVerticalBilliard(int numReflections, double x0, double y0, double px0, double py0) {
        List<double[]> reflectionPoints = new ArrayList<>();

        double x = x0;
        double y = y0;
        double px = px0;
        double py = py0;

        for (int i = 0; i < numReflections; i++) {
            // Calculate time to next reflection
            double a = px * px + (py - GRAVITY) * (py - GRAVITY);
            double b = 2 * (x * px + (y - RADIUS) * (py - GRAVITY));
            double c = x * x + (y - RADIUS) * (y - RADIUS) - RADIUS * RADIUS;

            double discriminant = b * b - 4 * a * c;

            if (discriminant < 0) {
                break;
            }

            double t = (-b + Math.sqrt(discriminant)) / (2 * a);

            // Calculate new position and reflection point
            double nextX = x + px * t;
            double nextY = y + (py - GRAVITY) * t - 0.5 * GRAVITY * t * t;

            double reflectionX = nextX;
            double reflectionY = 2 * RADIUS - nextY;

            // Update momentum
            double newPx = (reflectionY * reflectionY - reflectionX * reflectionX) * px - 2 * reflectionX * reflectionY * py;
            double newPy = -2 * reflectionX * reflectionY * px + (reflectionX * reflectionX - reflectionY * reflectionY) * py;

            // Store reflection point
            reflectionPoints.add(new double[]{reflectionX, reflectionY});

            // Update position and momentum
            x = nextX;
            y = nextY;
            px = newPx;
            py = newPy;
        }

        return reflectionPoints;
    }

    private static int testReversibility(List<double[]> reflectionPoints, int numReflections, double delta) {
        // Reverse the momentum
        for (int i = 0; i < numReflections; i++) {
            double[] point = reflectionPoints.get(i);
            point[1] = -point[1];
        }

        // Simulate reversed billiard
        List<double[]> reversedPoints = simulateVerticalBilliard(numReflections, reflectionPoints.get(numReflections - 1)[0], reflectionPoints.get(numReflections - 1)[1],
                reflectionPoints.get(numReflections - 1)[0], -reflectionPoints.get(numReflections - 1)[1]);

        // Check for deviation
        for (int i = 0; i < numReflections; i++) {
            double[] originalPoint = reflectionPoints.get(i);
            double[] reversedPoint = reversedPoints.get(i);

            if (Math.abs(originalPoint[0] - reversedPoint[0]) > delta || Math.abs(originalPoint[1] - reversedPoint[1]) > delta) {
                return i + 1;  // Deviation point
            }
        }

        return -1;  // No deviation
    }

    private static double getRandomNumber(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }
}
