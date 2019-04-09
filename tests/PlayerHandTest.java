import java.io.File;
import java.util.LinkedList;

public class PlayerHandTest {

	public static void main(String[] args) {
		Player[] players = new Player[3];
		
		for(int i = 0; i < players.length; i++)
		{
			players[i] = new Player("Player "+i,"Test",null);
			System.out.println("Added "+players[i].getTeamName());
		}
		
		Deck deck = new Deck(new File("cardlist.txt"));
		
		LinkedList<Card> temp = new LinkedList<Card>();
		
		int cardNum = 0;
		for(int i = 0; i < players.length; i++)
		{
			for(;temp.size() < 7; cardNum++)			//Get a list of cards that will be of even size to a player. UNO starts off with players having 7 cards
				temp.add(deck.cards.get(cardNum));		//add card reference to list
				//Give players their cards
			players[i].addCards(temp);
			
			//Test transfer cards between inactive and active
			if(i==0)
			{
				System.out.println("Moved player 0's cards to his inactive pile");
				players[i].transferActiveToInactive(temp);
			}
			temp.clear();									//Clear the list so we can give the next player their cards
			
			System.out.println(players[i].getTeamName()+"'s cards are: "+players[i].getCardListForUTF());
		}
		
	}

}
