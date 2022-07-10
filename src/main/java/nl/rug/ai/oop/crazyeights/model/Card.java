package nl.rug.ai.oop.crazyeights.model;

/**
 * Represents a playing card, implemented from the book : ThinkJava
 */

public record Card(int rank, int suit) {
    public static final String[] SUITS = {
            "C", "S", "H", "D"
    };

    public static final String[] RANKS = {
            null, "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "11", "12", "13"
    };

    @Override
    public String toString() {
        return SUITS[suit] + RANKS[rank];
    }

    public int getRank() {
        return this.rank;
    }
    public int getSuit(){
        return this.suit;
    }

    /**
     * checks to see if two cards are equal
     * @return boolean value
     */
    public boolean equals() {
        return rank == this.rank
                && suit == this.suit;
    }

    public static class Hands extends AllCards {
        /**
         * constructor for a hand object
         * @param label name of the hand
         */
        public Hands(String label) {
            super(label);
        }
    }
}

