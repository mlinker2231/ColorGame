import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class ColorGame {
    static int difficulty = 0;
    static int size = 3;
    static int count = 0;
    public static void main(String[] args) {
        difficultySelectScreen();

    }
    public static void runMainGame() {
        JFrame mainGameFrame = createAndShowFrame("GOOD LUCK");

        MyButton[][] buttons = new MyButton[size][size];

        JLabel clickCounter = makeLabel("Clicks: 0",350,0,300,50);

        for (int y = 0; y < buttons.length; y++) {
            for (int x = 0; x < buttons[y].length; x++) {
                buttons[x][y] = createButton(x * 53, y * 53, 50, randomColor());
                buttons[x][y].setOpaque(true);
                buttons[x][y].setBorderPainted(false);

                int finalX = x;
                int finalY = y;
                buttons[x][y].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        count++;
                        clickCounter.setText("Clicks: " + count);
                        System.out.println(count);
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


                    }
                });
                mainGameFrame.add(buttons[x][y]);
            }
        }


        JButton refresh = createButton(200,400,100,Color.yellow,"Reset");
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {for (int y = 0; y < buttons.length; y++) {
                for (int z = 0; z < buttons.length; z++) {
                    for (int a = 0; a < buttons[z].length; a++) {
                        if (difficulty != 2)
                        buttons[z][a].setBackground(Color.red);
                        else
                            buttons[z][a].setBackground(Color.black);
                    }
                }
                for (int z = 0; z < buttons.length; z++) {
                    for (int a = 0; a < buttons[z].length; a++) {

                        buttons[(int)(Math.random() * buttons.length)][(int)(Math.random() * buttons[z].length)].doClick();
                    }
                }
            }

            }
        });


        JButton newGame = createButton(300,400,120,Color.yellow,"New Game");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainGameFrame.setVisible(false);
                difficultySelectScreen();
            }
        });


        mainGameFrame.add(refresh);
        mainGameFrame.add(newGame);
        mainGameFrame.add(clickCounter);


        refresh.doClick();
    }
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
    public static void difficultySelectScreen() {
        JFrame frame = createAndShowFrame("Difficulty Select");

        JButton easy = createButton(50,50,100,Color.white,"Easy");
        JButton medium = createButton(50,150,100,Color.lightGray,"Medium");
        JButton hard = createButton(50,250,100,Color.gray,"Hard");
        JButton impossible = createButton(50,350,100,Color.darkGray,"Impossible");

        easy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) { difficulty = 0; sizeSelectScreen(); frame.setVisible(false);}});
        medium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) { difficulty = 1; sizeSelectScreen(); frame.setVisible(false); }});
        hard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) { difficulty = 2; sizeSelectScreen(); frame.setVisible(false); }});
        impossible.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) { difficulty = 666; sizeSelectScreen(); frame.setVisible(false);}});

        frame.add(easy);
        frame.add(medium);
        frame.add(hard);
        frame.add(impossible);
    }
    public static void sizeSelectScreen() {
        JFrame frame = createAndShowFrame("Size Select");

        JButton threeByThree = createButton(50,50,100,Color.white,"3x3");
        JButton fourByFour = createButton(50,150,100,Color.lightGray,"4x4");
        JButton fiveByFive = createButton(50,250,100,Color.gray,"5x5");
        JButton sixBySix = createButton(50,350,100,Color.darkGray,"6x6");

        threeByThree.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) { size = 3; runMainGame(); frame.setVisible(false);}});
        fourByFour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) { size = 4; runMainGame(); frame.setVisible(false); }});
        fiveByFive.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) { size = 5; runMainGame(); frame.setVisible(false); }});
        sixBySix.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) { size = 6; runMainGame(); frame.setVisible(false);}});

        frame.add(sixBySix);
        frame.add(fiveByFive);
        frame.add(fourByFour);
        frame.add(threeByThree);
    }
    public static MyButton createButton(int x, int y, int size, Color color) {
        MyButton button = new MyButton();
        button.setBackground(color);
        button.setBounds(x,y,size,size);
        return button;
    }
    public static JButton createButton(int x, int y, int size, Color color,String name) {
        JButton button = new JButton();
        button.setBackground(color);
        button.setBounds(x,y,size,size);
        button.setText(name);
        return button;
    }
    public static JFrame createAndShowFrame(String name) {
        JFrame J = new JFrame(name);
        J.setSize(500,500);
        J.setLayout(null);
        J.setVisible(true);
        return J;
    }
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
    public static JLabel makeLabel(String name,int x,int y, int length, int width) {
        JLabel label = new JLabel(name);
        label.setBounds(x,y,length,width);
        return label;
    }
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