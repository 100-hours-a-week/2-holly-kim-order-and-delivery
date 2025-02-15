package store.process;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServeTask implements Runnable {
    private LinkedBlockingQueue<String> orderQueue;
    private LinkedBlockingQueue<String> deliveryQueue;
    private AtomicBoolean shutdownFlag;
    private CountDownLatch latch;

    public ServeTask(LinkedBlockingQueue<String> deliveryQueue, AtomicBoolean shutdownFlag, CountDownLatch latch) {
        this.deliveryQueue = deliveryQueue;
        this.shutdownFlag = shutdownFlag;
        this.latch=latch;
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
                // 주문 하나가 완료되었으므로 CountDownLatch 감소
                latch.countDown();
            }
        }
    }
}
