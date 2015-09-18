package orange;

import java.util.Optional;
import java.util.Set;

public class Opad extends AbstractProduct {

    Opad(SerialNumber serialNumber, Optional<Set<String>> description) {
        super(serialNumber, description);
    }

    @Override
    public ProductType getProductType() {
        return ProductType.OPAD;
    }

    public static boolean isValidSerialNumber(SerialNumber serialNumber) {
        return serialNumber.isEven() && isThirdBitSet(serialNumber);
    }

    public static boolean isThirdBitSet(SerialNumber serialNumber) {
        return serialNumber.testBit(3);
    }
}
