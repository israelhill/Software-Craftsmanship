package orange;

public class ProductException extends Exception {

    private ProductType productType;
    private SerialNumber serialNumber;
    private ErrorCode errorCode;

    public enum ErrorCode {
        INVALID_SERIAL_NUMBER,
        INVALID_PRODUCT_TYPE,
        UNSUPPORTED_OPERATION;
    }

    /**
     * a constructor for the class
     * @param productType
     * @param serialNumber
     * @param errorCode
     */
    public ProductException(ProductType productType, SerialNumber serialNumber, ErrorCode errorCode) {
        this.productType = productType;
        this.serialNumber = serialNumber;
        this.errorCode = errorCode;
    }

    /**
     * get the type of the product
     * @return the product type
     */
    public ProductType getProductType() {
        return this.productType;
    }

    /**
     * get the name of the product
     * @return string containing the product name
     */
    public String getProductName() {
        return this.getProductType().getName();
    }

    /**
     * get the serial number of the product
     * @return Serial number object containing a BigInteger serial number
     */
    public SerialNumber getSerialNumber() {
        return this.serialNumber;
    }

    /**
     * get the error code
     * @return the enumerated type of the error
     */
    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
