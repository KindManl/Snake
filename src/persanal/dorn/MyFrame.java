package persanal.dorn;

import javax.swing.*;


public class MyFrame extends JFrame {

    MyFrame(){
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
