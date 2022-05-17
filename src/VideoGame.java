/*Henry Montesano III
 04/28/22
 VideoGame Class
 
This class is used to instantiate new Item objects in the Video Games category to be manipulated by store manager and customer. */

public class VideoGame extends Item {
	private String rating = "";
	private int year = 0;
	private String type = "video game";
	
	public VideoGame(String t, double p, String g, boolean r, String ra, int y, String ty) {
		super(t, p, g, r);
		rating = ra;
		year = y;
		type = ty;
	}//end overloaded constructor
	
	//accessor methods
	public String getRating() {
		return rating;
	}
		
	public int getYear() {
		return year;
	}
	
	public String getType() {
		return type;
	}
		
	//mutator methods
	public void setRating(String r) {
		rating =r;
	}
		
	public void setYear(int y) {
		year =y;
	}
	
	public void setType(String ty) {
		type = ty;
	}
	
	//toString
		public String toString() {
			return "\nGame: " + super.getTitle() + "\nRating: " + rating + "\nYear: " + year + super.toString();
		}

}//end class VideoGame
