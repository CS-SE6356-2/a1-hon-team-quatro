

//Launcher class, entry point of program
public class Main {

	
	public static void main(String args[]) {
		//Variable declarations
		//Cardgame game;//primary controller class
		GameGUI gui;//gui container and controller
		
		//Variable initializations
		//game = new Cardgame();
		gui = new GameGUI();
		
		
		gui.launchGUI();//launches the GUI
	}

}
