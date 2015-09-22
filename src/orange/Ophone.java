package orange;

import java.math.BigInteger;
import java.util.Optional;
import java.util.Set;

public class Ophone extends AbstractProduct {

    Ophone(SerialNumber serialNumber, Optional<Set<String>> description) {
        super(serialNumber, description);
    }

    @Override
    public ProductType getProductType() {
        return ProductType.OPHONE;
    }

    public static boolean isValidSerialNumber(SerialNumber serialNumber) {
        return serialNumber.isOdd() &&  isValidGcd(serialNumber);
    }

    @Override
    public void process(Exchange request, RequestStatus status) throws ProductException {

    }

    @Override
    public void process(Refund request, RequestStatus status) throws ProductException {

    }

    public static boolean isValidGcd(SerialNumber serialNumber) {
        SerialNumber number = new SerialNumber(BigInteger.valueOf(630));
        long value = serialNumber.gcd(number).longValue();

        return value > 42;
    }
}
