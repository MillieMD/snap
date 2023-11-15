package src;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    Deck(){
        
        String[] suits = {"Hearts", "Clubs", "Diamonds", "Spades" };

        cards = new ArrayList<>();

        for(String suit :suits){
            for(int i = 1; i <= 13; i++){
                cards.add(new Card(i, suit));
            }
        }

        printCards();
        System.out.println("DECK ENDS");

        shuffle();
    }

    //getters and setters
    public ArrayList<Card> getCards(){
        //will be used for adding cards to players hands when they are successful
        return cards;
    }

    public int getDeckSize(){
        return cards.size();
    }

    //methods
    public void printCards(){
        for(Card card:cards){
            System.out.println(card.getCardName());
        }
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public void deal(Player[] players){

        for(Player player: players){
            ArrayList<Card> pile = new ArrayList<>();

            for(int i = 0; i < 26; i++){
                pile.add(cards.get(0));
                cards.remove(0);
            }
            player.addCards(pile);
        }

    }

    public void addCard(Card newCard){
        cards.add(newCard);
    }

    public void clearDeck(){

        cards.clear();

        System.out.println("CARDS AFTER CLEARING");
        printCards();
    }
    
}
