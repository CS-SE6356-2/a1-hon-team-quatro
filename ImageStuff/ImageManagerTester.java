import java.io.File;
import javafx.application.Application; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ImageManagerTester extends Application {
	@Override
	public void start(Stage stage) throws Exception
	{
		stage.setTitle("Image Test");
		ImageManager imageManager = new ImageManager(new File("ImagesData.txt"));
		Image blue = imageManager.imageMap.get("Blue");
		Image yellow = imageManager.imageMap.get("Yellow");
		Image green = imageManager.imageMap.get("Green");
		Image red = imageManager.imageMap.get("Red");
		ImageView blueView = new ImageView(blue);
		ImageView yellowView = new ImageView(yellow);
		ImageView greenView = new ImageView(green);
		ImageView redView = new ImageView(red);
		
		GridPane grid = new GridPane();
		GridPane.setConstraints(blueView, 0, 0);
		GridPane.setConstraints(yellowView, 1, 0);
		GridPane.setConstraints(greenView, 2, 0);
		GridPane.setConstraints(redView, 3, 0);
		grid.getChildren().addAll(blueView, yellowView, greenView, redView);
		Scene scene = new Scene(grid, 500, 500);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String [] args)
	{
		launch(args);
	}
}