import java.util.LinkedList;

public class CardpileTest {

	public static void main(String[] args) {
		Cardpile pile = new Cardpile();
		
		//add some cards
		LinkedList<Card> cards = new LinkedList<Card>();
		for(int i = 1; i < 53; i++)
			cards.add(new Card(i+"","Big52"));
		
		pile.addCardsOnTop(cards);
		
		pile.shuffle();
		
		printCardpile(pile);

	}
	
	static void printCardpile(Cardpile pile) {
		for(int i = 0; i < pile.cards.size(); i++) {
			Card card = pile.cards.get(i);
			System.out.println(card.getVal() +" "+card.getCategory());
		}
	}

}
