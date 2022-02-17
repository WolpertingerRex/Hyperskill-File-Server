package utils;

import java.util.Scanner;

public class ConsoleReader {
    private static final Scanner scanner = new Scanner(System.in);


    public static RequestType getRequest(){
        int n = 0;
        String answer = scanner.nextLine();
        if (!answer.equals("exit")) n = Integer.parseInt(answer);

        switch (n){
            case 1 : return RequestType.GET;
            case 2 :return RequestType.PUT;
            case 3: return RequestType.DELETE;
            case 0: return RequestType.EXIT;
            default: return null;
        }
    }

    public static boolean byNameOrId(){
        int n = 0;
        String answer = scanner.nextLine();
        if (!answer.equals("exit")) n = Integer.parseInt(answer);

        switch (n){
            case 1 : return true;
            case 2 : return false;
            default: throw new IllegalArgumentException("Only 1 or 2 are valid answers!");
        }
    }

    public static String readString(){
        return scanner.nextLine();
    }

    public static int readInt(){
        return Integer.parseInt(scanner.nextLine());
    }
}
