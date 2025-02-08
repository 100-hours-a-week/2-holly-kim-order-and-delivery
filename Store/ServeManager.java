package Store;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServeManager implements Runnable {
    private LinkedBlockingQueue<String> orderQueue;
    private LinkedBlockingQueue<String> deliveryQueue;
    private AtomicBoolean shutdownFlag;

    public ServeManager(LinkedBlockingQueue<String> orderQueue, LinkedBlockingQueue<String> deliveryQueue, AtomicBoolean shutdownFlag){
        this.deliveryQueue = deliveryQueue;
        this.orderQueue=orderQueue;
        this.shutdownFlag=shutdownFlag;
    }

//    private Queue<String> deliveryQueue = new LinkedList<>();
//    private Queue<String> orderQueue = new LinkedList<>();
//    private AtomicBoolean shutdownFlag;

//    public ServeManager(Queue<String> orderQueue, Queue<String> deliveryQueue, AtomicBoolean shutdownFlag){
//        this.deliveryQueue = deliveryQueue;
//        this.orderQueue=orderQueue;
//        this.shutdownFlag=shutdownFlag;
//    }

    public void run() {
        while (!shutdownFlag.get()) {
            //synchronized (deliveryQueue) {
                if (!deliveryQueue.isEmpty()) {
                    String food = "";
                    food = deliveryQueue.poll();

                    System.out.println("🚶🏻‍♂️[서빙 중] " + food + "서빙 중...");
                    try {
                        Thread.sleep(4000); // 서빙 시간 4초
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("️🍽️[서빙 완료] " + food + " 가 서빙되었습니다. 맛있게 드세요. ");
                }
                checkAndShutDown();
            //}
        }
    }
    private void checkAndShutDown(){
        if(orderQueue.isEmpty() && deliveryQueue.isEmpty()){
            if(shutdownFlag.compareAndSet(false,true)){
                System.out.println("모든 작업이 끝났습니다.");
            }
        }
    }
}
