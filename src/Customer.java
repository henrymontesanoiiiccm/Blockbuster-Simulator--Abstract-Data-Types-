/*Henry Montesano III
 04/28/22
 CustomerClass
 
 This class is used to perform customer functionality, such as showing the customer's bag, purchasing or renting items, 
 and returning rented items. */

import java.util.*;
public class Customer {
	private BagInterface<Item> shoppingBag = new CustomerBag<Item>();
	private double wallet = 0;
	
	public Customer (BagInterface<Item> sb, double m) {
		shoppingBag = sb;
		wallet = m;
	}//end overloaded constructor
	
	//accessor methods
	public BagInterface<Item> getBag(){
		return shoppingBag;
	}
    
	public double getWallet() {
    	return wallet;
	}
    
    //mutator methods
    public void setMoney(double m) {
    	wallet = m;
    }
	
  //toString
  	public String toString() {
  		return "\nShopping Bag: " + getBag() + "\nMoney: " + getWallet();
  	}
  	
    /** Adds purchased item to customer's bag, subtracts item price from wallet, returns item price
	@param i  The Item object being purchased.
	@return The Item Object's price. */
	public double buyItem(Item i) {
		shoppingBag.add(i);
		wallet = wallet - i.getPrice();
		setMoney(wallet);
		return i.getPrice();
	}
	
	/** Sets purchased item to rental, adds to customer's bag, subtracts half of the item's price from wallet,
	 * returns half the item's price.
	@param i  The Item object being purchased.
	@return Half the Item Object's price. */
	public double processRentItem(Item i) {
		i.setRental(true);
		shoppingBag.add(i);
		wallet = wallet - (i.getPrice() / 2);
		setMoney(wallet);
		return (i.getPrice() / 2);
		
	}
	
	/** Displays the items in the customer's bag. */
	public void displayBag(){//overloaded
 		System.out.println("\nCustomer's Bag: ");		
 		Object[] bagArray = shoppingBag.toArray();
 		for (int index = 0; index < bagArray.length; index++){
 			System.out.print(bagArray[index] + " \n");
 		} // end for
 		System.out.println();
 	} // end displayBag Overloaded
	
	/** Displays the items of a bag.
	@param myBag The Bag object. */
	public void displayBag(BagInterface<Item> myBag){//Overloaded
 		System.out.println("Customer's Bag: ");		
 		Object[] bagArray = myBag.toArray();
 		for (int index = 0; index < bagArray.length; index++){
 			System.out.print(bagArray[index] + " \n");
 		} // end for
 		System.out.println();
 	} // end displayBag Overloaded
	
	/** Searches for item based on user's inputed item name, selects item based on user's inputed item number, runs
	 * buyItem(), removes item from Manager's inventory.
	@param m The Manager object.
	@param k The Scanner keyboard object. */
	public void purchaseItem(StoreManager m, Scanner k) {
		try {
			List<Item> itemOptions = new ArrayList<>();
			 System.out.println("\nYou have $" + getWallet() + " to spend.");
			 System.out.println("\nPlease enter the name of the item you wish to purchase: ");
			 String item = k.next();
		     k.nextLine();//Flushing the input buffer.
		     
		     for (int i = 0; i<m.getInventory().size(); i++) {
				if (m.getInventory().get(i).getTitle().toLowerCase().contains(item.toLowerCase())) {
					itemOptions.add(m.getInventory().get(i));
				}//end if statement
			 }//end for loop
		     System.out.println("Here are the items that matched your search: \n");
		     for (int i = 0; i<itemOptions.size(); i++) {
					System.out.println((i + 1) + ". " + itemOptions.get(i) + "\n");
				}//end for loop
		     System.out.println("Please enter the number next to the item you wish to purchase or press 0 to cancel purchase:");
		     int itemOption = k.nextInt() - 1;
		     k.nextLine();//Flushing the input buffer.
		     if (itemOption == -1) {
		    	 System.out.println("Purchase Cancelled. Here are your current purchased items:");
				 displayBag(getBag());
		     }else {
		    	 for (int j = 0; j<itemOptions.size(); j++) {
		    		 if (itemOption == itemOptions.indexOf(itemOptions.get(j))) {
		    			 //buy item, add to bag, pay Store Manager
		    			 Item purchasedItem = itemOptions.get(j);
		    			 double itemPrice = buyItem(purchasedItem);
		    			 System.out.println("Purchasing: " + purchasedItem.getTitle() + " for $" + itemPrice);
		    			 m.setMoney(m.getCash() + itemPrice);
		    			 //remove item from Store Manager inventory
		    			 for (int l = 0; l<m.getInventory().size(); l++) {
		    				 if (m.getInventory().get(l).equals(purchasedItem)) {
		    					 m.getInventory().remove(purchasedItem);
		    				 }
		    			 }//end for loop
		    			 System.out.println("Purchase successful! Here are your current purchased items: ");
		    			 displayBag(getBag());
		    			 System.out.println("You have: $" + getWallet() + " left to spend. \nWould you like to purchase another item? Press 1 for YES, any other number for NO:");
		    			 int continueBuy = k.nextInt();
		    			 k.nextLine();//flushing the input butter
		    			 if (continueBuy == 1) {
		    				 purchaseItem(m, k);//recursion
		    			 }//end if statement
		    		 }	
		    	 }//end for loop
		     }//end if else
		}
		catch(Exception e) {
			System.out.println("You encountered an error. \n" + e);
			k.nextLine();//flushing the input butter
		}
		 
	 }//end purchaseItem method
	
	/** Checks if users has available rental space, searches for item based on user's inputed item name, selects item 
	 * based on user's inputed item number, runs processRentItem(), removes item from Manager's inventory.
	@param m The Manager object.
	@param k The Scanner keyboard object. */
	public void rentItem(StoreManager m, Scanner k) {
		 
		 try {
			 List<Item> itemOptions = new ArrayList<>();
			 BagInterface<Item> customerBag = getBag();
			 BagInterface<Item> bagReplace = new CustomerBag<Item>();
			 int rentNum = 0;
			 while (!customerBag.isEmpty()) {
				 Item bagItem = customerBag.remove();
				 if (bagItem.isRental()) {
					 rentNum++;
				 }//end if statement
				 bagReplace.add(bagItem);
			 }//end while loop
			 while (!bagReplace.isEmpty()) {
				 Item replaceItem = bagReplace.remove();
				 customerBag.add(replaceItem);
			 }//end while loop
			 if (rentNum > 2) {
				 System.out.println("\nYou have reached your maximum allowed rentals. Please return your old rental items first!");
			 }else {
				 System.out.println("\nYou currently have "  + rentNum + " rentals.");
				 System.out.println("\nYou have $" + getWallet() + " to spend.");
				 System.out.println("\nPlease enter the name of the item you wish to rent: ");
				 String item = k.next();
			     k.nextLine();//Flushing the input buffer.
			     
			     for (int i = 0; i<m.getInventory().size(); i++) {
					if (!(m.getInventory().get(i).getClass().equals(Snack.class)) && (m.getInventory().get(i).getTitle().toLowerCase().contains(item.toLowerCase()))) {
						itemOptions.add(m.getInventory().get(i));
					}//end if statement
				 }//end for loop
			     System.out.println("Here are the items that matched your search: \n");
			     for (int i = 0; i<itemOptions.size(); i++) {
						System.out.println((i + 1) + ". " + itemOptions.get(i) + "\n");
					}//end for loop
			     System.out.println("Please enter the number next to the item you wish to rent or press 0 to cancel rental:");
			     int itemOption = k.nextInt() - 1;
			     k.nextLine();//Flushing the input buffer.
			     if (itemOption == -1) {
			    	 System.out.println("Rental Cancelled. Here are your current items:");
					 displayBag();
			     }else {
			    	 for (int j = 0; j<itemOptions.size(); j++) {
			    		 if (itemOption == itemOptions.indexOf(itemOptions.get(j))) {
			    			 //buy item, add to bag, pay Store Manager
			    			 Item purchasedItem = itemOptions.get(j);
			    			 double itemPrice = processRentItem(purchasedItem);
			    			 System.out.println("Renting: " + purchasedItem.getTitle() + " for $" + itemPrice);
			    			 m.setMoney(m.getCash() + itemPrice);
			    			 //remove item from Store Manager inventory
			    			 for (int l = 0; l<m.getInventory().size(); l++) {
			    				 if (m.getInventory().get(l).equals(purchasedItem)) {
			    					 m.getInventory().remove(purchasedItem);
			    				 }
			    			 }//end for loop
			    			 System.out.println("Rental successful! Here are your current items: ");
			    			 displayBag();
			    			 System.out.println("You have: $" + getWallet() + " left to spend. \nWould you like to rent another item? Press 1 for YES, any other number for NO:");
			    			 int continueBuy = k.nextInt();
			    			 k.nextLine();//flushing the input butter
			    			 if (continueBuy == 1) {
			    				 rentItem(m, k);//recursion
			    			 }//end if statement
			    		 }//end if else
			    	 }//end for loop
			     }//end if else 
			 }//end if else rentNum
		 }
		 catch(Exception e) {
				System.out.println("You encountered an error. \n" + e);
				k.nextLine();//flushing the input butter
			}
		 
	 }//end rentItem method
	
	/** Displays all rental items in customer's bag, selects items based on customer's inputed number, removed item from
	 * customer's bag, sets item to NOT rental, adds item to Manager inventory.
	@param m The Manager object.
	@param k The Scanner keyboard object. */
	public void returnRental(StoreManager m, Scanner k) {
			try {
				 BagInterface<Item> customerBag = getBag();
				 BagInterface<Item> rentalBag = new CustomerBag<Item>();
				 BagInterface<Item> notRentalBag = new CustomerBag<Item>();
				 while (!customerBag.isEmpty()) {
					 Item bagItem = customerBag.remove();
					 if (bagItem.isRental()) {
						 rentalBag.add(bagItem);
					 }else{
						 notRentalBag.add(bagItem);
					 }//end if else statement
				 }//end while loop
				 System.out.println("Here are you current rental items. Please enter the name of the item you wish to return:");
				 displayBag(rentalBag);
				 String returnItem = k.nextLine();
				 while (!rentalBag.isEmpty()) {
					 Item rentBagItem = rentalBag.remove();
					 String itemName = rentBagItem.getTitle();
					 if (returnItem.toLowerCase().equals(itemName.toLowerCase())) {
						 rentBagItem.setRental(false);
						 m.getInventory().add(rentBagItem);
						 System.out.println("Rental return successful!");
					 }else {
						 notRentalBag.add(rentBagItem);
					 }//end if else statement
				 }//end while loop
				 while (!notRentalBag.isEmpty()) {
					 Item replaceItem = notRentalBag.remove();
					 customerBag.add(replaceItem);
				 }//end while loop
			}
			catch(Exception e) {
				System.out.println("You encountered an error. \n" + e);
				k.nextLine();//flushing the input butter
			}
			 
		 }//end returnRental method
		
	
}//end class Customer
