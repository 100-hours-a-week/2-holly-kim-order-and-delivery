package store.core;

public class PromotionItem extends MainItem {

    public PromotionItem(String name, int price, double promotionRate) {
        super(name, price);
        this.promotionRate = promotionRate;
    }

    public PromotionItem(String name, int price) {
        super(name, price);
        this.promotionRate=0.1;
    }

    public void displayMenu() {
        System.out.println(String.format("🍔Main Menu %2d. %s / %,d won (해당 버거 %d %% 할인 쿠폰 지급)", id, name, price, (int)(promotionRate*100)));
    }


}
