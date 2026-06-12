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
            } else if (command.startsWith("custom_exe")) {
                executable(command);          
            } else {
                System.out.println(command + ": command not found");
            }
        }
        scanner.close();
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

    public void executable(String command) {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.environment().put("PATH", path);
        pb.inheritIO();
        Process process = pb.start();

        for (int i = 0; i < pathDirs.length; i++) {
            File file = new File(pathDirs[i], command);
            if (file.exists() && file.canExecute()) {
                process.waitFor();
            }
        }
    }
}
