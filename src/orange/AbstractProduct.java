package orange;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public abstract class AbstractProduct implements Product {

    private SerialNumber serialNumber;
    private Optional<Set<String>> description;

    /**
     * Constructor for the class
     * @param serialNumber
     * @param description
     */
    public AbstractProduct(SerialNumber serialNumber, Optional<Set<String>> description) {
        this.serialNumber = serialNumber;
        this.description = description;
    }

    /**
     * Get the serial number for an instance
     * @return a SerialNumber
     */
    public SerialNumber getSerialNumber() {
        return this.serialNumber;
    }

    /**
     * Get the name of a product
     * @return the string value of ProductType
     */
    public String getProductName() {
        return this.getProductType().getName();
    }

    /**
     * Get the description for a product if one exists
     * @return Product description
     */
    public Optional<Set<String>> getDescription() {
        return this.description;
    }

    /**
     * Object factory: Creates objects based on the user's input
     * @param productType
     * @param serialNumber
     * @param description
     * @return
     * @throws ProductException
     */
    public static Product make(ProductType productType, SerialNumber serialNumber, Optional<Set<String>> description)
            throws ProductException {

        Product retVal;
        String productName = productType.getName();

        switch (productName) {
            case "oPod": {
                retVal = createOpod(productType, serialNumber, description);
                break;
            }
            case "oPad": {
                retVal =  createOpad(productType, serialNumber, description);
                break;
            }
            case "oWatch": {
                retVal = createOwatch(productType, serialNumber, description);
                break;
            }
            case "oTv": {
                retVal =  createOtv(productType, serialNumber, description);
                break;
            }
            case "oPhone": {
                retVal = createOphone(productType, serialNumber, description);
                break;
            }
            default: {
                throw new ProductException(productType, serialNumber, ProductException.ErrorCode.INVALID_PRODUCT_TYPE);
            }
        }

        return retVal;
    }

    /**
     * Overrides Java.util.equals
     * Two products are equal if they have the same hashcode
     * @param obj
     * @return true if products have same hashcode, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Product)) {
            return false;
        }
        else {
            return this.hashCode() == obj.hashCode();
        }
    }

    /**
     * Overrides Java.util.hashcode
     * Hashcode is the int value of a product's SerialNumber
     * @return int value of a product's serial number
     */
    @Override
    public int hashCode() {
         return this.getSerialNumber().getSerialNumber().hashCode();
    }

    /**
     * Builds a product description for a product
     * @return product description
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Product Name: ");
        sb.append(this.getProductName());
        sb.append("\n");
        sb.append("Serial Number: ");
        sb.append(this.getSerialNumber().getSerialNumber());
        sb.append("\n");

        if(this.getDescription().isPresent()) {
            Stream<String> stream = this.getDescription().get().stream();

            stream.forEach(f -> {
                sb.append(f.substring(0, 1).toUpperCase());
                sb.append(f.substring(1, f.length()));
                sb.append("\n");
            });
        }
        else {
            sb.append("No Description Available");
        }

        return sb.toString();
    }

    //The following methods are used in the object factory to create the desired object
    private static Opod createOpod(ProductType productType, SerialNumber serialNumber, Optional<Set<String>> description) throws ProductException {
        if(Opod.isValidSerialNumber(serialNumber)) {
            return new Opod(serialNumber, description);
        }
        else {
            throw new ProductException(productType, serialNumber, ProductException.ErrorCode.INVALID_SERIAL_NUMBER);
        }
    }

    private static Opad createOpad(ProductType productType, SerialNumber serialNumber, Optional<Set<String>> description) throws ProductException {
        if(Opad.isValidSerialNumber(serialNumber)) {
            return new Opad(serialNumber, description);
        }
        else {
            throw new ProductException(productType, serialNumber, ProductException.ErrorCode.INVALID_SERIAL_NUMBER);
        }
    }

    private static Owatch createOwatch(ProductType productType, SerialNumber serialNumber, Optional<Set<String>> description) throws ProductException {
        if(Owatch.isValidSerialNumber(serialNumber)) {
            return new Owatch(serialNumber, description);
        }
        else {
            throw new ProductException(productType, serialNumber, ProductException.ErrorCode.INVALID_SERIAL_NUMBER);
        }
    }

    private static Ophone createOphone(ProductType productType, SerialNumber serialNumber, Optional<Set<String>> description) throws ProductException {
        if(Ophone.isValidSerialNumber(serialNumber)) {
            return new Ophone(serialNumber, description);
        }
        else {
            throw new ProductException(productType, serialNumber, ProductException.ErrorCode.INVALID_SERIAL_NUMBER);
        }
    }

    private static Otv createOtv(ProductType productType, SerialNumber serialNumber, Optional<Set<String>> description) throws ProductException {
        if(Otv.isValidSerialNumber(serialNumber)) {
            return new Otv(serialNumber, description);
        }
        else {
            throw new ProductException(productType, serialNumber, ProductException.ErrorCode.INVALID_SERIAL_NUMBER);
        }
    }
}
