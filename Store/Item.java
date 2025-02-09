package Store;

public abstract class Item {
    String name;
    int price;
    int id;

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
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
