package nl.rug.ai.oop.crazyeights.view;
import nl.rug.ai.oop.crazyeights.controller.MouseController;
import nl.rug.ai.oop.crazyeights.model.AllCards;
import nl.rug.ai.oop.crazyeights.model.Card;
import nl.rug.ai.oop.crazyeights.model.CrazyEights;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CardView extends JPanel {
    public static final Map<String, BufferedImage> cardImages = new HashMap(4);
    public static int CARDS_PER_SUIT = 13;
    public static int DEFAULT_SELECTION_DELTA = -40;

    private static JButton startButton;

    public int Selected =0;
    private String[] hand = {};
    private int selectedCard = -1;
    public static Dimension cardSize = new Dimension(120, 150);
    private int[] xCardPosition = {};
    private int selectionDelta = DEFAULT_SELECTION_DELTA;


    public int getSelectedCard() {
        return selectedCard;
    }

    /**
     * Preloads images links name n image w hash map
     */
    private static void loadImages() {
        try {
            for (String suit : new String[]{"C", "S", "D", "H"}) {
                for (int i = 1; i <= CARDS_PER_SUIT; i++) {
                    cardImages.put(suit + "" + i,
                            ImageIO.read(CardView.class.getResource("/" + suit + String.valueOf(i)+".png")));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public Dimension getPreferredSize() {

        return new Dimension(1000, (int)cardSize.getHeight()+Math.abs(selectionDelta));
    }

    /**
     * Sets up a new panel and mouse interactions.
     */
    public CardView(CrazyEights game) {
        if (cardImages.size() < 1) {
            loadImages();
        }

        JLabel title = new JLabel("Players Hand");
        title.setForeground(Color.white);
        this.add(title);
        this.setHand(game.getPlayer(0).getHand().toList());
        System.out.println("MADE CARDVIEW");


        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                selectedCard = getSelectedCard(e.getX());
                repaint();
            }
        });
    }

    public int getSelectedCard(int x) {
        for (int i = hand.length - 1; i >= 0; i--) {
            if (x > xCardPosition[i] && x < xCardPosition[i] + cardSize.getWidth()) {
                return i;
            }
        }
        return -1;
    }

    public void updateHand(CrazyEights game){

        this.setHand(game.getPlayer(1).getHand().toList());
    }
    @Override
    public void invalidate() {
        super.invalidate();
        selectedCard = -1;
        xCardPosition = new int[hand.length];
        int xDelta = (int)Math.floor(Math.min(cardSize.getWidth() * 1.1, (getWidth() - cardSize.getWidth())/(hand.length - 1)));
        int xPos = (int) ((getWidth() - cardSize.getWidth() - (hand.length - 1)*xDelta) / 2);
        for (int i = 0; i < hand.length; i++) {
            xCardPosition[i] = xPos;
            xPos += xDelta;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        for (int i = 0; i < hand.length; i++) {
            g2d.drawImage(cardImages.get(hand[i]),
                    xCardPosition[i], selectedCard == i ? Math.max(selectionDelta, 0) : Math.max(-selectionDelta, 0),
                    (int)cardSize.getWidth(), (int)cardSize.getHeight(), null);
        }
        g2d.dispose();
    }

    /**
     * Sets the hand of cards and resets the view to match.
     * @param hand hand of cards, where every card is a character "C", "S", "D", or "H"
     *             followed by a number between 1 and 13, or 00 for a face-down card
     */
    public void setHand(String[] hand) {
        this.hand = hand;
        revalidate();
    }

    public int getSelectionDelta() {
        return selectionDelta;
    }

    public void setSelectionDelta(int selectionDelta) {
        this.selectionDelta = selectionDelta;
    }


}
