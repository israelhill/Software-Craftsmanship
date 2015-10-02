package orange;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.Set;

public class Otv extends AbstractProduct {

    Otv(SerialNumber serialNumber, Optional<Set<String>> description) {
        super(serialNumber, description);
    }

    @Override
    public ProductType getProductType() {
        return ProductType.OTV;
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
        SerialNumber compatibleProduct = this.getCompatibleProductForExchange(request);
        if(compatibleProduct != null) {
            status.setResult(Optional.of(compatibleProduct.getSerialNumber()));
            status.setStatusCode(RequestStatus.StatusCode.OK);
        }
        else {
            status.setStatusCode(RequestStatus.StatusCode.FAIL);
            status.setResult(Optional.<BigInteger>empty());
            throw new ProductException(ProductType.OTV, this.getSerialNumber(),
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
        if(isValidRefund(request)) {
            status.setResult(Optional.<BigInteger>empty());
            status.setStatusCode(RequestStatus.StatusCode.OK);
        }
        else {
            status.setResult(Optional.<BigInteger>empty());
            status.setStatusCode(RequestStatus.StatusCode.FAIL);
            throw new ProductException(ProductType.OTV, this.getSerialNumber(), ProductException.ErrorCode.INVALID_REFUND);
        }
    }

    public static boolean isValidGcd(SerialNumber serialNumber) {
        SerialNumber number = new SerialNumber(BigInteger.valueOf(630));
        long value = serialNumber.gcd(number).longValue();

        return value <= 14;
    }

    /**
     * check if the rma is greater than 0
     * @param refund
     * @return true if rma is greater than 0, false otherwise
     */
    public static boolean isValidRefund(Refund refund) {
        return refund.getRMA().compareTo(BigInteger.ZERO) == 1;
    }

    /**
     * find a compatible product if one exists
     * @param request
     * @return null if one is not found, otherwise return the serial number of the compatible product
     */
    public SerialNumber getCompatibleProductForExchange(Exchange request) {
        final BigInteger UPPER_BOUND = this.getSerialNumber().getSerialNumber().add(BigInteger.valueOf(1024));
        SerialNumber upperBound = new SerialNumber(UPPER_BOUND);

        NavigableSet<SerialNumber> subSet =
                request.getCompatibleProducts().subSet(this.getSerialNumber(), false, upperBound, true);
        Iterator<SerialNumber> iterator = subSet.iterator();
        BigInteger sum = BigInteger.ZERO;

        while (iterator.hasNext()) {
            sum = sum.add(iterator.next().getSerialNumber());
        }

        BigInteger average;
        SerialNumber avgSerialNumber;
        if(subSet.size() > 0) {
            average = sum.divide(BigInteger.valueOf(subSet.size()));
            avgSerialNumber = new SerialNumber(average);
        }
        else {
            return null;
        }

        return subSet.lower(avgSerialNumber);
    }
}
