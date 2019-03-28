package thread.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MyExcutors {


    private final static ScheduledExecutorService executorService;

    static {
        executorService = Executors.newScheduledThreadPool(5);
    }

    public static ScheduledExecutorService getExecutorService() {
        return executorService;
    }


}
