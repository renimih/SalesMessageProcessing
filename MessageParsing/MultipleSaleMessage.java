package MessageParsing;

import MessageProcessor.ProductStats;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by renim on 16/09/2018.
 * Represents a sales message for quantities larger than one
 */
class MultipleSaleMessage extends Message {

    private Integer quantity;

    MultipleSaleMessage(String message[]) throws MessageFormatException {
        super(message[1], message[2]);
        this.quantity = checkValidQuantity(message[0]);
    }

    Integer getQuantity() {
        return quantity;
    }

    /**
     * checks if the provided quantity is a valid integer
     * @param word the String representing the quantity
     * @return Integer value of the quantity if valid
     * @throws MessageFormatException if the quantity is not a valid integer
     */
    private Integer checkValidQuantity(String word) throws MessageFormatException {
        try {
            return new Integer(word);
        } catch (NumberFormatException e) {
            throw new MessageFormatException("%s is invalid quantity." +
            "Quantity has to be a valid integer", word);
        }
    }

    @Override
    public void record(Map<String, ProductStats> report) {
        ProductStats productStats = getOrCreateProductReport(report);
        productStats.addToQuantity(quantity);
        productStats.setTotalValue(productStats.getTotalValue().add(getPrice().multiply(new BigDecimal(quantity))));
    }
}
