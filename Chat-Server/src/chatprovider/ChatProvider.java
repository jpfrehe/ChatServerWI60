package chatprovider;

import java.util.ArrayList;
import java.util.List;

public class ChatProvider implements IChatProvider {

    List<Chatter> chatterInChatRoom = new ArrayList<>();

    public void sendMessage (Chatter chatter, String message) {
        if (this.chatterInChatRoom.contains(chatter) == false) {
            throw new RuntimeException("Du darfst nichts schicken.");
        }
        this.chatterInChatRoom
                .stream ()
                .filter  (c -> c != chatter)
                .forEach (c -> c.receiveMessage ("[" + chatter.getName () + "]: " + message));

        chatter.receiveMessage("ich: " + message);
    }

    public void joinChat (Chatter chatter) {
        this.chatterInChatRoom.add (chatter);
        this.sendMessage(chatter, chatter.getName() + " joined the chat.");
    }

    public void leaveChat (Chatter chatter) {
        this.sendMessage(chatter, chatter.getName() + " left the chat.");
        this.chatterInChatRoom.remove (chatter);
    }

}
