// Sample program to demonstrate why RSA works (due to the asymmetric computational complexity property)
import java.math.BigInteger;

public class FactorPrime {
    // Precondition: n is a semi-prime number.
    // Postcondition: the returned value is a prime factor of n.
    public static BigInteger factor(BigInteger n) {
        BigInteger i = new BigInteger("2"); // Begin the guess from "2"
        BigInteger zero = new BigInteger("0");
        // Time complexity: O(n)
        while (i.compareTo(n) < 0) {
            if (n.remainder(i).compareTo(zero) == 0) {
                return i;
            }
            i = i.add(new BigInteger("1"));
        }

        assert(false); // If this is reached, an error occurs.
        return null;
    }
}
