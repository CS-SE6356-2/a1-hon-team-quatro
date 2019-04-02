/*
 * This is a test unit for the Cardpile class. A Carpile object is made, and is filled with cards. 
 * It is printed before being shuffled. It is then shuffled. The shuffled pile is then printed out
 */
import java.util.LinkedList;

public class CardpileTest {

	public static void main(String[] args) {
		Cardpile pile = new Cardpile();						//Makes a Cardpile object
		
		//add some cards
		LinkedList<Card> cards = new LinkedList<Card>();	//Makes a List to hold cards that will be added to the cardpile
		for(int i = 1; i < 53; i++)							//Add 52 cards to the list
			cards.add(new Card(i+"","Big52"));
		
		pile.addCardsOnTop(cards);							//Add all cards into the cardpile
		
		printCardpile(pile);								//Print the pile before shuffle
		
		pile.shuffle();										//Shuffle the pile
		
		printCardpile(pile);								//Print the pile after shuffle

	}
	
	static void printCardpile(Cardpile pile) {	
		for(int i = 0; i < pile.cards.size(); i++) {	//For each card in the pile, print its information
			Card card = pile.cards.get(i);
			System.out.println(card.getVal() +" "+card.getCategory());
		}
	}

}
