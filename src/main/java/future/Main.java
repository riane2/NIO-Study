package future;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        System.out.println("时间1" + System.currentTimeMillis() / 1000);
        Data data = client.request("riane");
        System.out.println("请求完毕");
        System.out.println("时间2" + System.currentTimeMillis() / 1000);
        try {
            Thread.sleep(5000);
        } catch (Exception ex) {

        }
        System.out.println("数据 = " + data.getResult());
        System.out.println("时间3" + System.currentTimeMillis() / 1000);
    }
}
