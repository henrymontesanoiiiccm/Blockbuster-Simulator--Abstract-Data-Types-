/*Henry Montesano III
 04/28/22
 StoreManager Class
 
This class is used to perform store manager functionality, such as showing the manager's inventory of items, 
adding new items to inventory at a cost, removing sold items from inventory at a profit, and displaying the inventory
with optional search filters. */

import java.util.*;
public class StoreManager {
	private List<Item> inventory = new ArrayList<>();
	private double cashRegister;
	
	public StoreManager(List<Item> l, double cr) {
		inventory = l;
		cashRegister = cr;
	}
	
	//accessor methods
	public List<Item> getInventory() {
    	return inventory;
	}
	
    public double getCash() {
    	return cashRegister;
	}
    
  //mutator methods
    public void setMoney(double m) {
    	cashRegister = m;
	}
	
	public void addItemPrice(Item i) {
		double register = cashRegister;
		inventory.add(i);
		cashRegister = cashRegister - i.getPrice() / 3;
		System.out.println("\nYou have added " + i.getTitle() + " to the inventory. \nCash Register before purchase: " + register + "\nCash Register after purchase: " + cashRegister);
	}
	
	public String removeItem (List<Item> l, Item i) {
		for (int j = 0; j < l.size(); j++) {
			 Item item = l.get(j);
			  if (item.equals(i)) {
				 l.remove(j);
				 return "Removed " + j;
			  }//end if statement
			}//end for loop
		return "Error";
	}
	
	public void addItem(Scanner k) {
		 System.out.println("\nPlease press the number next to which item type you want to add: ");
		 System.out.println("1. Add Movie");
		 System.out.println("2. Add Video Game");
		 System.out.println("3. Add Snack");
		 int addOption = k.nextInt();
	     k.nextLine();//Flushing the input buffer.
	     //begin Switch Statement
		 switch (addOption) {
	        case 1: addMovie(k);
	                 	break;
	        case 2: addGame(k);
        				break;
	        case 3: addSnack(k);
        				break;		
	        default: System.out.println("Error: You entered an invalid option. Please try again.");	 
	        			break;
		   }//end switch case	 
	 }//end addItem method
	 
	public void addMovie(Scanner key) {
		 try {
			 System.out.println("\nPlease enter the title of the Movie: ");
			 String title = key.nextLine();
			 System.out.println("\nPlease enter the price of the Movie: ");
			 double price = key.nextDouble();
			 System.out.println("\nPlease enter the genre of the Movie: ");
			 String genre = key.next();
			 System.out.println("\nPlease enter the rating of the Movie: ");
			 String rating = key.next();
			 System.out.println("\nPlease enter the year the Movie released: ");
			 int year = key.nextInt();
			 Movie newMovie = new Movie(title, price, genre, false, rating, year, "movie");
			 addItemPrice(newMovie);
			 key.nextLine();//Flushing the input buffer
		 }
		 catch(Exception e) {
			 System.out.println("Invalid Entry. Please try adding Movie again!");
			 key.nextLine();//Flushing input butter
			 addMovie(key);//recursion
		 }//end try catch
	 }//end addMovie method
	 
	public void addGame(Scanner key) {
		 try {
			System.out.println("\nPlease enter the title of the Game: ");
		 	String title = key.nextLine();
		 	System.out.println("\nPlease enter the price of the Game: ");
		 	double price = key.nextDouble();
		 	System.out.println("\nPlease enter the genre of the Game: ");
		 	String genre = key.next();
		 	System.out.println("\nPlease enter the rating of the Game: ");
		 	String rating = key.next();
		 	System.out.println("\nPlease enter the year the Game released: ");
		 	int year = key.nextInt();
		 	VideoGame newGame = new VideoGame(title, price, genre, false, rating, year, "video game");
		 	addItemPrice(newGame);
		 	key.nextLine();//Flushing the input buffer.
		 }
		 catch(Exception e) {
			 System.out.println("Invalid Entry. Please try adding Video Game again!");
			 key.nextLine();//Flushing input butter
			 addGame(key);//recursion
		 }//end try catch
	 }//end addGame method
	 
	public void addSnack(Scanner key) {
		 try{
			 System.out.println("\nPlease enter the name of the Snack: ");
			 String title = key.next();
			 System.out.println("\nPlease enter the price of the Snack: ");
			 double price = key.nextDouble();
			 System.out.println("\nPlease enter the type of Snack: ");
			 String type = key.next();
			 Snack newSnack= new Snack(title, price, type, false, "snack");
			 addItemPrice(newSnack);
			 key.nextLine();//Flushing the input buffer.
		 }
		 catch(Exception e) {
			 System.out.println("Invalid Entry. Please try adding Snack again!");
			 key.nextLine();//Flushing input butter
			 addSnack(key);//recursion
		 }//end try catch
	 }//end addSnack method

	public void displayFullList(){
		 System.out.println("\nStore Inventory: ");
		 Collections.sort(inventory, new compareYear());
		 for (int i = 0; i<inventory.size(); i++) {
				System.out.println(inventory.get(i));
			}//end for loop
	 }//end displayList
	 
	public void displayMovieList(){
		 System.out.println("\nMovie Inventory: ");
		 Collections.sort(inventory, new compareYear());
		 for (int i = 0; i<inventory.size(); i++) {
			 if (inventory.get(i).getClass().equals(Movie.class)) {
					System.out.println(inventory.get(i));
				}
			}//end for loop
	 }//end displayMovieList
	 
	public void displayGameList(){
		 System.out.println("\nVideo Game Inventory: ");
		 Collections.sort(inventory, new compareYear());
		 for (int i = 0; i<inventory.size(); i++) {
			 if (inventory.get(i).getClass().equals(VideoGame.class)) {
					System.out.println(inventory.get(i));
				}
			}//end for loop
	 }//end displayGameList
	 
	public void displaySnackList(){
		 System.out.println("\nSnack Inventory: ");
		 for (int i = 0; i<inventory.size(); i++) {
			 if (inventory.get(i).getClass().equals(Snack.class)) {
					System.out.println(inventory.get(i));
				}
			}//end for loop
	 }//end displaySnackList
	 
	public void findItemName(Scanner key) {
		 System.out.println("\nPlease press the number next to which items you want to find: ");
		 System.out.println("1. Find Movies by Title");
		 System.out.println("2. Find Video Games by Title");
		 System.out.println("3. Find Snacks by Name");
		 int nameOption = key.nextInt();
	        key.nextLine();//Flushing the input buffer.
	    	//begin Switch Statement
		    switch (nameOption) {
	        	case 1: findMovieName(key);
	                 	break;
	        	case 2: findGameName(key);
	        			break;
	        	case 3: findSnackName(key);
 					    break;
	        	default: System.out.println("Error: You entered an invalid option. Please try again.");	 
	        			break;
		    }//end switch case
		   
	 }//end findItemName method
	 
	public void findMovieName(Scanner key){
		 System.out.println("\nPlease enter the title of the Movie you want to find: ");
		 String movieName = key.nextLine();
		 System.out.println("\n***************");//topBorder
		 System.out.println("Search Results: ");
		 System.out.println("***************");//bottomBorder
		 Collections.sort(inventory, new compareYear());
		 for (int i = 0; i<inventory.size(); i++) {
			 if (inventory.get(i).getClass().equals(Movie.class)) {
				 if (inventory.get(i).getTitle().toLowerCase().contains(movieName.toLowerCase())) {
						System.out.println(inventory.get(i));
				 }//end if
			 }//end if
		 }//end for loop
		
	 }//end findMovieName method
	 
	public void findGameName(Scanner key){
		 System.out.println("\nPlease enter the title of the Video Game you want to find: ");
		 String gameName = key.nextLine();
		 System.out.println("\n***************");//topBorder
		 System.out.println("Search Results: ");
		 System.out.println("***************");//bottomBorder
		 Collections.sort(inventory, new compareYear());
		 for (int i = 0; i<inventory.size(); i++) {
			 if (inventory.get(i).getClass().equals(VideoGame.class)) {
				 if (inventory.get(i).getTitle().toLowerCase().contains(gameName.toLowerCase())) {
						System.out.println(inventory.get(i));
				 }//end if
			 }//end if
		 }//end for loop
		
	 }//end findGameName method
	 
	public void findSnackName(Scanner key){
		 System.out.println("\nPlease enter the name of the Snack you want to find: ");
		 String snackName = key.nextLine();
		 System.out.println("\n***************");//topBorder
		 System.out.println("Search Results: ");
		 System.out.println("***************");//bottomBorder
		 for (int i = 0; i<inventory.size(); i++) {
			 if (inventory.get(i).getClass().equals(Snack.class)) {
				 if (inventory.get(i).getTitle().toLowerCase().contains(snackName.toLowerCase())) {
						System.out.println(inventory.get(i));
				 }//end if
			 }//end if
		 }//end for loop
		
	 }//end findMovieName method
	 
	public void findItemGenre(Scanner key) {
		 System.out.println("\nPlease press the number next to which items you want to find: ");
		 System.out.println("1. Find Movies by Genre");
		 System.out.println("2. Find Video Games by Genre");
		 System.out.println("3. Find Snacks by Type");
		 int genreOption = key.nextInt();
	        key.nextLine();//Flushing the input buffer.
	    	//begin Switch Statement
		    switch (genreOption) {
	        	case 1: findMovieGenre(key);
	                 	break;
	        	case 2: findGameGenre(key);
	        			break;
	        	case 3: findSnackGenre(key);
 					    break;
	        	default: System.out.println("Error: You entered an invalid option. Please try again.");	 
	        			break;
		    }//end switch case
		   
	 }//end findItemGenre method
	 
	public void findMovieGenre(Scanner key){
		 System.out.println("\nPlease enter what genre of Movie you want to find: ");
		 String movieGenre = key.nextLine();
		 System.out.println("\n***************");//topBorder
		 System.out.println("Search Results: ");
		 System.out.println("***************");//bottomBorder
		 Collections.sort(inventory, new compareYear());
		 for (int i = 0; i<inventory.size(); i++) {
			 if (inventory.get(i).getClass().equals(Movie.class)) {
				 if (inventory.get(i).getGenre().toLowerCase().contains(movieGenre.toLowerCase())) {
						System.out.println(inventory.get(i));
				 }//end if
			 }//end if
		 }//end for loop
		
	 }//end findMovieGenre method
	 
	public void findGameGenre(Scanner key){
		 System.out.println("\nPlease enter what genre of Video Game you want to find: ");
		 String gameGenre = key.nextLine();
		 System.out.println("\n***************");//topBorder
		 System.out.println("Search Results: ");
		 System.out.println("***************");//bottomBorder
		 Collections.sort(inventory, new compareYear());
		 for (int i = 0; i<inventory.size(); i++) {
			 if (inventory.get(i).getClass().equals(VideoGame.class)) {
				 if (inventory.get(i).getGenre().toLowerCase().contains(gameGenre.toLowerCase())) {
						System.out.println(inventory.get(i));
					}//end if
			 }//end if
		 }//end for loop
		 
	 }//end findGameGenre method
	 
	public void findSnackGenre(Scanner key){
		 System.out.println("\nPlease enter what type of Snack you want to find: ");
		 String snackGenre = key.nextLine();
		 System.out.println("\n***************");//topBorder
		 System.out.println("Search Results: ");
		 System.out.println("***************");//bottomBorder
		 for (int i = 0; i<inventory.size(); i++) {
			 if (inventory.get(i).getClass().equals(Snack.class)) {
				 if (inventory.get(i).getGenre().toLowerCase().contains(snackGenre.toLowerCase())) {
						System.out.println(inventory.get(i));
				 }//end if
			 }//end if
		 }//end for loop
		 
	 }//end findSnackGenre method
	 
	public void findItemYear(Scanner key) {
		 System.out.println("\nPlease press the number next to which items you want to find: ");
		 System.out.println("1. Find Movies by Year");
		 System.out.println("2. Find Video Games by Year");
		 int yearOption = key.nextInt();
	        key.nextLine();//Flushing the input buffer.
	    	//begin Switch Statement
		    switch (yearOption) {
	        	case 1: findMovieYear(key);
	                 	break;
	        	case 2: findGameYear(key);
	        			break;
	        	default: System.out.println("Error: You entered an invalid option. Please try again.");	 
	        			break;
		    }//end switch case
		    
	 }//end findItemGenre method
	 
	public void findMovieYear(Scanner key){
		 System.out.println("\nPlease enter what year you want to search for: ");
		 try {
			 int movieYear = key.nextInt();
			 System.out.println("\n***************");//topBorder
			 System.out.println("Search Results: ");
			 System.out.println("***************");//bottomBorder
			 Collections.sort(inventory, new compareYear());
			 for (int i = 0; i<inventory.size(); i++) {
				 if (inventory.get(i).getClass().equals(Movie.class)) {
					 if (inventory.get(i).getYear() == movieYear) {
							System.out.println(inventory.get(i));
						}//end if	 
				 }//end if
			 }//end for loop	 
		 }
		 catch(Exception e) {
			 System.out.println("Invalid Year. Please try again!");
			 key.nextLine();//flushing input buffer
			 findMovieYear(key);//recursion
		 }//end try catch
		 
		 
	 }//end findMovieYear method
	 
	public void findGameYear(Scanner key){
		 System.out.println("\nPlease enter what year you want to search for: ");
		 try {
			 int gameYear = key.nextInt();
			 System.out.println("\n***************");//topBorder
			 System.out.println("Search Results: ");
			 System.out.println("***************");//bottomBorder
			 Collections.sort(inventory, new compareYear());
			 for (int i = 0; i<inventory.size(); i++) {
				 if (inventory.get(i).getClass().equals(VideoGame.class)) {
					 if (inventory.get(i).getYear() == gameYear) {
							System.out.println(inventory.get(i));
						}//end if
				 }//end if
			 }//end for loop
		 }
		 catch(Exception e) {
			 System.out.println("Invalid Year. Please try again!");
			 key.nextLine();//flushing input buffer
			 findGameYear(key);//recursion
		 }//end try catch
		 
	 }//end findGameYear method
	 
	public void findItemRating(Scanner key) {
		 System.out.println("\nPlease press the number next to which items you want to find: ");
		 System.out.println("1. Find Movies by Rating");
		 System.out.println("2. Find Video Games by Rating");
		 int ratingOption = key.nextInt();
		 key.nextLine();//Flushing the input buffer.
	    	//begin Switch Statement
	    	//begin Switch Statement
		    switch (ratingOption) {
	        	case 1: findMovieRating(key);
	                 	break;
	        	case 2: findGameRating(key);
	        			break;
	        	default: System.out.println("Error: You entered an invalid option. Please try again.");	 
	        			break;
		    }//end switch case
		    
	 }//end findItemRating method
	 
	public void findMovieRating(Scanner key){
		 System.out.println("\nPlease enter what Movie Rating you want to search for: ");
		 String movieRating = key.nextLine();
		 System.out.println("\n***************");//topBorder
		 System.out.println("Search Results: ");
		 System.out.println("***************");//bottomBorder
		 Collections.sort(inventory, new compareYear());
		 for (int i = 0; i<inventory.size(); i++) {
			 if (inventory.get(i).getClass().equals(Movie.class)) {
				 if (inventory.get(i).getRating().toLowerCase().equals(movieRating.toLowerCase())) {
						System.out.println(inventory.get(i));
				 }//end if	 
			 }//end if
		 }//end for loop
		 
	 }//end findMovieRating method
	 
	public void findGameRating(Scanner key){
		 System.out.println("\nPlease enter what Video Game Rating you want to search for: ");
		 String gameRating = key.nextLine();
		 System.out.println("\n***************");//topBorder
		 System.out.println("Search Results: ");
		 System.out.println("***************");//bottomBorder
		 Collections.sort(inventory, new compareYear());
		 for (int i = 0; i<inventory.size(); i++) {
			 if (inventory.get(i).getClass().equals(VideoGame.class)) {
				 if (inventory.get(i).getRating().toLowerCase().equals(gameRating.toLowerCase())) {
						System.out.println(inventory.get(i));
				 }//end if	 
			 }//end if
		 }//end for loop
		 
	 }//end findGameRating method
}//end class StoreManager
