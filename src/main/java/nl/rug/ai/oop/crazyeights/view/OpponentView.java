package nl.rug.ai.oop.crazyeights.view;

import nl.rug.ai.oop.crazyeights.model.Card;
import nl.rug.ai.oop.crazyeights.model.CrazyEights;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OpponentView extends JPanel {
    public static BufferedImage cardImages;
    public static int DEFAULT_SELECTION_DELTA = -40;
    private static JButton startButton;
    private String[] hand = {};
    private Dimension cardSize = new Dimension(120, 150);
    private int[] xCardPosition = {};
    private int selectionDelta = DEFAULT_SELECTION_DELTA;
    private int selectedCard = -1;

    private CrazyEights game;

    public void loadImage(){
        try {
            cardImages = ImageIO.read(Objects.requireNonNull(CardView.class.getResource("/deck.png")));
        } catch (IOException e) {
            System.out.println("FAILED");
        }
    }
    public void updateHand(){
        System.out.println("UPdate opponent hand");
        setHand(game.getPlayer(0).getHand().toList());
    }


    public OpponentView(CrazyEights game) {
        this.game= game;
        loadImage();
        JLabel title = new JLabel("Opponents Hand");
        title.setForeground(Color.white);
        this.add(title);
        setHand(game.getPlayer(0).getHand().toList());
        System.out.println("MADE Opponent VIEW");
        updateHand();
        revalidate();
    }





    @Override
    public Dimension getPreferredSize() {

        return new Dimension(1000, (int)cardSize.getHeight()+Math.abs(selectionDelta));
    }

    /**
     * Sets up a new panel and mouse interactions.
     */




    /**
     * Sets the hand of cards and resets the view to match.
     * @param hand hand of cards, where every card is a character "C", "S", "D", or "H"
     *             followed by a number between 1 and 13, or 00 for a face-down card
     */
    public void setHand(String[] hand) {
        this.hand = hand;
        revalidate();
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
        for (int i = 0; i < game.getPlayer(0).getHand().size(); i++) {
            g2d.drawImage(cardImages,
                    xCardPosition[i],
                    selectedCard == i ? Math.max(selectionDelta, 0) : Math.max(-selectionDelta, 0),
                    (int)cardSize.getWidth(),
                    (int)cardSize.getHeight(),
                    null);
        }
        g2d.dispose();
    }






}


