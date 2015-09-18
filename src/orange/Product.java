package orange;

import java.util.Optional;
import java.util.Set;

public interface Product {

    public SerialNumber getSerialNumber();

    public ProductType getProductType();

    public String getProductName();

    public Optional<Set<String>> getDescription();
}