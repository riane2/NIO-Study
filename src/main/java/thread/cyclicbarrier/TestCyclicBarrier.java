package thread.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅栏：演示士兵操练，先10个士兵先集合完毕，才能开始任务，之后10个士兵必须都完成任务才表明任务结束
 */
public class TestCyclicBarrier {

    public static class ABB implements Runnable {

        private CyclicBarrier cyclicBarrier;

        private String name;

        ABB(CyclicBarrier cyclicBarrier, String name) {
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
        }

        public void run() {
            try {
                cyclicBarrier.await();
                Thread.sleep(1000);
                System.out.println(name + "任务完成");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {

        Thread[] threads = new Thread[10];
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new Runnable() {

            boolean flag = false;

            public void run() {
                if (flag) {
                    System.out.println("任务完毕");
                } else {
                    System.out.println("集合完毕");
                    flag = true;
                }
            }
        });

        for (int i = 0; i < 10; i++) {
            System.out.println("士兵" + i + "报道");
            threads[i] = new Thread(new ABB(cyclicBarrier, "士兵" + i));
            threads[i].start();
        }
    }
}
