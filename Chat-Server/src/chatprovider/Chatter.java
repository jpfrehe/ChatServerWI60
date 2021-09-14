package chatprovider;

public interface Chatter {
    int getId ();
    String getName ();
    void receiveMessage (String message);
}
