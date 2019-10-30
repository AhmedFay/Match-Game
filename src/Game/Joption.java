package Game;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

@SuppressWarnings("unchecked")
public class Joption extends JPanel {

    JTextArea inf;
    JLabel jlmode, jlhint, jltime;
    JComboBox mode;
    JButton newbtn, sortbtn, shufbtn, hintbtn;

    public Joption() {
        this.setBounds(720, 10, 250, 595);
        this.setBorder(new LineBorder(Color.yellow));
        this.setLayout(null);

        inf = new JTextArea();
        inf.setBounds(0, 0, 250, 135);
        inf.setFont(new Font("Bold", 20, 20));
        inf.setBorder(new LineBorder(Color.red));
        inf.setEditable(false);

        jlmode = new JLabel("Select Mode");
        jlmode.setFont(new Font("Bold", 18, 18));
        String str[] = {"Any Color", "Same Color", "Zebra",};
        mode = new JComboBox(str);
        jlmode.setBounds(25, 150, 200, 30);
        mode.setBounds(25, 190, 200, 30);

        newbtn = new JButton("Start");
        newbtn.setBounds(25, 230, 200, 30);

        sortbtn = new JButton("Sort");
        sortbtn.setBounds(25, 270, 200, 30);

        shufbtn = new JButton("Shuffle");
        shufbtn.setBounds(25, 310, 200, 30);

        hintbtn = new JButton("Hint");
        hintbtn.setBounds(25, 350, 200, 30);

        jlhint = new JLabel();
        jlhint.setFont(new Font("Bold", 14, 14));
        jlhint.setBounds(40, 380, 200, 30);

        jltime = new JLabel("    0 : 0");
        jltime.setFont(new Font("Bold", 40, 40));
        jltime.setBorder(new LineBorder(Color.green, 5));
        jltime.setBounds(40, 530, 170, 50);

        this.add(inf);
        this.add(mode);
        this.add(newbtn);
        this.add(sortbtn);
        this.add(shufbtn);
        this.add(hintbtn);
        this.add(jlmode);
        this.add(jlhint);
        this.add(jltime);
    }

}
