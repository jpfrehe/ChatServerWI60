package chatproviderproxy;

import chatprovider.Chatter;

import java.io.*;
import java.net.Socket;

public class ChatterClientStub implements Chatter {

    private Socket socket;

    private BufferedReader in;
    private PrintWriter out;

    public ChatterClientStub (String host, int port) {
        try {
            this.socket = new Socket(host, port);

            if (this.openInputStream() == false || this.openOutputStream() == false) {
                return;
            }

            // TODO: check if header exist
            String headerLine;
            while ((headerLine = this.in.readLine()) != null) {
                if (headerLine.equalsIgnoreCase("----")) {
                    break;
                }
                System.out.println("[INFORMATION]: Server said: " + headerLine);
            }


        } catch (Throwable t) {
            System.out.println("[ERROR]: beim öffnen des Sockets ist folgender Fehelr aufgetreten:");
            t.printStackTrace();
        }
    }

    public boolean openInputStream () {
        try {
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            return true;
        } catch (IOException e) {
            System.out.println("[ERROR]: beim öffnen des Input-Streams ist folgender Fehelr aufgetreten:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean openOutputStream () {
        try {
            this.out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
            return true;
        } catch (IOException e) {
            System.out.println("[ERROR]: beim öffnen des Output-Streams ist folgender Fehelr aufgetreten:");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void receiveMessage(String message) {

    }
}
