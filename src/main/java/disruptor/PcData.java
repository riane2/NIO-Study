package disruptor;

public class PcData {

    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PcData{" +
                "value=" + value +
                '}';
    }
}
