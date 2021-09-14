package chat;

public class ActualChatter implements Chatter {

    public static int CHATTER_COUNT = 0;
    public synchronized static ActualChatter createInstance (String name) {
        ActualChatter chatter = new ActualChatter(ActualChatter.CHATTER_COUNT, name);
        ActualChatter.CHATTER_COUNT += 1;
        return chatter;
    }

    private int id;
    private String name;

    private ActualChatter (int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void receiveMessage(String message) {
        System.err.println(message);
    }
}
