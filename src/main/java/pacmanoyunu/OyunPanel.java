package main.java.pacmanoyunu;

import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Path2D;

public class OyunPanel extends JPanel implements ActionListener {
    private Oyun oyun;

    OyunPanel() {
        initPanel();
    }

    private void initPanel() {
        oyun = new Oyun(this);

        addKeyListener(new TAdapter());
        setFocusable(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
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
        final float ANIM_RATIO = (float) oyun.getAnimationComplate() / Oyun.PROCESS_DELAY; // animasyon oranı

        // haritanın her karesini çizer
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
                        g2d.setPaint(new Color(190, 135, 109));
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

        // harita çizildikten sonra pacman ve düşmanların pozisyonlarını çizer

        // pacman karakterini çiziyor
        // pacma aslında bir yaydır
        g2d.setPaint(Color.yellow);
        Point paP = oyun.getPacmanPosition();
        g2d.fillArc(
            (int) (paP.x * SQUARE + (oyun.getPacmanAspect().isX() ? oyun.getPacmanAspect().axisSignum() * ANIM_RATIO * SQUARE : 0)),
            (int) (paP.y * SQUARE + (oyun.getPacmanAspect().isY() ? oyun.getPacmanAspect().axisSignum() * ANIM_RATIO * SQUARE : 0)),
            SQUARE,
            SQUARE,
            oyun.getPacmanAspect().getValue() + 45,
            270
        );

        g2d.dispose();
    }

    class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            oyun.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            oyun.keyPressed(e);
        }
    }
}
