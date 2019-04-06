package separator;

public class ClientLauncher {

	static ClientController game;
	static ClientGUI gui;
	
	public static void main(String[] args) {
		
		game = new ClientController();
		gui = new ClientGUI();
		
		gui.launchGUI();//launches the GUI

	}

}
