package pool;

import java.util.concurrent.*;

public class ExceptionThreadPool {

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

    static class TraceThreadPoolExcutor extends ThreadPoolExecutor {

        public TraceThreadPoolExcutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        @Override
        public void execute(Runnable command) {
            super.execute(wrap(command, clientTrace()));
        }

        @Override
        public Future<?> submit(Runnable task) {
            return super.submit(wrap(task, clientTrace()));
        }

        private Exception clientTrace() {
            return new Exception("Client stack trace");
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println("准备执行任务：" + t.getName());
        }

        private Runnable wrap(final Runnable task, final Exception clientStack) {
            return () -> {
                try {
                    task.run();
                } catch (Exception ex) {
                    clientStack.printStackTrace();
                    // ex.printStackTrace();
                    throw ex;
                }
            };
        }

    }


    public static void main(String[] args) {
        /*ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        for (int i = 0; i < 5; i++) {
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
        }
        poolExecutor.shutdown();*/

        ThreadPoolExecutor poolExecutor = new TraceThreadPoolExcutor(5, 5, 0L,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        for (int i = 0; i < 5; i++) {
            DivTask divTask = new DivTask(100, i);
            //poolExecutor.submit(divTask);
            poolExecutor.execute(divTask);
        }
        poolExecutor.shutdown();
    }
}
