import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class TCPClient {

    public static final void main(final String[] args) {
        Socket client;
        OutputStream os;
        InetAddress ia;

        try {
            ia = InetAddress.getByName("localhost"); 

            client = new Socket(ia, 9999);

            os = client.getOutputStream();
            Random random = new Random();
            int randomNumber = random.nextInt(10) + 1;
            os.write(randomNumber); 
            System.out.println("Enviado: " + randomNumber);
            client.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
