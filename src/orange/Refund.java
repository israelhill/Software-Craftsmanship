package orange;

import java.math.BigInteger;

public final class Refund implements Request {
    private BigInteger rmaCode;

    /**
     * constructor for the class
     * @param builder
     */
    private Refund(Builder builder) {
        rmaCode = builder.getRMA();
    }

    /**
     * process a refund
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
            String message = "An error occured while processing your request.";
            throw new RequestException(message, e);
        }
    }

    /**
     *
     * @return
     */
    public BigInteger getRMA() {
        return rmaCode;
    }


    public static class Builder {
        private BigInteger rma;

        /**
         * set the RMA for the builder
         * @param rma
         * @return builder object
         * @throws RequestException
         */
        public Builder setRMA(BigInteger rma) throws RequestException {
            if(rma == null) {
                throw new RequestException();
            }
            else {
                this.rma = rma;
            }
            return new Builder();
        }

        /**
         * get the RMA
         * @return RMA
         */
        public BigInteger getRMA() {
            return rma;
        }

        /**
         * create new instance of Refund and pass it a builder
         * @return new Refund object
         */
        public Refund build() {
            return new Refund(this);
        }
    }
}
