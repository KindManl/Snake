package persanal.dorn;

import javax.swing.*;
import java.io.IOException;


public class MyFrame extends JFrame {

    MyFrame() throws IOException {
        Panel panel = new Panel();
        this.add(panel);
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
