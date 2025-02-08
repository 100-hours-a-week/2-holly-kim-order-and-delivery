package Store;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {

        LinkedBlockingQueue<String> orderQueue = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<String> deliveryQueue = new LinkedBlockingQueue<>();

//        Queue<String> orderQueue = new LinkedList<>();
//        Queue<String> deliveryQueue = new LinkedList<>();
        AtomicBoolean shutdownFlag = new AtomicBoolean(false);

        Order storeOrder = new Order();
        storeOrder.welcome();
        Scanner scanner = storeOrder.getScanner();
        boolean ordering = true;
        while (ordering) {
            storeOrder.mainOrder(); // 메인 메뉴 주문
            storeOrder.selectSet(); // 세트 메뉴 여부 선택
            storeOrder.drinkOrder(); // 음료 주문
            storeOrder.selectQuantity(); // 해당 상품 수량 선택

            System.out.print("다른 상품을 추가 주문하시겠습니까? (Y / N): ");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("n")) {
                ordering = false;
                System.out.println("주문을 종료합니다. 계산을 진행하겠습니다.");
                orderQueue=storeOrder.getOrderQueue();
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
            boolean finished=executor.awaitTermination(40, TimeUnit.SECONDS);
            System.out.println("Finished? " + finished);
            System.out.println("================== Thank you for visiting McDonald's =====================");
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        executor.shutdownNow();
    }
}