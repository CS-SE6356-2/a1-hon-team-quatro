
public class PlayerQueueTest {

	public static void main(String[] args) 
	{
		PlayerQueue players = new PlayerQueue();
		
		//Test adding to queue and print each players name
		for(int i = 0; i < 5; i++)
		{
			players.enqueue(new Player("Player "+i,"S",null));
			System.out.println("Added "+players.getPlayer().getTeamName());
		}
		
		//Test foreach loop
		printPlayers(players);
		System.out.println();
		
		playRound(players);
		//Test player skip
		System.out.println("Skipping a player");
		players.skipPlayer();
		playRound(players);
		//Test reverse
		System.out.println("Reversing play order");
		players.reverseOrder();
		playRound(players);
		
		System.out.println("Skipping a player while reversed");
		players.skipPlayer();
		playRound(players);
		//Test reverse
		System.out.println("Reversing play order again");
		players.reverseOrder();
		playRound(players);
		

	}
	
	public static void printPlayers(PlayerQueue players)
	{
		for(Player p: players)
		{
			System.out.println(p.getTeamName());
		}
	}
	
	public static void playRound(PlayerQueue players)
	{
		for(int i = 0; i < players.size(); i++)
			System.out.println(players.nextPlayer().getTeamName()+"'s turn");
	}
}
