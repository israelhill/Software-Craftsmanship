package orange;

import java.util.HashSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public final class Exchange implements Request {
    private static NavigableSet<SerialNumber> set;

    private Exchange(Builder builder) {
        set = new TreeSet<>(builder.getCompatibleProducts());
    }

    @Override
    public void process(Product product, RequestStatus requestStatus) throws RequestException {

    }

    public NavigableSet<SerialNumber> getCompatableProducts() {
        return set;
    }

    public static class Builder {
        private Set<SerialNumber> compatibleSet = new HashSet<>();

        public Builder addCompatible(SerialNumber serialNumber) {
            compatibleSet.add(serialNumber);
            return new Builder();
        }

        public Set<SerialNumber> getCompatibleProducts() {
             return compatibleSet;
        }

        public Exchange build() {
            return new Exchange(this);
        }
    }
}
