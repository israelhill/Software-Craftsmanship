package orange;

public enum ProductType {
    OPOD("oPod"),
    OPAD("oPad"),
    OPHONE("oPhone"),
    OTV("oTv"),
    OWATCH("oWatch");

    private final String productName;

    ProductType(String productName) {
        this.productName = productName;
    }

    public String getName() {
        return this.productName;
    }

}