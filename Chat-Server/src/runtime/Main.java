package runtime;

import chatprovider.ChatProvider;
import chatprovider.IChatProvider;

public class Main {
    public static void main (String[] args) {
        IChatProvider chatProvider = new ChatProvider();
        new Server (chatProvider).start();
    }
}
