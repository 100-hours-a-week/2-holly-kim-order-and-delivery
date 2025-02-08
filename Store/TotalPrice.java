package Store;

public class TotalPrice {
    private int totalPrice;
    private int currentPrice;

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void resetCurrentPrice() {
        currentPrice = 0;
    }

    public int add(int amount) {
        currentPrice += amount;
        return totalPrice;
    }

    public int multiply(int order_count, double discountRate) {
        totalPrice += currentPrice * (1 - discountRate) * order_count;
        return totalPrice;
    }

}
