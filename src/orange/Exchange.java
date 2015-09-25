package orange;

import java.util.HashSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public final class Exchange implements Request {
    private static NavigableSet<SerialNumber> set;

    /**
     * private constructor for the class
     * Instances of this Exchange must be created with a Builder
     * @param builder
     */
    private Exchange(Builder builder) {
        set = new TreeSet<>(builder.getCompatibleProducts());
    }

    /**
     * process an exchange
     * @param product
     * @param requestStatus
     * @throws RequestException
     */
    @Override
    public void process(Product product, RequestStatus requestStatus) throws RequestException {
        try {
            product.process(this, requestStatus);
        }
        catch(ProductException e) {
            throw new RequestException();
        }
    }

    /**
     * get set of compatible products
     * @return a set of serial number
     */
    public NavigableSet<SerialNumber> getCompatibleProducts() {
        return new TreeSet<>(set);
    }

    public static class Builder {
        private Set<SerialNumber> compatibleSet = new HashSet<>();

        /**
         * add serial numbers to builder's set
         * @param serialNumber
         * @return a Builder object
         */
        public Builder addCompatible(SerialNumber serialNumber) {
            compatibleSet.add(serialNumber);
            return new Builder();
        }

        /**
         * get the set of compatible products
         * @return Set containing serial numbers
         */
        public Set<SerialNumber> getCompatibleProducts() {
             return compatibleSet;
        }

        /**
         * @return a new Exchange object and pass a reference to builder
         */
        public Exchange build() {
            return new Exchange(this);
        }
    }
}
