package nl.rug.ai.oop.crazyeights.view;

import nl.rug.ai.oop.crazyeights.controller.ButtonController;
import nl.rug.ai.oop.crazyeights.controller.MouseController;
import nl.rug.ai.oop.crazyeights.model.CrazyEights;

import java.awt.*;

public class GameView {

    public static GameFrame view;

    public static void main(String[] args) {
        CrazyEights game = new CrazyEights();
        view = new GameFrame();
        MouseController mouse = new MouseController(game,view,view.playerHand);
        ButtonController button = new ButtonController(game);
        //GameFrame.setup(game, button);
    }
}
