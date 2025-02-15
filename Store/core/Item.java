package store.core;

public abstract class Item {
    String name;
    int price;
    int id;
    double promotionRate = 0;

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setPromotionRate(double promotionRate) {
        this.promotionRate = promotionRate;
    }

    public double getPromotionRate() {
        return promotionRate;
    }

    public int getPrice() {
        return price;
    }


    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String toString() {
        return this.name;
    }

    public void displayMenu() {}
}
