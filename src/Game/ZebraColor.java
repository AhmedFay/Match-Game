package Game;

import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.border.BevelBorder;

public class ZebraColor extends JSuperPat {

    public ZebraColor() {

        setBounds(0, 0, 720, 650);
        this.setBorder(new BevelBorder(10));
        //  this.setBackground(Color.WHITE);
        //  setBounds(10 , 10, 700 , 600);
        // this.setLayout(new GridLayout(6, 9,5,5));

        cards = new ArrayList<Card>();
        List<String> cardVals = new ArrayList<String>();

        for (int i = 1; i <= 13; i++) {
            cardVals.add("" + i);
            cardVals.add("" + i);
            cardVals.add("20" + i);
            cardVals.add("20" + i);
        }
        cardVals.add("40" + 14);
        cardVals.add("2040" + 14);

        //   
        int i = 1;
        for (String id : cardVals) {
            Card c = new Card();
            c.setId(id);

            try {
                Image img = ImageIO.read(getClass().getResource("/data/" + i + ".png"));
                c.setImg(img);
                i++;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }

            c.addActionListener((ActionEvent ae) -> {
                selectedCard = c;
                doTurn();
            });

            cards.add(c);
        }

        Collections.shuffle(this.cards);
        for (Card c : cards) {
            this.add(c);
        }

    }

    @Override
    public boolean isSame(Card c1, Card c2) {
        return (c1.getId().equals(("20" + c2.getId())) || ("20" + c1.getId()).equals(c2.getId()));
    }

}
