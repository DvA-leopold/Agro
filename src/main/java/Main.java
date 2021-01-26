import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Insert some data pls and empty line");
            Scanner scanner = new Scanner(System.in);
            StringBuilder data = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals(""))
                    break;

                data.append(line).append(System.lineSeparator());
            }

            System.out.println("------------------------------");
            new MailMerger(data.toString()).print();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }
}
