package Game;

import static Game.Card.BackGroundIcon;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.border.LineBorder;

public class Board extends JFrame {

    Container PaneCont;

    JSuperPat JPanPattern;
    AnyColor AC;
    SameColor SC;
    ZebraColor ZC;

    Joption option;
    int score = 0;
    int bestscore = 0;
    int hidescore = 0;
    int timeHint = 3;

    boolean isShow = false;

    Backgroundclass bgc = new Backgroundclass();

    JMenuBar jmb;
    JMenu JGame;
    JMenuItem Jnew;
    JMenu JColor, JCard;
    JMenuItem ch1, ch2, bg3;

    Timer pt;
    int scnd = 0;
    int mint = 0;
    int Bons = 2500;
    int bestscnd = 59;
    int bestmint = 9;

    public Board() {
        super("Memory Match");
        setIconImage();
//        setMouseCursor();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        PaneCont = getContentPane();
        PaneCont.setLayout(null);

        AC = new AnyColor();
        SC = new SameColor();
        ZC = new ZebraColor();
        Timer T = new Timer(750, (ActionEvent ae) -> {
            this.checkCards();
        });
        T.setRepeats(false);
        AC.t = T;
        SC.t = T;
        ZC.t = T;

        JPanPattern = AC;

        option = new Joption();

        pt = new Timer(1000, e -> {
            scnd++;
            Bons -= 10;
            if (scnd == 60) {
                scnd = 0;
                mint++;
            }
            option.jltime.setText("    " + mint + ":" + scnd);
        });
        pt.setRepeats(true);
        pt.start();

        try {
            Image BackGround = ImageIO.read(getClass().getResource("/data/B1.png"));
            BackGroundIcon = new ImageIcon(BackGround.getScaledInstance(72, 96, Image.SCALE_SMOOTH));
            JPanPattern.cards.forEach((x) -> {
                x.setIcon(BackGroundIcon);
            });
        } catch (IOException ex) {
            System.out.println(ex);
        }

        option.inf.setText("\n  Your Score : " + score + " \t \n\n"
                + "  Best Score : " + bestscore + "  \n"
                + "  Best Time :  " + bestmint + ":" + bestscnd + "  \n");
        option.jlhint.setText("Your hint : " + timeHint);

        PaneCont.add(JPanPattern);
        PaneCont.add(option);

        jmb = new JMenuBar();
        setJMenuBar(jmb);
        JGame = new JMenu("Game");
        jmb.add(JGame);
        Jnew = new JMenuItem("New Game");
        JGame.add(Jnew);
        JColor = new JMenu("Color");
        jmb.add(JColor);
        ch1 = new JMenuItem("Chosse Color");
        JColor.add(ch1);
        ch2 = new JMenuItem("Random Color");
        JColor.add(ch2);
        JCard = new JMenu("Card");
        jmb.add(JCard);
        bg3 = new JMenuItem("Chosse Backgroung");
        JCard.add(bg3);

        for (Card x : AC.cards) {
            x.addKeyListener(keyshow);
        }
        for (Card x : ZC.cards) {
            x.addKeyListener(keyshow);
        }
        for (Card x : SC.cards) {
            x.addKeyListener(keyshow);
        }

        option.newbtn.addActionListener(A2);
        option.sortbtn.addActionListener(Asor);
        option.shufbtn.addActionListener(Ashuf);
        option.hintbtn.addActionListener(Ahint);
        option.mode.addItemListener(itemmode);

        Jnew.addActionListener(A2);
        ch1.addActionListener(A3);
        ch2.addActionListener(A4);
        bg3.addActionListener(A5);

        for (JButton JL : bgc.JL) {
            JL.addActionListener(AX2);
        }

        PaneCont.setBackground(JPanPattern.getBackground());
    }

    public void NewGame() {
        isShow = false;
        mint = 0;
        scnd = 0;
        pt.start();
        timeHint = 3;
        if (score > bestscore) {
            bestscore = score;
        }
        score = 0;
        hidescore = 0;
        option.inf.setForeground(Color.RED);
        option.inf.setText("\n  Your Score : " + score + " \t \n\n"
                + "  Best Score : " + bestscore + "  \n"
                + "  Best Time :  " + bestmint + ":" + bestscnd + "");
        option.jlhint.setText("Your hint : " + timeHint);
        JPanPattern.t.stop();
        JPanPattern.c1 = null;
        JPanPattern.c2 = null;
        JPanPattern.selectedCard = null;
        JPanPattern.setVisible(false);
        Color color1 = JPanPattern.getBackground();
        JPanPattern.cards.forEach((x) -> {
            x.setBorder(new LineBorder(Color.black));
            x.setEnabled(true);
        });
        JPanPattern.NewGame();
        JPanPattern.setBackground(color1);

        try {
            JPanPattern.cards.forEach((x) -> {
                x.setIcon(BackGroundIcon);
            });
        } catch (Exception ex) {
            System.out.println(ex);
        }
        JPanPattern.setVisible(true);
    }

    ActionListener A2 = (ActionEvent ae) -> {
        this.NewGame();
    };

    ActionListener A3 = (ActionEvent ae) -> {
        Color c1 = JPanPattern.getBackground();
        c1 = JColorChooser.showDialog(ch1, "Chose Color", c1);
        PaneCont.setBackground(c1);
        JPanPattern.setBackground(c1);
        for (Card card : JPanPattern.cards) {
            card.setBackground(c1);
        }

    };

    ActionListener A4 = (ActionEvent ae) -> {
        Random random = new Random();
        final float hue = random.nextFloat();
        final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
        final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
        Color c2 = Color.getHSBColor(hue, saturation, luminance);
        PaneCont.setBackground(c2);
        JPanPattern.setBackground(c2);
        for (Card card : JPanPattern.cards) {
            card.setBackground(c2);
        }
    };

    ActionListener A5 = (ActionEvent ae) -> {
        bgc.setVisible(true);
    };

    ActionListener AX = (ActionEvent ae) -> {

        if (ae.getSource() == bgc.JL[0]) {
            JPanPattern.c1 = null;
            JPanPattern.selectedCard = null;
            bgc.setVisible(false);
            Card.BackGroundIcon = (ImageIcon) bgc.JL[0].getIcon();
            JPanPattern.cards.stream().filter((card) -> (!card.getMatched())).forEachOrdered((card) -> {
                card.setIcon(BackGroundIcon);
            });
        } else if (ae.getSource() == bgc.JL[1]) {
            JPanPattern.c1 = null;
            JPanPattern.selectedCard = null;
            bgc.setVisible(false);
            Card.BackGroundIcon = (ImageIcon) bgc.JL[1].getIcon();
            JPanPattern.cards.stream().filter((card) -> (!card.getMatched())).forEachOrdered((card) -> {
                card.setIcon(BackGroundIcon);
            });
        } else if (ae.getSource() == bgc.JL[2]) {
            JPanPattern.c1 = null;
            JPanPattern.selectedCard = null;
            bgc.setVisible(false);
            Card.BackGroundIcon = (ImageIcon) bgc.JL[2].getIcon();
            JPanPattern.cards.stream().filter((card) -> (!card.getMatched())).forEachOrdered((card) -> {
                card.setIcon(BackGroundIcon);
            });
        } else if (ae.getSource() == bgc.JL[3]) {
            JPanPattern.c1 = null;
            JPanPattern.selectedCard = null;
            bgc.setVisible(false);
            Card.BackGroundIcon = (ImageIcon) bgc.JL[3].getIcon();
            JPanPattern.cards.stream().filter((card) -> (!card.getMatched())).forEachOrdered((card) -> {
                card.setIcon(BackGroundIcon);
            });
        } else if (ae.getSource() == bgc.JL[4]) {
            JPanPattern.c1 = null;
            JPanPattern.selectedCard = null;
            bgc.setVisible(false);
            Card.BackGroundIcon = (ImageIcon) bgc.JL[4].getIcon();
            JPanPattern.cards.stream().filter((card) -> (!card.getMatched())).forEachOrdered((card) -> {
                card.setIcon(BackGroundIcon);
            });
        } else if (ae.getSource() == bgc.JL[5]) {
            JPanPattern.c1 = null;
            JPanPattern.selectedCard = null;
            bgc.setVisible(false);
            Card.BackGroundIcon = (ImageIcon) bgc.JL[5].getIcon();
            JPanPattern.cards.stream().filter((card) -> (!card.getMatched())).forEachOrdered((card) -> {
                card.setIcon(BackGroundIcon);
            });
        }
    };

    ActionListener AX2 = (ActionEvent ae) -> {

        JButton x = (JButton) ae.getSource();
        JPanPattern.t.stop();
        JPanPattern.c1 = null;
        JPanPattern.c2 = null;
        JPanPattern.selectedCard = null;
        bgc.setVisible(false);
        Card.BackGroundIcon = (ImageIcon) x.getIcon();
        JPanPattern.cards.stream().filter((card) -> (!card.getMatched())).forEachOrdered((card) -> {
            card.setIcon(BackGroundIcon);
        });

    };

    ActionListener Asor = (ActionEvent ae) -> {
//         if (score > -5000)
//         hidescore = score-1;
//         score = -10000;
//            if (score>bestscore) {
//            bestscore = score; 
//            option.inf.setForeground(Color.GREEN); }
//            else option.inf.setForeground(Color.RED);
//            option.inf.setText("\n  Your Score : "+score+" \t \n\n"
//             + "  Best Score : "+bestscore+"  \n"
//             + "  Best Time :  "+bestmint+":"+bestscnd+"");
        JPanPattern.setVisible(false);

        JPanPattern.removeAll();

        ArrayList<Card> x[] = new ArrayList[6];
        for (int i = 0; i < 6; i++) {
            x[i] = new ArrayList();
        }

        x[0].addAll(JPanPattern.cards.subList(0, 9));
        x[1].addAll(JPanPattern.cards.subList(9, 18));
        x[2].addAll(JPanPattern.cards.subList(18, 27));
        x[3].addAll(JPanPattern.cards.subList(27, 36));
        x[4].addAll(JPanPattern.cards.subList(36, 45));
        x[5].addAll(JPanPattern.cards.subList(45, 54));

        JPanPattern.cards.clear();

        for (ArrayList<Card> arrayList : x) {
            Collections.sort(arrayList);
            JPanPattern.cards.addAll(arrayList);
        }

        // Collections.sort(JPanPattern.cards);
        for (Card card : JPanPattern.cards) {
            JPanPattern.add(card);
        }

        JPanPattern.setVisible(true);

//            if (hidescore > 0) 
//     JOptionPane.showMessageDialog(null, "Do shuffle to return your score");
    };

    ActionListener Ashuf = (ActionEvent ae) -> {
        if (hidescore != 0) {
            score = hidescore;
        }
        hidescore = 0;
        if (score > bestscore) {
            bestscore = score;
            option.inf.setForeground(Color.GREEN);
        } else {
            option.inf.setForeground(Color.RED);
        }
        option.inf.setText("\n  Your Score : " + score + " \t \n\n"
                + "  Best Score : " + bestscore + "  \n"
                + "  Best Time :  " + bestmint + ":" + bestscnd + "");
        JPanPattern.setVisible(false);
        Collections.shuffle(JPanPattern.cards);
        for (Card card : JPanPattern.cards) {
            JPanPattern.add(card);
        }
        JPanPattern.setVisible(true);
    };

    ActionListener Ahint = (ActionEvent ae) -> {

        Timer t = new Timer(900, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JPanPattern.cards.forEach((card) -> {
                    if (!card.getMatched()) {
                        card.setBorder(new LineBorder(Color.black));
                    }
                });
            }
        });
        t.setRepeats(false);

        if (JPanPattern.c1 != null) {
            if (timeHint > 0) {
                for (Card card : JPanPattern.cards) {
                    if (JPanPattern.isSame(JPanPattern.c1, card) && card != JPanPattern.c1 && !card.getMatched()) {
                        card.setBorder(new LineBorder(Color.RED, 4));
                    }
                    t.start();
                }
                --timeHint;
                option.jlhint.setText("Your hint : " + timeHint);
            } else {
                JOptionPane.showMessageDialog(null, "your hints is end");
            }

        }
    };

    KeyAdapter keyshow = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_Q) {
                if (isShow) {
                    hideCard();
                } else {
                    showCard();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_E) {
                timeHint++;
                option.jlhint.setText("Your hint : " + timeHint);
            }

        }

    };

    ItemListener itemmode = (ItemEvent ie) -> {
        if (option.mode.getSelectedItem().equals("Any Color")) {
            mod1();
        } else if (option.mode.getSelectedItem().equals("Same Color")) {
            mod2();
        } else if (option.mode.getSelectedItem().equals("Zebra")) {
            mod3();
        }
    };

    public void mod1() {
        isShow = false;
        mint = 0;
        scnd = 0;
        timeHint = 3;
        if (score > bestscore) {
            bestscore = score;
        }
        score = 0;
        hidescore = 0;
        option.inf.setForeground(Color.RED);
        option.inf.setText("\n  Your Score : " + score + " \t \n\n"
                + "  Best Score : " + bestscore + "  \n"
                + "  Best Time :  " + bestmint + ":" + bestscnd + "  \n");
        option.jlhint.setText("Your hint : " + timeHint);
        JPanPattern.setVisible(false);
        JPanPattern.t.stop();
        AC.NewGame();
        AC.setBackground(JPanPattern.getBackground());
        JPanPattern = AC;
        try {
            JPanPattern.cards.forEach((x) -> {
                x.setBorder(new LineBorder(Color.black));
                x.setEnabled(true);
                x.setIcon(BackGroundIcon);
            });
        } catch (Exception ex) {
            System.out.println(ex);
        }
        PaneCont.add(JPanPattern);
        JPanPattern.setVisible(true);
    }

    public void mod2() {
        isShow = false;
        mint = 0;
        scnd = 0;
        timeHint = 3;
        if (score > bestscore) {
            bestscore = score;
        }
        score = 0;
        hidescore = 0;
        option.inf.setForeground(Color.RED);
        option.inf.setText("\n  Your Score : " + score + " \t \n\n"
                + "  Best Score : " + bestscore + "  \n"
                + "  Best Time :  " + bestmint + ":" + bestscnd + "  \n");
        option.jlhint.setText("Your hint : " + timeHint);
        JPanPattern.setVisible(false);
        JPanPattern.t.stop();
        SC.NewGame();
        SC.setBackground(JPanPattern.getBackground());
        JPanPattern = SC;
        try {
            JPanPattern.cards.forEach((x) -> {
                x.setBorder(new LineBorder(Color.black));
                x.setEnabled(true);
                x.setIcon(Card.BackGroundIcon);
            });
        } catch (Exception ex) {
            System.out.println(ex);
        }
        PaneCont.add(JPanPattern);
        JPanPattern.setVisible(true);
    }

    public void mod3() {
        isShow = false;
        mint = 0;
        scnd = 0;
        timeHint = 3;
        if (score > bestscore) {
            bestscore = score;
        }
        score = 0;
        hidescore = 0;
        option.inf.setForeground(Color.RED);
        option.inf.setText("\n  Your Score : " + score + " \t \n\n"
                + "  Best Score : " + bestscore + "  \n"
                + "  Best Time :  " + bestmint + ":" + bestscnd + "  \n");
        option.jlhint.setText("Your hint : " + timeHint);
        JPanPattern.setVisible(false);
        JPanPattern.t.stop();
        ZC.NewGame();
        ZC.setBackground(JPanPattern.getBackground());
        JPanPattern = ZC;
        try {
            JPanPattern.cards.forEach((x) -> {
                x.setBorder(new LineBorder(Color.black));
                x.setEnabled(true);
                x.setIcon(Card.BackGroundIcon);
            });
        } catch (Exception ex) {
            System.out.println(ex);
        }
        PaneCont.add(JPanPattern);
        JPanPattern.setVisible(true);
    }

    public void showCard() {
        isShow = true;
        JPanPattern.setVisible(false);
        for (Card card : JPanPattern.cards) {
            if (!card.getMatched()) {
                card.setIcon(new ImageIcon(card.getImg()));
            }
            //card.setEnabled(false);
        }
        JPanPattern.setVisible(true);
    }

    public void hideCard() {
        isShow = false;
        JPanPattern.setVisible(false);
        JPanPattern.t.stop();
        JPanPattern.c1 = null;
        JPanPattern.c2 = null;
        JPanPattern.selectedCard = null;
        for (Card card : JPanPattern.cards) {
            if (!card.getMatched()) {
                card.setEnabled(true);
                card.setIcon(Card.BackGroundIcon);
            }
        }
        JPanPattern.setVisible(true);
    }

    public void checkCards() {
        if (JPanPattern.isSame(JPanPattern.c1, JPanPattern.c2)) {
            score += 100;
            if (score > bestscore) {
                bestscore = score;
                option.inf.setForeground(Color.GREEN);
            } else {
                option.inf.setForeground(Color.RED);
            }
            option.inf.setText("\n  Your Score : " + score + " \t \n\n"
                    + "  Best Score : " + bestscore + "  \n"
                    + "  Best Time :  " + bestmint + ":" + bestscnd + "  \n");
            JPanPattern.c1.setIcon(null);
            JPanPattern.c2.setIcon(null);
            JPanPattern.c1.setBorder(null);
            JPanPattern.c2.setBorder(null);
//            JPanPattern.c1.setBackground(JPanPattern.getBackground()); 
//            JPanPattern.c2.setBackground(JPanPattern.getBackground());
            JPanPattern.c1.setEnabled(false);
            JPanPattern.c2.setEnabled(false);
            JPanPattern.c1.setMatched(true);
            JPanPattern.c2.setMatched(true);
            if (JPanPattern.isGameWon()) {
                pt.stop();
                score += Bons;
                if (score >= bestscore) {
                    bestscore = score;
                }
                if (mint < bestmint) {
                    bestmint = mint;
                    bestscnd = scnd;
                }
                if (mint == bestmint && scnd < bestscnd) {
                    bestmint = mint;
                    bestscnd = scnd;
                }

                int response = JOptionPane.showConfirmDialog(null, "You win !! "
                        + "\nYour Time Bouns :" + Bons
                        + "\nYour Score :" + score + ""
                        + "\nPlaying time " + scnd + ":" + mint
                        + "\nAgain ?", "You win",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.NO_OPTION) {
                    System.exit(0);
                } else if (response == JOptionPane.YES_OPTION) {
                    NewGame();
                } else if (response == JOptionPane.CLOSED_OPTION) {
                    NewGame();
                }
            }
        } else {
            score -= 10;
            if (score > bestscore) {
                bestscore = score;
                option.inf.setForeground(Color.GREEN);
            } else {
                option.inf.setForeground(Color.RED);
            }
            option.inf.setText("\n  Your Score : " + score + " \t \n\n"
                    + "  Best Score : " + bestscore + "  \n"
                    + "  Best Time :  " + bestmint + ":" + bestscnd + "  \n");
            try {
                JPanPattern.c1.setIcon(BackGroundIcon);
                JPanPattern.c2.setIcon(BackGroundIcon);
            } catch (Exception ex) {
            }
        }

        JPanPattern.c1 = null;
        JPanPattern.c2 = null;
    }

    private void setIconImage() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/data/B1.png")));
    }

    public void setMouseCursor() {
        try {
            setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                    //                    Toolkit.getDefaultToolkit().getImage(getClass().getResource("/data/a.gif"))
                    ImageIO.read(getClass().getResource("/data/ad.png")),
                    new Point(0, 0),
                    "custom cursor"));
        } catch (Exception rx) {
        }
    }

}
