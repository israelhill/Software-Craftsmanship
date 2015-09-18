package orange;

import java.math.BigInteger;

public class SerialNumber implements Comparable {
    private BigInteger serialNumber;

    /**
     * Constructor for the class
     * @param serialNumber
     */
    public SerialNumber(BigInteger serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Get the serial number
     * @return the serial number for the particular instance
     */
    public BigInteger getSerialNumber() {
        return this.serialNumber;
    }

    /**
     * Get the greatest common denominator
     * @param other
     * @return the gcd of the two serial numbers
     */
    public BigInteger gcd(SerialNumber other) {
        return this.serialNumber.gcd(other.serialNumber);
    }

    /**
     * Get the modulus of two serial numbers
     * @param other
     * @return
     */
    public BigInteger mod(SerialNumber other) {
        return this.serialNumber.mod(other.serialNumber);
    }

    /**
     * Check if the given bit is set
     * @param bit
     * @return true if bit is 1, false if bit is 0
     */
    public boolean testBit(int bit) {
        return this.serialNumber.testBit(bit);
    }

    /**
     * Check if a serial number is even
     * @return  true if even, false if not
     */
    public boolean isEven() {
        final BigInteger TWO = BigInteger.valueOf(2);
        return this.serialNumber.mod(TWO).equals(BigInteger.ZERO);
    }

    /**
     * Check if a serial number is odd
     * @return true if odd, false if no
     */
    public boolean isOdd() {
        return !this.isEven();
    }

    /**
     * The natural ordering of serial numbers is based on
     * their big integer values
     * @param o
     * @return
     */
    @Override
    public int compareTo(Object o) {
        if(!(o instanceof SerialNumber)) {
            return -1;
        }

        SerialNumber other = (SerialNumber) o;

        return this.getSerialNumber().compareTo(other.getSerialNumber());
    }

}
