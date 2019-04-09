import java.util.Iterator;

/**
 * Provides a means for circling through a list of players, handling skips, and reversing order
 * @author Chris
 */
public class PlayerQueue implements Iterable<Player>
{
	////Member Variables////
	private Node head;			//Tracks the player to go next
	private int size;			//Tracks the amount of players that are playing
	private boolean reversed;	//Tracks when we go to the next player or previous player
	
	////Constructor////
	
	/**
	 * Sets variables to null or 0
	 * @author Chris
	 */
	PlayerQueue()
	{
		head = null;
		size = 0;
		reversed = false;
	}

	////Functions////
	
	/**
	 * Adds a new player to the queue
	 * @param A Player to be added
	 * @return The Player that was added
	 */
	public Player enqueue(Player person)
	{
		enqueue(new Node(person));
		++size;
		return person;
	}
	
	/**
	 * Adds a new Node as either the head or the new tail
	 * @author Chris
	 * @param newNode
	 */
	private void enqueue(Node newNode)
	{
		if(size == 0)				//Put the player at the head if there are no other players
		{
			head = newNode;
			head.next = head;
			head.prev = head;
		}
		else if (size == 1)			//Put the player as the tail. This separates the head from being the tail
		{
			head.next = newNode;
			head.prev = newNode;
			newNode.next = head;
			newNode.prev = head;
			
		}
		else						//Adds the player in the queue
		{
			//Set a pointer to the old tail
			Node tail = head.prev;
			//Insert the new node into the list
			tail.next = newNode;
			head.prev = newNode;
			//Have the new node point to its respective next and prev
			newNode.next = head;
			newNode.prev = tail;
		}
	}
	
	/**
	 * Returns the Player at the head of the queue
	 * @author Chris
	 * @return A player
	 */
	public Player getPlayer() {return head.data;}
	/**
	 * Returns the amount of players in the queue
	 * @return The number of players in the queue
	 */
	public int size() {return size;}
	/**
	 * Moves head to the next player in the queue and returns the old head as 
	 * the player that is to take their turn
	 * @author Chris
	 * @return The next Player to take their turn
	 */
	public Player nextPlayer()
	{
		head = reversed? head.prev: head.next;
		return reversed? head.next.data: head.prev.data;
	}
	/**
	 * Moves head to the next player without getting back a player, essentially 
	 * skipping the skipped player's turn
	 * @author Chris
	 */
	public void skipPlayer()
	{
		head = (reversed)? head.prev: head.next;
	}
	/**
	 * Sets the reverse flag to its opposite. Calls skipPlayer() twice to set the order to the proper Player
	 * @author Chris
	 */
	public void reverseOrder() 
	{
		reversed = !reversed;
		skipPlayer();
		skipPlayer();
	}
	/**
	 * Clears the queue
	 * @author Chris
	 */
	public void clear() {head = null; size = 0;}
	
	/**
	 * A node to hold a Player and is able to look backwards and forwards
	 * @author Chris
	 */
	private class Node
	{
		Player data;
		Node next;
		Node prev;
		
		Node(Player data)
		{
			this.data = data;
			next = null;
			prev = null;
		}
	}

	@Override
	public Iterator<Player> iterator() 
	{	
		return new PlayerQueueIterator(this);
	}
	
	class PlayerQueueIterator implements Iterator<Player>
	{
		Node focus;
		boolean notHitHead;

		public PlayerQueueIterator(PlayerQueue playerQueue) 
		{
			focus = playerQueue.head;
			notHitHead = true;
		}

		@Override
		public boolean hasNext() 
		{
			return focus != head||notHitHead;
		}

		@Override
		public Player next() 
		{
			notHitHead = false;
			Player temp = focus.data;
			focus = head.next;
			return temp;
		}
		
	}
}
