package nl.rug.ai.oop.crazyeights.view;

import nl.rug.ai.oop.crazyeights.controller.ButtonController;
import nl.rug.ai.oop.crazyeights.controller.MouseController;
import nl.rug.ai.oop.crazyeights.model.AllCards;
import nl.rug.ai.oop.crazyeights.model.Card;
import nl.rug.ai.oop.crazyeights.model.CrazyEights;
import nl.rug.ai.oop.crazyeights.view.OpponentView;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {

    JButton step;
    JButton draw;
    CrazyEights game = new CrazyEights();
    public static JLabel label;
    static Color backgroundColor = new Color(666633);

    CardView playerHand;
    JPanel deckPanel;
    JPanel DiscardPanel;
    JPanel oppHand;
    JPanel control;

    public int i = 1;
    public int j = 0;



    public GameFrame(){
        this.setTitle("Crazy Eights");
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(backgroundColor);

        game.letsPlay();

        deckPanel = new JPanel();
        DiscardPanel = new JPanel();
        playerHand = new  CardView(game);
        oppHand = new OpponentView(game);
        control = new JPanel();

        playerHand.updateHand(game);
        playerHand.addMouseListener(new MouseController(game,this,this.playerHand));
        game.addListener(evt -> updateHand());
        ButtonController controller = new ButtonController(game);

        deckPanel.setBackground(backgroundColor);
        DiscardPanel.setBackground(backgroundColor);
        playerHand.setBackground(backgroundColor);
        oppHand.setBackground(backgroundColor);
        control.setBackground(backgroundColor);

        deckPanel.setPreferredSize(new Dimension(300,50));
        DiscardPanel.setPreferredSize(new Dimension(100,400));
        playerHand.setPreferredSize(new Dimension(100,100));
        oppHand.setPreferredSize(new Dimension(100,200));
        control.setPreferredSize(new Dimension(100,100));


        ImageIcon deckImage = new ImageIcon("src/main/resources/deck.png");
        this.setIconImage(deckImage.getImage());
        JLabel deckLabel = new JLabel();
        deckLabel.setIcon(deckImage);
        deckPanel.add(deckLabel);

        step = new JButton("Opponents Play");
        step.setActionCommand("step");

        control.add(step);

        draw = new JButton("Draw Card");
        draw.setActionCommand("draw");
        control.add(draw);


        Card c = game.discardPile.last();
        ImageIcon first = new ImageIcon("src/main/resources/" + c.toString() + ".png");
        label = new JLabel();
        label.setIcon(first);
        DiscardPanel.add(label);

        this.setup(game,controller);

        this.add(deckPanel,BorderLayout.WEST);
        this.add(DiscardPanel,BorderLayout.CENTER);
        this.add(playerHand,BorderLayout.SOUTH);
        this.add(oppHand,BorderLayout.NORTH);
        this.add(control,BorderLayout.EAST);
        this.setVisible(true);
        this.setup(game,controller);


    }

    public void setup(CrazyEights model, ButtonController controller){
        step.addActionListener(controller);
        draw.addActionListener(controller);
    }

    private void updateHand() {
        playerHand.updateHand(game);
        playerHand.repaint();
        oppHand.repaint();
    }

    public CardView getPlayerHand() {
        return playerHand;
    }

    public JPanel getOppHand() {
        return oppHand;
    }

    public static void main(String[] args) {
        CrazyEights game = new CrazyEights();
        GameFrame view = new GameFrame();
        MouseController mouse = new MouseController(game,view,view.playerHand);
    }
}
