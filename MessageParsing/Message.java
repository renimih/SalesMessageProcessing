package MessageParsing;

import MessageProcessor.ProductStats;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by renim on 16/09/2018.
 * Abstract class representing a basic sales message
 */
public abstract class Message {

    private String productType;
    private BigDecimal price;

    Message(String productType, String price) throws MessageFormatException {
        this.productType = productType;
        this.price = checkValidPrice(price);
    }

    public BigDecimal getPrice() {
        return price;
    }

    private String getProductType() {
        return productType;
    }

    /**
     * checks if a price is valid (i.e. starts with '£' for pounds or ends with 'p' for pence)
     * @param price the price as a String
     * @return the price as BigDecimal if it is valid
     * @throws MessageFormatException if the price is not valid
     */
    private BigDecimal checkValidPrice(String price) throws MessageFormatException {
        if (price.startsWith("£")) {
            // if price is in pounds, multiply by 100
            return (new BigDecimal(price.substring(1))).multiply( new BigDecimal(100) );
        }
        else if (price.endsWith("p")) {
            return new BigDecimal(price.substring(0, (price.length() - 1)));
        }
        else {
            // if not specified if price is in pence or pounds throw exception
            throw new MessageFormatException("%s is invalid price. " +
                    "Price has to be in pounds or pence (e.g. £50 or 50p)", price);
        }
    }

    /**
     * records the message effects in a report
     * @param report contains the products and product statistics
     * @throws MessageFormatException if the message cannot be recorded
     */
    public abstract void record(Map<String, ProductStats> report) throws MessageFormatException;

    /**
     * returns the Product statistics object report if the product is in the report
     * or adds the product to the report if and returns it
     * @param report contains the products and product statistics
     * @return ProductStats for the product
     */
    ProductStats getOrCreateProductReport(Map<String, ProductStats> report) {
        ProductStats productStats = report.get(getProductType());
        if (productStats == null) {
            productStats = new ProductStats();
            report.put(getProductType(), productStats);
        }
        return productStats;
    }
}
