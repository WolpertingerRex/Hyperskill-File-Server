package server;


import utils.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;


public class FileManager {

    private final static FileManager ourInstance = new FileManager();

    public static FileManager getInstance() {
        return ourInstance;
    }

    private FileManager() {
    }

    private final String fileStorage = "E://Программирование/File Server/File Server/task/src/server/data/";

    // fileStorage = System.getProperty("user.dir") +
    //    File.separator + "src" + File.separator + "server" + File.separator + "data" + File.separator;


    private void exit() {
        System.exit(0);
    }


    private Response add(Request request) {
        Response response;
        String filename = request.getFilename();
        byte[] content = request.getContent();

        Random random = new Random();
        int id = 0;
        while (FileStorage.getAllFiles().containsKey(id)) {
            id = random.nextInt(10000);
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


    public Response processRequest(Request request) {
        RequestType type = request.getType();
        FileStorage.load();

        Response response = null;

        switch (type) {
            case EXIT:
                exit();
                break;
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
}
