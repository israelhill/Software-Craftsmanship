package orange;

public interface Request {

    public void process(Product product, RequestStatus requestStatus) throws RequestException;
}
