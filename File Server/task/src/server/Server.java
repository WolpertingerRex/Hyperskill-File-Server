package server;

import utils.ConsoleWriter;
import utils.Request;
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
                    FileManager manager = new FileManager();
                    manager.setRequest(request);
                    Future<Response> future = executor.submit(manager);
                    Response response = future.get();

                    if (response == null) {
                        executor.shutdown();
                        server.close();
                    } else output.writeObject(response);
                    TimeUnit.MILLISECONDS.sleep(50);
                    output.flush();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
