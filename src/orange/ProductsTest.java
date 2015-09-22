package orange;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ProductsTest {

    private Opod opod;
    private Opad opad;
    private Owatch owatch;
    private Ophone ophone;
    private Otv otv;

    @Before
    public void initialize() {
        SerialNumber opodNum = new SerialNumber(BigInteger.valueOf(240));
        SerialNumber owatchNum = new SerialNumber(BigInteger.valueOf(360));
        SerialNumber opadNum = new SerialNumber(BigInteger.valueOf(1000));
        SerialNumber ophoneNum = new SerialNumber(BigInteger.valueOf(999));
        SerialNumber otvNum = new SerialNumber(BigInteger.valueOf(1234));

        opod = new Opod(opodNum, null);
        opad = new Opad(opadNum, null);
        ophone = new Ophone(ophoneNum, null);
        otv = new Otv(otvNum, null);
        owatch = new Owatch(owatchNum, null);
    }

    @Test
    public void testGetSerialNumber() throws Exception {
        Assert.assertEquals(opod.getSerialNumber().getSerialNumber().intValue(), 240);
        Assert.assertEquals(opad.getSerialNumber().getSerialNumber().intValue(), 1000);
        Assert.assertEquals(ophone.getSerialNumber().getSerialNumber().intValue(), 999);
        Assert.assertEquals(otv.getSerialNumber().getSerialNumber().intValue(), 1234);
        Assert.assertEquals(owatch.getSerialNumber().getSerialNumber().intValue(), 360);
    }

    @Test
    public void testGcd() throws Exception {
        Assert.assertEquals(opod.getSerialNumber().gcd(opad.getSerialNumber()), BigInteger.valueOf(40));
    }

    @Test
    public void testMod() throws Exception {
        Assert.assertTrue(!ophone.getSerialNumber().mod(otv.getSerialNumber()).equals(BigInteger.ZERO));
    }

    @Test
    public void testTestBit() throws Exception {
        String binaryString = "01111";
        BigInteger test = new BigInteger(binaryString, 2);
        SerialNumber num = new SerialNumber(test);
        Opad o = new Opad(num, null);

        Assert.assertTrue(o.getSerialNumber().testBit(0));
    }

    @Test
    public void testIsEven() throws Exception {
        Assert.assertFalse(ophone.getSerialNumber().isEven());
        Assert.assertTrue(opod.getSerialNumber().isEven());
    }

    @Test
    public void testIsOdd() throws Exception {
        Assert.assertTrue(ophone.getSerialNumber().isOdd());
        Assert.assertFalse(otv.getSerialNumber().isOdd());
    }

    @Test
    public void testGetProductName() {
        Assert.assertEquals(ophone.getProductName(), "oPhone");
        Assert.assertEquals(opod.getProductName(), "oPod");
        Assert.assertEquals(opad.getProductName(), "oPad");
        Assert.assertEquals(owatch.getProductName(), "oWatch");
        Assert.assertEquals(otv.getProductName(), "oTv");
    }

    @Test
    public void testGetProductType() {
        Assert.assertEquals(ophone.getProductType(), ProductType.OPHONE);
        Assert.assertEquals(opad.getProductType(), ProductType.OPAD);
        Assert.assertEquals(opod.getProductType(), ProductType.OPOD);
        Assert.assertEquals(owatch.getProductType(), ProductType.OWATCH);
        Assert.assertEquals(otv.getProductType(), ProductType.OTV);

    }

    @Test
    public void getSerialNumber() {
        BigInteger test = BigInteger.valueOf(500);
        SerialNumber num = new SerialNumber(test);
        Opad o = new Opad(num, null);

        Assert.assertEquals(o.getSerialNumber(), num);
    }

    @Test
    public void getDescription() {
        Set<String> set = new HashSet<>();
        set.add("string1");
        set.add("I am Second");
        set.add("the last string");
        Optional<Set<String>> description = Optional.of(set);

        BigInteger test = BigInteger.valueOf(500);
        SerialNumber num = new SerialNumber(test);

        Opad o = new Opad(num, description);

        System.out.println(o.toString());
    }

    @Test
    public void testIsValidSerialNumber() {
        Assert.assertTrue(Opod.isValidSerialNumber(opod.getSerialNumber()));
        Assert.assertTrue(Opad.isValidSerialNumber(opad.getSerialNumber()));
        Assert.assertFalse(Ophone.isValidSerialNumber(ophone.getSerialNumber()));
        Assert.assertFalse(Otv.isValidSerialNumber(otv.getSerialNumber()));
    }

    @Test
    public void testMake() {
        SerialNumber opadNum = new SerialNumber(BigInteger.valueOf(1001));
        Set<String> set = new HashSet<>();
        set.add("z");
        set.add("a");
        set.add("abc");
        set.add("abcd");
        Optional<Set<String>> description = Optional.of(set);

        try {
            Opad o = (Opad) AbstractProduct.make(ProductType.OPAD, opadNum, description);
            System.out.println(o.toString());
        }
        catch (ProductException e) {
            System.out.println("Exception Thrown: " + e.getErrorCode() + "\n");
        }
    }

    @Test
    public void testEquals() {
        SerialNumber num1 = new SerialNumber(new BigInteger("12"));
        SerialNumber num2 = new SerialNumber(new BigInteger("12"));

        Opod o1 = new Opod(num1, null);
        Opod o2 = new Opod(num2, null);

        Assert.assertTrue(o1.equals(o2));
    }

    @Test
    public void testExchangeBuilder() {
        SerialNumber s1 = new SerialNumber(BigInteger.valueOf(1));
        SerialNumber s2 = new SerialNumber(BigInteger.valueOf(2));
        SerialNumber s3 = new SerialNumber(BigInteger.valueOf(3));

        Exchange.Builder builder = new Exchange.Builder();
        builder.addCompatible(s1);
        builder.addCompatible(s2);

        // There should be 2 serials in the set
        Exchange exchange = builder.build();
        Assert.assertEquals(exchange.getCompatableProducts().size(), 2);

        // Changing the builder should not change the exchange
        // builder's size should now be 3
        // exchange size should remain at 2
        builder.addCompatible(s3);
        Assert.assertEquals(builder.getCompatibleProducts().size(), 3);
        Assert.assertEquals(exchange.getCompatableProducts().size(), 2);


        for (SerialNumber serialNumber : exchange.getCompatableProducts()) {
            System.out.println("Compatible serial: " + serialNumber.getSerialNumber().toString());
        }
    }

    @Test
    public void testRefundBuilder() {
        BigInteger s1 = BigInteger.valueOf(1234);
        BigInteger s2 = BigInteger.valueOf(5678);

        Refund.Builder builder = new Refund.Builder();
        try {
            builder.setRMA(s1);
        }
        catch (RequestException e) {
            System.out.println("Exception: " + e.toString());
        }

        Refund refund = builder.build();
        Assert.assertEquals(refund.getRMA(), builder.getRMA());

        try {
            builder.setRMA(s2);
        }
        catch (RequestException e) {
            System.out.println("Exception: " + e.toString());
        }

        // When you change the builder's rma, the Refund rma should remain the same
        Assert.assertNotEquals(refund.getRMA(), builder.getRMA());
    }
}