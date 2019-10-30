package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class Card extends JButton implements Comparable<Card> {

    private String id;
    private Image img;
    public static ImageIcon BackGroundIcon;
    private boolean matched = false;

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public boolean getMatched() {
        return this.matched;
    }

    public Card() {
        setBorder(new LineBorder(Color.black));
        setSize(new Dimension(72, 96));
        setPreferredSize(new Dimension(72, 96));
    }

    @Override
    public int compareTo(Card t) {
        return (new Integer(this.getId()).compareTo(new Integer(t.getId())));
    }

}
