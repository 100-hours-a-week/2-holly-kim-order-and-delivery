package Store;

public class PaymentManager {
    private int totalPrice;
    private int currentPrice;

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice){
        this.currentPrice=currentPrice;
    }

    public void resetCurrentPrice() {
        currentPrice = 0;
    }

    public int add(int amount) {
        currentPrice += amount;
        return totalPrice;
    }

    public int multiply(int order_count, double discountRate) {
        totalPrice += currentPrice * order_count;
        return totalPrice;
    }
}
