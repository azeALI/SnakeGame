import javax.swing.*;
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

    public Login() {
        setSize(400, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.black);

        text = new JLabel("Enter UserName");
        text.setBounds(130, 120, 150, 30);
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setBorder(BorderFactory.createLineBorder(Color.white, 1, true));
        text.setForeground(Color.white);
        text.setFocusable(false);

        userName = new JTextField();
        userName.setForeground(Color.white);
        userName.setBorder(BorderFactory.createLineBorder(Color.white, 1, true));
        userName.setBounds(130, 160, 150, 30);
        userName.setBackground(Color.black);
        userName.setCaretColor(Color.white);
        userName.setFont(new Font("Arial", 0, 16));
        userName.setFocusable(true);


        submit = new JButton("Login");
        submit.setBounds(178, 200, 55, 30);
        submit.setBackground(Color.black);
        submit.setForeground(Color.white);
        submit.setBorder(BorderFactory.createLineBorder(Color.white, 1, true));

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
                if (e.getKeyCode()==10){
                    attempt();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        this.add(text);
        this.add(userName);
        this.add(submit);
        this.setVisible(true);

    }
    public void attempt(){
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
