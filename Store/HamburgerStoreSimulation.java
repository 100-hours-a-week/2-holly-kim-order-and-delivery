package Store;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class HamburgerStoreSimulation {
    public static void main(String[] args) {

        LinkedBlockingQueue<String> orderQueue = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<String> deliveryQueue = new LinkedBlockingQueue<>();
        AtomicBoolean shutdownFlag = new AtomicBoolean(false);

        OrderManager storeOrder = new OrderManager();
        storeOrder.welcome();
        Scanner scanner = storeOrder.getScanner();
        boolean ordering = true;
        while (ordering) {
            storeOrder.mainOrder(); // 메인 메뉴 주문
            storeOrder.selectSet(); // 세트 메뉴 여부 선택
            storeOrder.drinkOrder(); // 음료 주문
            storeOrder.displayOrder(); // 현재 주문할 상품 내용 출력
            storeOrder.selectQuantity(); // 해당 상품 수량 선택

            System.out.print("다른 상품을 추가 주문하시겠습니까? (Y / N): ");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("n")) {
                ordering = false;
                System.out.println("주문을 종료합니다. 계산을 진행하겠습니다.");
                orderQueue=storeOrder.getOrderQueue();
                System.out.println("orderQueue = " + orderQueue);
                System.out.println("--------------------------------------------------------------------------");
            } else if (answer.equalsIgnoreCase("y")) {
                System.out.println("현재 상품이 장바구니에 담겼습니다.");
                System.out.println("--------------------------------------------------------------------------");
            } else {
                System.out.print("다시 입력해주세요. (Y / N) : ");
            }
        }
        storeOrder.pay();
        storeOrder.closeScanner();

        ExecutorService executor = Executors.newFixedThreadPool(3);
        TimeManager timeJob = new TimeManager(shutdownFlag);
        CookManager cookJob = new CookManager(orderQueue, deliveryQueue, shutdownFlag);
        ServeManager serveJob = new ServeManager(orderQueue, deliveryQueue, shutdownFlag);

        executor.execute(timeJob);
        executor.execute(cookJob);
        executor.execute(serveJob);

        executor.shutdown();
        try {
            boolean finished=executor.awaitTermination(3, TimeUnit.MINUTES);
            if (!finished) {
                System.out.println("모든 작업이 끝나지 않았지만 타임아웃이 발생했습니다.");
            }
            System.out.println("Finished? " + finished);
            System.out.println("================== Thank you for visiting McDonald's =====================");
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        executor.shutdownNow();
    }
}