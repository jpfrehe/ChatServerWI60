package proxy;


import chat.Chatter;

import java.io.*;
import java.net.Socket;

public class ChatterServerStub implements Runnable {

    private Socket socket;
    private Chatter chatter;

    private BufferedReader in;
    private PrintWriter out;

    public ChatterServerStub (Socket socket, Chatter chatter) {
        this.socket = socket;
        this.chatter = chatter;
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

    public void pleaseGetId () throws Exception {

        this.out.println("int getId() selected");

        try {
            int id = this.chatter.getId();
            this.out.println("1");
            this.out.println(id);
        } catch (Exception e) {
            this.out.println("0");
            this.out.println("FachlicherFehler: " + e.getMessage());
        }
    }

    public void pleaseGetName () throws Exception {
        this.out.println("String getName() selected");

        try {
            String name = this.chatter.getName();
            this.out.println("1");
            this.out.println(name);
        } catch (Exception e) {
            this.out.println("0");
            this.out.println("FachlicherFehler: " + e.getMessage());
        }

    }

    public  void pleaseReceiveMessage () throws Exception {
        this.out.println("receiveMessage(String message) selected");
        this.out.println("provide String message");
        String message = this.in.readLine();

        try {
            this.chatter.receiveMessage(message);
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
            this.out.println("1 - getId");
            this.out.println("2 - getName");
            this.out.println("3 - receiveMessage");
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
                        this.pleaseGetId ();
                        break;
                    case "2":
                        this.pleaseGetName ();
                        break;
                    case "3":
                        this.pleaseReceiveMessage ();
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
