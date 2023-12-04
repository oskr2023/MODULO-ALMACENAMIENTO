import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static final void main(final String[] args) {
        ServerSocket server;
        InputStream is;
        Socket client;

        try {
            server = new ServerSocket(9999);

            for (int j = 5; (--j) >= 0;) {
                client = server.accept();
                System.out.println("Nueva conexón de: " + client.getRemoteSocketAddress());

                is = client.getInputStream();
                int numberReceived = is.read();

                for (int i = 0; i < numberReceived; i++) {
                    System.out.print("X");
                }
                System.out.println();

                client.close();
            }
            server.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
