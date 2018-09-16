package MessageParsing;

import MessageProcessor.ProductStats;

import java.util.Map;

/**
 * Created by renim on 16/09/2018.
 * Represents a sales message for a single product count
 */
class SingleSaleMessage extends Message {

    SingleSaleMessage(String message[]) throws MessageFormatException {
        super(message[0], message[1]);
    }

    @Override
    public void record(Map<String, ProductStats> report) {
        ProductStats productStats = getOrCreateProductReport(report);
        productStats.addToQuantity(1);
        productStats.setTotalValue(productStats.getTotalValue().add(getPrice()));
    }
}
