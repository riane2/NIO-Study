package future;

import future.impl.FutureData;
import future.impl.RealData;

public class Client {


    public Data request(final String queryStr) {
        FutureData futureData = new FutureData();
        new Thread() {
            @Override
            public void run() {
                RealData realData = new RealData(queryStr);
                futureData.setRealData(realData);
            }
        }.start();
        return futureData;
    }


}
