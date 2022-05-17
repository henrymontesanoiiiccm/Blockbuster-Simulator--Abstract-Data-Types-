/*Henry Montesano III
 04/28/22
 BlockbusterSimulator Class
 
 This client program demonstrates the ADT of Lists & Bags, simulating the experience of a store manager and customer
 at a Blockbuster store. This client imports .txt files into Item objects for both the manager's inventory List and the 
 customer's Bag with previously purchased items. The client also creates Store Manager and Customer objects that use the 
 inventory List and customer Bag respectively. The client will ask the user if they would like to use either the store 
 manager or customer functionality and display the appropriate menu. When exiting the program, the client writes remaining items in manager's inventory and 
 the customer's bag to the .txt files used for importing. */

import java.util.*;
import java.io.*;
public class BlockbusterSimulator {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		//open store data files
		File moviesData = new File("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/moviesData");
	    Scanner moviesInput = new Scanner(moviesData);
		File gamesData = new File("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/gamesData");
		Scanner gamesInput = new Scanner(gamesData);
		File snacksData = new File("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/snacksData");
		Scanner snacksInput = new Scanner(snacksData);
		File registerData = new File("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/cashRegisterData");
		Scanner registerInput = new Scanner(registerData);
		List<Item> stock = new ArrayList<>();
		
		//open customer data files
		File customerBagData = new File("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/customerBag");
		Scanner bagInput = new Scanner(customerBagData);
		File customerCashData = new File("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/customerCashData");
		Scanner cashInput = new Scanner(customerCashData);
		BagInterface<Item> bag = new CustomerBag<Item>();
		
		//instantiate Customer & StoreManager objects
		StoreManager Manager = new StoreManager(stock, registerInput.nextDouble());
		Customer StoreCustomer = new Customer(bag, cashInput.nextDouble());
		boolean endProgram = false;
		Scanner keyboard = new Scanner(System.in);
		
		//import data
	    importMovies(Manager.getInventory(), moviesInput);//import movies file
	    importGames(Manager.getInventory(), gamesInput);//import games file
	    importSnacks(Manager.getInventory(), snacksInput);//import snacks file
	    importBag(StoreCustomer, bagInput);//import customer bag
	    
	    //close files
	    moviesInput.close();//close movies file
	    gamesInput.close();//close games file
	    snacksInput.close();//close snacks file
	    registerInput.close();//close register file
	    bagInput.close();//close customer bag file
	    cashInput.close();//close customer cash file
	   
	    //Display Opening Program Menu
	    System.out.println("********************************************");//TopBorder
	    System.out.println("* Store Manager: Enter 1                   *\n* Customers: Enter Any Other Number/Letter *");
	    System.out.println("********************************************");//bottomBorder
	    try {
	    	int isManager = keyboard.nextInt();
		    if (isManager == 1) {
		    	runManager(endProgram, Manager, keyboard);
		    }else {
		    	//Display Customer Menu
		    	runCustomer(endProgram, Manager, StoreCustomer, keyboard);
		    }//end if else
	    }
	    catch(Exception e) {
	    	keyboard.nextLine();//Flushing the input buffer.
	    	runCustomer(endProgram, Manager, StoreCustomer, keyboard);
	    }
	    
		//export data
	    exportMovies(Manager.getInventory());
	    exportGames(Manager.getInventory());
	    exportSnacks(Manager.getInventory());
	    exportRegister(Manager);
	    exportCustomerCash(StoreCustomer);
	    exportBag(StoreCustomer);
	    System.out.println("Exiting Blockbuster Program. Have a good day!");


	}//end main method
	
	/** Displays the manager menu referring to the manager switch case, runs the manager switch case
	@param ep  The boolean endProgram that checks if user wants to stop using the program
	@param mng The Manager object. 
	@param k The Scanner keyboard object. */
	private static void runManager(boolean ep, StoreManager mng, Scanner k) {
		//Open Store Manager Menu
    	while (!ep) {
    		managerMenu();
    		managerSwitch(mng, k);
    		try {
    			System.out.println("\n*************************************************************");//topBorder
       		 	System.out.println("Enter 0 to close program. Enter any other key to continue.");
       		 	System.out.println("*************************************************************\n");//bottomBorder
       		 	int continueProgram = k.nextInt();
    		    	k.nextLine();//Flushing the input buffer.
    			    if (continueProgram == 0) {
    			    	ep = true;
    			    }//end if	
    		}
    		catch(Exception e) {
    			k.nextLine();//Flushing the input buffer.
    		}
    		
    	}//end while
	}//end runManager method
	
	/** Displays the customer menu referring to the customer switch case, runs the customer switch case
	@param ep  The boolean endProgram that checks if user wants to stop using the program
	@param mng The Manager object. 
	@param mng The StoreCustomer object.
	@param k The Scanner keyboard object. */
	private static void runCustomer(boolean ep, StoreManager mng, Customer cust, Scanner k) {
		//Open Store Manager Menu
    	while (!ep) {
    		customerMenu();
    		customerSwitch(mng, cust, k);
    		try {
    			System.out.println("\n*************************************************************");//topBorder
       		 	System.out.println("Enter 0 to close program. Enter any other key to continue.");
       		 	System.out.println("*************************************************************\n");//bottomBorder
       		 	int continueProgram = k.nextInt();
    		    	k.nextLine();//Flushing the input buffer.
    			    if (continueProgram == 0) {
    			    	ep = true;
    			    }//end if
    		}
    		catch(Exception e) {
    			k.nextLine();//Flushing the input buffer.
    		}
    		
    	}//end while
	}//end runManager method
	
	/** Reads moviesData.txt file, creates new Movie object based on imported data, adds object to Manager inventory
	@param s  The stock list used as the StoreManager's inventory.
	@param m The Scanner moviesInput object. */
	 private static void importMovies(List<Item> s, Scanner m) throws FileNotFoundException {
		 while (m.hasNext()) {
			 String movie = m.nextLine();
			 String [] details = movie.split(",");
			 s.add(new Movie(details[0], Double.parseDouble(details[1]), details[2], Boolean.parseBoolean(details[3]), details[4], Integer.parseInt(details[5]), details[6]));
		 }//end while
	 }//end importMovies method
	 
	 /** Reads gamesData.txt file, creates new VideoGame object based on imported data, adds object to Manager inventory
		@param s  The stock list used as the StoreManager's inventory.
		@param g The Scanner gamesInput object. */
	 private static void importGames(List<Item> s, Scanner g) throws FileNotFoundException {
		 while (g.hasNext()) {
		    String game = g.nextLine();
		    String [] details = game.split(",");
		    s.add(new VideoGame(details[0], Double.parseDouble(details[1]), details[2], Boolean.parseBoolean(details[3]), details[4], Integer.parseInt(details[5]), details[6]));
		  }//end while
	 }//end importGames method
	 
	 /** Reads snacksData.txt file, creates new Snack object based on imported data, adds object to Manager inventory
		@param s  The stock list used as the StoreManager's inventory.
		@param si The Scanner snacksInput object. */
	 private static void importSnacks(List<Item> s, Scanner si) throws FileNotFoundException {
		 while (si.hasNext()) {
		    String game = si.nextLine();
		    String [] details = game.split(",");
		    s.add(new Snack(details[0], Double.parseDouble(details[1]), details[2], Boolean.parseBoolean(details[3]),details[4]));
		  }//end while 
	 }//end importSnacks method
	 
	 /** Reads customerBag.txt file, creates new Item object based on imported data, adds object to customer's bag
		@param cus  The StoreCustomer object.
		@param si The Scanner bagInput object. */
	 private static void importBag(Customer cus, Scanner si) throws FileNotFoundException {
		 while (si.hasNext()) {
		    String item = si.nextLine();
		    String [] details = item.split(",");
		    if (details.length == 5) {
		    	cus.getBag().add(new Snack(details[0], Double.parseDouble(details[1]), details[2], Boolean.parseBoolean(details[3]), details[4]));
		    }else if (details.length == 7) {
		    	if (details[6] == "video game") {
		    		cus.getBag().add(new VideoGame(details[0], Double.parseDouble(details[1]), details[2], Boolean.parseBoolean(details[3]), details[4], Integer.parseInt(details[5]), details[6]));
		    	} else {
		    		cus.getBag().add(new Movie(details[0], Double.parseDouble(details[1]), details[2], Boolean.parseBoolean(details[3]), details[4], Integer.parseInt(details[5]), details[6]));
		    	}//end if else
		    }//end if else
		    
		  }//end while 
	 }//end importBag method
	 
	 /** Writes all Movie objects in store manager's inventory to moviesData.txt file
		@param myList  The Manager's inventory List. */
	 private static void exportMovies(List<Item> myList) throws IOException {
		 FileOutputStream fos = new FileOutputStream("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/moviesData",true);
		 BufferedOutputStream bos = new BufferedOutputStream(fos);
		 PrintWriter pw = new PrintWriter("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/moviesData");
		 pw.close();
		 for (int i = 0; i<myList.size(); i++) {
			 if (myList.get(i).getClass().equals(Movie.class)) {
				  	String title = myList.get(i).getTitle();
				  	double price = myList.get(i).getPrice();
				  	String genre = myList.get(i).getGenre();			  
				  	String rating = myList.get(i).getRating();
				  	int year = myList.get(i).getYear();
				  	boolean rental = myList.get(i).isRental();
				  	String movieObject = title + "," + price + "," + genre + "," + rental + "," + rating + "," + year + "," + "movie\n";
				  	byte[] bytes = movieObject.getBytes();
				  	bos.write(bytes);
				}//end if
		 }//end for loop
		 bos.close();
         fos.close();
		 System.out.println("Successfully exported Movies.");
	 }//end exportMovies method
	 
	 /** Writes all VideoGame objects in store manager's inventory to gamesData.txt file
		@param myList  The Manager's inventory List. */
	 private static void exportGames(List<Item> myList) throws IOException {
		 FileOutputStream fos = new FileOutputStream("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/gamesData",true);
		 BufferedOutputStream bos = new BufferedOutputStream(fos);
		 PrintWriter pw = new PrintWriter("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/gamesData");
		 pw.close();
		 for (int i = 0; i<myList.size(); i++) {
			 if (myList.get(i).getClass().equals(VideoGame.class)) {
				  	String title = myList.get(i).getTitle();
				  	double price = myList.get(i).getPrice();
				  	String genre = myList.get(i).getGenre();			  
				  	String rating = myList.get(i).getRating();
				  	int year = myList.get(i).getYear();
				  	boolean rental = myList.get(i).isRental();
				  	String gameObject = title + "," + price + "," + genre + "," + rental + "," + rating + "," + year + "," + "video game\n";
				  	byte[] bytes = gameObject.getBytes();
				  	bos.write(bytes);
				}//end if
		 }//end for loop
		 bos.close();
         fos.close();
		 System.out.println("Successfully exported Video Games.");
	 }//end exportGames method
	 
	 /** Writes all Snack objects in store manager's inventory to snacksData.txt file
		@param myList  The Manager's inventory List. */
	 private static void exportSnacks(List<Item> myList) throws IOException {
		 FileOutputStream fos = new FileOutputStream("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/snacksData",true);
		 BufferedOutputStream bos = new BufferedOutputStream(fos);
		 PrintWriter pw = new PrintWriter("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/snacksData");
		 pw.close();
		 for (int i = 0; i<myList.size(); i++) {
			 if (myList.get(i).getClass().equals(Snack.class)) {
				  	String title = myList.get(i).getTitle();
				  	double price = myList.get(i).getPrice();
				  	String genre = myList.get(i).getGenre();
				  	String snackObject = title + "," + price + "," + genre + "," + false + ",snack\n";
				  	byte[] bytes = snackObject.getBytes();
				  	bos.write(bytes);
				}//end if
		 }//end for loop
		 bos.close();
         fos.close();
		 System.out.println("Successfully exported Snacks.");
	 }//end exportSnacks method
	 
	 /** Writes the double amount of money in the Manager's register to cashRegisterData.txt file
		@param manager  The Manager object. */
	 private static void exportRegister(StoreManager manager) throws IOException {
		 FileOutputStream fos = new FileOutputStream("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/cashRegisterData",true);
		 BufferedOutputStream bos = new BufferedOutputStream(fos);
		 PrintWriter pw = new PrintWriter("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/cashRegisterData");
		 pw.close();
		 String reg = Double.toString(manager.getCash());
		 byte[] bytes = reg.getBytes();
		 bos.write(bytes);
		 bos.close();
         fos.close();
		 System.out.println("Successfully exported Register.");
	 }//end exportRegister method
	 
	 /** Writes all Item objects in the customer's bag to customerBag.txt file
		@param cust  The StoreCustomer object. */
	 private static void exportBag(Customer cust) throws IOException {
		 FileOutputStream fos = new FileOutputStream("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/customerBag",true);
		 BufferedOutputStream bos = new BufferedOutputStream(fos);
		 PrintWriter pw = new PrintWriter("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/customerBag");
		 pw.close();
		 BagInterface<Item> customerBag = cust.getBag();
		 while (!customerBag.isEmpty()) {
			 Item bagItem = customerBag.remove();
			 if (bagItem.getClass().equals(Snack.class)) {
				 	String title = bagItem.getTitle();
				  	double price = bagItem.getPrice();
				  	String genre = bagItem.getGenre();			  
				  	String snackObject = title + "," + price + "," + genre + "," + false + ",snack\n";
				  	byte[] bytes = snackObject.getBytes();
				  	bos.write(bytes);
			 }else if (bagItem.getClass().equals(Movie.class)) {
				 	String title = bagItem.getTitle();
				  	double price = bagItem.getPrice();
				  	String genre = bagItem.getGenre();			  
				  	String rating = bagItem.getRating();
				  	int year = bagItem.getYear();
				  	boolean rental = bagItem.isRental();
				  	String Object = title + "," + price + "," + genre + "," + rental + "," + rating + "," + year + "," + "movie\n";
				  	byte[] bytes = Object.getBytes();
				  	bos.write(bytes);
			 }else {
				 	String title = bagItem.getTitle();
				  	double price = bagItem.getPrice();
				  	String genre = bagItem.getGenre();			  
				  	String rating = bagItem.getRating();
				  	int year = bagItem.getYear();
				  	boolean rental = bagItem.isRental();
				  	String Object = title + "," + price + "," + genre + rental + "," + "," + rating + "," + year + "," + "video game\n";
				  	byte[] bytes = Object.getBytes();
				  	bos.write(bytes);
			 }//end if/else
		 }//end while loop
		 bos.close();
         fos.close();
		 System.out.println("Successfully exported Customer Bag.");
	 }//end exportGames method
	 
	 /** Writes the double amount of money in the customer's wallet to customerCashData.txt file
		@param cust  The StoreCustomer object. */
	 private static void exportCustomerCash(Customer cust) throws IOException {
		 FileOutputStream fos = new FileOutputStream("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/customerCashData",true);
		 BufferedOutputStream bos = new BufferedOutputStream(fos);
		 PrintWriter pw = new PrintWriter("/Users/henrymontesano/eclipse/CS3/Final Project (Blockbuster)/customerCashData");
		 pw.close();
		 String reg = Double.toString(cust.getWallet());
		 byte[] bytes = reg.getBytes();
		 bos.write(bytes);
		 bos.close();
         fos.close();
		 System.out.println("Successfully exported Customer Money.");
	 }//end exportCustomerCash method
	 
	 /** Displays the Store Manager Menu */
	 private static void managerMenu(){
		 System.out.println("************************************************************");//topBorder
	     System.out.println("*                                                          *");   
		 System.out.println("*           Blockbuster Store Manager System               *");
		 System.out.println("*                                                          *");  
		 System.out.println("************************************************************\n\n");//bottomBorder
		 System.out.println("Please select your action by entering the corresponding number:");
		 System.out.println("1. Show Full Inventory");
		 System.out.println("2. Show Movie Inventory");
		 System.out.println("3. Show Video Game Inventory");
		 System.out.println("4. Show Snack Inventory");
		 System.out.println("5. Find Items by Name");
		 System.out.println("6. Find Items by Type");
		 System.out.println("7. Find Items by Year");
		 System.out.println("8. Find Items by Rating");
		 System.out.println("9. Add Item to Inventory");
	 }//end managerMenu method
	 
	 /** Displays the Customer Menu*/
	 private static void customerMenu(){
		 System.out.println("************************************************************");//topBorder
	     System.out.println("*                                                          *");   
		 System.out.println("*                  Welcome to Blockbuster!                 *");
		 System.out.println("*                                                          *");  
		 System.out.println("************************************************************\n\n");//bottomBorder
		 System.out.println("Please select your action by entering the corresponding number:");
		 System.out.println("1. Show Customer Bag");
		 System.out.println("2. Show Full Inventory");
		 System.out.println("3. Show Movie Inventory");
		 System.out.println("4. Show Video Game Inventory");
		 System.out.println("5. Show Snack Inventory");
		 System.out.println("6. Find Items by Name");
		 System.out.println("7. Find Items by Type");
		 System.out.println("8. Find Items by Year");
		 System.out.println("9. Find Items by Rating");
		 System.out.println("10. Purchase Item");
		 System.out.println("11. Rent Item");
		 System.out.println("12. Return Rental Item");
	 }//end customerMenu method	 
	 
	 /** Takes in user inputed number to run specific desired Manager allowed function
		@param sm  The Manager object.
		@param k  The Scanner keyboard object. */
	 private static void managerSwitch(StoreManager sm, Scanner k) {
		    int option = k.nextInt();
	        k.nextLine();//Flushing the input buffer.
	    	//begin Manager Switch Statement
		    switch (option) {
	        	case 1: sm.displayFullList();
	                 	break;
	        	case 2: sm.displayMovieList();
	        			break;
	        	case 3: sm.displayGameList();
 					break;
	        	case 4: sm.displaySnackList();
 					break;
	        	case 5: sm.findItemName(k);
						break;
	        	case 6: sm.findItemGenre(k);
						break;
	        	case 7: sm.findItemYear(k);
						break;
	        	case 8: sm.findItemRating(k);
						break;
	        	case 9: sm.addItem(k);
					break;
	        	default: System.out.println("Error: You entered an invalid option. Please try again.");	 
	        			break;
		    }//end switch case
	 }//end method managerSwitch

	 /** Takes in user inputed number to run specific desired Customer allowed function
		@param sm The Manager object.
		@param c  The StoreCustomer object.
		@param k  The Scanner keyboard object. */
	 private static void customerSwitch(StoreManager sm, Customer c, Scanner k) {
		 int option = k.nextInt();
		 k.nextLine();//Flushing the input buffer.
		 //begin Manager Switch Statement
		 switch (option) {
		 	case 1: c.displayBag();
		 		break;
		 	case 2: sm.displayFullList();
             	break;
	 		case 3: sm.displayMovieList();
    			break;
	 		case 4: sm.displayGameList();
				break;
	 		case 5: sm.displaySnackList();
				break;
	 		case 6: sm.findItemName(k);
				break;
	 		case 7: sm.findItemGenre(k);
				break;
	 		case 8: sm.findItemYear(k);
				break;
	 		case 9: sm.findItemRating(k);
				break;
	 		case 10: c.purchaseItem(sm, k);
	 			break;
	 		case 11: c.rentItem(sm, k);
	 			break;
	 		case 12: c.returnRental(sm, k);
	 			break;
	 		default: System.out.println("Error: You entered an invalid option. Please try again.");	 
    			break;
    }//end switch case
		 
}//end method customerSwitch
	
}//end class BlockbusterSimulator
