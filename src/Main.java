import java.io.IOException;
import java.util.Scanner;
import java.io.File;
public class Main {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(new File("ip.txt"));
    API.api = sc.nextLine();
    sc.close();
    new Mode();
  }
}
