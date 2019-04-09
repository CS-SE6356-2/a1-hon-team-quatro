import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
		LinkedList<Card> temp = new LinkedList<Card>();
		for(Player player: players)
		{
			for(;temp.size() < 7; currentCard++)			//Get a list of cards that will be of even size to a player. UNO starts off with players having 7 cards
				temp.add(cardDeck.cards.get(currentCard));		//add card reference to list
				//Give players their cards
			player.addCards(temp);
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
	public void shuffleCards() {cardDeck.shuffle();}
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
	public PlayerQueue sortPlayersInPlayOrder()
	{
		//CLIENTSOCKS AND CLIENTLABELS are automatically sorted within the playerQueue as they are part of the Player object
		
		int dealerNum;	//Track the index of the dealer
		 //Index through array until dealer is found, if not then stop at end of list
		for(dealerNum = 0;dealerNum < players.length && !players[dealerNum].getRole().equals("Dealer"); dealerNum++);
		
		//Move number to next in list as dealer doesn't usually go first
		dealerNum = (dealerNum+1)%players.length;
		//Create the playerQueue
		PlayerQueue playOrder = new PlayerQueue();
		
		for(int i = 0; i < players.length; i++)							//For each player
			playOrder.enqueue(players[(dealerNum+i)%players.length]);	//Starting at the dealer, add them to the queue
		
		return playOrder;	//Return  the queue
	}
	/**
	 * Assigns the given player as the new dealer.
	 * @author Chris
	 * @param newDealer
	 * @return True if a new dealer has been assigned | False if not
	 */
	public boolean assignDealear(String newDealer)
	{
		for(Player p: players)
			if(p.getTeamName().equals(newDealer))
			{
				p.assignRole("Dealer");
				return true;
			}
		return false;
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

	public boolean isLegalMove(Player focusPlayer, String move) {
		//Depends on game type
		// TODO Extend Game Logic here, what kind of card or action did they make
		return true;
	}
	
	/**
	 * Checks to see if a player has met the winning conditions
	 * @author Chris
	 * @return
	 */
	public boolean checkWinCondition(Player focusPlayer, String move)
	{
		//TODO extend into a specific game type (set of rules)
		if(focusPlayer.getNumOfCards() == 0)
			return true;
		return false;
	}

}