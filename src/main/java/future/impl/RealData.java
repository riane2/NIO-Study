package future.impl;

import future.Data;

public class RealData implements Data {

    private final String data;

    public RealData(String data) {
        StringBuffer stringBuffer = new StringBuffer(data);
        for (int i = 0; i < 10; i++) {
            stringBuffer.append(i);
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.data = stringBuffer.toString();
    }

    @Override
    public String getResult() {
        return data;
    }
}
