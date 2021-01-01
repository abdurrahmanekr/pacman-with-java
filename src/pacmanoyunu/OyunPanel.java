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
        final int DOT_SIZE = SQUARE/4; // duvarların, noktaların genişliği
        final int OBJ_CR = (SQUARE - DOT_SIZE) / 2; // nesneni kordinati

        for (int i = 0; i < Oyun.MAP_SIZE; i++) {
            for (int j = 0; j < Oyun.MAP_SIZE; j++) {

                // siyah arkaplana nesneler çizilecek
                g2d.setPaint(Color.black);
                g2d.fillRect(x, y, SQUARE, SQUARE);

                switch (oyun.getMapBlock(i, j)) {
                    case 1: // yatay duvar
                        g2d.setPaint(Color.blue);
                        g2d.fillRect(x, y + OBJ_CR, SQUARE, DOT_SIZE);
                        break;
                    case 2: // yem
                        g2d.setPaint(Color.yellow);
                        g2d.fillOval(x + OBJ_CR, y + OBJ_CR, DOT_SIZE, DOT_SIZE);
                        break;
                    case 3: // dikey duvar
                        g2d.setPaint(Color.blue);
                        g2d.fillRect(x + OBJ_CR, y, DOT_SIZE, SQUARE);
                        break;
                    case 4: // köşe 1
                        g2d.setPaint(Color.blue);
                        g2d.fillRect(x + OBJ_CR, y + OBJ_CR, SQUARE - DOT_SIZE, DOT_SIZE);
                        g2d.fillRect(x + OBJ_CR, y + OBJ_CR, DOT_SIZE, SQUARE - DOT_SIZE);
                        break;
                    case 5: // köşe 2
                        g2d.setPaint(Color.blue);
                        g2d.fillRect(x, y + OBJ_CR, SQUARE - OBJ_CR, DOT_SIZE);
                        g2d.fillRect(x + OBJ_CR, y + OBJ_CR, DOT_SIZE, SQUARE - OBJ_CR);
                        break;
                    case 6: // köşe 3
                        g2d.setPaint(Color.blue);
                        g2d.fillRect(x + OBJ_CR, y, DOT_SIZE, SQUARE - OBJ_CR);
                        g2d.fillRect(x + OBJ_CR, y + OBJ_CR, SQUARE - OBJ_CR, DOT_SIZE);
                        break;
                    case 7: // köşe 4
                        g2d.setPaint(Color.blue);
                        g2d.fillRect(x + OBJ_CR, y, DOT_SIZE, SQUARE - OBJ_CR);
                        g2d.fillRect(x, y + OBJ_CR, SQUARE - OBJ_CR, DOT_SIZE);
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
