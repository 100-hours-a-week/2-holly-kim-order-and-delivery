package store.core;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Item> items;
    private int totalPrice;
    private int quantity; // 주문 수량
    private int perPrice;

    public Order(List<Item> items, int quantity, int perPrice) {
        this.items = new ArrayList<>(items); // 리스트 복사
        this.quantity = quantity;
        this.perPrice = perPrice;
        this.totalPrice = calculateTotalPrice();
    }

    private int calculateTotalPrice() {
        return perPrice*quantity;
   }

    public List<Item> getItems() {
        return items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getPerPrice() {
        return perPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
