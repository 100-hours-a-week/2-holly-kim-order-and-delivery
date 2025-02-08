package Store;

public class MainItem extends Item{

    public MainItem(String name, int price){
        super(name, price);
    }


    public void displayMenu(){
        System.out.println(String.format("🍔Main Menu %2d. %s / %,d won ", id, name, price));
    }
}
