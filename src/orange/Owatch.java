package orange;

import java.math.BigInteger;
import java.util.Optional;
import java.util.Set;

public class Owatch extends AbstractProduct {

    Owatch(SerialNumber serialNumber, Optional<Set<String>> description) {
        super(serialNumber, description);
    }

    @Override
    public ProductType getProductType() {
        return ProductType.OWATCH;
    }

    public static boolean isValidSerialNumber(SerialNumber serialNumber) {
        return serialNumber.isOdd() &&  isValidGcd(serialNumber);
    }

    public static boolean isValidGcd(SerialNumber serialNumber) {
        SerialNumber number = new SerialNumber(BigInteger.valueOf(630));
        long value = serialNumber.gcd(number).longValue();

        return 14 < value && value <= 42;
    }
}
