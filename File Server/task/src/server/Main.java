package server;


public class Main {
    private static final int PORT = 34522;
    private static FileManager manager = FileManager.getInstance();

    public static void main(String[] args) {
        Server server = new Server();
        server.setManager(manager);
        server.runServer(PORT);

    }
}