package atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class RefrenceDemo {

    static AtomicReference<Integer> reference = new AtomicReference<>(19);

    static class ChongZhi implements Runnable {

        @Override
        public void run() {
            while (true) {
                Integer balance = reference.get();
                if (balance < 20) {

                    if (reference.compareAndSet(balance, balance + 20)) {
                        System.out.println("当前账户余额：" + balance + ",少于20块，一次性充值20元");
                        break;
                    }
                } else {
                    System.out.println("当前账户余额：" + balance + ",多于20块");
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 4; i++) {
            if (i != 4) {
                threadPool.execute(new ChongZhi());
            }
            if (i == 4) {
                threadPool.execute(() -> {
                    for (int j = 0; j < 100; j++) {
                        while (true) {
                            Integer balance = reference.get();
                            if (balance > 10) {
                                if (reference.compareAndSet(balance, balance - 10)) {
                                    System.out.println("当前账户余额：" + balance + ",多于10块，一次性消费10元");
                                    break;
                                }
                            } else {
                                System.out.println("当前账户余额不足：" + balance);
                                break;
                            }
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

        //消费线程
        /*new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                while (true) {
                    Integer balance = reference.get();
                    if (balance > 10) {
                        if (reference.compareAndSet(balance, balance - 10)) {
                            System.out.println("当前账户余额：" + balance + ",多于10块，一次性消费10元");
                            break;
                        }
                    } else {
                        System.out.println("当前账户余额不足：" + balance);
                        break;
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/

    }

}
