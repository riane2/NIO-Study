package pool;

import java.util.concurrent.*;

public class ExtendThreadPool {

    static class MyTask implements Runnable {

        private String name;

        MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getId() + "," + Thread.currentThread().getName() + "," + this.name);
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    static class DivTask implements Runnable {
        int a;
        int b;

        DivTask(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {

            System.out.println(a / b);

        }
    }

    public static void main(String[] args) {
        System.out.println("可用CPU的数目为：" + Runtime.getRuntime().availableProcessors());
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>()) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                //System.out.println("准备执行任务：" + t.getName() + "," + ((MyTask) r).name);
                System.out.println("准备执行任务：" + t.getName());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                //System.out.println("结束执行任务：" + ((MyTask) r).name);
                System.out.println("结束执行任务：");
            }

            @Override
            protected void terminated() {
                System.out.println("整个线程池退出");
            }
        };
        for (int i = 0; i < 5; i++) {
            MyTask myTask = new MyTask("Task" + i);
            poolExecutor.execute(myTask);
        }
        /*for (int i = 0; i < 5; i++) {
            DivTask divTask = new DivTask(100, i);
            //poolExecutor.submit(divTask);//这种方式没法获取线程内部的异常
            //poolExecutor.execute(divTask);//这种方式可以获取线程内部的异常
            //这种方式也可以获取线程内部的异常
            Future<?> future = poolExecutor.submit(divTask);
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }*/
        poolExecutor.shutdown();
    }
}
