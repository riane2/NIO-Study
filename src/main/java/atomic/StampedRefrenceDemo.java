package atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

public class StampedRefrenceDemo {

    static AtomicStampedReference<Integer> reference = new AtomicStampedReference<>(19, 0);

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            int stamp = reference.getStamp();
            new Thread(() -> {
                while (true) {
                    Integer balance = reference.getReference();
                    if (balance < 20) {
                        if (reference.compareAndSet(balance, balance + 20, stamp, stamp + 1)) {
                            System.out.println("当前账户余额：" + balance + ",少于20块，一次性充值20元");
                            break;
                        }
                    } else {
                        System.out.println("当前账户余额：" + balance + ",多于20块");
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        //消费线程
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                while (true) {
                    Integer balance = reference.getReference();
                    int stamp = reference.getStamp();
                    if (balance > 10) {
                        if (reference.compareAndSet(balance, balance - 10, stamp, stamp + 1)) {
                            System.out.println("当前账户余额：" + balance + ",多于10块，一次性消费10元");
                            break;
                        }
                    } else {
                        System.out.println("当前账户余额不足：" + balance);
                        break;
                    }
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
