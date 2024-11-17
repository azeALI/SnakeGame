
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        API.api = new Scanner("api.txt").nextLine();
        new Login();
    }
}
