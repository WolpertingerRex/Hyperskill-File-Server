package server;


import utils.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.Callable;


public class FileManager implements Callable<Response> {

    private final String fileStorage = "E://Программирование/File Server/File Server/task/src/server/data/";
    private Request request;

    public void setRequest(Request request) {
        this.request = request;
    }


    private Response add(Request request) {
        Response response;
        String filename = request.getFilename();
        byte[] content = request.getContent();


        int id = 0;
        while (FileStorage.getAllFiles().containsKey(id)) {
            id++;
        }

        if (filename.isEmpty()) filename = "file" + id;
        FileStorage.addToAllFiles(id, filename);


        try {
            FileHelper.writeContent(fileStorage, filename, content);
            response = new Response(ResponseType.SUCCESS);
            response.setId(id);
            return response;
        } catch (IOException e) {
            return new Response(ResponseType.FORBIDDEN);
        }
    }

    private Response delete(Request request) {
        String filename;
        if (request.isFileName()) {
            filename = request.getFilename();
        } else {
            filename = FileStorage.getAllFiles().get(request.getId());
        }

        Path path = Paths.get(fileStorage + filename);
        try {
            boolean success = Files.deleteIfExists(path);
            FileStorage.remove(request.getId());
            if (success) return new Response(ResponseType.SUCCESS);
            return new Response(ResponseType.NOT_EXIST);
        } catch (IOException e) {
            return new Response(ResponseType.NOT_EXIST);
        }
    }

    private Response get(Request request) {
        String filename;
        if (request.isFileName()) {
            filename = request.getFilename();
        } else {
            filename = FileStorage.getAllFiles().get(request.getId());
        }

        try {
            byte[] content = FileHelper.readContent(fileStorage, filename);
            Response response = new Response(ResponseType.SUCCESS);
            response.setContent(content);
            return response;
        } catch (IOException e) {
            return new Response(ResponseType.NOT_EXIST);
        }
    }


    public Response processRequest() {
        RequestType type = request.getType();
        FileStorage.load();


        Response response = null;

        switch (type) {
            case PUT:
                response = add(request);
                break;
            case GET:
                response = get(request);
                break;
            case DELETE:
                response = delete(request);
                break;
        }
        FileStorage.save();
        return response;
    }

    @Override
    public Response call() {
        return processRequest();
    }
}
