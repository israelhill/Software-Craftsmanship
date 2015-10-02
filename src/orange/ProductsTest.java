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
        Assert.assertEquals(exchange.getCompatibleProducts().size(), 2);

        // Changing the builder should not change the exchange
        // builder's size should now be 3
        // exchange size should remain at 2
        builder.addCompatible(s3);
        Assert.assertEquals(builder.getCompatibleProducts().size(), 3);
        Assert.assertEquals(exchange.getCompatibleProducts().size(), 2);

        //Exchange should remain immutable
        Set<SerialNumber> products;
        products = exchange.getCompatibleProducts();
        products.add(new SerialNumber(BigInteger.valueOf(23)));
        Assert.assertEquals(exchange.getCompatibleProducts().size(), 2);

    }

    @Test
    public void testRefundBuilder() throws RequestException {
        BigInteger s1 = BigInteger.valueOf(1234);
        BigInteger s2 = BigInteger.valueOf(5678);

        Refund.Builder builder = new Refund.Builder();
        builder.setRMA(s1);

        Refund refund = builder.build();
        Assert.assertEquals(refund.getRMA(), builder.getRMA());

        builder.setRMA(s2);

        // When you change the builder's rma, the Refund rma should remain the same
        Assert.assertNotEquals(refund.getRMA(), builder.getRMA());
    }

    @Test
    public void testOpodExhchange() {
        SerialNumber num = new SerialNumber(BigInteger.valueOf(1000));
        Opod tOPod = new Opod(num, null);

        SerialNumber s1 = new SerialNumber(BigInteger.valueOf(1032));
        SerialNumber s2 = new SerialNumber(BigInteger.valueOf(1244));

        //do not add any compatible products, status should be FAIL for first test
        Exchange.Builder builder = new Exchange.Builder();
        Exchange exchange = builder.build();
        RequestStatus status = new RequestStatus();

        try {
            tOPod.process(exchange, status);
        } catch (ProductException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(status.getStatusCode(), RequestStatus.StatusCode.FAIL);
        Assert.assertEquals(Optional.empty(), status.getResult());

        //this one should pass
        Exchange.Builder builder2 = new Exchange.Builder();
        builder2.addCompatible(s1).addCompatible(s2);
        Exchange exchange2 = builder2.build();
        RequestStatus status2 = new RequestStatus();

        try {
            tOPod.process(exchange2, status2);
        } catch (ProductException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(status2.getStatusCode(), RequestStatus.StatusCode.OK);
        Assert.assertEquals(Optional.of(BigInteger.valueOf(1032)), status2.getResult());

    }

    @Test
    public void testOpadExchange() {
        SerialNumber num = new SerialNumber(BigInteger.valueOf(1048));
        Opad tOPad = new Opad(num, null);

        SerialNumber s1 = new SerialNumber(BigInteger.valueOf(1032));
        SerialNumber s2 = new SerialNumber(BigInteger.valueOf(1244));

        Exchange.Builder builder = new Exchange.Builder();
        builder.addCompatible(s1).addCompatible(s2);
        Exchange exchange = builder.build();

        RequestStatus status = new RequestStatus();

        try {
            tOPad.process(exchange, status);
        } catch (ProductException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(status.getStatusCode(), RequestStatus.StatusCode.OK);
        Assert.assertEquals(Optional.of(BigInteger.valueOf(1032)), status.getResult());
    }
    
    @Test
    public void testOphoneExchange() {
        SerialNumber num = new SerialNumber(BigInteger.valueOf(5));
        Ophone phone = new Ophone(num, null);

        SerialNumber s1 = new SerialNumber(BigInteger.valueOf(1));
        SerialNumber s2 = new SerialNumber(BigInteger.valueOf(6));
        SerialNumber s3 = new SerialNumber(BigInteger.valueOf(7));
        SerialNumber s4 = new SerialNumber(BigInteger.valueOf(10));

        Exchange.Builder builder = new Exchange.Builder();
        builder.addCompatible(s1).addCompatible(s2).addCompatible(s3).addCompatible(s4);
        Exchange exchange = builder.build();

        RequestStatus status = new RequestStatus();

        try {
            phone.process(exchange, status);
        } catch (ProductException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(status.getStatusCode(), RequestStatus.StatusCode.OK);

        Exchange.Builder builder2 = new Exchange.Builder();
        Exchange exchange2 = builder2.build();

        RequestStatus status2 = new RequestStatus();

        try {
            phone.process(exchange2, status2);
        } catch (ProductException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(RequestStatus.StatusCode.FAIL, status2.getStatusCode());
        Assert.assertEquals(Optional.of(BigInteger.valueOf(6)), status.getResult());
    }

    @Test
    public void testOwatchExchange() {
        SerialNumber num = new SerialNumber(BigInteger.valueOf(6));
        Owatch w = new Owatch(num, null);

        //None of these are greater than w, the exchange should fail
        SerialNumber s1 = new SerialNumber(BigInteger.valueOf(5));
        SerialNumber s2 = new SerialNumber(BigInteger.valueOf(6));
        SerialNumber s3 = new SerialNumber(BigInteger.valueOf(3));
        SerialNumber s4 = new SerialNumber(BigInteger.valueOf(1));

        Exchange.Builder builder = new Exchange.Builder();
        builder.addCompatible(s1).addCompatible(s2).addCompatible(s3).addCompatible(s4);
        Exchange exchange = builder.build();

        RequestStatus status = new RequestStatus();

        try {
            w.process(exchange, status);
        } catch (ProductException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(RequestStatus.StatusCode.FAIL, status.getStatusCode());
        Assert.assertEquals(Optional.empty(), status.getResult());
    }

    @Test
    public void testOtvExchange() {
        SerialNumber num = new SerialNumber(BigInteger.valueOf(10));
        Otv tv = new Otv(num, null);

        //None of these are greater than w, the exchange should fail
        SerialNumber s1 = new SerialNumber(BigInteger.valueOf(20));
        SerialNumber s2 = new SerialNumber(BigInteger.valueOf(30));
        SerialNumber s3 = new SerialNumber(BigInteger.valueOf(40));
        //SerialNumber s4 = new SerialNumber(BigInteger.valueOf(1));

        Exchange.Builder builder = new Exchange.Builder();
        builder.addCompatible(s1).addCompatible(s2).addCompatible(s3);
        Exchange exchange = builder.build();

        RequestStatus status = new RequestStatus();

        try {
            tv.process(exchange, status);
        } catch (ProductException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(RequestStatus.StatusCode.OK, status.getStatusCode());
        //Avg is 26, result should be 20
        Assert.assertEquals(Optional.of(BigInteger.valueOf(20)), status.getResult());
    }

    @Test
    public void testOpodRefund() throws RequestException, ProductException {
        SerialNumber num = new SerialNumber(BigInteger.valueOf(72));
        Opod o = new Opod(num, null);

        BigInteger s1 = BigInteger.valueOf(48);

        Refund.Builder builder = new Refund.Builder();
        builder.setRMA(s1);
        Refund refund = builder.build();

        RequestStatus status = new RequestStatus();

        o.process(refund, status);

        Assert.assertEquals(RequestStatus.StatusCode.OK, status.getStatusCode());
    }

    @Test
    public void testOpadRefund() throws RequestException, ProductException {
        SerialNumber num = new SerialNumber(BigInteger.valueOf(72));
        Opad o = new Opad(num, null);

        BigInteger s1 = BigInteger.valueOf(48);

        Refund.Builder builder = new Refund.Builder();
        builder.setRMA(s1);
        Refund refund = builder.build();

        RequestStatus status = new RequestStatus();

        o.process(refund, status);

        Assert.assertEquals(RequestStatus.StatusCode.OK, status.getStatusCode());
    }

    @Test
    public void testOphoneRefund() throws RequestException, ProductException {
        SerialNumber num = new SerialNumber(BigInteger.valueOf(73));
        Ophone o = new Ophone(num, null);

        BigInteger s1 = BigInteger.valueOf(48);

        Refund.Builder builder = new Refund.Builder();
        builder.setRMA(s1);
        Refund refund = builder.build();

        RequestStatus status = new RequestStatus();

        try {
            o.process(refund, status);
        }
        catch (ProductException e) {
            e.printStackTrace();
        }

        //Ophone always fails. Ophone is odd -- left shit produces even
        Assert.assertNotEquals(RequestStatus.StatusCode.OK, status.getStatusCode());
    }

    @Test
    public void testOtvRefund() throws RequestException, ProductException {
        SerialNumber num = new SerialNumber(BigInteger.valueOf(73));
        Otv o = new Otv(num, null);

        BigInteger s1 = BigInteger.valueOf(-48);

        Refund.Builder builder = new Refund.Builder();
        builder.setRMA(s1);
        Refund refund = builder.build();

        RequestStatus status = new RequestStatus();

        try {
            o.process(refund, status);
        }
        catch (ProductException e) {
            e.printStackTrace();
        }

        //negative values result in a failure.
        Assert.assertNotEquals(RequestStatus.StatusCode.OK, status.getStatusCode());

        BigInteger s2 = BigInteger.valueOf(48);

        Refund.Builder builder2 = new Refund.Builder();
        builder2.setRMA(s2);
        Refund refund2 = builder2.build();

        RequestStatus status2 = new RequestStatus();

        try {
            o.process(refund2, status2);
        }
        catch (ProductException e) {
            e.printStackTrace();
        }

        //positive rma results in success
        Assert.assertEquals(RequestStatus.StatusCode.OK, status2.getStatusCode());
    }
}