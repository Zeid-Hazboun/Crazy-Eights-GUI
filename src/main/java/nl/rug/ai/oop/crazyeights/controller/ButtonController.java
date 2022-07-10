package nl.rug.ai.oop.crazyeights.controller;

import nl.rug.ai.oop.crazyeights.model.AllCards;
import nl.rug.ai.oop.crazyeights.model.Card;
import nl.rug.ai.oop.crazyeights.model.CrazyEights;
import nl.rug.ai.oop.crazyeights.view.CardView;
import nl.rug.ai.oop.crazyeights.view.GameFrame;
import nl.rug.ai.oop.crazyeights.view.GameView;
import nl.rug.ai.oop.crazyeights.view.LaunchWinnerPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonController implements ActionListener {
    public final CrazyEights model;
    //private final GameFrame frame;


    public ButtonController(CrazyEights game){
        this.model=game;
        //this.frame=frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "step" -> {
                System.out.println("///////////////////////////////STEP BUTTON JUST PRESSED////////////////////////////////");
                Card card = model.letsPLayButton(model);
                try {
                    ImageIcon icon = new ImageIcon("src/main/resources/" + card.toString() + ".png");
                    GameFrame.label.setIcon(icon);
                    if(model.getPlayer(0).getHand().empty()){
                        GameView.view.dispose();
                        LaunchWinnerPage winnerPage = new LaunchWinnerPage("THE AI HAS WON!!!");
                    }

                }catch (Exception ie) {
                    System.out.println("NOT UR TURN");
                }
                System.out.println("Discard Pile: " + card.toString());
                //frame.getOppHand().repaint();
                //frame.getOppHand().revalidate();
                return;
            }
            case "draw" -> {
                System.out.println("DRAW");
                AllCards.RandomCrazyEightsPlayer player = model.getPlayer(1);
                Card card = model.draw();
                model.addToHand(1,card);
                //frame.getPlayerHand().updateHand(model);
                //frame.repaint();
                return;
            }
        }
    }
}
