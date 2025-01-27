import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

class SnakeGame extends JFrame {
  GamePanel gp = new GamePanel();
  Font font = new Font("JetBrains Mono Bold Italic",Font.PLAIN,20);
  Leaderboard ld;
  User user;
  JLabel highScore;
  JLabel scorePanel = new JLabel(" Score : ");
  int hScore;

  SnakeGame(User user) throws IOException {
    this.user = user;
    this.highScore = new JLabel(" HighScore : " + user.getScore());
    this.hScore = Integer.parseInt(user.getScore());
    this.setTitle("Snake Game");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(576, 649);
    this.setResizable(false);
    this.setLayout(null);
    this.setFocusable(true);
    this.setLocationRelativeTo(null);
    addKeyListener(gp);
    highScore.setBounds(250, 0, 310, 49);
    highScore.setHorizontalAlignment(4);
    highScore.setForeground(new Color(15, 136, 22));
    highScore.setFont(font);
    highScore.setBackground(new Color(103, 234, 198));
    highScore.setOpaque(true);
    scorePanel.setBounds(0, 0, 250, 49);
    scorePanel.setHorizontalAlignment(2);
    scorePanel.setForeground(new Color(147, 41, 199));
    scorePanel.setFont(font);
    scorePanel.setBackground(new Color(103, 234, 198));
    scorePanel.setOpaque(true);
    this.add(highScore);
    this.add(scorePanel);
    this.add(gp);
    if (API.mode) {
      this.ld = new Leaderboard(this.user);
      this.add(ld);
      this.setSize(772,649);
    }
    this.setVisible(true);
    timer.start();
  }

  Timer timer = new Timer(10, e -> {
    scorePanel.setText(scorePanel.getText().substring(0, 9) + (gp.snake.size - 2));
    if (gp.snake.die) {
      if ((gp.snake.size - 2) > hScore) {
        hScore = gp.snake.size - 2;
        highScore.setText(highScore.getText().substring(0, 13) + (hScore));
        user.setScore(Integer.toString(hScore));
        if (API.mode) {
          try {
            API.updateRecord(user.getName(), Integer.parseInt(user.getScore()));
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
        }
      }
      if (API.mode) {
        remove(ld);
        try {
          ld = new Leaderboard(user);
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
      remove(gp);
      gp = new GamePanel();
      if (API.mode) {
        add(ld);
      }
      add(gp);
      addKeyListener(gp);
      setFocusable(true);
    }
    Toolkit.getDefaultToolkit().sync();
  });
}

// Snake Setup

class Snake {
  boolean die = false;
  int size = 2;
  char direction = 'r';
  char tailDirection = 'r';
  LinkedList<Integer[]> tp = new LinkedList<>();
  LinkedList<Integer[]> mp = new LinkedList<>();
  LinkedList<Character> td = new LinkedList<>();
  LinkedList<Character> ml = new LinkedList<>();
  int X = 9;
  int Y = 9;
  int tailX = 9;
  int tailY = 6;
  JPanel[][] grid;

  Snake(JPanel[][] grid) {
    this.grid = grid;
    draw(grid);
    for (int i = tailY; i < tailY + size; i++) {
      grid[9][i + 1].setBackground(Color.green);
      grid[9][i + 1].setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }
  }

  void draw(JPanel[][] grid) {
    grid[X][Y].setBackground(Color.green);
    grid[X][Y].setBorder(BorderFactory.createLineBorder(Color.black, 1));

    grid[tailX][tailY].setBorder(null);
    grid[tailX][tailY].setBackground(new Color(20, 20, 20));

  }

  void eat(JPanel[][] grid) {
    size++;
    grid[tailX][tailY].setBorder(BorderFactory.createLineBorder(Color.black, 1));
    grid[tailX][tailY].setBackground(Color.green);
    switch (tailDirection) {
      case 'l':
        tailY++;
        break;
      case 'r':
        tailY--;
        break;
      case 'u':
        tailX++;
        break;
      case 'd':
        tailX--;
        break;
    }
  }

}

// Game Window Setup

class GamePanel extends JPanel implements KeyListener {

  JPanel[][] grid = new JPanel[20][20];
  Random rand = new Random();
  Snake snake;

  GamePanel() {
    // Game Grid Setup
    setBounds(0, 50, 560, 560);
    setLayout(new GridLayout(20, 20));
    for (int i = 0; i < 20; i++) {
      for (int j = 0; j < 20; j++) {
        grid[i][j] = new JPanel();
        grid[i][j].setBackground(new Color(20, 20, 20));
        add(grid[i][j]);
      }
    }
    snake = new Snake(grid);

    // Adding Apples
    addApple();
  }

  int a1;
  int a2;

  void addApple() {
    do {
      a1 = rand.nextInt(0, 20);
      a2 = rand.nextInt(0, 20);
    } while (grid[a1][a2].getBackground() == Color.green);
    grid[a1][a2].setBackground(Color.orange);
    grid[a1][a2].setBorder(null);
  }

  void checkCollision() {
    if (grid[snake.X][snake.Y].getBorder() != null) {
      collision = true;
    }
  }

  void teleport(int a, int b, char direction) {
    boolean e = false;
    if (b < 0 || b > 19 || a > 19 || a < 0) {
      if (snake.tailX == a && snake.tailY == b) {
        e = true;
      }
      switch (direction) {
        case 'l':
          b = 19;
          break;
        case 'r':
          b = 0;
          break;
        case 'u':
          a = 19;
          break;
        case 'd':
          a = 0;
          break;
      }
      if (!e) {
        snake.X = a;
        snake.Y = b;
      } else {
        snake.tailX = a;
        snake.tailY = b;
      }
    }
  }

  boolean collision = false;
  boolean running = false;
  Timer move = new Timer(100, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      temp = snake.direction;
      switch (snake.direction) {
        case 'r':
          snake.Y++;
          break;
        case 'l':
          snake.Y--;
          break;
        case 'u':
          snake.X--;
          break;
        case 'd':
          snake.X++;
          break;
      }
      teleport(snake.X, snake.Y, snake.direction);
      checkCollision();
      if (!snake.td.isEmpty()) {
        snake.ml.add(snake.td.poll());
        snake.mp.add(snake.tp.poll());
      }
      if (!snake.ml.isEmpty() && snake.tailX == snake.mp.getFirst()[0]
          && snake.tailY == snake.mp.getFirst()[1]) {
        snake.tailDirection = snake.ml.poll();
        snake.mp.poll();
      }

      switch (snake.tailDirection) {
        case 'r':
          snake.tailY++;
          break;
        case 'l':
          snake.tailY--;
          break;
        case 'u':
          snake.tailX--;
          break;
        case 'd':
          snake.tailX++;
          break;
      }
      teleport(snake.tailX, snake.tailY, snake.tailDirection);

      snake.draw(grid);
      if (snake.X == a1 && snake.Y == a2) {
        snake.eat(grid);
        addApple();
      } else {
        grid[a1][a2].setBackground(Color.orange);
      }
      if (collision) {
        grid[snake.X][snake.Y].setBackground(Color.red);
        snake.die = true;
        move.stop();
      }
    }

  });
  char temp;

  @Override
  public void keyReleased(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == 37 & temp != 'r' & temp != 'l') {
      snake.direction = 'l';
      snake.tp.clear();
      snake.tp.add(new Integer[] { snake.X, snake.Y });
      snake.td.clear();
      snake.td.add(snake.direction);
    } else if (e.getKeyCode() == 38 && temp != 'd' & temp != 'u') {
      snake.direction = 'u';
      snake.tp.clear();
      snake.tp.add(new Integer[] { snake.X, snake.Y });
      snake.td.clear();
      snake.td.add(snake.direction);
    } else if (e.getKeyCode() == 39 && temp != 'l' & temp != 'r') {
      snake.direction = 'r';
      snake.tp.clear();
      snake.tp.add(new Integer[] { snake.X, snake.Y });
      snake.td.clear();
      snake.td.add(snake.direction);
    } else if (e.getKeyCode() == 40 && temp != 'u' & temp != 'd') {
      snake.direction = 'd';
      snake.tp.clear();
      snake.tp.add(new Integer[] { snake.X, snake.Y });
      snake.td.clear();
      snake.td.add(snake.direction);
    } else if (e.getKeyCode() == 32) {
      if (!running) {
        move.start();
        running = true;
      } else {
        move.stop();
        running = false;
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }
}

class Leaderboard extends JPanel {

  public Leaderboard(User u) throws IOException {
    this.setLayout(new GridLayout(10, 0, 0, 5));
    this.setBounds(560, 0, 200, 610);
    this.setBackground(Color.DARK_GRAY);
    this.setBorder(null);
    int k = 1;
    User[] users = Mapper.stringToUsers(API.getLeaderdoard());
    boolean exist = false;
    for (User usr : users) {
      row r = new row();
      if (usr.getName().equals(u.getName())) {
        exist = true;
        r.rid.setForeground(new Color(255, 130, 101));
        r.rname.setForeground(new Color(255, 130, 101));
        r.rscore.setForeground(new Color(255, 130, 101));
      }
      r.setId(usr.getId());
      r.setName(usr.getName());
      r.setScore(usr.getScore());
      this.add(r);
      if (k == 9) {
        if (!exist)
          break;
      }
      if (k == 10) {
        break;
      }
      k++;
    }
    if (!exist) {
      row r = new row();
      r.setId(u.getId());
      r.setScore(u.getScore());
      r.setName(u.getName());
      this.add(r);
    }
  }

  class row extends JPanel {
    Font font = new Font("JetBrains Mono Bold",Font.PLAIN,20);
    JLabel rid = new JLabel();
    JLabel rname = new JLabel();

    JLabel rscore = new JLabel();

    public row() {
      this.setLayout(null);
      rid.setFont(font);
      rname.setFont(font);
      rscore.setFont(font);
      rid.setBounds(0, 0, 50, 56);
      rname.setBounds(50, 0, 100, 56);
      rscore.setBounds(150, 0, 50, 56);
      rid.setOpaque(true);
      rscore.setOpaque(true);
      rname.setOpaque(true);
      rid.setBackground(new Color(35, 97, 196));
      rname.setBackground(new Color(35, 97, 196));
      rscore.setBackground(new Color(35, 97, 196));
      rid.setForeground(new Color(241, 243, 150));
      rname.setForeground(new Color(241, 243, 150));
      rscore.setForeground(new Color(241, 243, 150));
      rscore.setHorizontalAlignment(0);
      rname.setHorizontalAlignment(0);
      rid.setHorizontalAlignment(0);
      this.add(rid);
      this.add(rscore);
      this.add(rname);
      this.setOpaque(true);
    }

    public int getId() {
      return Integer.parseInt(rid.getText());
    }

    public void setId(int id) {
      this.rid.setText(Integer.toString(id));
    }

    public String getName() {
      return rname.getText();
    }

    public void setName(String name) {
      this.rname.setText(name);
    }

    public int getScore() {
      return Integer.parseInt(rscore.getText());
    }

    public void setScore(String score) {
      this.rscore.setText(score);
    }
  }
}
