import java.util.Scanner;
import java.io.File;

public class Main {
    static String path = System.getenv("PATH");        
    static String[] pathDirs = path.split(":");
    
    public static void main(String[] args) throws Exception {       
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("$ ");

            String command = scanner.nextLine();

            if (command.equals("exit")) {
                break;
            } else if (command.startsWith("echo ")) {
                System.out.println(command.substring(5));
            } else if (command.startsWith("type ")) { 
                System.out.println(type(command.substring(5)));
            }           
            else {
                System.out.println(command + ": command not found");
            }
        }
    }

    public static String type(String command) {
        String[] builtins = {"exit", "echo", "type"};
        
        boolean isBuiltin = false;
        for (int i = 0; i < builtins.length; i++) {
            if (builtins[i].equals(command)) {
                return command + " is a shell builtin";
            }
        }

        for (int i = 0; i < pathDirs.length; i++) {
            File file = new File(pathDirs[i], command);
            if (file.exists() && file.canExecute()) {
                return command + " is " + file.getAbsolutePath();
            }
        }

        return command + ": not found";
    }
}
