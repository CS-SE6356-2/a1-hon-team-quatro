package separator;

public class ServerLauncher {

	static ServerController game;
	static ServerGUI gui;
	
	public static void main(String[] args) {
		
		game = new ServerController();
		gui = new ServerGUI();
		
		gui.launchGUI();//launches the GUI

	}

}
