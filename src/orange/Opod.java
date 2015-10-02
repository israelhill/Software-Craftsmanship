package orange;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public final class Opod extends AbstractProduct {
    private final static BigInteger VALID_REFUND = BigInteger.valueOf(24);

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

    @Override
    public void process(Exchange request, RequestStatus status) throws ProductException {
        Iterator<SerialNumber> iterator = request.getCompatibleProducts().iterator();

        // if you find a compatible product, status is set to OK. Result is set to the serial number of
        // the compatible product. We are done, break out the loop.
        if(iterator.hasNext()) {
            Optional<BigInteger> compatibleSerialNumber = Optional.of(iterator.next().getSerialNumber());
            status.setResult(compatibleSerialNumber);
            status.setStatusCode(RequestStatus.StatusCode.OK);
        }
        else {
            status.setStatusCode(RequestStatus.StatusCode.FAIL);
            status.setResult(Optional.<BigInteger>empty());
            throw new ProductException(ProductType.OPOD, this.getSerialNumber(),
                    ProductException.ErrorCode.NO_COMPATIBLE_PRODUCTS);
        }

    }

    @Override
    public void process(Refund request, RequestStatus status) throws ProductException {
        if(isValidRefund(request, this.getSerialNumber())) {
            status.setStatusCode(RequestStatus.StatusCode.OK);
            status.setResult(Optional.<BigInteger>empty());

        }
        else {
            status.setStatusCode(RequestStatus.StatusCode.FAIL);
            status.setResult(Optional.<BigInteger>empty());
            throw new ProductException(ProductType.OPOD, this.getSerialNumber(),
                    ProductException.ErrorCode.INVALID_REFUND);
        }
    }

    /**
     * @param serialNumber
     * @return true if valid, false if not
     */
    public static boolean isValidSerialNumber(SerialNumber serialNumber) {
        return serialNumber.isEven() && !isThirdBitSet(serialNumber);
    }

    public boolean testMeth() {
        Opod.isValidSerialNumber(this.getSerialNumber());
        return true;
    }

    /**
     * check if the third bit is set
     * @param serialNumber
     * @return true if set
     */
    public static boolean isThirdBitSet(SerialNumber serialNumber) {
        return serialNumber.testBit(3);
    }

    public static boolean isValidRefund(Refund refund, SerialNumber serialNumber) {
        BigInteger gcd = refund.getRMA().gcd(serialNumber.getSerialNumber());
        return gcd.compareTo(VALID_REFUND) == 1 || gcd.compareTo(VALID_REFUND) == 0;
    }
}
