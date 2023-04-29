import java.util.Arrays;

public class Converter8Bit extends Converter {

    // Define the 8-bit systems of springs
    private static final Spring[][] SPRING_SYSTEMS = {
        { new Spring(2), new Spring(1), new Spring(1), new Spring(1), new Spring(1), new Spring(1), new Spring(1), new Spring(1) },
        { new Spring(4), new Spring(2), new Spring(1), new Spring(1), new Spring(1), new Spring(1), new Spring(1), new Spring(1) },
        { new Spring(8), new Spring(4), new Spring(2), new Spring(1), new Spring(1), new Spring(1), new Spring(1), new Spring(1) }
        // Add more systems as needed for larger numbers of bits
    };
    
    // Define the decimal values of each bit in the system
    private static final int[] BIT_VALUES = { 128, 64, 32, 16, 8, 4, 2, 1 };

    // Override the abstract method to create a system of springs for the given sequence of bits
    @Override
    protected Spring[] createSpringSystem(int[] bits) {
        Spring[] system = new Spring[8];
        for (int i = 0; i < 8; i++) {
            if (bits[i] == 1) {
                system[i] = new Spring(1);
            } else {
                system[i] = new Spring(0);
            }
        }
        return system;
    }

    // Override the abstract method to evaluate the decimal value of the binary sequence
    // using the computed frequency amplitudes
    @Override
    protected int evaluateDecimalValue(double[] frequencyAmplitudes) {
        int decimalValue = 0;
        for (int i = 0; i < 8; i++) {
            decimalValue += Math.round(frequencyAmplitudes[i]) * BIT_VALUES[i];
        }
        return decimalValue;
    }
    
    // Test the class for several cases in the main() function
    public static void main(String[] args) {
        Converter8Bit converter = new Converter8Bit();
        
        // Test a binary sequence that represents the number 165
        int[] bits = { 1, 0, 1, 0, 0, 1, 0, 1 };
        double[] frequencyAmplitudes = converter.computeFrequencyAmplitudes(bits);
        int decimalValue = converter.evaluateDecimalValue(frequencyAmplitudes);
        System.out.println("Binary sequence: " + Arrays.toString(bits));
        System.out.println("Frequency amplitudes: " + Arrays.toString(frequencyAmplitudes));
        System.out.println("Decimal value: " + decimalValue);
        
        // Test a binary sequence that represents the number 0
        int[] bits2 = { 0, 0, 0, 0, 0, 0, 0, 0 };
        double[] frequencyAmplitudes2 = converter.computeFrequencyAmplitudes(bits2);
        int decimalValue2 = converter.evaluateDecimalValue(frequencyAmplitudes2);
        System.out.println("Binary sequence: " + Arrays.toString(bits2));
        System.out.println("Frequency amplitudes: " + Arrays.toString(frequencyAmplitudes2));
        System.out.println("Decimal value: " + decimalValue2);
    }
}
