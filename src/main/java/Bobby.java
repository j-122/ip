import java.util.Scanner;

public class Bobby {
    public static void main(String[] args) {
        String banner = " ____        _     _           \n"
                + "| __ )  ___ | |__ | |__  _   _ \n"
                + "|  _ \\ / _ \\| '_ \\| '_ \\| | | |\n"
                + "| |_) | (_) | |_) | |_) | |_| |\n"
                + "|____/ \\___/|_.__/|_.__/ \\__, |\n"
                + "                         |___/ \n";
        String greeting = "____________________________________________________________\n" +
                "Hi! I'm Bobby.\n" +
                "What can I do for you?\n" +
                "____________________________________________________________\n";

        System.out.println(banner + greeting);

        Scanner scanner = new Scanner(System.in);
        String exit = "Bye Bye!";
        String exitWord = "bye";
        String inputLine = scanner.nextLine();
        String[] list = new String[100];
        String listWord = "list";
        int numOfTasks = 0;

        while (!inputLine.equals(exitWord)) {
            if (inputLine.equals(listWord)) {
                for (int i = 0; i < numOfTasks; i++) {
                    System.out.println(String.valueOf(i+1) + ". " + list[i]);
                }
            } else {
                list[numOfTasks] = inputLine;
                numOfTasks++;
                System.out.println("added " + inputLine);
            }
            inputLine = scanner.nextLine();
        }

        System.out.println(exit);
    }
}
