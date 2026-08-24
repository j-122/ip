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

        while (!inputLine.equals(exitWord)) {
            System.out.println(inputLine);
            inputLine = scanner.nextLine();
        }
        System.out.println(exit);
    }
}
