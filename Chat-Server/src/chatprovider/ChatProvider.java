package chatprovider;

import java.util.ArrayList;
import java.util.List;

public class ChatProvider implements IChatProvider {

    List<Chatter> chatterInChatRoom = new ArrayList<>();

    public void sendMessage (Chatter chatter, String message) {
        this.chatterInChatRoom
                .stream ()
                .filter  (c -> c.getId () != chatter.getId ())
                .forEach (c -> c.receiveMessage ("[" + chatter.getName () + "]: " + message));

        chatter.receiveMessage("ich: " + message);
    }

    public void joinChat (Chatter chatter) {
        this.chatterInChatRoom.add (chatter);
        this.sendMessage(chatter, chatter.getName() + " joined the chat.");
    }

    public void leaveChat (Chatter chatter) {
        this.chatterInChatRoom.remove (chatter);
        this.sendMessage(chatter, chatter.getName() + " left the chat.");
    }

}
