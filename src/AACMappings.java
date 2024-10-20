import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.KeyNotFoundException;
import edu.grinnell.csc207.util.NullKeyException;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Creates a set of mappings of an AAC that has two levels,
 * one for categories and then within each category, it has
 * images that have associated text to be spoken. This class
 * provides the methods for interacting with the categories
 * and updating the set of images that would be shown and handling
 * an interactions.
 * 
 * @author Catie Baker & Sam Schmidt
 *
 */
public class AACMappings implements AACPage {

	AACCategory category = new AACCategory("");
	AssociativeArray<String, AACCategory> arr;
	
	/**
	 * Creates a set of mappings for the AAC based on the provided
	 * file. The file is read in to create categories and fill each
	 * of the categories with initial items. The file is formatted as
	 * the text location of the category followed by the text name of the
	 * category and then one line per item in the category that starts with
	 * > and then has the file name and text of that image
	 * 
	 * for instance:
	 * img/food/plate.png food
	 * >img/food/icons8-french-fries-96.png french fries
	 * >img/food/icons8-watermelon-96.png watermelon
	 * img/clothing/hanger.png clothing
	 * >img/clothing/collaredshirt.png collared shirt
	 * 
	 * represents the file with two categories, food and clothing
	 * and food has french fries and watermelon and clothing has a 
	 * collared shirt
	 * @param filename the name of the file that stores the mapping information
	 * @throws NullKeyException 
	 * @throws IOException 
	 */
	public AACMappings(String filename) throws NullKeyException, IOException {
		//arr.set("", category);
		Scanner eyes = new Scanner(filename);

		while (eyes.hasNextLine()) {
			String line = eyes.nextLine();
			if (line == null) {
			} else {
			String[] fileInput = line.split("\\s+", 2);
			String locInput = fileInput[0];
			String nameInput = fileInput[1];
			if (locInput.charAt(0) != '>'){
				category = new AACCategory(nameInput);
				arr.set(nameInput, category);
			}
			category.addItem(locInput, nameInput);
			}
			eyes.close();
		}
	} //AACMappings(String)
	
	/**
	 * Given the image location selected, it determines the action to be
	 * taken. This can be updating the information that should be displayed
	 * or returning text to be spoken. If the image provided is a category, 
	 * it updates the AAC's current category to be the category associated 
	 * with that image and returns the empty string. If the AAC is currently
	 * in a category and the image provided is in that category, it returns
	 * the text to be spoken.
	 * @param imageLoc the location where the image is stored
	 * @return if there is text to be spoken, it returns that information, otherwise
	 * it returns the empty string
	 * @throws KeyNotFoundException 
	 * @throws NoSuchElementException if the image provided is not in the current 
	 * category
	 */
	public String select(String imageLoc) throws KeyNotFoundException {
		if (hasImage(imageLoc)) {
			return category.select(imageLoc);
		} else if (arr.hasKey(imageLoc)) {
			category = arr.get(imageLoc);
			return null;
		} else {
			return "No such element";
		}
	} //select(String)
	
	/**
	 * Provides an array of all the images in the current category
	 * @return the array of images in the current category; if there are no images,
	 * it should return an empty array
	 */
	public String[] getImageLocs() {
		return category.getImageLocs();
	} //getImageLocs()
	
	/**
	 * Resets the current category of the AAC back to the default
	 * category
	 * @throws KeyNotFoundException 
	 */
	public void reset() throws KeyNotFoundException {
		category = arr.get("");
	} //reset()
	
	
	/**
	 * Writes the ACC mappings stored to a file. The file is formatted as
	 * the text location of the category followed by the text name of the
	 * category and then one line per item in the category that starts with
	 * > and then has the file name and text of that image
	 * 
	 * for instance:
	 * img/food/plate.png food
	 * >img/food/icons8-french-fries-96.png french fries
	 * >img/food/icons8-watermelon-96.png watermelon
	 * img/clothing/hanger.png clothing
	 * >img/clothing/collaredshirt.png collared shirt
	 * 
	 * represents the file with two categories, food and clothing
	 * and food has french fries and watermelon and clothing has a 
	 * collared shirt
	 * 
	 * @param filename the name of the file to write the
	 * AAC mapping to
	 */
	public void writeToFile(String filename) {
		
	}
	
	/**
	 * Adds the mapping to the current category (or the default category if
	 * that is the current category)
	 * @param imageLoc the location of the image
	 * @param text the text associated with the image
	 * @throws NullKeyException 
	 */
	public void addItem(String imageLoc, String text) throws NullKeyException {
		category.addItem(imageLoc, text);
	}


	/**
	 * Gets the name of the current category
	 * @return returns the current category or the empty string if 
	 * on the default category
	 */
	public String getCategory() {
		try {
			return category.getCategory();
		} catch (Exception e) {
			return "";
		}
	} // getCategory()

	/**
	 * Determines if the provided image is in the set of images that
	 * can be displayed and false otherwise
	 * @param imageLoc the location of the category
	 * @return true if it is in the set of images that
	 * can be displayed, false otherwise
	 */
	public boolean hasImage(String imageLoc) {
		String[] imageLocsArr = getImageLocs();
		for (int i = 0; i < imageLocsArr.length; i++) {
			if (imageLoc.equals(imageLocsArr[i])) {
				return true;
			} //endif
		} //endfor
		return false;
	} // hasImage(String)
}
