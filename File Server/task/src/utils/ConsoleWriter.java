package utils;

public class ConsoleWriter {
    public final static String START_DIALOG = "Enter action (1 - get a file, 2 - save a file, 3 - delete a file):";
    public final static String BY_NAME_OR_ID = "Do you want to get the file by name or by id (1 - name, 2 - id):";
    public final static String CHOOSE_FILE_DIALOG = "Enter filename:";
    public final static String FILE_TO_BE_SAVED_ON_SERVER = "Enter name of the file to be saved on server:";
    public static final String CHOOSE_ID = "Enter id:";
    public final static String REQUEST_SENT = "The request was sent.";
    public final static String NOT_FOUND = "The response says that the file was not found!";
    public final static String SAVE_AS = "The file was downloaded! Specify a name for it:";
    public static final String SAVE_SUCCESS = "File saved on the hard drive!";
    public final static String CREATION_SUCCESS = "Response says that file is saved! ID = ";
    public final static String CREATION_FAIL = "The response says that creating the file was forbidden!";
    public final static String DELETION_SUCCESS = "The response says that the file was successfully deleted!";


    public static void printMessage(String message) {
        System.out.println(message);
    }


}
