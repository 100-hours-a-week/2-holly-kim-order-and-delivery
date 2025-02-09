package Store;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServeManager implements Runnable {
    private LinkedBlockingQueue<String> orderQueue;
    private LinkedBlockingQueue<String> deliveryQueue;
    private AtomicBoolean shutdownFlag;

    public ServeManager(LinkedBlockingQueue<String> orderQueue, LinkedBlockingQueue<String> deliveryQueue, AtomicBoolean shutdownFlag) {
        this.deliveryQueue = deliveryQueue;
        this.orderQueue = orderQueue;
        this.shutdownFlag = shutdownFlag;
    }

    public void run() {
        while (!shutdownFlag.get()) {
            if (!deliveryQueue.isEmpty()) {
                String food = "";
                food = deliveryQueue.poll();

                System.out.println("🚶🏻‍♂️[서빙 중] " + food + "서빙 중...");
                try {
                    Thread.sleep(2000); // 서빙 시간 2초
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("️🍽️[서빙 완료] " + food + " 가 서빙되었습니다. 맛있게 드세요. ");
            }
            checkAndShutDown();
        }
    }

//    private void checkAndShutDown() {
//        if (orderQueue.isEmpty() && deliveryQueue.isEmpty()) {
//            if (shutdownFlag.compareAndSet(false, true)) {
//                System.out.println("모든 작업이 끝났습니다.");
//            }
//        }
//    }
private void checkAndShutDown() {
    if (orderQueue.isEmpty() && deliveryQueue.isEmpty() && !Thread.currentThread().isInterrupted()) {
        try {
            Thread.sleep(1000); // 모든 작업이 완료되었는지 확인할 여유 시간 1초
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 다시 한 번 확인하여 안전하게 종료
        if (orderQueue.isEmpty() && deliveryQueue.isEmpty()) {
            if (shutdownFlag.compareAndSet(false, true)) {
                System.out.println("모든 작업이 끝났습니다.");
            }
        }
    }
}

}
