
public abstract class Item {
	private String title = "";
	private double price = 0;
	private String genre = "";
	private boolean rental;
	
	public Item(String t, double p, String g, boolean r) {
		title = t;
		price = p;
		genre = g;
		rental = r;
	}//end overloaded constructor
	
	//accessor methods
	
	public String getTitle() {
    	return title;
	}
	
	public double getPrice() {
		return price;
	}
	
	
	public String getGenre() {
		return genre;
	}
	
	public abstract String getRating();
	
	public abstract int getYear();
	
	public abstract String getType();
	
	public boolean isRental() {
		return rental;
	}
	
	//mutator methods
	public void setTitle(String s) {
		title =s;
	}
	public void setPrice(double p) {
		price = p;
	}
	
	
	public void setGenre(String g) {
		genre = g;
	}
	
	public void setRental(boolean r) {
		rental = r;
	}
	
	public abstract void setRating(String r);
		
	public abstract void setYear(int y);
	
	//toString
	public String toString() {
		return "\nPrice: " + price + "\nGenre: " + genre + "\nRental: " + rental;
	}
	
}//end class Item
