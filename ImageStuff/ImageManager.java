/* @author Jacob */

import javafx.scene.image.Image;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

public class ImageManager
{
/* Data Members */

	/* Holds all the images and the string used to identify them */
	public Map<String, Image> imageMap;

/* Public Methods */

	/* Uses the passed in file to fill in the imageMap */
	/* Format of the expected file is a text file containing the
	   image's name followed by it's path, with one image's information
           on each line of the file. */  
	public ImageManager(File imageDataFile) throws Exception
	{
		imageMap = new HashMap<>();
		Scanner inputFile = new Scanner(imageDataFile);
		while (inputFile.hasNext())
		{
			String imageName = inputFile.next();
			String imagePath = inputFile.next();
			imageMap.put(imageName, new Image(new FileInputStream(imagePath)));
		}
	}
}