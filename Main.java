
// This program generates an invoice for Quick Food with captures customer, restaurant and driver details.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {

		// While loop runs runProgram() until it returns false to exit
		while (runProgram()) {
			
		}

	}
	// this Method runs until all order information for Customer Class and Restaurant
	public static boolean runProgram() {
		
		// Main Variable declaration
		String orderNum = "";
		String cusName, cusNum, cusAddress, cusCity, cusEmail, cusInstr, restName, restNum, restCity, driver;
		List<MealOrder> order = new ArrayList<MealOrder>();
		List<Drivers> drivers = new ArrayList<Drivers>();
		JFrame frame = new JFrame("JoptionPane Test");
		String[] cityToChoose = { 
				"Cape Town", "Durban", "Johannesburg", "Potchefstroom", 
				"Springbok", "Bloemfontein","Port Elizabeth", "Witbank" 
				};
		String[] choice = { "YES", "NO", "EXIT" };
		String[] orderChoice = { "YES", "NO" };
		cusEmail = "";
		cusName = "";
		cusCity = "";
		restCity = "";
		cusAddress = "";
		cusNum = "";
		restName = "";
		restNum = "";
		cusInstr = "";
		String newCustomer = "";
		
		boolean newOrd = true;
		boolean isCity = false;
		boolean isEmail = false;

		// Dropdown menu input for new order "YES", "NO", "EXIT"
		newCustomer = (String) JOptionPane.showInputDialog(null, "New Order:", null, JOptionPane.QUESTION_MESSAGE,
				null, choice, null);

		// if statements runs code based on newCustomer value
		if (newCustomer.equals("EXIT")) {
			return false;
		}
		// if "YES" selected then input prompts run to capture Customer and Restaurant
		// details
		else if (newCustomer.equals("YES")) {
			
			// This method generates a random 4 digit string
			orderNum = randNum();
			cusName = JOptionPane.showInputDialog("Customer Name:");
			// This variable uses a method that captures a number input and checks the length is correct
			cusNum = numberCheck();
			cusAddress = JOptionPane.showInputDialog("Customer Address:").toLowerCase();
			// This input uses a list of cities in a dropdown menu
			cusCity = (String) JOptionPane.showInputDialog(null, "Select Customer City", null,
					JOptionPane.QUESTION_MESSAGE, null, cityToChoose, null);

			// This while loop is used to make sure an @ is input for the Email address
			while (isEmail == false) {
				String cusEmailTemp = JOptionPane.showInputDialog("Customer Email address:").toLowerCase();
				if (cusEmailTemp.contains("@")) {
					cusEmail = cusEmailTemp;
					isEmail = true;
				} else {
					JOptionPane.showMessageDialog(frame, "Not a valid format");
				}
			}

			restName = JOptionPane.showInputDialog("Restaurant Name:");
			// This variable uses a method that captures a number input and checks the
			// length is correct
			restNum = numberCheck();

			// This while loop is used to check that the Restaurant is in the same city as
			// the Customer
			while (isCity == false) {
				String restCityTemp = (String) JOptionPane.showInputDialog(null, "Select Restaurant City", null,
						JOptionPane.QUESTION_MESSAGE, null, cityToChoose, null);

				if (restCityTemp.equals(cusCity)) {
					restCity = restCityTemp;
					isCity = true;
				} else {
					JOptionPane.showMessageDialog(frame, "Restaurant is not in Customers City: " + cusCity);
				}
			}

			// This while loop is used to check if a meal order has been placed for the
			// customer
			while (newOrd == true) {

				String meal = "No Meal Ordered";
				double mAmount = 0;
				int numMeal = 0;

				String ordComplete = (String) JOptionPane.showInputDialog(null, "Order complete?", null,
						JOptionPane.QUESTION_MESSAGE, null, orderChoice, null);

				// if statement captures the meal order and creates an object for each menu item
				// ordered
				if (ordComplete.equals("NO")) {
					meal = JOptionPane.showInputDialog("Meal:").toLowerCase();
					mAmount = Double.parseDouble(JOptionPane.showInputDialog("Meal Price (R):"));
					numMeal = Integer.parseInt(JOptionPane.showInputDialog("Number of meals"));
					// creates a new object "ord" in the MealOrder class
					MealOrder ord = new MealOrder(meal, mAmount, numMeal);
					// adds the object to an ArrayList<MealOrder order
					order.add(ord);
				}

				else if (ordComplete.equals("YES")) {
					// if the order list is empty then display message
					if (order.isEmpty()) {
						JOptionPane.showMessageDialog(frame, meal);
					}
					// if not empty end loop by changing loop condition to false
					else {
						newOrd = false;
					}
				}

			}
			
			cusInstr = JOptionPane.showInputDialog("Special Instructions:").toLowerCase();

			// This code reads the drivers.txt file and finds the correct driver based on
			// city and number of jobs
			try {
				String filePath = "driver-info.txt";
				File driverF = new File(filePath);
				Scanner sc = new Scanner(driverF);
				String driverLine = "";

				// while loop reads each line of text file
				while (sc.hasNextLine()) {
					// every loop a new line is saved in a variable
					driverLine = sc.nextLine();
					// variable is changed to a string array
					String strArr[] = driverLine.split(",");
					// drivers details are saved in variables
					String dCity = strArr[1].strip();
					String dName = strArr[0].strip();
					int dJob = Integer.parseInt(strArr[2].strip());
					// if the drivers city matches the customer city then create a driver object
					// with name and number of jobs
					if (dCity.equals(cusCity)) {
						Drivers d = new Drivers(dName, dJob);
						// new driver object is added to a Driver ArrayList
						drivers.add(d);
					}
				}
				sc.close();
			}

			catch (FileNotFoundException e) {
				System.out.println("Error: File does not exist!");
				System.out.println("");
				System.out.println(e.getMessage());
				System.out.println("");
				e.printStackTrace();
			}

			// This for loop arranges the Driver arrayList by number of jobs with the
			// smallest at the top
			for (int i = 0; i < drivers.size() - 1; i++) {
				if (drivers.get(i).dJobs > drivers.get(i + 1).dJobs) {
					Drivers temp = new Drivers(drivers.get(i).dName, drivers.get(i).dJobs);
					drivers.set(i, drivers.get(i + 1));
					drivers.set(i + 1, temp);
					i = -1;
				}
			}

			// The driver at index 0 in the Driver arrayList is selected to place in the
			// invoice
			driver = drivers.get(0).dName;

			// New Customer object is created based on the variables captured
			Customer newCust = new Customer(restName, restNum, restCity, orderNum, cusName, cusNum, cusAddress,
					cusCity, cusEmail, order, cusInstr, driver);
			// The method in customer class is called to create the invoice
			newCust.invoice();

		}
		return true;
		
	}
	

	// This method generates a random number and returns a string
	public static String randNum() {
		Random rand = new Random();
		int number = rand.nextInt(9999);
		return String.format("%04d", number);
	}

	// This method check if a string is a number
	public static boolean isNumeric(String str) {
		return str != null && str.matches("[-+]?\\d*\\.?\\d+");
	}

	// This method captures a phone number and checks it length
	public static String numberCheck() {
		String reNum = "";
		JFrame frame = new JFrame("Number check");
		boolean isNum = false;
		while (isNum == false) {
			String tempNum = JOptionPane.showInputDialog("Phone Number:");
			if (isNumeric(tempNum)) {
				if (tempNum.charAt(0) == '0') {
					if (tempNum.length() == 10) {
						reNum = tempNum;
						isNum = true;
					} else if (tempNum.length() < 10) {
						JOptionPane.showMessageDialog(frame, "Phone Number to Short");
					} else {
						JOptionPane.showMessageDialog(frame, "Phone Number to long");
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "Number must start with '0'");
				}
				
			} else {
				JOptionPane.showMessageDialog(frame, "Not vaild input");
			}

		}
		return reNum;

	}
}
