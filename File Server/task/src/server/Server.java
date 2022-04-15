package server;

import utils.ConsoleWriter;
import utils.Request;
import utils.RequestType;
import utils.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class Server {


    public void runServer(int PORT) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            ConsoleWriter.printMessage("Server started!");
            ExecutorService executor = Executors.newFixedThreadPool(4);
            while (true) {
                try (Socket socket = server.accept();
                     ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {

                    Request request = (Request) input.readObject();
                    if(request.getType().equals(RequestType.EXIT)) {
                        socket.close();
                        executor.shutdown();
                        server.close();
                    }
                    else {
                        FileManager manager = new FileManager();
                        manager.setRequest(request);
                        Future<Response> future = executor.submit(manager);
                        Response response = future.get();

                        output.writeObject(response);
                        output.flush();
                    }
                } catch (ClassNotFoundException | ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
