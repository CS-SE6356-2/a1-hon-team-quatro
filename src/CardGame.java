import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.net.Socket;

public class CardGame
{
	////Member Variables////
	Player[] players;			//Holds the data for each player
	Deck cardDeck;				//Holds the information for each card
	Cardpile[] piles;			
	
	////Constructor////
	public CardGame(int numOfPlayers, ArrayList<String> playerNames, ArrayList<Socket> clientSocks,
		File cardList)
	{
		players = new Player[numOfPlayers];		//Create a list of Players
		cardDeck = new Deck(cardList);			//Create the deck of cards. The Card Game class thus has a reference to all cards
		piles = new Cardpile[2];				//Create the list of piles, will give amount that fits a specific card game
		
		//Create Card Piles
		piles[0] = new Cardpile("Draw");
		piles[1] = new Cardpile("Used");
		
		//Create Players
		createPlayers(playerNames, clientSocks);
	}
	
	/**
	 * 
	 */
	void dealCards()
	{
		int currentCard = 0;
		List<Card> temp = new LinkedList<Card>();
		for(Player player: players)
		{
			for(;currentCard < 7; currentCard++)			//Get a list of cards that will be of even size to a player. UNO starts off with players having 7 cards
				temp.add(cardDeck.cards.get(currentCard));		//add card reference to list
			//Give players their cards
			temp.clear();									//Clear the list so we can give the next player their cards
		}
		
		//Give rest of cards to draw pile
		for(;currentCard < cardDeck.numOfCards; currentCard++)
			temp.add(cardDeck.cards.get(currentCard));
		piles[0].addCardsOnTop(temp);
		temp.clear();
		
		//Put the first card on top of the draw deck on to the used pile
		piles[1].addCardsOnTop(piles[0].takeCards(1));
	}
	private void createPlayers(ArrayList<String> playerNames, ArrayList<Socket> clientSocks)
	{
		for(int i = 0; i < players.length; i++)
		{
			players[i] = new Player(playerNames.get(i),"Solo", clientSocks.get(i));
		}
	}
	
	/**
	 * Sorts the list of players initially in a game by finding the dealer, adding them and the other players into a circular linked list called playerQueue
	 * @author Chris
	 * @return playerQueue
	 */
	private PlayerQueue sortPlayersInPlayOrder(ArrayList<Socket> clientSocks, 
		ArrayList<String> clientLabels)
	{
		//SORT CLIENTSOCKS AND CLIENTLABELS THE SAME
		
		int dealerNum;	//Track the index of the dealer
		 //Index through array until dealer is found, if not then stop at end of list
		for(dealerNum = 0;dealerNum < players.length||players[dealerNum].getRole().equals("Dealer"); dealerNum++);
		
		//Create the playerQueue
		PlayerQueue playOrder = new PlayerQueue();
		
		for(int i = 0; i < players.length; i++)							//For each player
			playOrder.enqueue(players[(dealerNum+i)%players.length]);	//Starting at the dealer, add them to the queue
		
		return playOrder;	//Return  the queue
	}
	
	private String checkForTrick(List<Card> trick)
	{
		//TODO
		return "Royal Flush!";
	}
	private int getMatchValue(List<Card> match)
	{
		//TODO
		return 21;
	}
	private bool checkMove(int currentPlayer, String move) {
		//Depends on game type
		return true;
	}
	
	/**
	 * Checks to see if a player has met the winning conditions
	 * @author Chris
	 * @return
	 */
	private int checkWinCondition(int currentPlayer, String move)
	{
		for(int i = 0; i < players.length; i++)
			if(players[i].getNumOfCards() == 0)
				return i;
		return -1;
	}
	
	
	public String playGame()
	{
		//Assign a player as the dealer
		//Sort the players in a play order
		PlayerQueue playOrder = sortPlayersInPlayOrder(); //Before starting the rounds, Sort the players
		dealCards();					//Then deal out the cards to the players and cardPiles
		
		//TODO the control flow
		
		return "A winner is you!";
	}
}