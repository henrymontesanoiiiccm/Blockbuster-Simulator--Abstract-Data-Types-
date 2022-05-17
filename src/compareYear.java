/*Henry Montesano III
 04/28/22
compareYear Class
 
This class is used for sorting displayed items in the store manager's inventory by year of release in ascending order. */

import java.util.Comparator;
public class compareYear implements Comparator<Item>{
	public int compare(Item i1, Item i2) {
		return i1.getYear() - i2.getYear();
	}//end compare method
}//end class compareYear
