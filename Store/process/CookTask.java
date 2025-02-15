package store.process;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class CookTask implements Runnable {
    private LinkedBlockingQueue<String> orderQueue;
    private LinkedBlockingQueue<String> deliveryQueue;
    private AtomicBoolean shutdownFlag;

    public CookTask(LinkedBlockingQueue<String> orderQueue, LinkedBlockingQueue<String> deliveryQueue, AtomicBoolean shutdownFlag) {
        this.orderQueue = orderQueue;
        this.deliveryQueue = deliveryQueue;
        this.shutdownFlag = shutdownFlag;
    }

    public void run() {
        while (!shutdownFlag.get()) {
            if (!orderQueue.isEmpty()) {
                String order = orderQueue.poll();
                System.out.println("👨🏻‍🍳[준비 중] " + order + "를 준비하고 있습니다.");
                // 요리 시간 5초
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("☑️[준비 완료] " + order + "가 준비되었습니다.");
                deliveryQueue.add(order);
                System.out.println("📦[서빙 대기] " + order + "가 서빙을 기다리고 있습니다.");
            }
        }
    }
}
