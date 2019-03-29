public class playerQueue
{
	Node head;
	int size;
	boolean reversed;
	
	playerQueue()
	{
		head = null;
		size = 0;
		reversed = false;
	}

	public Player enqueue(Player person)
	{
		enqueue(new Node(person));
		++size;
		return person;
	}
	
	private void enqueue(Node newNode)
	{
		if(size == 0)
			head = newNode;
		else if (size == 1)
		{
			head.next = newNode;
			head.prev = head.next;
		}
		else
		{
			Node tail = head.prev;
			tail.next = newNode;
			head.prev = newNode;
			
			newNode.next = head;
			newNode.prev = tail;
		}
	}
	
	public Player getPlayer()
	{
		return head.data;
	}
	public Player nextPlayer()
	{
		head = reversed? head.prev: head.next;
		return reversed? head.next.data: head.prev.data;
	}
	public void skipPlayer()
	{
		head = (reversed)? head.prev: head.next;
	}
	public void reverseOrder()
	{
		reversed = !reversed;
	}
	
	
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
}
