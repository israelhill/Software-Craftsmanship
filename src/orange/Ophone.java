package orange;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.NavigableSet;
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

    /**
     * process an exchange
     * @param request
     * @param status
     * @throws ProductException
     */
    @Override
    public void process(Exchange request, RequestStatus status) throws ProductException {
        SerialNumber compatibleProduct = this.getCompatibleProductForExhange(request);
        if(compatibleProduct != null) {
            status.setResult(Optional.of(compatibleProduct.getSerialNumber()));
            status.setStatusCode(RequestStatus.StatusCode.OK);
        }
        else {
            status.setStatusCode(RequestStatus.StatusCode.FAIL);
            status.setResult(Optional.<BigInteger>empty());
            throw new ProductException(ProductType.OPHONE, this.getSerialNumber(), ProductException.ErrorCode.NO_COMPATIBLE_PRODUCTS);
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
        if(isValideRefund(request, this.getSerialNumber())) {
            status.setStatusCode(RequestStatus.StatusCode.OK);
            status.setResult(Optional.<BigInteger>empty());
        }
        else {
            status.setResult(Optional.<BigInteger>empty());
            status.setStatusCode(RequestStatus.StatusCode.FAIL);
            throw new ProductException(ProductType.OPHONE, this.getSerialNumber(), ProductException.ErrorCode.INVALID_REFUND);
        }
    }

    public static boolean isValidGcd(SerialNumber serialNumber) {
        SerialNumber number = new SerialNumber(BigInteger.valueOf(630));
        long value = serialNumber.gcd(number).longValue();

        return value > 42;
    }

    /**
     * determine whether a refund is valid based on the rma and current product serial number
     * @param refund
     * @param serialNumber
     * @return true if criteria is met
     */
    public static boolean isValideRefund(Refund refund, SerialNumber serialNumber) {
        BigInteger rma = refund.getRMA();
        BigInteger ophoneSerialNumber = serialNumber.getSerialNumber();

        return rma.shiftLeft(1).equals(ophoneSerialNumber) || rma.shiftLeft(2).equals(ophoneSerialNumber)
                || rma.shiftLeft(3).equals(ophoneSerialNumber);
    }

    /**
     * determines if an exchange has a compatible product
     * @param request
     * @return compatible products serial number if it exists, null otherwise
     */
    public SerialNumber getCompatibleProductForExhange(Exchange request) {
        NavigableSet<SerialNumber> largerSerialNumbersSet =
                (NavigableSet<SerialNumber>) request.getCompatibleProducts().tailSet(this.getSerialNumber());
        Iterator<SerialNumber> iterator = largerSerialNumbersSet.iterator();

        BigInteger sum = BigInteger.valueOf(0);
        BigInteger count = BigInteger.valueOf(largerSerialNumbersSet.size());
        while(iterator.hasNext()) {
            sum = sum.add(iterator.next().getSerialNumber());
        }

        BigInteger average;
        SerialNumber avgSerialNumber;
        if(largerSerialNumbersSet.size() > 0) {
            average = sum.divide(count);
            avgSerialNumber = new SerialNumber(average);
        }
        else {
            return null;
        }

        return largerSerialNumbersSet.lower(avgSerialNumber);
    }
}
