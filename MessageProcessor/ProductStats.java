package MessageProcessor;

import MessageParsing.PriceAdjustmentMessage;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by renim on 16/09/2018.
 * Represents the sales statistics for a product type
 */
public class ProductStats {

    private int quantity;
    private BigDecimal totalValue;
    private ArrayList<PriceAdjustmentMessage> productAdjustments;

    public ProductStats() {
        quantity = 0;
        totalValue = new BigDecimal(0);
        productAdjustments = new ArrayList<>();
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    ArrayList<PriceAdjustmentMessage> getProductAdjustments() {
        return productAdjustments;
    }

    public void addToQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void addAdjustment(PriceAdjustmentMessage adjustmentMessage) {
        productAdjustments.add(adjustmentMessage);
    }
}
