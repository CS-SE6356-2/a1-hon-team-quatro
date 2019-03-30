import javafx.application.Application;
import javafx.stage.Stage;

public class GameGUI extends Application {

	//Cardgame cardgame;//reference to game controller
	
	//inherited from Application, called when the GUI is launched
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("card game goes here");
        stage.show();
	}
	
	public void launchGUI() {
		launch();
	}

}
