package MessageParsing;

import MessageProcessor.ProductStats;
import org.junit.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by renim on 16/09/2018.
 * Unit test for message processing
 */
public class MessageTest {

    private String[] singleSaleMessageArray;
    private Message singleSaleMessage;

    private String[] multipleSalesMessageArray;
    private Message multipleSalesMessage;

    private String[] priceAjdMessageArray;
    private Message priceAjdMessage;

    private BigDecimal defaultPrice = new BigDecimal(500);
    private String productType = "apples";
    private Integer quantity = 10;
    private MessageOperation operation = MessageOperation.ADD;
    private Map<String, ProductStats> report;
    private String pencePrice = defaultPrice + "p";

    @Before
    public void setUp() throws MessageFormatException {
        singleSaleMessageArray = new String[]{productType, pencePrice};
        singleSaleMessage = new SingleSaleMessage(singleSaleMessageArray);

        multipleSalesMessageArray = new String[]{quantity.toString(), productType, pencePrice};
        multipleSalesMessage = new MultipleSaleMessage(multipleSalesMessageArray);

        priceAjdMessageArray = new String[]{operation.toString(), pencePrice, productType};
        priceAjdMessage = new PriceAdjustmentMessage(priceAjdMessageArray);

        report = new HashMap<>();
    }

    // checkValid price is called in the constructor so the following tests create a new message to test it
    @Test
    public void test_checkValidPrice_pound() throws MessageFormatException {
        String poundPrice = "£" + defaultPrice.divide(new BigDecimal(100));
        String[] messageArray = {productType, poundPrice};
        Message message = new SingleSaleMessage(messageArray);
        assertEquals(defaultPrice, message.getPrice());
    }

    @Test
    public void test_checkValidPrice_pence() throws MessageFormatException {
        Message message = new SingleSaleMessage(singleSaleMessageArray);
        assertEquals(defaultPrice, message.getPrice());
    }

    @Test(expected=MessageFormatException.class)
    public void test_checkValidPrice_error() throws MessageFormatException {
        String[] messageArray = {productType, defaultPrice.toString()};
        new SingleSaleMessage(messageArray);
    }

    @Test
    public void test_checkValidQuantity() throws MessageFormatException {
        MultipleSaleMessage message = new MultipleSaleMessage(multipleSalesMessageArray);
        assertEquals(quantity, message.getQuantity());
    }

    @Test(expected=MessageFormatException.class)
    public void test_checkInValidQuantity() throws MessageFormatException {
        String[] messageArray = {"invalidQuantity", productType, defaultPrice + "p"};
        new MultipleSaleMessage(messageArray);
    }

    @Test
    public void test_checkValidOperation() throws MessageFormatException {
        PriceAdjustmentMessage message = new PriceAdjustmentMessage(priceAjdMessageArray);
        assertEquals(operation, message.getOperation());
    }

    @Test(expected=MessageFormatException.class)
    public void test_checkInValidOperation() throws MessageFormatException {
        String[] messageArray = {"InvalidOperation", defaultPrice + "p", productType};
        new PriceAdjustmentMessage(messageArray);
    }


    @Test
    public void test_record_single() throws MessageFormatException {
        singleSaleMessage.record(report);

        assertEquals(1, report.keySet().size());
        assertTrue(report.containsKey(productType));
        assertEquals(1, report.get(productType).getQuantity());
        assertEquals(defaultPrice, report.get(productType).getTotalValue());
    }

    @Test
    public void test_record_multiple() throws MessageFormatException {
        multipleSalesMessage.record(report);

        assertEquals(1, report.keySet().size());

        assertTrue(report.containsKey(productType));
        assertEquals(quantity, Integer.valueOf(report.get(productType).getQuantity()));
        assertEquals(defaultPrice.multiply(new BigDecimal(quantity)), report.get(productType).getTotalValue());
    }

    @Test
    public void test_record_adjustment() throws MessageFormatException {
        singleSaleMessage.record(report);
        multipleSalesMessage.record(report);
        int totalQuantity = 1 + quantity;
        BigDecimal totalValue = defaultPrice.multiply(new BigDecimal(totalQuantity));

        assertEquals(1, report.keySet().size());
        assertTrue(report.containsKey(productType));
        assertEquals(totalQuantity, report.get(productType).getQuantity());
        assertEquals(totalValue, report.get(productType).getTotalValue());

        priceAjdMessage.record(report);

        BigDecimal adjustedTotalValue = totalValue.add(defaultPrice.multiply(new BigDecimal(totalQuantity)));

        // assert only total value has changed
        assertEquals(1, report.keySet().size());
        assertTrue(report.containsKey(productType));
        assertEquals(totalQuantity, report.get(productType).getQuantity());
        assertEquals(adjustedTotalValue, report.get(productType).getTotalValue());
    }

}
