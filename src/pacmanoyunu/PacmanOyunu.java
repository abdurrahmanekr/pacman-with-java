package pacmanoyunu;

import javax.swing.*;
import java.awt.*;

public class PacmanOyunu extends JFrame {
    public static final int SCREEN_SIZE = 480;
    PacmanOyunu() {
        initUI();
    }

    private void initUI() {

        add(new OyunPanel());

        setResizable(false);
        pack();

        // +100 en altta skor değerlerini görebilmek için eklendi
        setSize(new Dimension(SCREEN_SIZE, SCREEN_SIZE + 100));
        setTitle("Pacman Oyunu");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            PacmanOyunu ex = new PacmanOyunu();
            ex.setVisible(true);
        });
    }
}
