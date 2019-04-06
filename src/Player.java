/* @author Jacob */

import java.util.LinkedList;

/* Represents one of the people playing the game */
public class Player
{
/* Data */
	/* Name of the team that the player belongs to */
	private String teamName;

	/* Identifier marking the role the player has in the game */
	 String role;

	/* Cards the player possesses (both active and inactive) */
	private Hand hand;

/* Public methods */
	
	/* Constructor */
	public Player(String cTeamName, String cRole)
	{
		this.teamName = cTeamName;
		this.role = cRole;
		this.hand = new Hand();
	}

	/* Adds all the cards in the list to the player's active cards */
	public void addCards(LinkedList<Card> cards)
	{
		this.hand.addCards(cards);
	}

	/* Removes all the cards in the list from the player's active cards
	 * and returns a list of all cards successfully removed */
	public LinkedList<Card> removeCards(LinkedList<Card> cards)
	{
		return this.hand.removeCards(cards);
	}
	
	/**
	 * Returns the number of cards this player has
	 * @author Chris
	 * @return
	 */
	public int getNumOfCards() {return hand.getNumOfCards();}
	/**
	 * Returns this player's role
	 * @author Chris
	 * @return
	 */
	public String getRole() {return role;}

	/* Transfers all the cards in the list from the player's active cards
	 * to their inactive cards and returns a list of all cards successfully
	 * transferred */
	public LinkedList<Card> transferActiveToInactive(LinkedList<Card> cards)
	{
		return this.hand.transferActiveToInactive(cards);
	}

	/* Transfers all the cards in the list from the player's inactive cards
	 * to their active cards and returns a list of all cards successfully
	 * transferred */
	public LinkedList<Card> transferInactiveToActive(LinkedList<Card> cards)
	{
		return this.hand.transferInactiveToActive(cards);
	}

	/* Used to perform game-specific actions that go beyond
	 * manipulating one's cards. Returns result of action as a String */
	public String takeAction(String action)
	{
		String result = "";
		return result;
		/* TODO */
	}

/* Getters */
	public LinkedList<Card> getActiveCards()
	{
		return this.hand.getActiveCards();
	}

	public LinkedList<Card> getInactiveCards()
	{
		return this.hand.getInactiveCards();
	}
}
