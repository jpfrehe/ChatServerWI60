package chatproviderproxy;

import chatprovider.Chatter;
import chatprovider.IChatProvider;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatProviderServerStub implements Runnable {

    private Socket socket;
    private IChatProvider chatProvider;

    private BufferedReader in;
    private PrintWriter out;

    private Map<Integer, Chatter> chatterMap = new HashMap<>();


    public ChatProviderServerStub(Socket socket, IChatProvider chatProvider) {
        this.socket = socket;
        this.chatProvider = chatProvider;
    }


    private Chatter resolveChatter () throws Exception {

        this.out.println("Gib mir den Chatter-Communication-Id (int)");
        String sCommunicationId = this.in.readLine();
        int communicationId = Integer.valueOf (sCommunicationId);

        if (this.chatterMap.containsKey(communicationId) == false) {
            // Hole den Client vom client

            this.out.println("!- Gib mir den Chatter{id: int, name: String}, bitte.");

            String sId = this.in.readLine();
            String sName = this.in.readLine();

            int id = Integer.valueOf(sId);

            this.chatterMap.put(communicationId, new Chatter() {
                @Override
                public int getId() {
                    return id;
                }

                @Override
                public String getName() {
                    return sName;
                }

                @Override
                public void receiveMessage(String message) {
                    System.out.println("[INFORMATION]: received message \"" + message + "\" for Chatter{id=" + this.getId() + ", name=" + this.getName() + "}");
                }
            });
        } else {
            this.out.println("Chatter konnte resolved werden.");
        }
        return this.chatterMap.get(communicationId);
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

    public void pleaseKill () {
        this.out.println("killConnection selected");
        try {
            this.out.println("0");
            this.in.close();
            this.out.close();
            this.socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void pleaseJoin () throws Exception {

        this.out.println("joinChat(Chatter chatter) selected");
        Chatter chatter = this.resolveChatter();

        try {
            this.chatProvider.joinChat(chatter);
            this.out.println("1");
        } catch (Exception e) {
            this.out.println("0");
            this.out.println("FachlicherFehler: " + e.getMessage());
        }
    }

    public void pleaseLeave () throws Exception {
        this.out.println("leaveChat(Chatter chatter) selected");
        Chatter chatter = this.resolveChatter();

        try {
            this.chatProvider.leaveChat(chatter);
            this.out.println("1");
        } catch (Exception e) {
            this.out.println("0");
            this.out.println("FachlicherFehler: " + e.getMessage());
        }

    }

    public  void pleaseSend () throws Exception {
        this.out.println("sendMessage(Chatter chatter, String message) selected");
        Chatter chatter = this.resolveChatter();
        this.out.println("provide String message");
        String message = this.in.readLine();

        try {
            this.chatProvider.sendMessage(chatter, message);
            this.out.println("1");
        } catch (Exception e) {
            this.out.println("0");
            this.out.println("FachlicherFehler: " + e.getMessage());
        }

    }

    @Override
    public void run() {

        try {
            // Öffne den Reader/Writer:
            if (this.openInputStream() == false || this.openOutputStream() == false) {
                return;
            }

            this.out.println("Buenas Dias");
            this.out.println("Available Commands:");
            this.out.println("0 - kill the connection");
            this.out.println("1 - joinChat");
            this.out.println("2 - leaveChat");
            this.out.println("3 - sendMessage");
            this.out.println("----");

            String line;
            while ((line = this.in.readLine()) != null) {
                System.out.println("[INFORMATION]: client sent: " + line);
                switch (line) {
                    case "0":
                        this.pleaseKill ();
                        System.out.println("[INFORMATION]: client closed connection");
                        return;
                    case "1":
                        this.pleaseJoin ();
                        break;
                    case "2":
                        this.pleaseLeave ();
                        break;
                    case "3":
                        this.pleaseSend ();
                        break;
                    default:
                        throw new Exception("Unsupported operation");
                }
            }
        } catch (Exception e) {
            System.out.println("[ERROR]: beim lesen ist folgender Fehler aufgetreten:");
            e.printStackTrace();
        }
    }
}