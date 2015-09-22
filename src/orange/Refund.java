package orange;

import java.math.BigInteger;

public final class Refund implements Request {
    private BigInteger rmaCode;

    private Refund(Builder builder) {
        rmaCode = builder.getRMA();
    }

    @Override
    public void process(Product product, RequestStatus requestStatus) throws RequestException {

    }

    public BigInteger getRMA() {
        return rmaCode;
    }

    public static class Builder {
        private BigInteger rma;

        public Builder setRMA(BigInteger rma) throws RequestException {
            if(rma == null) {
                throw new RequestException();
            }
            else {
                this.rma = rma;
            }
            return new Builder();
        }

        public BigInteger getRMA() {
            return rma;
        }

        public Refund build() {
            return new Refund(this);
        }
    }
}
