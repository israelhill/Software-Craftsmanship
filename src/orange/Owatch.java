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

    /**
     * process an exchange
     * @param request
     * @param status
     * @throws ProductException
     */
    @Override
    public void process(Exchange request, RequestStatus status) throws ProductException {
        SerialNumber compatibleProduct = request.getCompatibleProducts().higher(this.getSerialNumber());

        if(compatibleProduct != null) {
            status.setStatusCode(RequestStatus.StatusCode.OK);
            status.setResult(Optional.of(compatibleProduct.getSerialNumber()));
        }
        else {
            status.setStatusCode(RequestStatus.StatusCode.FAIL);
            status.setResult(Optional.<BigInteger>empty());
            throw new ProductException(ProductType.OWATCH, this.getSerialNumber(),
                    ProductException.ErrorCode.NO_COMPATIBLE_PRODUCTS);
        }
    }

    /**
     * process a refund
     * @param request
     * @param status
     * @throws ProductException
     */
    @Override
    public void process(Refund request, RequestStatus status) throws ProductException {
        if(isValidRefund(request,this.getSerialNumber())) {
            status.setResult(Optional.<BigInteger>empty());
            status.setStatusCode(RequestStatus.StatusCode.OK);
        }
        else {
            status.setResult(Optional.<BigInteger>empty());
            status.setStatusCode(RequestStatus.StatusCode.FAIL);
            throw new ProductException(ProductType.OWATCH, this.getSerialNumber(), ProductException.ErrorCode.INVALID_REFUND);
        }
    }

    public static boolean isValidGcd(SerialNumber serialNumber) {
        SerialNumber number = new SerialNumber(BigInteger.valueOf(630));
        long value = serialNumber.gcd(number).longValue();

        return 14 < value && value <= 42;
    }

    /**
     * check that a refund is valid
     * @param refund
     * @param serialNumber
     * @return
     */
    public static boolean isValidRefund(Refund refund, SerialNumber serialNumber) {
        final BigInteger VALID_INPUT = BigInteger.valueOf(14);
        BigInteger xorValue = refund.getRMA().xor(serialNumber.getSerialNumber());

        return xorValue.compareTo(VALID_INPUT) == 1;
    }
}
