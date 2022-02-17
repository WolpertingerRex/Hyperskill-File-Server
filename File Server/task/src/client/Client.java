package client;

import utils.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static utils.ConsoleReader.*;
import static utils.ConsoleWriter.*;


public class Client {
    private final String fileStorage = "E://Программирование/File Server/File Server/task/src/client/data/";


    public void runClient(String ADDRESS, int PORT) {
        try (Socket socket = new Socket(ADDRESS, PORT)) {

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            output.flush();

            printMessage(START_DIALOG);

            RequestType requestType = getRequest();
            Request request = formRequest(requestType);

            output.writeObject(request);
            output.flush();
            TimeUnit.MILLISECONDS.sleep(50);

            Response response = (Response) input.readObject();
            processResponse(response, requestType);

        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void processResponse(Response response, RequestType requestType) {
        ResponseType type = response.getType();

        switch (type) {
            case SUCCESS: {
                switch (requestType) {
                    case PUT:
                        printMessage(CREATION_SUCCESS + response.getId());
                        break;
                    case DELETE:
                        printMessage(DELETION_SUCCESS);
                        break;
                    case GET:
                        String filename = getFileName(SAVE_AS);
                        byte[] content = response.getContent();

                        try {
                            FileHelper.writeContent(fileStorage, filename, content);
                            printMessage(SAVE_SUCCESS);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
            break;
            case FORBIDDEN:
                printMessage(CREATION_FAIL);
                break;
            case NOT_EXIST:
                printMessage(NOT_FOUND);
                break;
        }
    }


    public Request formRequest(RequestType requestType) {

        Request result = null;

        switch (requestType) {
            case PUT: {
                String sourceFileName = getFileName(CHOOSE_FILE_DIALOG);
                byte[] content = new byte[0];
                try {
                    content = FileHelper.readContent(fileStorage, sourceFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String newfilename = getFileName(FILE_TO_BE_SAVED_ON_SERVER);
                if(newfilename == null) newfilename = "";
                result = new Request(requestType, newfilename);
                result.setContent(content);
            }
            break;
            case GET:
            case DELETE: {
                printMessage(BY_NAME_OR_ID);
                boolean param = byNameOrId();

                if (param) {
                    String filename = getFileName(CHOOSE_FILE_DIALOG);
                    result = new Request(requestType, true, filename);
                } else {
                    printMessage(CHOOSE_ID);
                    int id = readInt();
                    result = new Request(requestType, false, id);
                }

            }
            break;
            case EXIT:
                result = new Request(requestType);
                break;
        }
        printMessage(REQUEST_SENT);
        return result;
    }

    private String getFileName(String message) {
        printMessage(message);
        String sourceFileName = null;
        while (sourceFileName == null) {
            sourceFileName = readString();
        }
        return sourceFileName;
    }


}
