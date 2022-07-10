package nl.rug.ai.oop.crazyeights.model;
public class Deck extends AllCards{

    /**
     * constructor to build a deck of cards with 52 standard playing cards
     * @param label name of the deck
     */
    public Deck(String label){
        super(label);
        for(int suit = 0; suit <=3; suit++){
            for(int rank = 1; rank < 13; rank++){
                addCard(new Card(rank, suit));
            }
        }
    }
}