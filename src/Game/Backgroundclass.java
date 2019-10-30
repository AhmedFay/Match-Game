package Game;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Backgroundclass extends JFrame {

    Container C;
    JButton[] JL = new JButton[6];

    public Backgroundclass() {
        setLocation(500, 200);
        C = getContentPane();
        C.setLayout(new FlowLayout());

        for (int i = 0; i < JL.length; i++) {
            JL[i] = new JButton();
            JL[i].setSize(new Dimension(72, 96));
            JL[i].setPreferredSize(new Dimension(72, 96));
            C.add(JL[i]);
        }

        try {
            Image x1 = ImageIO.read(getClass().getResource("/data/B1.png"));
            JL[0].setIcon(new ImageIcon(x1.getScaledInstance(72, 96, Image.SCALE_SMOOTH)));

            Image x2 = ImageIO.read(getClass().getResource("/data/B2.jpg"));
            JL[1].setIcon(new ImageIcon(x2));

            Image x3 = ImageIO.read(getClass().getResource("/data/B3.jpg"));
            JL[2].setIcon(new ImageIcon(x3));

            Image x4 = ImageIO.read(getClass().getResource("/data/B4.jpg"));
            JL[3].setIcon(new ImageIcon(x4.getScaledInstance(72, 96, Image.SCALE_SMOOTH)));

            Image x5 = ImageIO.read(getClass().getResource("/data/B5.png"));
            JL[4].setIcon(new ImageIcon(x5));

            Image x6 = ImageIO.read(getClass().getResource("/data/B6.jpg"));
            JL[5].setIcon(new ImageIcon(x6));

        } catch (Exception ex) {
            System.out.println(ex);
        }

        pack();

    }

}
