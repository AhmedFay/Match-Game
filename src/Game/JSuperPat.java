package Game;

import static Game.Card.BackGroundIcon;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class JSuperPat extends JPanel {

    List<Card> cards;
    Card selectedCard;
    Card c1;
    Card c2;
    Timer t;

    public JSuperPat() {

    }

    public void NewGame() {
        this.setVisible(false);
        t.stop();
        c1 = null;
        c2 = null;
        selectedCard = null;
        this.removeAll();
        Collections.shuffle(this.cards);
        try {
            for (Card x : this.cards) {
                x.setIcon(BackGroundIcon);
                x.setMatched(false);
                x.setEnabled(true);
                this.add(x);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        this.setVisible(true);
    }

    public boolean isSame(Card c1, Card c2) {
        return (c1.getId().equals(c2.getId()));
    }

    public boolean isGameWon() {
        for (Card c : this.cards) {
            if (!c.getMatched()) {
                return false;
            }
        }
        return true;
    }

    public void doTurn() {
        if (c1 == null && c2 == null && !selectedCard.getMatched()) {
            c1 = selectedCard;
            c1.setIcon(new ImageIcon(c1.getImg()));

        }

        if (c1 != null && c1 != selectedCard && c2 == null && !selectedCard.getMatched()) {
            c2 = selectedCard;
            c2.setIcon(new ImageIcon(c2.getImg()));
            t.start();

        }
//        if (c1 != null && c1 != selectedCard && c2 != null && c2 != selectedCard && !selectedCard.getMatched()){
//            checkCards();
//            c1 = selectedCard;
//            c1.setIcon(new ImageIcon(c1.getImg()));
//        }

    }

    public void checkCards() {
        if (isSame(c1, c2)) {
            c1.setIcon(null);
            c2.setIcon(null);
            c1.setEnabled(false);
            c2.setEnabled(false);
            c1.setMatched(true);
            c2.setMatched(true);
            if (this.isGameWon()) {
                int response = JOptionPane.showConfirmDialog(null, "You win!? \nAgain ?", "You win",
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
            try {
                c1.setIcon(BackGroundIcon);
                c2.setIcon(BackGroundIcon);
            } catch (Exception ex) {
            }
        }
        c1 = null;
        c2 = null;
    }

}
