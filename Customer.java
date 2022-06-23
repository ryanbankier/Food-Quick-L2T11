import java.util.Formatter;
import java.util.List;

public class Customer extends Restaurant {
	
	// Attributes
	String cusName, cusNum, cusAddress, cusCity, cusEmail, orderNum, cusInstr;
	
	// This is the object constructor method
	Customer(String restName, String restNum, String restCity, String orderNum, String cusName, String cusNum, String cusAddress, String cusCity, String cusEmail,List<MealOrder> mealOrder, String cusInstr, String driver){
		super(restName, restNum, restCity, mealOrder, driver);
		this.orderNum = orderNum;
		this.cusName = cusName;
		this.cusNum = cusNum;
		this.cusAddress = cusAddress;
		this.cusCity = cusCity;
		this.cusEmail = cusEmail;
		this.cusInstr = cusInstr;
	}

// This method saves an invoice in a text file with data received from the Main class. It also prints out a message to the user
public void invoice() {
	
		// file name constructed using the order number
		String FileName = "invoice"+orderNum + ".txt";
		// message is output to the user inputing order
		System.out.println("------ ORDER COMPLETE --------");
		System.out.println("Order Number: "+ orderNum);
		System.out.println("Invoice Number: "+ "invoice"+ orderNum);
		
		// Thi code creates a invoice text file with all relevant info
		try {
			Formatter inv = new Formatter(FileName);
			inv.format("%s", "Order Number: "+ orderNum+"\r\n");
			inv.format("%s","Customer: "+ cusName+"\r\n");
			inv.format("%s","Email: "+ cusEmail+"\r\n");
			inv.format("%s","Phone Number: "+"+27 "+ cusNum+"\r\n");
			inv.format("%s","Location: "+ cusCity+"\r\n");
			inv.format("%s","\r\n");
			inv.format("%s","You have ordered the following from "+ restName+" in "+restCity+"\r\n");
			inv.format("%s","\r\n");
			for (int i = 0; i < mealOrder.size(); ++ i) {
				inv.format("%s",mealOrder.get(i).numMeal+"x " + mealOrder.get(i).meal + "(R"+ mealOrder.get(i).mealPrice +	")"+"\r\n");
			}
			inv.format("%s","\r\n");
			inv.format("%s","Special Instructions: "+ cusInstr + "\r\n");
			inv.format("%s","\r\n");
			inv.format("%s","Total: R"+ totalAmount + "\r\n");
			inv.format("%s","\r\n");
			inv.format("%s",driver + "is nearest to the restaurant and so he will be delivering your order to you at:\r\n");
			inv.format("%s","\r\n");
			inv.format("%s",cusAddress+"\r\n");
			inv.format("%s","\r\n");
			inv.format("%s","If you need to contact the restaurant, their number is " +"+27 "+restNum);
			
			inv.close();
			}
			catch (Exception e) {
				System.out.println("An Error occured");
				System.out.println("");
				System.out.println(e.getMessage());
				System.out.println("");
				e.printStackTrace();
			}
	}
	
}
