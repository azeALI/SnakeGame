import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class Login extends JFrame {
  User user = new User();
  JLabel text;
  JTextField userName;
  JButton submit;
  JButton back;
  Font font = new Font("JetBrains Mono Bold", Font.PLAIN, 20);
  Border border = BorderFactory.createLineBorder(Color.white, 1, true);

  public Login() {
    setSize(400, 400);
    setLayout(null);
    setLocationRelativeTo(null);
    setTitle("Login");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setBackground(Color.black);

    back = new JButton();
    back.setText("BACK");
    back.setBounds(20, 20, 90, 50);
    back.setFont(font);
    back.setBorder(border);
    back.setBackground(Color.black);
    back.setForeground(Color.white);
    // back.setHorizontalAlignment(JLabel.CENTER);
    back.setFocusable(false);
    back.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
        new Mode();
      }

    });

    text = new JLabel("Enter UserName");
    text.setBounds(110, 120, 200, 50);
    text.setFont(font);
    text.setHorizontalAlignment(JLabel.CENTER);
    text.setBorder(border);
    text.setForeground(Color.white);
    text.setFocusable(false);

    userName = new JTextField();
    userName.setForeground(Color.white);
    userName.setFont(font);
    userName.setBorder(border);
    userName.setBounds(110, 175, 200, 50);
    userName.setBackground(Color.black);
    userName.setCaretColor(Color.white);
    userName.setFont(font);
    userName.setFocusable(true);

    submit = new JButton("Login");
    submit.setBounds(178, 250, 75, 30);
    submit.setFont(font);
    submit.setHorizontalAlignment(JLabel.CENTER);
    submit.setBackground(Color.black);
    submit.setForeground(Color.white);
    submit.setFocusable(false);
    submit.setBorder(border);

    submit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        attempt();
      }
    });

    userName.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {

      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) {
          attempt();
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {

      }
    });
    this.add(back);
    this.add(text);
    this.add(userName);
    this.add(submit);
    this.setVisible(true);

  }

  public void attempt() {
    try {
      boolean check = API.checkLogin(userName.getText());
      if (!check) {
        if (!user.getName().equals(userName.getText())) {
          text.setText("User : " + userName.getText() + " not found");
          submit.setText("Create");
          user.setName(userName.getText());
        } else {
          API.addUser(user.getName());
          new SnakeGame(Mapper.stringToUser(API.getUser(user.getName())));
          dispose();
        }
      } else {
        user = Mapper.stringToUser(API.getUser(userName.getText()));
        new SnakeGame(Mapper.stringToUser(API.getUser(user.getName())));
        dispose();
      }

    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }
}
