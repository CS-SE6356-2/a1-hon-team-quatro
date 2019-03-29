import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class CardGame
{
	////Member Variables////
	Player[] players;			//Holds the data for each player
	Deck cardDeck;				//Holds the information for each card
	CardPile[] piles;			
	
	////Constructor////
	public CardGame(int numOfPlayers, File cardList)
	{
		players = new Player[numOfPlayers];		//Create a list of Players
		cardDeck = new Deck(cardList);			//Create the deck of cards. The Card Game class thus has a reference to all cards
		piles = new CardPile[2];				//Create the list of piles, will give amount that fits a specific card game
		
		//Create Card Piles
		piles[0] = new CardPile("Draw");
		piles[1] = new CardPile("Used");
		
		//Create Players
		createPlayers();
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
				temp.add(cardDeck.cards[currentCard]);		//add card reference to list
			//Give players their cards
			temp.clear();									//Clear the list so we can give the next player their cards
		}
		
		//Give rest of cards to draw pile
		for(;currentCard < cardDeck.numOfCards; currentCard++)
			temp.add(cardDeck.cards[currentCard]);
		piles[0].addCardsOnTop(temp);
		temp.clear();
		
		//Put the first card on top of the draw deck on to the used pile
		piles[1].addCardsOnTop(piles[0].takeCards(1));
	}
	private void createPlayers()
	{
		
	}
	private playerQueue sortPlayersInPlayOrder()
	{
		int dealerNum;
		for(dealerNum = 0;dealerNum < players.length||players[dealerNum].role.equals("Dealer"); dealerNum++);
		
		playerQueue playOrder = new playerQueue();
		for(int i = 0; i < players.length; i++)
			playOrder.enqueue(players[(dealerNum+i)%players.length]);
		
		return playOrder;
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
	
	/**
	 * Checks to see if a player has met the winning conditions
	 * @author Chris
	 * @return
	 */
	private int checkWinCondition()
	{
		for(int i = 0; i < players.length; i++)
			if(players[i].hand.numOfCards == 0)
				return i;
		return -1;
	}
	
	
	public String playGame()
	{
		sortPlayersInPlayOrder();		//Before starting the rounds, Sort the players
		dealCards();					//Then deal out the cards to the players and cardPiles
		
		//TODO the control flow
		
		return "A winner is you!";
	}
}