package nl.rug.ai.oop.crazyeights.view;

import javax.swing.*;
import java.awt.*;

public class LaunchWinnerPage {

    JFrame winnerFrame = new JFrame("Crazy Eights");

    public LaunchWinnerPage(String winner){
        winnerFrame.setSize(420,420);
        winnerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winnerFrame.setLayout(new FlowLayout());
        winnerFrame.getContentPane().setBackground(GameFrame.backgroundColor);


        ImageIcon icon = new ImageIcon("src/main/resources/deck.png");
        winnerFrame.setIconImage(icon.getImage());

        JLabel label = new JLabel();
        label.setText("GAME HAS ENDED");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 40));

        JPanel panel = new JPanel();
        panel.setBackground(GameFrame.backgroundColor);
        JLabel winnerLabel = new JLabel(winner);
        winnerLabel.setForeground(Color.WHITE);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 40));
        panel.add(winnerLabel);


        winnerFrame.add(label, BorderLayout.NORTH);
        winnerFrame.add(winnerLabel, BorderLayout.CENTER);
        winnerFrame.setVisible(true);
    }
}
