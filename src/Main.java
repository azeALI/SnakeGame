import java.io.IOException;
import java.util.Scanner;
import java.io.File;
public class Main {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(new File("api.txt"));
    API.api = sc.nextLine();
    sc.close();
    new Login();
  }
}
