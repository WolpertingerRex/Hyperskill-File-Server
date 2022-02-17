package client;


public class Main {
    private static final String ADDRESS= "127.0.0.1";
    private static final int PORT = 34522;

    public static void main(String[] args) {
        Client client = new Client();
        client.runClient(ADDRESS, PORT);
    }
}
