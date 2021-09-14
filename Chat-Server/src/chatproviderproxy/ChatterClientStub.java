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
        try {
            System.out.println("[INFORMATION]: send \"1\" to server.");
            this.out.println("1");
            System.out.println("[INFORMATION]: server said: " + this.in.readLine());

            String sStatus = this.in.readLine();
            System.out.println("[INFORMATION]: server said: " + sStatus);

            if (sStatus.equalsIgnoreCase("1") == true) {
                System.out.println("[INFORMATION]: .getId() executed successfully.");
                return Integer.valueOf(this.in.readLine());
            } else if (sStatus.equalsIgnoreCase("0") == true) {
                throw new RuntimeException(this.in.readLine());
            } else {
                throw new RuntimeException("This should never happen....");
            }

        } catch (RuntimeException e) {
            System.out.println("[ERROR]: bei .getId() ist folgender !!fachlicher!! Fehler aufgetreten:");
            throw e;
        } catch (Throwable t) {
            System.out.println("[ERROR]: bei .getId() ist folgender Fehler aufgetreten:");
            t.printStackTrace();
            throw new RuntimeException("Kommunikationsfehler....");
        }
    }

    @Override
    public String getName() {
        try {
            System.out.println("[INFORMATION]: send \"2\" to server.");
            this.out.println("2");
            System.out.println("[INFORMATION]: server said: " + this.in.readLine());

            String sStatus = this.in.readLine();
            System.out.println("[INFORMATION]: server said: " + sStatus);

            if (sStatus.equalsIgnoreCase("1") == true) {
                System.out.println("[INFORMATION]: .getName() executed successfully.");
                return this.in.readLine();
            } else if (sStatus.equalsIgnoreCase("0") == true) {
                throw new RuntimeException(this.in.readLine());
            } else {
                throw new RuntimeException("This should never happen....");
            }

        } catch (RuntimeException e) {
            System.out.println("[ERROR]: bei .getName() ist folgender !!fachlicher!! Fehler aufgetreten:");
            throw e;
        } catch (Throwable t) {
            System.out.println("[ERROR]: bei .getName() ist folgender Fehler aufgetreten:");
            t.printStackTrace();
            throw new RuntimeException("Kommunikationsfehler....");
        }
    }

    @Override
    public void receiveMessage(String message) {
        try {
            System.out.println("[INFORMATION]: send \"3\" to server.");
            this.out.println("3");
            System.out.println("[INFORMATION]: server said: " + this.in.readLine());
            System.out.println("[INFORMATION]: server said: " + this.in.readLine());

            System.out.println("[INFORMATION]: send \"" + message + "\" to server.");
            this.out.println(message);

            String sStatus = this.in.readLine();
            System.out.println("[INFORMATION]: server said: " + sStatus);

            if (sStatus.equalsIgnoreCase("1") == true) {
                System.out.println("[INFORMATION]: .getName() executed successfully.");
            } else if (sStatus.equalsIgnoreCase("0") == true) {
                throw new RuntimeException(this.in.readLine());
            } else {
                throw new RuntimeException("This should never happen....");
            }

        } catch (RuntimeException e) {
            System.out.println("[ERROR]: bei .receiveMessage(String message) ist folgender !!fachlicher!! Fehler aufgetreten:");
            throw e;
        } catch (Throwable t) {
            System.out.println("[ERROR]: bei .receiveMessage(String message) ist folgender Fehler aufgetreten:");
            t.printStackTrace();
            throw new RuntimeException("Kommunikationsfehler....");
        }
    }
}
