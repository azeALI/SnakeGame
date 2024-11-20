import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Mode extends JFrame {
  JButton single = new JButton("Offline");
  JButton multi = new JButton("Online");

  public Mode() {
    this.setSize(400, 400);
    this.setLayout(null);
    this.setLocationRelativeTo(null);
    this.setTitle("Game Mode");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    getContentPane().setBackground(Color.black);

    single.setBounds(250, 140, 90, 90);
    single.setBackground(Color.black);
    single.setForeground(Color.white);
    single.setBorder(BorderFactory.createLineBorder(Color.white, 1, true));
    single.setFont(new Font("JetBrains Mono", 1, 20));
    single.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          new SnakeGame(new User());
        } catch (Exception exc) {
        }
      }
    });
    this.add(single);

    multi.setBounds(90, 140, 90, 90);
    multi.setBackground(Color.black);
    multi.setForeground(Color.white);
    multi.setBorder(BorderFactory.createLineBorder(Color.white, 1, true));
    multi.setFont(new Font("a", 1, 20));
    multi.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        new Login();

      }
    });
    this.add(multi);

  }
}
