import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientGUI extends Application{
	
	ClientController game = null;
	
	String state;
	String yourName;
	
	//GUI stuff
	VBox root = new VBox();
	Button hostButton;
	Button joinButton;
	Button connectButton;
	Button serverButton;
	Button backButton;
	Button exitButton;
	Button playButton;
	Button startButton;
	Text menuLabel;
	Text addressLabel;
	Text infoLabel;
	Text turnLabel;
	TextField addressInput;
	TextField nameInput;
	TextField gameInput;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		game = ClientLauncher.game;
		ClientLauncher.gui = this;
		game.gui = ClientLauncher.gui;
		
		state = "";
		yourName = "Player";
		
		root = new VBox();
		hostButton = new Button("Host Game");//part of main menu screen
		joinButton = new Button("Join Game");//part of main menu screen
		connectButton = new Button("Connect");//part of join screen
		serverButton = new Button("Create Server");//part of host screen
		backButton = new Button("Back");//part of host and join screens
		exitButton = new Button("Exit");//part of main menu screen
		playButton = new Button("Play");//part of game screen
		startButton = new Button("Start Game");//part of hosting screen
		menuLabel = new Text("Main Menu");//part of all screens
		addressLabel = new Text("Starting server...");//part of host screen
		infoLabel = new Text("Enter details below");//part of join and game screen
		turnLabel = new Text();//part of game screen
		addressInput = new TextField();//part of join screen
		nameInput = new TextField();//part of host and join screen
		gameInput = new TextField();//part of game screen
		
		//setup buttons and what-not
		hostButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				preHost();
			}
		});
		
		serverButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				hosting();
			}
		});
		
		joinButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				join();
			}
		});
		
		connectButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				connectButton.setDisable(true);
				
				connect();
				
				connectButton.setDisable(false);
			}
		});
		
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				main();
			}
		});
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();//closes the window
			}
		});
		
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				play();
			}
		});
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				game();
			}
		});
		
		main();
		
		stage.setTitle("Client Test");
		Scene scene = new Scene(root, 800, 450);
		stage.setScene(scene);
        stage.show();
	}//end of start
	
	
	//methods called by buttons
	void main() {
		if(state.equals("hosting") || state.equals("lobby"))
			game.closeSocks(state);
		
		mainScreen();
		
		state = "main";
	}
	void mainScreen() {
		root.getChildren().clear();
		root.getChildren().addAll(menuLabel, hostButton, joinButton, exitButton);
		menuLabel.setText("Main Menu");
	}
	
	void preHost() {
		preHostScreen();
		state = "host";
	}
	void preHostScreen() {
		root.getChildren().clear();
		root.getChildren().addAll(menuLabel, infoLabel, nameInput, serverButton, backButton);
		menuLabel.setText("Host a Game");
		infoLabel.setText("Enter your name and click \"Create Server\"");
	}
	
	void hosting() {
		
		if(nameInput.getText().isEmpty()) {
			infoLabel.setText("Enter your name!");
			connectButton.setDisable(false);
			return;
		}
		yourName = nameInput.getText();
		
		String address = game.setupHost();
		if(address.equals("Unable to establish host")) {
			infoLabel.setText(address);
			return;
		}
		
		String result = game.connectToHost(address, yourName);
		if(!result.equals("Connected!")) {
			infoLabel.setText(result);
			return;
		}
		addressLabel.setText(address);
		
		hostingScreen();
		
		state = "hosting";
		
		game.isServer = true;
		game.serverThread.start();
		game.clientThread.start();
	}
	void hostingScreen() {
		root.getChildren().clear();
		root.getChildren().addAll(menuLabel, addressLabel, infoLabel, startButton, backButton);
		menuLabel.setText("Host a Game");
		infoLabel.setText(yourName);
	}
	
	void join() {
		joinScreen();
		state = "join";
	}
	void joinScreen() {
		root.getChildren().clear();
		root.getChildren().addAll(menuLabel, infoLabel, addressInput, nameInput, connectButton, backButton);
		menuLabel.setText("Join a Game");
		infoLabel.setText("Enter details below");
		addressInput.setPromptText("Enter Host Address");
		nameInput.setPromptText("Enter Your Name");
	}
	
	void connect() {
		if(addressInput.getText().isEmpty()) {
			infoLabel.setText("Enter the host address!");
			connectButton.setDisable(false);
			return;
		}
		if(nameInput.getText().isEmpty()) {
			infoLabel.setText("Enter your name!");
			connectButton.setDisable(false);
			return;
		}
		if(addressInput.getText().split(":", 0).length != 2) {
			infoLabel.setText("Invalid host name!");
			connectButton.setDisable(false);
			return;
		}
		
		String result = game.connectToHost(addressInput.getText(), nameInput.getText());
		
		if(result.equals("Connected!")) {
			yourName = nameInput.getText();
			lobby();
		}
		infoLabel.setText(result);
	}
	
	void lobby(){
		lobbyScreen();
		state = "lobby";
		game.clientThread.start();
	}
	void lobbyScreen(){
		root.getChildren().clear();
		root.getChildren().addAll(menuLabel, infoLabel, backButton);
		menuLabel.setText("Lobby");
		infoLabel.setText(yourName);
	}
	
	void game() {
		gameScreen();
		state = "game";
	}
	void gameScreen() {
		root.getChildren().clear();
		root.getChildren().addAll(menuLabel, infoLabel, turnLabel, gameInput);
		menuLabel.setText("Game");
		gameInput.setPromptText("Write your move here");
	}
	
	void play() {
		if(gameInput.getText().isEmpty()) {
			infoLabel.setText("Enter your move");
			return;
		}
		
		boolean success = endTurn(gameInput.getText());
		
		if(success) {
			gameInput.setText("");
			root.getChildren().remove(playButton);
		}
		else {
			infoLabel.setText("Failed to reach server, try again");
		}
	}
	
	//call this to end the clients turn
	//pass the string to write to the server
	//returns true if the message was sucesesfully sent without error
	boolean endTurn(String messageToServer) {
		boolean success = false;
		int attempts = 0;//keeps track of attempts
		while(!success && attempts++ < 10){//tries ten times talk to server
			try {
				game.writeToServer(messageToServer);
				success = true;
			} catch (IOException e) {}
		}
		return success;
	}

	
	public void launchGUI() {
		launch();
	}
	
}//end of GUI



