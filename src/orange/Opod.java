package orange;

import java.util.Optional;
import java.util.Set;

public final class Opod extends AbstractProduct {

    Opod(SerialNumber serialNumber, Optional<Set<String>> description) {
        super(serialNumber, description);
    }

    /**
     * @return OPOD
     */
    @Override
    public ProductType getProductType() {
        return ProductType.OPOD;
    }

    /**
     * @param serialNumber
     * @return true if valid, false if not
     */
    public static boolean isValidSerialNumber(SerialNumber serialNumber) {
        return serialNumber.isEven() && !isThirdBitSet(serialNumber);
    }

    /**
     * check if the third bit is set
     * @param serialNumber
     * @return true if set
     */
    public static boolean isThirdBitSet(SerialNumber serialNumber) {
        return serialNumber.testBit(3);
    }
}
