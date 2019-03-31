
public class CardpileTest {

	public static void main(String[] args) {
		Cardpile pile = new Cardpile();
		
		//add some cards
		
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
