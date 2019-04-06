package separator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ServerGUI extends Application 
{

	//Member variables
	ServerController game = null;
	
	String state;
	
	//Gui items
	VBox root = new VBox();
	Button serverButton;
	Button quitButton;
	Button backButton;
	Button startButton;
	Text addressLabel;
	Text menuLabel;
	Text infoLabel;
	
	@Override
	public void start(Stage stage) throws Exception 
	{
		//initial setup for the gui across the network/gui classes
		game = ServerLauncher.game;
		ServerLauncher.gui = this;
		game.gui = ServerLauncher.gui;

		state = "";
		
		root = new VBox();
		serverButton = new Button("Create Server");
		quitButton = new Button("Quit");
		backButton = new Button("Back");
		startButton = new Button("Start");
		addressLabel = new Text("Starting server...");
		menuLabel = new Text("Menu");
		infoLabel = new Text("");
		
		serverButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				server();
			}
		});
		
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				main();
			}
		});
		
		quitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				game();
			}
		});
		
		//GOTO First scene
		main();
		
		stage.setTitle("Server Test");
		Scene scene = new Scene(root,450,800);
		stage.setScene(scene);
		stage.show();
	}
	
	//Scenes
	void main()
	{
		if(state.equals("Server"))
			game.closeSocks(state);
		
		root.getChildren().clear();
		root.getChildren().addAll(menuLabel, infoLabel, infoLabel, serverButton, quitButton);
		menuLabel.setText("Menu");
		infoLabel.setText("");
		state = "main";
	}
	
	void server()
	{
		String address = game.setupHost();
		if(address.equals("Unable to establish host")) {
			infoLabel.setText(address);
			return;
		}
		
		root.getChildren().clear();
		root.getChildren().addAll(menuLabel,infoLabel,addressLabel, startButton, backButton, quitButton);
		infoLabel.setText("Connect to:");
		addressLabel.setText(address);
		state = "Server";
		
		game.isServer = true;
		game.clientCatcher.start();
	}
	void game()
	{
		root.getChildren().clear();
		root.getChildren().addAll(menuLabel,infoLabel,quitButton);
		menuLabel.setText("Game has started");
		infoLabel.setText("");
		state = "game";
		
		game.broadcaster.start();
	}
	
	
	public void launchGUI()
	{
		launch();
	}
}
