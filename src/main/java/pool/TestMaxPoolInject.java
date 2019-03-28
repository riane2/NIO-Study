package pool;

import sun.misc.FloatingDecimal;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestMaxPoolInject {

    private static class MyRejectHandle implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            Thread thread = new Thread(r);
            System.out.println(thread.getName() + " refuse to execute ");
        }
    }

    /*public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5), new MyRejectHandle());
        for (int i = 0; i < 11; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    Thread.sleep(5000);
                    System.out.println(Thread.currentThread().getName());
                } catch (Exception ex) {

                }
            });
        }
    }*/
    public static void main(String[] args) {
        /*List<String> aa = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            aa.add(i + "");
        }
        double size = aa.size();
        long timeMillis = System.currentTimeMillis();
        String deviceId = "1111";
        double frequency = timeMillis / size;
        System.out.println(frequency);
        String format = new DecimalFormat("#.000").format(frequency);
        System.out.println(format);
        double doubleValue = Double.valueOf(format).doubleValue();
        System.out.println((String.format("该井盖设备%s的频次为%fms/次.", deviceId, doubleValue)));

        long aaa = 7;

        System.out.println(timeMillis / aaa);

        System.out.println(FloatingDecimal.parseDouble("0.0"));

        double b = 0.0;
        System.out.println(b == 0);

        System.out.println(29520000.00 / 1000 / 60);*/

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse("2019-01-18 11:20:16");
            Date date2 = simpleDateFormat.parse("2019-01-21 11:22:16");
            System.out.println(date2.getTime() - date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


}
