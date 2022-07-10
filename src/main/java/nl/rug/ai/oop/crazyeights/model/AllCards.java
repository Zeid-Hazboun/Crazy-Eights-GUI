package nl.rug.ai.oop.crazyeights.model;

import java.util.ArrayList;
import java.util.Random;

public class AllCards {
    private String label;
    private ArrayList<Card> cards;

    /**
     * constructor for a set of cards
     * @param label name of set of cards
     */
    public AllCards(String label){
        this.label = label;
        this.cards = new ArrayList<Card>();
    }

    public String getLabel(){
        return this.label;
    }
    public Card getCard(int i){
        return cards.get(i);
    }

    /**
     * adds a card into an allCards object
     * @param card card to be put inside the object
     */
    public void addCard(Card card){
        cards.add(card);
    }

    /**
     * removes a card from an allCards object
     * @param i index of card to be removed
     * @return returns the allCards object without the card given by the index i
     */
    public Card cardRemove(int i){
        return cards.remove(i);
    }

    /**
     * removes last card in an allCards object
     */
    public Card cardRemove(){
        int i = size() -1;
        return cardRemove(i);
    }

    /**
     *
     * @return returns number of cards in an allCards object
     */
    public int size(){
        return cards.size();
    }

    /**
     * boolean function to check if an allCards object is empty.
     * @return boolean
     */
    public boolean empty(){
        return cards.size() == 0;
    }

    /**
     * deals n number of cards from the top of the current allCards object to another allCards object specified in the argument
     * @param that all cards object that should receive cards from current all cards object
     * @param n number of cards to be dealt
     */
    public void dealCards(AllCards that, int n){
        for(int i =0; i<n; i++) {
            Card card = cardRemove();
            that.addCard(card);
        }
    }


    public void dealRest(AllCards that){
        int n = size();
        dealCards(that, n);
    }

    /**
     * swaps two cards in an allCards object by their index
     * @param i index of card 1
     * @param j index of card 2
     */
    public void swapCards(int i, int j){
        Card temp = cards.get(i);
        cards.set(i, cards.get(j));
        cards.set(j, temp);
    }

    /**
     * Method that shuffles the cards in an allCards object by randomly swapping cards within the cards list
     */
    public void shuffle(){
        Random random = new Random();
        for(int i = size()-1; i>0; i--){
            int j = random.nextInt(i+1);
            swapCards(i, j);
        }
    }

    /**
     * method to print the cards in an allCards object
     */
    public void display(){
        System.out.println(label + ": ");
        for(Card card : cards){
            System.out.println(card);
        }
        System.out.println();
    }

    /**
     * method to return the last card in the cards array of an AllCards object
     * @return last card
     */
    public Card last() {
        int i = size() - 1;
        return cards.get(i);
    }

    public String[] toList(){
        String[] hand= new String[cards.size()];
        for(int i = cards.size()-1;i>=0;i--){
            hand[i]=cards.get(i).toString();
            System.out.println(hand[i]);
        }

        return hand;
    }

    public static class RandomCrazyEightsPlayer {
        private String name;
        private Card.Hands hand;

        public RandomCrazyEightsPlayer(String name){
            this.name = name;
            this.hand = new Card.Hands(name);
        }

        public String getName() {
            return name;
        }
        public Card.Hands getHand() {
            return hand;
        }

        /**
         * prints out the cards in the hand of a player
         */
        public void display(){
            hand.display();
        }

        public Card play(CrazyEights eights, Card dis){

            Card card = searchHand(dis);
            if(card == null){
                card = eights.draw();
            }
            return card;
        }

        /**
         *searchHand function searches the players hand for a card that can be played on top
         * of the current card in the discard pile. If no such card exists then the player
         * draws to search for a match.
         */

        public Card searchHand(Card dis){
            for(int i = 0; i < hand.size(); i++){
                Card card = hand.getCard(i);
                if(cardIsMatch(card, dis)){
                    return hand.cardRemove(i);
                }
            }
            return null;
        }

        /**
         * drawCard method draws cards from the deck and adds them to the players hand
         * until a card is found that can be played on top of the card on the discard pile.
         */
        public Card drawCard(CrazyEights eights, Card dis){
            while(true){
                Card card = eights.draw();
                System.out.println(name + " draws " + card);
                if(cardIsMatch(card, dis)){
                    return card;
                }
                hand.addCard(card);
            }
        }

        /**
         * Boolean that checks if a card can be played or not
         */
        public static boolean cardIsMatch(Card card, Card dis){
            return card.rank() == 8 || card.suit() == dis.suit()
                    || card.rank() == dis.rank();
        }

    }
}

