import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class Mode extends JFrame {
  JButton single = new JButton("Offline");
  JButton multi = new JButton("Online");
  Font font = new Font("JetBrains Mono ExtraBold", Font.PLAIN, 20);
  Border border = BorderFactory.createLineBorder(Color.white, 1, true);

  public Mode() {
    this.setSize(400, 400);
    this.setLayout(null);
    this.setLocationRelativeTo(null);
    this.setTitle("Game Mode");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    getContentPane().setBackground(Color.black);

    single.setBounds(215, 140, 150, 60);
    single.setBackground(Color.black);
    single.setForeground(Color.white);
    single.setBorder(border);
    single.setFont(font);
    single.setHorizontalAlignment(JLabel.CENTER);
    single.setFocusable(false);
    single.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          API.mode = false;
          new SnakeGame(new User());
          dispose();
        } catch (Exception exc) {
        }
      }
    });
    this.add(single);

    multi.setBounds(30, 140, 150, 60);
    multi.setBackground(Color.black);
    multi.setForeground(Color.white);
    multi.setHorizontalAlignment(JLabel.CENTER);
    multi.setBorder(border);
    multi.setFont(font);
    multi.setFocusable(false);
    multi.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        API.mode = true;
        new Login();
        dispose();
      }
    });
    this.add(multi);
    this.setVisible(true);
  }
}
