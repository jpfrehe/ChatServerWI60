package runtime;

import chatprovider.IChatProvider;
import chatproviderproxy.ChatProviderServerStub;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket socket;
    private IChatProvider chatProvider;
    private boolean isRunning = true;

    public Server (IChatProvider chatProvider) {
        try {
            this.chatProvider = chatProvider;
            this.socket = new ServerSocket (9999);
        } catch (Exception e) {
            System.out.println("[ERROR]: die eröffnung des servers gab folgenden fehler:");
            e.printStackTrace();
        }
    }

    public void start () {
        while (this.isRunning) {
            System.out.println("[INFORMATION]: der Server wartet auf anfragen...");
            try {
                Socket client = this.socket.accept();
                new Thread(new ChatProviderServerStub(client, this.chatProvider)).start();
            } catch (Exception e) {
                System.out.println("[ERROR]: die verbindung mit dem client gab folgenden fehler:");
                e.printStackTrace();
            }
        }
    }
}
