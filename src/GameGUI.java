import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameGUI extends Application {

	//Cardgame cardgame;//reference to game controller
	
	//inherited from Application, called when the GUI is launched
	public void start(Stage stage) throws Exception {
		BorderPane root = new BorderPane();//A container object that will be our root container, border pane can have 5 children, one aligned to the top of he screen, the bottom, the left, the right, and the center.
		
		VBox vbox = new VBox();//container which aligns children in a vertical line
		
		HBox hbox1 = new HBox();//container which aligns children in a horizontal line
		Text label1 = new Text("Text1");//a text label object
		hbox1.getChildren().add(label1);//add a child to the hbox
		Button but1 = new Button("BUtton 1");//a button object you can click
		but1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				label1.setText(label1.getText()+"1");
			}
		});
		hbox1.getChildren().add(but1);//add the button to the hbox
		
		HBox hbox2 = new HBox();
		Text label2 = new Text("Text2");//a text label object
		hbox2.getChildren().add(label2);//add a child to the hbox
		Button but2 = new Button("BUtton 2");//a button object you can click
		but2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				label2.setText(label2.getText()+"2");
			}
		});
		hbox2.getChildren().add(but2);//add the button to the hbox
		
		HBox hbox3 = new HBox();
		Text label3 = new Text("Text3");//a text label object
		hbox3.getChildren().add(label3);//add a child to the hbox
		Button but3 = new Button("BUtton 3");//a button object you can click
		but3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				label3.setText(label3.getText()+"3");
			}
		});
		hbox3.getChildren().add(but3);//add the button to the hbox
		
		vbox.getChildren().addAll(hbox1, hbox2, hbox3);//Add multiple children at the same time
		root.setCenter(vbox);//puts the VBox in the BorderPane, as the BorderPane's center child
		
		root.setTop(new Text("Top"));
		root.setBottom(new Text("Bottom"));
		root.setLeft(new Text("Left"));
		root.setRight(new Text("Right"));
		
		stage.setTitle("card game goes here");//Name of window
		Scene scene = new Scene(root, 800, 450);//Sets the root container object and the size of the window, 800x450
		stage.setScene(scene);//something needed
        stage.show();//something else needed
	}
	
	public void launchGUI() {
		launch();
	}

}
