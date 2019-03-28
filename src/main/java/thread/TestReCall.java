package thread;

public class TestReCall {
    public static void main(String[] args) {
        MyThread thread = new MyThread(10,"riane");
        thread.run();
        Thread thread1 = new Thread();
        thread1.start();
    }
}
