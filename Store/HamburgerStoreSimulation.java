package store;

import store.process.CookTask;
import store.service.OrderService;
import store.process.ServeTask;
import store.process.TimeTask;

import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class HamburgerStoreSimulation {
    public static void main(String[] args) {

        LinkedBlockingQueue<String> orderQueue = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<String> deliveryQueue = new LinkedBlockingQueue<>();
        AtomicBoolean shutdownFlag = new AtomicBoolean(false);
        OrderService storeOrder = new OrderService();

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

        ExecutorService executor = Executors.newFixedThreadPool(4);

        orderQueue=storeOrder.getOrderQueue();
        System.out.println("orderQueue = " + orderQueue);
        int totalOrders = orderQueue.size();
        System.out.println("totalOrders = " + totalOrders);
        CountDownLatch latch = new CountDownLatch(totalOrders); // 주문 개수 추적을 위한 CountDownLatch

        TimeTask timeJob = new TimeTask(shutdownFlag);
        CookTask cookJob1 = new CookTask(orderQueue, deliveryQueue, shutdownFlag);
        CookTask cookJob2 = new CookTask(orderQueue, deliveryQueue, shutdownFlag);
        ServeTask serveJob = new ServeTask(deliveryQueue, shutdownFlag, latch);

        executor.execute(new TimeTask(shutdownFlag));
        // 요리는 오래걸리므로 2개의 스레드에서 실행
        for (int i = 0; i < 2; i++) {
            executor.execute(new CookTask(orderQueue, deliveryQueue, shutdownFlag));
        }
        executor.execute(new ServeTask(deliveryQueue, shutdownFlag, latch));

        // 모든 주문이 서빙될 때까지 대기
        try {
            latch.await();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("모든 주문이 서빙되었습니다.");
        shutdownFlag.set(true);
        executor.shutdown();
        try {
            boolean finished=executor.awaitTermination(3, TimeUnit.MINUTES);
            if (!finished) {
                System.out.println("모든 작업이 끝나지 않았지만 타임아웃이 발생했습니다.");
            }
            
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        storeOrder.closeScanner();
        System.out.println("================== Thank you for visiting McDonald's =====================");
        executor.shutdownNow();
    }
}