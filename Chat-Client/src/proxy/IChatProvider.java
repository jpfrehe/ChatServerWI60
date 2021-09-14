package proxy;

import chat.Chatter;

public interface IChatProvider {
    void joinChat (Chatter chatter);
    void leaveChat (Chatter chatter);
    void sendMessage (Chatter chatter, String message);
}