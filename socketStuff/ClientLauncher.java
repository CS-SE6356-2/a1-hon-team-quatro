
class ClientLauncher {

	static ClientController game;
	static ClientGUI gui;
	static CardGame cardGame;
	
	public static void main(String[] args) {
		
		game = new ClientController();
		gui = new ClientGUI();
		cardGame = null;
		
		gui.launchGUI();//launches the GUI

	}

}
