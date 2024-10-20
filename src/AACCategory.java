import java.util.NoSuchElementException;
import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.KeyNotFoundException;
import edu.grinnell.csc207.util.NullKeyException;

/**
 * Represents the mappings for a single category of items that should
 * be displayed
 * 
 * @author Catie Baker & Sam Schmidt
 *
 */
public class AACCategory implements AACPage {
	String nameOfCategory;
	AssociativeArray<String, String> arr;


	
	/**
	 * Creates a new empty category with the given name
	 * @param name the name of the category
	 */
	public AACCategory(String name) {
		this.nameOfCategory = name;
		this.arr = new AssociativeArray<String, String>();
	} // AACCategory(String)
	
	/**
	 * Adds the image location, text pairing to the category
	 * @param imageLoc the location of the image
	 * @param text the text that image should speak
	 * @throws NullKeyException 
	 */
	public void addItem(String imageLoc, String text) throws NullKeyException {
		try {
			arr.set(imageLoc, text);
		} catch (Exception e) {
			throw new NullKeyException();
		} // try/catch
	} // addItem(String, String)

	/**
	 * Returns an array of all the images in the category
	 * @return the array of image locations; if there are no images,
	 * it should return an empty array
	 */
	public String[] getImageLocs() {
		int generalArrayLength = arr.size();
		if (generalArrayLength == 0) {
			return new String[0];
		} // endif
		String[] imageLocsArr = new String[generalArrayLength];

		for (int i = 0; i < generalArrayLength; i++) {
			String locs = arr.getKey(i);
			imageLocsArr[i] = locs;
		} // end for

		return imageLocsArr;
	} // getImageLocs()

	/**
	 * Returns the name of the category
	 * @return the name of the category
	 */
	public String getCategory() {
		return nameOfCategory;
	} // getCategory()

	/**
	 * Returns the text associated with the given image in this category
	 * @param imageLoc the location of the image
	 * @return the text associated with the image
	 * @throws KeyNotFoundException 
	 * @throws NoSuchElementException if the image provided is not in the current
	 * 		   category
	 */
	public String select(String imageLoc) throws KeyNotFoundException {
		return arr.get(imageLoc);
	} //select(String)

	/**
	 * Determines if the provided images is stored in the category
	 * @param imageLoc the location of the category
	 * @return true if it is in the category, false otherwise
	 */
	public boolean hasImage(String imageLoc) {
		return false;
	} //hasImage(String)
}
