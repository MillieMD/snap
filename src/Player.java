package src;
import java.util.ArrayList;

public class Player {
    //attributes
    private String name = "You";
    private ArrayList<Card> hand;

    Player(){
        hand = new ArrayList<>();
    }

    //getters and setters
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ArrayList<Card> getCards(){
        return hand;
    }

    public int getDeckSize(){
        return hand.size();
    }

    //methods
    public void displayDeck(){
        for(Card card :hand){
            System.out.println(card.getCardName());
        }
    }

    public void addCards(ArrayList<Card> cards){
        for(Card card: cards){
            hand.add(card);
        }
    }

    public Card place(){
        Card placedCard = hand.get(0);
        hand.remove(0);
        return placedCard;
    }

}
