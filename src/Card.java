package src;

public class Card{
    //attributes
    private int number;
    private String suit;

    Card(int number, String suit){
        this.number = number;
        this.suit = suit;
    }

    //getters and setters
    public void setNumber(int x){
        number = x;
    }

    public boolean setSuit(String x){
        String[] suits = {"Hearts", "Clubs", "Diamonds", "Spades"};

        for(String suit: suits){
            if(x == suit){
                this.suit = x;
                return true; 
            }
        }

        return false;
    }

    public int getNumber(){
        return number;
    }

    public String getSuit(){
        return suit;
    }

    //methods
    public String getCardName(){
        switch(number){
            case 13:
                return "King of " + suit;

            case 12:
                return "Queen of " + suit;
    
            case 11:
                return "Jack of " + suit;

            case 1:
                return "Ace of " + suit;

            default:
                return number + " of " + suit;    
            }
    }

}
