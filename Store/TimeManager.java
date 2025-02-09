package Store;

import java.util.concurrent.atomic.AtomicBoolean;

class TimeManager implements Runnable{
    private int time = 0;
    private AtomicBoolean shutdownFlag;

    public TimeManager(AtomicBoolean shutdownFlag) {
        this.shutdownFlag=shutdownFlag;
    }

    public void run() {
        while (!shutdownFlag.get()) {
            System.out.println( time + "초 경과");
            time++;
            try {
                Thread.sleep(1000); // 1초 흐름
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
