import java.io.File;
import java.net.Socket;
import java.util.ArrayList;

public class CardGameTest {

	public static void main(String[] args) 
	{
		ArrayList<String> playerNames = new ArrayList<String>();
		ArrayList<Socket> socks = new ArrayList<Socket>();
		for(int i = 0; i < 3; i++)
		{
			playerNames.add("Player "+i);
			socks.add(new Socket());
			System.out.println("Added "+playerNames.get(i));
		}
		CardGame game = new CardGame(playerNames.size(),playerNames,socks,new File("cardlist.txt"));
		//createPlayers is tested within the constructor
		
		//Assign player 2 as dealer
		game.assignDealear("Player 2");
		//Shuffle the cards
		game.shuffleCards();
		//Deal the cards
		game.dealCards();
		
		//Get the list of cards from each player and see they have been sorted as the dealer goes last
		for(Player p: game.sortPlayersInPlayOrder())
			System.out.println(p.getTeamName()+" has these cards: "+p.getCardListForUTF());
		
	}

}
