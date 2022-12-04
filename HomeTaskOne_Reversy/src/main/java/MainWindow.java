import Player.Person;
import Reversy.*;
import Reversy.Box;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class MainWindow extends JFrame {
    private Game game;
    private final int IMAGE_SIZE = 52;
    private final int Rows = 8;
    private final int Cols = 8;
    final private int height = 25*26;
    final private int width = Cols * IMAGE_SIZE + IMAGE_SIZE/2;


    private JLabel stateLabel; // состояние игры: кто ходит, кто выиграл
    private JPanel panel;
    private JButton buttonBack;
    private JButton buttonNewGame;
    private JButton buttonForward;
    private JButton buttonGameWithFriend;
    private JButton buttonGameWithComputer;
    private JButton buttonGameWithCleverCopmuter;

    //конструктор
    private MainWindow() {
        game = new Game(Cols, Rows);
        game.start(Mode.MIDDLE);
        setImages();
        initPanel();
        initWindow();
   }

    private void initWindow() {
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // установка окна посередине
        setTitle("Reversy");
        setResizable(false);
        setIconImage(getImage("Icon_for_game"));
        setVisible(true);
        setLayout(new FlowLayout());
    }

    private void initPanel() {
        initBackButton();
        initForwardButton();
        initNewGameButton();
        setGameField(); // установили игровое поле
        initGameWithFriendButton();
        initGameWithComputerButton();
        initGameWithCleverComputerButton();
        initStateLabel();
    }

    private void initStateLabel() {
        stateLabel = new JLabel();
        stateLabel.setSize(new Dimension(20, 15));
        stateLabel.setText("Welcome! " + getMessage());
        add(stateLabel);
    }

    private void initBackButton() {
        buttonBack = new JButton("Step back");
        ActionListener buttonBackListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.pressStepBack();
                panel.repaint();
            }
        };
        buttonBack.addActionListener(buttonBackListener);
        this.add(buttonBack);
    }

    private void initForwardButton() {
        buttonForward = new JButton("Step forward");
        ActionListener buttonForwardLostener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.pressStepForward();
                stateLabel.setText("Coming soon too...");
                panel.repaint();
            }
        };

        buttonForward.addActionListener(buttonForwardLostener);
        add(buttonForward);
    }

    private void initNewGameButton() {
        buttonNewGame = new JButton("New Game");
        ActionListener buttonNewGameListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.start(Mode.getMode());
                stateLabel.setText("New Game. " + getMessage());
                panel.repaint();
            }
        };
        buttonNewGame.addActionListener(buttonNewGameListener);
        add(buttonNewGame);
    }

    private void initGameWithFriendButton() {
        buttonGameWithFriend = new JButton("with friend");
        ActionListener buttonWithFriendListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stateLabel.setText("Play with Friend. " + getMessage());
                Mode.setMode(Mode.FRIEND);
                game.start(Mode.getMode());
                panel.repaint();
            }
        };
        buttonGameWithFriend.addActionListener(buttonWithFriendListener);
        add(buttonGameWithFriend);
    }

    private void initGameWithComputerButton() {
        buttonGameWithComputer = new JButton("with Computer");
        ActionListener buttonWithComputerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stateLabel.setText("Play with Computer. " + getMessage());
                Mode.setMode(Mode.MIDDLE);
                game.start(Mode.getMode());
                panel.repaint();
            }
        };

        buttonGameWithComputer.addActionListener(buttonWithComputerListener);
        add(buttonGameWithComputer);
    }

    private void initGameWithCleverComputerButton() {
        buttonGameWithCleverCopmuter = new JButton("with Clever Computer");
        ActionListener buttonWithCleverComputerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stateLabel.setText("Coming soon...");
//                Mode.setMode(Mode.SENIOR);
//                game.start(Mode.getMode());
                panel.repaint();
            }
        };
        buttonGameWithCleverCopmuter.addActionListener(buttonWithCleverComputerListener);
        add(buttonGameWithCleverCopmuter);
    }

    private void setGameField() {
        panel = new JPanel() // устанавливается игровое поле
        {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Location location : Ranges.getAllLocations()) {
                    g.drawImage((Image) game.getBox(location).image, location.x * IMAGE_SIZE,
                            location.y * IMAGE_SIZE, this);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Location location = new Location(x, y);
                if(e.getButton() == MouseEvent.BUTTON1) {
                    game.pressLeftButton(location);
                    stateLabel.setText(getMessage());
                    panel.repaint();
                }
            }
        });

        // устанавливается размер окна
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));

        add(panel, BorderLayout.CENTER);
    }

    private String getMessage() {
        switch (game.getState()) {
            case StepFirst: return new String(((Person)game.firstPlayer).getName() +
                    " turn to make move");
            case StepSecond: return new String(((Person)game.secondPlayer).getName() +
                    " turn to make move");
            case WinFirs: return new String(((Person)game.firstPlayer).getName() + " Won!!");
            case WinSecond: return new String(((Person)game.secondPlayer).getName() + "  Won!!");
            case Draw: return new String("Draw!!");
        }
        return "___";
    }

    private void setImages() {
        for (Reversy.Box box : Box.values()) {
            box.image = getImage(box.name());
        }
    }
    private Image getImage(String name) {
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource(filename)));
        return icon.getImage();
    }

    public static void main(String[] args) {
        new MainWindow();
    }


}
