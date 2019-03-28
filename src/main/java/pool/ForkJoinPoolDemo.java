package pool;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.*;

public class ForkJoinPoolDemo {

    static class MyTask extends RecursiveTask<Long> {

        private static final int THRESHOLD = 1000;
        private long start;
        private long end;

        MyTask(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            long dif = end - start;
            long sum = 0;
            if (dif <= THRESHOLD) {
                for (long i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                //分成10个小任务去执行
                long step = dif / 10;
                long post = start;
                List<MyTask> myTasks = new CopyOnWriteArrayList<>();
                for (int i = 0; i < 10; i++) {
                    long lastOne = post + step;
                    if (lastOne > end) {
                        lastOne = end;
                    }
                    MyTask myTask = new MyTask(post, lastOne);
                    myTasks.add(myTask);
                    post += step + 1;
                    myTask.fork();
                }
                for (MyTask myTask : myTasks) {
                    sum += myTask.join();
                }
            }
            return sum;
        }
    }


    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        MyTask task = new MyTask(0, 10000);
        ForkJoinTask<Long> result = forkJoinPool.submit(task);
        try {
            Long res = result.get();
            System.out.println(res);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Collections.synchronizedMap(new Hashtable<>());
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("riane");
        System.out.println(queue.poll());
        queue.add("riane2");
        queue.size();

        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("1");
        list.get(1);
        list.remove("2");

        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
        ConcurrentSkipListMap<String,String> skipMap = new ConcurrentSkipListMap<>();
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put("a","a");

    }
}
