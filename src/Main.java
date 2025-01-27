import java.util.Scanner;
import javax.swing.UIManager;
import java.io.File;
public class Main {
  public static void main(String[] args) throws Exception {
    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    Scanner sc = new Scanner(new File("ip.txt"));
    API.api = sc.nextLine();
    sc.close();
    new Mode();
  }
}
