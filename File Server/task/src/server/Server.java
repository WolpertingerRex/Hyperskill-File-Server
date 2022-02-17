package server;

import utils.ConsoleWriter;
import utils.Request;
import utils.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private FileManager manager;

    public void runServer(int PORT) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            ConsoleWriter.printMessage("Server started!");
            while (true) {
                try (Socket socket = server.accept();
                     ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {
                     Request request = (Request) input.readObject();

                    Response response = manager.processRequest(request);

                    if (response == null) {
                        break;
                    } else output.writeObject(response);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setManager(FileManager manager){
        this.manager = manager;
    }
}
