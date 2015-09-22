package orange;

import java.util.Optional;
import java.util.Set;

public interface Product {

    public SerialNumber getSerialNumber();

    public ProductType getProductType();

    public String getProductName();

    public Optional<Set<String>> getDescription();

    public void process(Exchange request, RequestStatus status) throws ProductException;

    public void process(Refund request, RequestStatus status) throws ProductException;
}