package nl.rug.ai.oop.crazyeights.controller;

import nl.rug.ai.oop.crazyeights.model.AllCards;
import nl.rug.ai.oop.crazyeights.model.Card;
import nl.rug.ai.oop.crazyeights.model.CrazyEights;
import nl.rug.ai.oop.crazyeights.view.CardView;
import nl.rug.ai.oop.crazyeights.view.GameFrame;
import nl.rug.ai.oop.crazyeights.view.GameView;
import nl.rug.ai.oop.crazyeights.view.LaunchWinnerPage;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MouseController extends MouseInputAdapter {
    public final CrazyEights model;
    private final GameFrame frame;
    private final CardView view;


    public MouseController(CrazyEights game, GameFrame frame, CardView view){
        this.model=game;
        this.frame=frame;
        this.view = view;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int Selected = view.getSelectedCard(e.getX());
        if (Selected >= 0) {
            // prints index of card
            System.out.println("The user selected card "+Selected+".");
            Card card = model.discardPile.last();
            Card play = model.getPlayer(1).getHand().getCard(Selected);
            if ( AllCards.RandomCrazyEightsPlayer.cardIsMatch( play,card)){
                play = model.letsPLayButton(model,Selected);
                ImageIcon icon = new ImageIcon("src/main/resources/" + play.toString() + ".png");
                System.out.println("Card to be played: " + play.toString());
                System.out.println("Discard Pile: " + card.toString());
                GameFrame.label.setIcon(icon);
                if(model.getPlayer(1).getHand().empty()){
                   GameView.view.dispose();
                    LaunchWinnerPage winnerPage = new LaunchWinnerPage("YOU WON! CONGRATS");
                }

            }else{
                System.out.println("CARD NOT PLAYABLE");
            }
            System.out.println("GAME INDEX: " + model.index);
            view.updateHand(model);
            view.revalidate();
            view.repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        view.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int selectedCard = view.getSelectedCard(e.getX());
        view.repaint();
    }





}
