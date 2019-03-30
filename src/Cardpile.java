import java.util.LinkedList;
import java.util.List;

public class Cardpile {

	int numOfCards;
	String pileName;
	LinkedList<Card> cards;//front of the list is the top of the pile
	
	//default constructor
	public Cardpile() {
		this("Pile");
	}
	
	//Constructor, sets the name of the pile and initializes the pile to be empty
	public Cardpile(String name) {
		pileName = name;
		numOfCards = 0;
		cards = new LinkedList<Card>();
	}
	
	//Returns the specified number of cards from the pile. The first element of the returned list is the first card drawn.
	//If the pile contains less cards than requested, all the cards in the pile will be returned
	List<Card> takeCards(int amount){
		LinkedList<Card> taken = new LinkedList<Card>();
		
		if(amount > numOfCards) {//if there are not enough cards in the pile
			taken.addAll(cards);//return all cards
			cards.clear();//pile is emptied
			numOfCards = 0;
		}
		else {//otherwise if there are enough cards
			for(int i = 0;i < amount; i++) {
				taken.add(cards.removeFirst());//return one card at a time, each drawn card is added to the end of the returned list
			}
		numOfCards -= amount;//update the number of cards left in the pile;
		}
		
		return taken;
	}
	
	void addCardsOnTop(List<Card> cards) {
		this.cards.addAll(0, cards);//adds cards to the front of the list
		numOfCards += cards.size();//updates the number of cards in the pile
	}
	
	void addCardsOnBot(List<Card> cards) {
		this.cards.addAll(cards);//adds cards to the end of the list
		numOfCards += cards.size();//updates the number of cards in the pile
	}

	void shuffle() {
		
	}
	
}
