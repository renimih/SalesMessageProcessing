package MessageParsing;

import MessageProcessor.ProductStats;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by renim on 16/09/2018.
 * Represents a price adjustment message
 */
public class PriceAdjustmentMessage extends Message {

    private MessageOperation operation;

    PriceAdjustmentMessage(String message[]) throws MessageFormatException{
        super(message[2], message[1]);
        this.operation = checkValidOperation(message[0]);
    }

    public MessageOperation getOperation() {
        return operation;
    }

    /**
     * checks if the supplied adjustment operation is valid
     * @param word the String for the adjustment operation
     * @return the MessageOperation enum representation of the operation
     * @throws MessageFormatException if the adjustment operation is not supported/invalid
     */
    private MessageOperation checkValidOperation(String word) throws MessageFormatException{
        try {
            return MessageOperation.valueOf(word.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new MessageFormatException("%s is invalid operation. " +
                    "Supported operations are %s", word, Arrays.toString(MessageOperation.values()));
        }
    }

    @Override
    public void record(Map<String, ProductStats> report) throws MessageFormatException {
        ProductStats productStats = getOrCreateProductReport(report);
        switch (operation) {
            case ADD:
                BigDecimal priceToAdd = getPrice().multiply(new BigDecimal(productStats.getQuantity()));
                productStats.setTotalValue(productStats.getTotalValue().add(priceToAdd));
                break;
            case SUBTRACT:
                BigDecimal priceToSubtract = getPrice().multiply(new BigDecimal(productStats.getQuantity()));
                if (priceToSubtract.compareTo(productStats.getTotalValue()) > 1){
                    throw new MessageFormatException("Price subtraction would cause the total value to become negative");
                }
                productStats.setTotalValue(productStats.getTotalValue().subtract(priceToSubtract));
                break;
            case MULTIPLY:
                if (getPrice().compareTo(new BigDecimal(0)) < 0){
                    throw new MessageFormatException("Price multiplier would cause the total value to become negative");
                }
                productStats.setTotalValue(productStats.getTotalValue().multiply(getPrice()));
                break;
        }
        productStats.addAdjustment(this);
    }
}
