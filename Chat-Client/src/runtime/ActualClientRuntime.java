package runtime;

import chat.Chatter;
import proxy.ChatProviderClientStub;
import proxy.IChatProvider;

public class ActualClientRuntime {

    public IChatProvider getChatProvider (String address, int port) {
        return new ChatProviderClientStub (address, port);
    }

    public void close (IChatProvider provider) {
        ((ChatProviderClientStub) provider).killConnection ();
    }
}
