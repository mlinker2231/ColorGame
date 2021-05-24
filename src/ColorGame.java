import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ColorGame {
    //Never Bothered to make these local
    static int difficulty = 0;
    static int size = 3;
    // Runs my game.
    public static void main(String[] args) {
        difficultySelectScreen();
    }
    //Everything in here is how I did the screen where you click the colored buttons
    public static void runMainGame() {
        JFrame mainGameFrame = createAndShowFrame("Make all squares the same color!");
        JLabel highScoreClicks = makeLabel("HighScore: 9999",350,100,200,50);
        MyButton[][] buttons = new MyButton[size][size];
        JLabel clickerLabel = makeLabel("Clicks: 0",350,50,200,50);
        AtomicBoolean notFirstTime = new AtomicBoolean(false);

        for (int y = 0; y < buttons.length; y++) {
            for (int x = 0; x < buttons[y].length; x++) {
                buttons[x][y] = createButton(x * 53, y * 53, 50, randomColor());
                buttons[x][y].setOpaque(true);
                buttons[x][y].setBorderPainted(false);

                int finalX = x;
                int finalY = y;
                buttons[x][y].addActionListener(actionEvent -> {
                    changeColor(buttons[finalX][finalY]);
                    if (!((finalX - 1 < 0) || (finalY - 1 < 0)))
                        changeColor(buttons[finalX - 1][finalY - 1]);
                    if (!((finalX - 1 < 0)))
                        changeColor(buttons[finalX - 1][finalY]);
                    if (!((finalY - 1 < 0)))
                        changeColor(buttons[finalX][finalY - 1]);
                    if (!((finalX + 1) > (size - 1) || (finalY + 1) > (size - 1)))
                        changeColor(buttons[finalX + 1][finalY + 1]);
                    if (!((finalX + 1) > (size - 1)))
                        changeColor(buttons[finalX + 1][finalY]);
                    if (!((finalY + 1) > (size - 1)))
                        changeColor(buttons[finalX][finalY + 1]);
                    if (!((finalX + 1 > (size - 1) || (finalY - 1) < 0)))
                        changeColor(buttons[finalX + 1][finalY - 1]);
                    if (!((finalX - 1 < 0) || (finalY + 1) > (size - 1)))
                        changeColor(buttons[finalX - 1][finalY + 1]);
                    updateClickCount(clickerLabel,1);
                });
                mainGameFrame.add(buttons[x][y]);
            }
        }

        JButton refresh = createButton(100,350,100,Color.yellow,"Reset");
        refresh.addActionListener(actionEvent -> {
            if (notFirstTime.get() && won(buttons)) {
                updateHighScore(clickerLabel, highScoreClicks);
            }
            switch (buttons.length) {
                case 3:
                    updateClickCount(clickerLabel, -9);
                    break;
                case 4:
                    updateClickCount(clickerLabel, -16);
                    break;
                case 5:
                    updateClickCount(clickerLabel, -25);
                    break;
                case 6:
                    updateClickCount(clickerLabel, -36);
                    break;
                default:
                    updateClickCount(clickerLabel, -100);
                    break;
            }
            for (MyButton[] button : buttons) {
                for (MyButton myButton : button) {
                    if (difficulty != 2)
                        myButton.setBackground(Color.red);
                    else
                        myButton.setBackground(Color.black);
                }
            }

            for (MyButton[] button : buttons) {
                for (MyButton ignored : button)
                    buttons[(int) (Math.random() * buttons.length)][(int) (Math.random() * button.length)].doClick();
            }


            clickerLabel.setText("Clicks: 0");
            notFirstTime.set(true);
        });

        JButton newGame = createButton(200,350,100,Color.yellow,"New Game");
        newGame.addActionListener(e -> {
            mainGameFrame.setVisible(false);
            difficultySelectScreen();
        });


        mainGameFrame.add(refresh);
        mainGameFrame.add(newGame);
        mainGameFrame.add(clickerLabel);
        mainGameFrame.add(highScoreClicks);

        refresh.doClick();
    }
    //This is how I did everything in the difficulty select screen
    public static void difficultySelectScreen() {
        JFrame frame = createAndShowFrame("Difficulty Select");

        JButton easy = createButton(50,50,100,Color.white,"Easy");
        JButton medium = createButton(50,150,100,Color.lightGray,"Medium");
        JButton hard = createButton(50,250,100,Color.gray,"Hard");
        JButton impossible = createButton(50,350,100,Color.darkGray,"Impossible");
        JButton instructions = createButton(250,200,100,Color.yellow,"How to Play");


        easy.addActionListener(actionEvent -> { difficulty = 0; sizeSelectScreen(); frame.setVisible(false);});
        medium.addActionListener(actionEvent -> { difficulty = 1; sizeSelectScreen(); frame.setVisible(false); });
        hard.addActionListener(actionEvent -> { difficulty = 2; sizeSelectScreen(); frame.setVisible(false); });
        impossible.addActionListener(actionEvent -> { difficulty = 666; sizeSelectScreen(); frame.setVisible(false);});
        instructions.addActionListener(actionEvent -> { instructionsScreen();frame.setVisible(false);});


        frame.add(easy);
        frame.add(medium);
        frame.add(hard);
        frame.add(impossible);
        frame.add(instructions);

    }

    private static void instructionsScreen() {
        JFrame frame = createAndShowFrame("Directions and Tips");
        JLabel instructionLabel1 = makeLabel("In this game your goal is to match all of the squares and make them the same color."
        ,75,0,500,50);
        JLabel instructionLabel2 = makeLabel( " \n To do this you must click on the sqaures, changeing its and all the sqaures touching its color. ",
                75,50,600,50);
        JLabel instructionLabel3 = makeLabel("\n Easy has two colors, medium has three, hard has five, and impossible has twelve. "
                ,75,100,500,50);
        JLabel instructionLabel4 = makeLabel("\n The reset button re-scrambles the puzzle and keeps track of the lowest number of clicks it took to solve it."
                ,75,150,900,50);
        JLabel instructionLabel5 = makeLabel("\n New Game button takes you back  to the difficulty select screen.",
                75,200,500,50);
        JButton backButton = createButton(0,350,100,Color.yellow,"back");

        backButton.addActionListener(actionEvent -> {
            difficultySelectScreen(); frame.setVisible(false);
        });

        frame.add(instructionLabel1);
        frame.add(instructionLabel2);
        frame.add(instructionLabel3);
        frame.add(instructionLabel4);
        frame.add(instructionLabel5);
        frame.add(backButton);

    }

    //This is how I did everything for the size select screen
    public static void sizeSelectScreen() {
        JFrame frame = createAndShowFrame("Size Select");

        JButton threeByThree = createButton(50,50,100,Color.white,"3x3");
        JButton fourByFour = createButton(50,150,100,Color.lightGray,"4x4");
        JButton fiveByFive = createButton(50,250,100,Color.gray,"5x5");
        JButton sixBySix = createButton(50,350,100,Color.darkGray,"6x6");

        threeByThree.addActionListener(actionEvent -> { size = 3; runMainGame(); frame.setVisible(false);});
        fourByFour.addActionListener(actionEvent -> { size = 4; runMainGame(); frame.setVisible(false); });
        fiveByFive.addActionListener(actionEvent -> { size = 5; runMainGame(); frame.setVisible(false); });
        sixBySix.addActionListener(actionEvent -> { size = 6; runMainGame(); frame.setVisible(false);});

        frame.add(sixBySix);
        frame.add(fiveByFive);
        frame.add(fourByFour);
        frame.add(threeByThree);
    }
    // Every button you see was created with this method
    public static MyButton createButton(int x, int y, int size, Color color) {
        MyButton button = new MyButton();
        button.setOpaque(true);
        button.setBackground(color);
        button.setBounds(x,y,size,size);
        return button;
    }
    //Or this method, different type to return because idk
    public static JButton createButton(int x, int y, int size, Color color,String name) {
        JButton button = new JButton();
        button.setBackground(color);
        button.setBounds(x,y,size,size);
        button.setText(name);
        return button;
    }
    //Each time a new screen pops up this method is called
    public static JFrame createAndShowFrame(String name) {
        JFrame J = new JFrame(name);
        J.setSize(700,500);
        J.setLayout(null);
        J.setVisible(true);
        return J;
    }
    //Everytime you press a button on the main game this is called, to change color of whatever button I send it
    public static void changeColor(JButton b) {
        if (difficulty == 0) {
            if (b.getBackground().equals(Color.red))
                b.setBackground(Color.blue);
            else
                b.setBackground(Color.red);
        }else if (difficulty == 1){
            if (b.getBackground().equals(Color.red))
                b.setBackground(Color.blue);
            else if (b.getBackground().equals(Color.blue))
                b.setBackground(Color.green);
            else
                b.setBackground(Color.red);
        }else if (difficulty == 2){
            if (b.getBackground().equals(Color.black))
                b.setBackground(Color.darkGray);
            else if (b.getBackground().equals(Color.darkGray))
                b.setBackground(Color.gray);
            else if (b.getBackground().equals(Color.gray))
                b.setBackground(Color.lightGray);
            else if (b.getBackground().equals(Color.lightGray))
                b.setBackground(Color.white);
            else
                b.setBackground(Color.black);
        }else {
            if (b.getBackground().equals(Color.black))
                b.setBackground(Color.darkGray);
            else if (b.getBackground().equals(Color.darkGray))
                b.setBackground(Color.gray);
            else if (b.getBackground().equals(Color.gray))
                b.setBackground(Color.lightGray);
            else if (b.getBackground().equals(Color.lightGray))
                b.setBackground(Color.white);
            else if (b.getBackground().equals(Color.white))
                b.setBackground(Color.pink);
            else if (b.getBackground().equals(Color.pink))
                b.setBackground(Color.red);
            else if (b.getBackground().equals(Color.red))
                b.setBackground(Color.magenta);
            else if (b.getBackground().equals(Color.magenta))
                b.setBackground(Color.blue);
            else if (b.getBackground().equals(Color.blue))
                b.setBackground(Color.cyan);
            else if (b.getBackground().equals(Color.cyan))
                b.setBackground(Color.green);
            else if (b.getBackground().equals(Color.green))
                b.setBackground(Color.orange);
            else if (b.getBackground().equals(Color.orange))
                b.setBackground(Color.yellow);
            else if (b.getBackground().equals(Color.yellow))
                b.setBackground(Color.black);
        }
    }
    //This is used to make every label you see, which is hopefully the clicker counter,timer,and high-scores
    public static JLabel makeLabel(String name,int x,int y, int length, int height) {
        JLabel label = new JLabel(name);
        label.setBounds(x,y,length,height);
        return label;
    }
    //This...Updates the clicker count everytime you press a button
    public static void updateClickCount(JLabel j,int u) {
        j.setText("Clicks: " + (Integer.parseInt(j.getText().substring(8)) + u));
    }
    //Chooses a random color of the 12 given with Color.
    public static Color randomColor() {
        int rand = (int) (Math.random() * 12);
        switch (rand) {
            case 0:
                return Color.red;
            case 1:
                return Color.green;
            case 2:
                return Color.orange;
            case 3:
                return Color.yellow;
            case 4:
                return Color.blue;
            case 5:
                return Color.magenta;
            case 6:
                return Color.cyan;
            case 7:
                return Color.white;
            case 8:
                return Color.gray;
            case 9:
                return Color.lightGray;
            default:
                return Color.black;
        }
    }
    public static void updateHighScore(JLabel c, JLabel h) {
        int clickNow = Integer.parseInt(c.getText().substring(8));
        int highscore = Integer.parseInt(h.getText().substring(11));
        if (clickNow < highscore) {
            h.setText("Highscore: " + clickNow);
        }
    }
    // detmines if all squares are same
    public static boolean won(MyButton[][] b) {
        for (MyButton[] myButtons : b) {
            for (int y = 0; y < b.length; y++) {
                Color color1 = b[0][0].getBackground();
                Color color2 = myButtons[y].getBackground();
                if (color1 != color2) {
                    return false;
                }
            }
        }
        return true;
    }
    //This is a unnecessary class I made to use instead of a JButton, because I wanted to use the method I assigned to them, only long after realizing this was not necessary
    static class MyButton extends JButton {
        public ActionListener action;
        public MyButton() {
            super();
        }

        @Override
        public void addActionListener(ActionListener l) {
            action = l;
            super.addActionListener(l);
        }
    }


}
