package nl.rug.ai.oop.crazyeights.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

/**
 * This main class controls the game of Crazy Eights.
 */

public class CrazyEights  {
    public ArrayList<PropertyChangeListener> listeners = new ArrayList();

    public final Deck deck;
    public final AllCards drawPile;
    public final AllCards discardPile;
    private final Scanner in;
    private ArrayList<AllCards.RandomCrazyEightsPlayer> players;

    public int index = 0;


    /** Constructs a new CrazyEights game with initialized deck of cards,discard pile, players, and player hand's
     *
     */
    public CrazyEights(){
        deck = new Deck("Deck");
        deck.shuffle();
        this.players= new ArrayList<AllCards.RandomCrazyEightsPlayer>();

        discardPile = new AllCards("Discards");
        deck.dealCards(discardPile, 1);
        drawPile = new AllCards("Draw Pile");
        deck.dealRest(drawPile);

        in = new Scanner(System.in);
    }

    /**
     * adds a player to the list of players in the CrazyEights game
     * @param player takes a RandomCrazyEightsPlayer
     */
    public void addPlayer(AllCards.RandomCrazyEightsPlayer player) {
        players.add(player);
    }

    /**
     * takes string input to use as a players name
     * @return string input
     */
    public String playerName() {
        System.out.println("Enter player name: ");
        String name = "player"+index;
        while (Objects.equals(name, "")) {
            name = in.nextLine();
        }
        return name;
    }


    /**
     * initializes a new player, adds them to the player list of the CrazyEights object
     * and draws five cards to add to the player's hand
     */
    public void dealToPlayer() {
        AllCards.RandomCrazyEightsPlayer player = new AllCards.RandomCrazyEightsPlayer(playerName());
        addPlayer(player);
        drawPile.dealCards(player.getHand(), 5);
    }

    /**
     * gets a player from the player list of a crazyEights object
     * @param i index of the player in the list
     * @return player
     */
    public AllCards.RandomCrazyEightsPlayer getPlayer(int i) {
        return players.get(i);
    }

    /**
     * gets the index of a certain player in the player list of a crazyEights object
     * @param player
     * @return
     */
    public int getPlayerIndex(AllCards.RandomCrazyEightsPlayer player) {
        return players.indexOf(player);
    }

    /**
     * method to check whether the game is finished.
     * returns true when a player in the crazyEights game does not have any cards left in their hand
     * @return boolean
     */
    public boolean gameOver(){
        for (int i =0;i<players.size();i++){
            if (getPlayer(i).getHand().empty()){
                System.out.println(getPlayer(i).getName() + " is the winner!!!");
                return true;
            }
        }
        return false;
    }

    /**
     * method to reshuffle the discard pile and use it as a draw pile whenever the draw pile is finished.
     */
    public void reshuffle() {
        System.out.println("The draw pile has been emptied! Lets reshuffle the discard pile.");
        Card dis = discardPile.cardRemove();
        discardPile.dealRest(drawPile);
        drawPile.shuffle();

        discardPile.addCard(dis);


    }

    /**
     * draws a card from the drawpile
     * @return card
     */
    public Card draw() {
        if (drawPile.empty()) {
            reshuffle();
        }
        return drawPile.cardRemove();
    }

    /**
     * method to alter turn of players in the crazyEights game
     * this method has a special if statement for when a Queen card is played on the previous player's turn,
     * in which it skips the following player's turn
     * @param current previous player
     * @param dis card that is played by the previous player, only used to check if it is a queen
     * @return next player
     */
    public AllCards.RandomCrazyEightsPlayer nextPlayer(AllCards.RandomCrazyEightsPlayer current, Card dis) {
        int flag = 0;
        if(dis.rank() == 12){
            if(getPlayerIndex(current) < players.size() - 1) {
                System.out.println("A Queen was played, therefore " + getPlayer(getPlayerIndex(current) + 1).getName() + "'s turn is skipped!");
            }
            else{
                System.out.println("A Queen was played, therefore " + getPlayer(getPlayerIndex(current) + 1 - players.size()).getName() + "'s turn is skipped!");
            }
            System.out.println();
            flag = 1;
            if (players.size() == 2) {
                return getPlayer(getPlayerIndex(current));
            }
        }

        if (getPlayerIndex(current) < players.size() - 1) {
            int index = getPlayerIndex(current) + 1 + flag;
            if (index == players.size()){
                index -= players.size();
            }
            return getPlayer(index);
        }
        if (getPlayerIndex(current) == players.size() - 1) {
            int index = (getPlayerIndex(current) + 1 + flag - players.size());
            if(index == players.size()){
                index -= players.size();
            }
            return getPlayer(index);
        }
        return null;
    }

    /**
     * displays the current state of the game : hands of the players, and number of cards in the draw pile
     */
    public void addListener(PropertyChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(PropertyChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        Iterator<PropertyChangeListener> allListeners = listeners.iterator();
        PropertyChangeEvent hands = new PropertyChangeEvent(this, "hands", null, getPlayer(0).getHand().size());
        while (allListeners.hasNext()) {
            allListeners.next().propertyChange(hands);
        }
    }
    public void displayState() {
        for (int x = 0; x < players.size(); x++) {
            getPlayer(x).display();
        }
        discardPile.display();
        System.out.print("Draw pile: ");
        System.out.println(drawPile.size() + " cards");
    }

    /**
     * method to make a player take their turn.
     * has if statements for different 'special cards' in the game such as Ace, 2 and 8.
     * @param player
     */
    public Card takeTurn(AllCards.RandomCrazyEightsPlayer player) {
        Card dis = discardPile.last();
        if(dis.getRank() == 2){
            System.out.println("A 2 was played, therefore " + player.getName() + " has to draw 2 cards!");
            for(int i =0; i<2; i++){
                Card card = draw();
                System.out.println(player.getName() + " draws " + card);
                player.getHand().addCard(card);
                notifyListeners();
            }
            notifyListeners();
        }
        Card next = player.play(this, dis);
        if(dis.getRank() == 8){
            discardPile.cardRemove();
        }
        discardPile.addCard(next);
        notifyListeners();

        System.out.println(player.getName() + " plays " + next);
        System.out.println();

        if (players.size() > 2) {
            if (next.rank() == 1) {
                System.out.println("An Ace was played, therefore the direction of the play is now reversed!");
                Collections.reverse(players);
            }
        }
        if (next.rank() == 8) {
            System.out.println("An Eight was played therefore " + player.getName() + " gets to choose a suit!");
            Random random = new Random();
            int suit = random.nextInt(4);
            Card card = new Card(8, suit);
            System.out.println(player.getName() + " has chosen the following card: " + card);
            discardPile.addCard(card);
            return card;
        }
        return next;
    }

    public Card takeTurn(AllCards.RandomCrazyEightsPlayer player, int c_index) {
        Card dis = discardPile.last();
        if(dis.getRank() == 2){
            System.out.println("A 2 was played, therefore " + player.getName() + " has to draw 2 cards!");
            for(int i =0; i<2; i++){
                Card card = draw();
                System.out.println(player.getName() + " draws " + card);
                addToHand(1,card);
            }
            notifyListeners();
        }
        Card next = player.getHand().getCard(c_index);

        if(!AllCards.RandomCrazyEightsPlayer.cardIsMatch(next, dis)){
            System.out.println("CARD NOT PLAYABLE");
            return null;
        }



        if(dis.getRank() == 8){
            discardPile.cardRemove();
        }
        discardPile.addCard(next);

        System.out.println(player.getName() + " plays " + next);
        System.out.println();

        if (players.size() > 2) {
            if (next.rank() == 1) {
                System.out.println("An Ace was played, therefore the direction of the play is now reversed!");
                Collections.reverse(players);
            }
        }
        if (next.rank() == 8) {
            System.out.println("An Eight was played therefore " + player.getName() + " gets to choose a suit!");
            Random random = new Random();
            int suit = random.nextInt(4);
            Card card = new Card(8, suit);
            System.out.println(player.getName() + " has chosen the following card: " + card);
            discardPile.addCard(card);
            player.getHand().cardRemove(c_index);
            return card;
        }
        player.getHand().cardRemove(c_index);
        return next;
    }

    /**
     * initializes crazyEights game and starts the crazyEights game play.
     * Takes number of players as input from the terminal.
     */

    public void addToHand(int playerIdx, Card card){
        getPlayer(playerIdx).getHand().addCard(card);
        notifyListeners();
        return;
    }

    public void letsPlay(){
        System.out.println("Lets play Crazy Eights!");

        for(int i = 0; i<2; i++){
            dealToPlayer();
            index++;
        }
        index=0;
        AllCards.RandomCrazyEightsPlayer player = getPlayer(0);
        if(!gameOver()){
            if(index<1) {
                displayState();
                System.out.println();
                takeTurn(player);
                player = nextPlayer(player, discardPile.last());
                index=1;
            }
        }
    }

    public Card letsPLayButton(CrazyEights game){
        if(index == 1){
            System.out.println("NOT YOUR TURN");
            return null;
        }
        if(index == 0){
            displayState();
            System.out.println();
            Card card = takeTurn(game.getPlayer(index));
//            if(card.getRank()==2){
//                notifyListeners();
//            }
            if(card.getRank() == 12){
                return card;
            } else {
                index = 1;
                return card;
            }


        }
        return null;
    }


    public Card letsPLayButton(CrazyEights game, int cardIndex){
        if(index == 1){
            displayState();
            System.out.println();
            if (game.discardPile.last().getRank()==2){
                notifyListeners();
            }
            Card card = takeTurn(game.getPlayer(index),cardIndex);
            if(card.getRank() == 12){
                return card;
            }
            else {
                index = 0;
                return card;
            }

        }
        if(index == 0){
            System.out.println("Not your turn");
        }
        return null;
    }

    /**
     * Main function for the game
     * @param args
     */
    public static void main(String[] args) {
        CrazyEights game = new CrazyEights();
        game.letsPlay();
    }
}




