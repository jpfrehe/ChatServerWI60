package proxy;


import chat.Chatter;

import java.io.*;
import java.net.Socket;

public class ChatterServerStub {

    private Socket socket;
    private Chatter chatter;

    private BufferedReader in;
    private PrintWriter out;

    public ChatterServerStub (Chatter chatter, Socket socket) {

        this.chatter = chatter;
        this.socket = socket;
        try {
            if (this.openInputStream() == false || this.openOutputStream() == false) {
                return;
            }

        } catch (Throwable t) {
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

}
