package Game;

import static Game.Card.BackGroundIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class TestColor extends JSuperPat {

    private Timer t;
    JPanel JB[] = new JPanel[4];
    List<JPanel> JBlist;

    public TestColor() {

        setBounds(10, 10, 1100, 400);
        this.setBorder(new BevelBorder(10));
        this.setBackground(Color.WHITE);
        this.setLayout(new GridLayout(4, 13, 5, 5));

        List<Card> cardsList = new ArrayList<Card>();
        List<String> cardVals = new ArrayList<String>();

        for (int i = 0; i < 4; i++) {
            JB[i] = new JPanel();
            JB[i].setLayout(new GridLayout(1, 13, 10, 0));
            JB[i].setBackground(Color.white);
        }
        for (int i = 1; i <= 13; i++) {
            cardVals.add("" + i);
            cardVals.add("s" + i);
            cardVals.add("x" + i);
            cardVals.add("sx" + i);
        }

        //   
        int i = 1;
        for (String val : cardVals) {
            Card c = new Card();
            c.setId(val);

            try {
                Image img = ImageIO.read(getClass().getResource("/data/" + i + ".png"));
                c.setImg(img);
                i++;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }

            c.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    selectedCard = c;
                    doTurn();
                }
            });
            cardsList.add(c);
        }

        this.cards = cardsList;
        Collections.shuffle(this.cards);
        for (Card c : cards) {
            if (c.getId().matches("[0-9]*")) {
                JB[0].add(c);
            }
            if (c.getId().matches("s[0-9]*")) {
                JB[1].add(c);
            }
            if (c.getId().matches("x[0-9]*")) {
                JB[2].add(c);
            }
            if (c.getId().matches("sx[0-9]*")) {
                JB[3].add(c);
            }
        }
        this.add(JB[0]);
        this.add(JB[1]);
        this.add(JB[2]);
        this.add(JB[3]);

        t = new Timer(750, (ActionEvent ae) -> {
            checkCards();
        });
        t.setRepeats(false);

    }

    public void doTurn() {
        if (c1 == null && c2 == null) {
            c1 = selectedCard;
            c1.setIcon(new ImageIcon(c1.getImg()));

        }

        if (c1 != null && c1 != selectedCard && c2 == null) {
            c2 = selectedCard;
            if (c1.getId().matches("[0-9]*") && !c2.getId().matches("[0-9]*")) {
                c2 = selectedCard;
                c2.setIcon(new ImageIcon(c2.getImg()));
                t.start();
            }
            if (c1.getId().matches("s[0-9]*") && !c2.getId().matches("s[0-9]*")) {
                c2 = selectedCard;
                c2.setIcon(new ImageIcon(c2.getImg()));
                t.start();
            }
            if (c1.getId().matches("x[0-9]*") && !c2.getId().matches("x[0-9]*")) {
                c2 = selectedCard;
                c2.setIcon(new ImageIcon(c2.getImg()));
                t.start();
            }
            if (c1.getId().matches("sx[0-9]*") && !c2.getId().matches("sx[0-9]*")) {
                c2 = selectedCard;
                c2.setIcon(new ImageIcon(c2.getImg()));
                t.start();
            }
            t.start();

        }

    }

    public void checkCards() {
        if (c1.getId().equals(("s" + c2.getId())) || ("s" + c1.getId()).equals(c2.getId())) {//match condition
//            c1.setEnabled(false); //disables the button
//            c2.setEnabled(false);
            c1.setMatched(true); //flags the button as having been matched
            c2.setMatched(true);
            if (this.isGameWon()) {
                String str = JOptionPane.showInputDialog("You win! \nAgain ? Any key - No");
                if (str.toUpperCase().equals("NO")) {
                    System.exit(0);
                } else {
                    NewGame();
                }
            }
        } else {
            c1.setBackground(Color.lightGray);
            c2.setBackground(Color.lightGray);
            try {
                c1.setIcon(BackGroundIcon);
                c2.setIcon(BackGroundIcon);
            } catch (Exception ex) {
            }
        }
        c1 = null;
        c2 = null;
    }

    public boolean isGameWon() {
        for (Card c : this.cards) {
            if (c.getMatched() == false) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void NewGame() {
        this.setVisible(false);
        this.removeAll();
        Collections.shuffle(this.cards);

        try {
            for (Card x : this.cards) {
                x.setIcon(BackGroundIcon);
                x.setMatched(false);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        for (Card c : cards) {
            if (c.getId().matches("[0-9]*")) {
                JB[0].add(c);
            }
            if (c.getId().matches("s[0-9]*")) {
                JB[1].add(c);
            }
            if (c.getId().matches("x[0-9]*")) {
                JB[2].add(c);
            }
            if (c.getId().matches("sx[0-9]*")) {
                JB[3].add(c);
            }
        }
        this.add(JB[0]);
        this.add(JB[1]);
        this.add(JB[2]);
        this.add(JB[3]);

        this.setVisible(true);
    }

}
