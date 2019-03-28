package java8.complete;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {

    public static void main(String[] args) {
        CompletableFuture<Integer> future = new CompletableFuture<>();

        /**
         * 启动线程执行，但是当运行到线程的run方法的future.get()时由于future中数据还没有准备好，所以只能阻塞等待
         */
        new Thread(new AskThread(future)).start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 完成future的赋值操作并标记为完成状态，之后future.get()开始运行
         */
        future.complete(30);

    }

    static class AskThread implements Runnable {
        CompletableFuture<Integer> future = null;

        AskThread(CompletableFuture<Integer> future) {
            this.future = future;
        }

        @Override
        public void run() {
            try {
                int res = future.get() * 2;
                System.out.println(res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

