/* @author Jacob  */

import java.util.LinkedList;


/* Represents the cards in a specific Player's possession. */
public class Hand
{
/* Data */
	/* Number of active and inactive cards in the hand */
	int numOfCards;

	/* All the cards that are able to be played */
	private LinkedList<Card> activeCards;

	/* Cards the hand owns but cannot use (e.g. matched cards) */
	private LinkedList<Card> inactiveCards;

/* Public methods */

	/* Constructor */
	public Hand()
	{
		this.numOfCards = 0;
		this.activeCards = new LinkedList<Card>();
		this.inactiveCards = new LinkedList<Card>();
	}

	/* Looks at the activeCards for matches and returns all unique pairs
	 * of matching cards. Games requiring a more sophisticated 
	 * matching function would need to override this function */
	public LinkedList<Card> checkMatches()
	{
		LinkedList<Card> matchingCards = new LinkedList<Card>();

		for (int card1Index = 0;
		     card1Index < this.activeCards.size(); 
		     ++card1Index)
		{
			for (int card2Index = card1Index + 1;
			     card2Index < this.activeCards.size();
			     ++card2Index)
			{
				Card card1 = this.activeCards.get(card1Index);
				Card card2 = this.activeCards.get(card2Index);

				if (false /*TODO card1.matches(card2)*/)
				{
					matchingCards.add(card1);
					matchingCards.add(card2);
				}
			}
		}
		return matchingCards;
	}

	/* Adds all the cards in the list to the active cards */
	public void addCards(LinkedList<Card> cards)
	{
		for (int index = 0;
		     index < cards.size();
		     ++index)
		{
			Card cardToAdd = cards.get(index);
			this.activeCards.add(cardToAdd);
		}
		updateNumOfCards();
	}

	/* Removes all the cards in the list from the active cards,
	 * returning a list of all cards successfully removed */
	public LinkedList<Card> removeCards(LinkedList<Card> cards)
	{
		LinkedList<Card> removedCards = new LinkedList<Card>();
		for (int index = 0;
		     index < cards.size();
		     ++index)
		{
			Card cardToRemove = cards.get(index);
			if (this.activeCards.remove(cardToRemove))
			{
				removedCards.add(cardToRemove);
			}
		}
		updateNumOfCards();
		return removedCards;
	}

	/* Transfers all the cards in the list from active cards to inactive cards 
	 * and returns a list of all cards successfully transferred */
	public LinkedList<Card> transferActiveToInactive(LinkedList<Card> cards)
	{
		LinkedList<Card> transferredCards = new LinkedList<Card>();
		for (int index = 0;
		     index < cards.size();
		     ++index)
		{
			Card cardToTransfer = cards.get(index);
			if (this.activeCards.remove(cardToTransfer))
			{
				this.inactiveCards.add(cardToTransfer);
				transferredCards.add(cardToTransfer);
			}
		}
		return transferredCards;
	}

	/* Transfers all the cards in the list from inactive cards to active cards
	 * and returns a list of all cards successfully transferred */
	public LinkedList<Card> transferInactiveToActive(LinkedList<Card> cards)
	{
		LinkedList<Card> transferredCards = new LinkedList<Card>();
		for (int index = 0;
		     index < cards.size();
		     ++index)
		{
			Card cardToTransfer = cards.get(index);
			if (this.inactiveCards.remove(cardToTransfer))
			{
				this.activeCards.add(cardToTransfer);
				transferredCards.add(cardToTransfer);
			}
		}
		return transferredCards;
	}

/* Getters */
	public LinkedList<Card> getActiveCards()
	{
		return this.activeCards;
	}
	
	public LinkedList<Card> getInactiveCards()
	{
		return this.inactiveCards;
	}

	public int getNumOfCards()
	{
		return numOfCards;
	}

/* Private methods */
	/* Used to recalculate numOfCards when cards are added or removed */
	private void updateNumOfCards()
	{
		numOfCards = this.activeCards.size() + this.inactiveCards.size();
	}
}
