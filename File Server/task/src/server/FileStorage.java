package server;

import java.io.*;
import java.util.HashMap;

public class FileStorage implements Serializable {
    private static final long serialVersionUID = 1L;
    private static HashMap<Integer, String> allFiles = new HashMap<>();
    private static final String dir = "E://Программирование/File Server/File Server/task/src/server/data/";

    public static HashMap<Integer, String> getAllFiles() {
        return allFiles;
    }

    public static void addToAllFiles(int id, String filename) {
        allFiles.put(id, filename);
    }

    public static void remove(int id) {
        allFiles.remove(id);
    }

    public static void save(){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dir + "map"))) {
            oos.writeObject(allFiles);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dir + "map"))) {
            allFiles = (HashMap<Integer, String>) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
