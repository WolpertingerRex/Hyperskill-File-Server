package server;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

public class FileStorage implements Serializable {
    private static final long serialVersionUID = 1L;
    private static ConcurrentHashMap<Integer, String> allFiles = new ConcurrentHashMap<>();

    private static final String dir = System.getProperty("user.dir") +
    File.separator + "src" + File.separator + "server" + File.separator + "data" + File.separator;

    public static ConcurrentHashMap<Integer, String> getAllFiles() {
        return allFiles;
    }

    public static void addToAllFiles(int id, String filename) {
        allFiles.put(id, filename);
    }

    public static void remove(int id) {
        allFiles.remove(id);
    }

    public static void remove(String filename) {
        allFiles.forEach((k,v)-> {
            if(v.equals(filename)) allFiles.remove(k);
        });
    }

    public static void save() {
        String filename = dir + "map";
        if (!Files.exists(Paths.get(filename))) {
            try {
                Files.createFile(Paths.get(filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(allFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        String filename = dir + "map";
        if (Files.exists(Paths.get(filename))) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
                allFiles = (ConcurrentHashMap<Integer, String>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            allFiles = new ConcurrentHashMap<>();
        }
    }

    public static String getStorage(){
        return dir;
    }


}
