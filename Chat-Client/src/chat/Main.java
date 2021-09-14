package chat;

import proxy.IChatProvider;
import runtime.ActualClientRuntime;

import java.util.Scanner;

public class Main {
    public static void main (String[] args) {

        ActualClientRuntime runtime = new ActualClientRuntime();

        Scanner scanner = new Scanner (System.in);
        boolean isRunning = true;

        System.out.print ("Bitte geben Sie einen Namen ein: ");
        String name = scanner.nextLine ();
        Chatter chatter = ActualChatter.createInstance (name);

        IChatProvider provider = runtime.getChatProvider ("localhost", 9999);
        provider.joinChat (chatter);

        while (isRunning == true) {
            System.out.println ("Vorhandene Befehle: 0 - Programm beenden, 1 - Chat verlassen, Irgendwas - Zum senden einer Nachricht ");
            System.out.print ("Bitte geben Sie einen Command/Message ein: ");
            String command = scanner.nextLine ();

            if (command.equalsIgnoreCase("0")) {
                System.out.println ("[INFORMATION]: closing connection");
                isRunning = false;
            } else if (command.equalsIgnoreCase("1")) {
                provider.leaveChat(chatter);
            }else {
                provider.sendMessage(chatter, command);
            }
        }

        runtime.close (provider);

    }
}
