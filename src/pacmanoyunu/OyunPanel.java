package pacmanoyunu;

import javax.swing.*;
import java.awt.*;

public class OyunPanel extends JPanel {
    private Oyun oyun;
    OyunPanel() {
        initPanel();
    }

    private void initPanel() {
        oyun = new Oyun();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintGame(g);

        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Oyun değişkeninde bulunan her şeyin son halini çizer
     */
    public void paintGame(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // haritadaki her konumu alıyorlar
        int x = 0, y = 0;
        final int SQUARE = PacmanOyunu.SCREEN_SIZE / Oyun.MAP_SIZE;
        for (int i = 0; i < Oyun.MAP_SIZE; i++) {
            for (int j = 0; j < Oyun.MAP_SIZE; j++) {

                switch (oyun.getMapBlock(i, j)) {
                    case 0: // boşluk
                        g2d.setPaint(Color.black);
                        g2d.fillRect(x, y, SQUARE, SQUARE);
                        break;
                    case 1: // yatay duvar
                        g2d.setPaint(Color.blue);
                        g2d.fillRect(x, y, SQUARE, SQUARE);
                        break;
                    case 2: // yem
                        g2d.setPaint(Color.orange);
                        g2d.fillRect(x, y, SQUARE, SQUARE);
                        break;
                    case 3: // dikey duvar
                        g2d.setPaint(Color.pink);
                        g2d.fillRect(x, y, SQUARE, SQUARE);
                        break;

                }

                x += SQUARE;
            }
            x = 0;
            y += SQUARE;
        }

        g2d.dispose();
    }
}
