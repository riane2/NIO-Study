package future.impl;

import future.Data;

public class FutureData implements Data {

    RealData realData = null;
    boolean isReady = false;

    public synchronized void setRealData(RealData realData) {
        if (isReady) {
            return;
        }
        this.realData = realData;
        isReady = true;
        notifyAll(); //realData已经注入，通知getResult
        System.out.println("已完成数据注入..." + System.currentTimeMillis() / 1000);
    }

    @Override
    public synchronized String getResult() { //会等待resultData完成
        while (!isReady) {
            try {
                System.out.println("未完成等待中..." + System.currentTimeMillis() / 1000);
                wait(); //一直等待，直到数据注入完成
            } catch (Exception ex) {

            }
        }
        return realData.getResult();
    }
}
