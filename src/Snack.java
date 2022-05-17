/*Henry Montesano III
 04/28/22
 Snack Class
 
This class is used to instantiate new Item objects in the Snacks category to be manipulated by store manager and customer. */

public class Snack extends Item {
	private String type = "snack";
	
	public Snack(String t, double p,  String g, boolean r, String ty) {
		super(t, p, g, r);
		type = ty;
	}
	public String getRating() {
		return null;
	}
		
	public int getYear() {
		return 0;
	}
	
	public String getType() {
		return type;
	}
		
	//mutator methods
		
	public void setRating(String r) {
		
	}
		
	public void setYear(int y) {
		
	}
	
	public void setType(String ty) {
		type = ty;
	}
		
	//toString
	public String toString() {
		return "\nSnack: " + super.getTitle() + super.toString();
	}

}//end class Snack
