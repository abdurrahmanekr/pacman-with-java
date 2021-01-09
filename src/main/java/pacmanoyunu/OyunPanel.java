package main.java.pacmanoyunu;

import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.*;

public class OyunPanel extends JPanel implements ActionListener {
    private Oyun oyun;

    OyunPanel() {
        initPanel();
    }

    private void initPanel() {
        oyun = new Oyun(this);

        addKeyListener(new TAdapter(this));
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

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        // şekillerin kenarlarını yumuşatmak için
        g2d.setRenderingHints(rh);

        // haritadaki her konumu alıyorlar
        int x = 0, y = 0;
        final int SQUARE = PacmanOyunu.SCREEN_SIZE / Oyun.MAP_SIZE;
        final int DOT_SIZE = SQUARE/4; // duvarların, noktaların genişliği
        final int OBJ_CR = (SQUARE - DOT_SIZE) / 2; // nesneni kordinati
        final float ANIM_RATIO = (float) oyun.getAnimationComplate() / Oyun.PROCESS_DELAY; // animasyon oranı
        final int SQ_CENTER = SQUARE * (Oyun.MAP_SIZE/2); // ortadaki kareye olan uzaklık

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
            (int) (paP.x * SQUARE + (oyun.getPacmanAspect().isX() && !oyun.isBorder() ? oyun.getPacmanAspect().axisSignum() * ANIM_RATIO * SQUARE : 0)),
            (int) (paP.y * SQUARE + (oyun.getPacmanAspect().isY() && !oyun.isBorder() ? oyun.getPacmanAspect().axisSignum() * ANIM_RATIO * SQUARE : 0)),
            SQUARE,
            SQUARE,
            oyun.getPacmanAspect().getValue() + 45,
            270
        );

        // oyundaki düşmanları çizer
        for (Dusman enemy : oyun.getEnemies()) {
            Point eP = enemy.getPoint();
            Yon eAs = enemy.getAspect();
            int ex = (int) (eP.x * SQUARE + (eAs.isX() ? eAs.axisSignum() * ANIM_RATIO * SQUARE : 0));
            int ey = (int) (eP.y * SQUARE + (eAs.isY() ? eAs.axisSignum() * ANIM_RATIO * SQUARE : 0));

            g2d.setPaint(enemy.getColor());

            Area a1 = new Area(new Rectangle2D.Double(ex, ey, SQUARE, SQUARE));
            Area dikey = new Area(new Ellipse2D.Double(ex + OBJ_CR, ey, DOT_SIZE, SQUARE));
            Area yatay = new Area(new Ellipse2D.Double(ex, ey + OBJ_CR, SQUARE, DOT_SIZE));
            Area nokta1 = new Area(new Ellipse2D.Double(ex, ey, DOT_SIZE, DOT_SIZE));
            Area nokta2 = new Area(new Ellipse2D.Double(ex + SQUARE - DOT_SIZE, ey + SQUARE - DOT_SIZE, DOT_SIZE, DOT_SIZE));
            Area nokta3 = new Area(new Rectangle2D.Double(ex + SQUARE - DOT_SIZE, ey, DOT_SIZE, DOT_SIZE));
            Area nokta4 = new Area(new Rectangle2D.Double(ex, ey + SQUARE - DOT_SIZE, DOT_SIZE, DOT_SIZE));

            dikey.add(yatay);
            dikey.add(nokta1);
            dikey.add(nokta2);
            dikey.add(nokta3);
            dikey.add(nokta4);
            g2d.fill(dikey);

        }

        // oyunun son hali ile ilgili olaylar çiziliyor
        // bittiyse sonuçlar, başlamadıysa boşluğa basın gibi
        g2d.setPaint(Color.white);
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));

        // oyun başlamış ve bitmişse
        if (oyun.isStarted() && oyun.isEnded()) {
            // oyunu kazanmışsa
            if (oyun.isWin()) {
                g2d.setPaint(Color.green);
                g2d.drawString("Kazandın!!!", SQ_CENTER - 2 * SQUARE, SQ_CENTER);
            }

            // kaybetmişse
            else {
                g2d.setPaint(Color.red);
                g2d.drawString("Kaybettin!!!", SQ_CENTER - 2 * SQUARE, SQ_CENTER);
            }
        }
        else if (!oyun.isStarted()) {
            g2d.drawString("Başlamak için", SQ_CENTER - 2 * SQUARE, SQ_CENTER - SQUARE);
            g2d.drawString("Boşluk", SQ_CENTER - SQUARE, SQ_CENTER);
        }

        g2d.dispose();
    }

    class TAdapter extends KeyAdapter {
        private final ActionListener listener;
        TAdapter(ActionListener listener) {this.listener = listener;}

        @Override
        public void keyReleased(KeyEvent e) {
            // oyun başlamış ve bitmişse herhangi bir tuşa basında oyunu yeniden oluşturmalı
            if (oyun.isStarted() && oyun.isEnded()) {
                oyun = new Oyun(this.listener);
                oyun.start();
            }
            else {
                oyun.keyReleased(e);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            oyun.keyPressed(e);
        }
    }
}
