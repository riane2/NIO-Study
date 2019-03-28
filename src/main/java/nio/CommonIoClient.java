package nio;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 普通的IO客户端
 */
public class CommonIoClient {
    public static void main(String[] args) throws IOException {
        Socket client = null;
        PrintWriter writer = null;
        BufferedReader reader = null;

        try {

            client = new Socket();
            client.connect(new InetSocketAddress("10.45.146.67", 8000));
            writer = new PrintWriter(client.getOutputStream(), true);

            while (true) {
                writer.println("Hello");
                writer.flush();

                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println("from server:" + reader.readLine());
                Thread.sleep(2000);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            writer.close();
            reader.close();
            client.close();
        }
    }
}
