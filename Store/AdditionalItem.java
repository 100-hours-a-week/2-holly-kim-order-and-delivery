package Store;

public class AdditionalItem extends Item {
    public AdditionalItem(String name, int price) {
        super(name, price);
    }

    public void displayMenu() {
        System.out.println(String.format("%d. %s (+%,d won)", this.id, name, price));
    }
}
