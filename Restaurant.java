// This is the subclass to the Customer superclass
import java.util.List;

public class Restaurant {
	// Attributes
	String restName, restNum, restCity, driver;
	double totalAmount;
	List<MealOrder> mealOrder;
	
	// This is the object constructor method
	Restaurant (String restName, String restNum, String restCity, List<MealOrder> mealOrder, String driver){
		
		this.restName = restName;
		this.restNum = restNum;
		this.restCity = restCity;
		this.mealOrder = mealOrder;
		this.driver = driver;
		this.totalAmount = findTotal();
		
	}
	
	// This method finds and returns the total of all meals ordered based on price and number meals ordered
	private double findTotal() {
		double total = 0;
		
		// this for loop iterates through the MealOrder object list calculates the subtotal and adds it to total
		for (int i = 0; i < mealOrder.size(); ++ i) {
			total += mealOrder.get(i).mealPrice * mealOrder.get(i).numMeal;
		}
		return total;
	}
	
}
