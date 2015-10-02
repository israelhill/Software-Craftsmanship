package orange;

import java.math.BigInteger;
import java.util.Optional;
import java.util.Set;

public class Opad extends AbstractProduct {
    private final static BigInteger VALID_REFUND = BigInteger.valueOf(12);

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

    @Override
    public void process(Exchange request, RequestStatus status) throws ProductException {
        SerialNumber compatibleProduct = request.getCompatibleProducts().lower(this.getSerialNumber());

        if(compatibleProduct != null) {
            status.setResult(Optional.of(compatibleProduct.getSerialNumber()));
            status.setStatusCode(RequestStatus.StatusCode.OK);
        }
        else {
            status.setResult(Optional.<BigInteger>empty());
            status.setStatusCode(RequestStatus.StatusCode.FAIL);
            throw new ProductException(ProductType.OPAD, this.getSerialNumber(),
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
            throw new ProductException(ProductType.OPAD, this.getSerialNumber(),
                    ProductException.ErrorCode.INVALID_REFUND);
        }
    }

    public static boolean isThirdBitSet(SerialNumber serialNumber) {
        return serialNumber.testBit(3);
    }

    public static boolean isValidRefund(Refund refund, SerialNumber serialNumber) {
        BigInteger gcd = refund.getRMA().gcd(serialNumber.getSerialNumber());
        return gcd.compareTo(VALID_REFUND) == 1 || gcd.compareTo(VALID_REFUND) == 0;
    }
}
