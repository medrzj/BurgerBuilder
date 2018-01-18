package BurgerBuilder;

public class Burger {
	private static final int PATTY_COUNT_MAX = 3;
	private static final int PATTY_COUNT_MIN = 1;
	
	private int myPattyCount;
	private boolean myIsBaronBurger;
	private final MyStack<String> myBottomStack; // top is cheese
	private final MyStack<String> myTopStack; // top is additional patties
	private String myPattyType;
	
	protected Burger(final boolean theWorks) {
		myPattyCount = 1; // default patty count
		myIsBaronBurger = theWorks;
		myBottomStack = new MyStack<>();
		myTopStack = new MyStack<>();
		myPattyType = "Beef";
		buildBurger(theWorks);
	}
	
	private void buildBurger(final boolean theWorks) {
		if (theWorks) {
			myBottomStack.push("Bun");
			myBottomStack.push("Ketchup");
			myBottomStack.push("Mustard");
			myBottomStack.push("Mushrooms");
			myBottomStack.push(myPattyType);
			
			myTopStack.push("Pickle");
			myTopStack.push("Bun");
			myTopStack.push("Mayonnaise");
			myTopStack.push("Baron-Sauce");
			myTopStack.push("Lettuce");
			myTopStack.push("Tomato");
			myTopStack.push("Onions");
			myTopStack.push("Pepperjack");
			myTopStack.push("Mozzarella");
			myTopStack.push("Cheddar");
		} else {
			myBottomStack.push("Bun");
			myBottomStack.push(myPattyType);
			
			myTopStack.push("Bun");
		}
	}
	 
	protected void changePatties(final String thePattyType) {
		myPattyType = thePattyType;
		// switch old patties
	}
	
	protected void addPatty() {
		if (myPattyCount < PATTY_COUNT_MAX) {
			myPattyCount++;
			myTopStack.push(myPattyType);
		}
	}
	
	/*
	 * Removes an additional patty from myTopStack, if it exists.
	 */
	protected void removePatty() {
		if (myPattyCount > PATTY_COUNT_MIN) {
			myPattyCount--;
			myTopStack.pop(); // remove top patty
		}
	}
	
	protected void addCategory(final String theType) {
		
	}

	protected void removeCategory(final String theType) {
		String current;
		final MyStack<String> temp = new MyStack<>();

		switch (theType) {
		
			case("Cheese"):
				current = myBottomStack.peek();
				while(current.equals("Pepperjack") || current.equals("Mozzarella") || current.equals("Cheddar")) {
					myBottomStack.pop();
					current = myBottomStack.peek();
				}
				break;

			case("Sauce"):
				while(!myBottomStack.isEmpty()) {
					current = myBottomStack.peek();
					if (current.equals("Mustard") || current.equals("Ketchup")) {
						myBottomStack.pop();
					} else {
						temp.push(myBottomStack.pop());
					}
				}
				while (!temp.isEmpty()) {
					myBottomStack.push(temp.pop());
				}
		
				while(!myTopStack.isEmpty()) {
					current = myTopStack.peek();
					if (current.equals("Baron-Sauce") || current.equals("Mayonnaise")) {
						myTopStack.pop();
					} else {
						temp.push(myTopStack.pop());
					}
				}
				while (!temp.isEmpty()) {
					myTopStack.push(temp.pop());
				}
				break;
				
			case("Veggies"): 
				while(!myBottomStack.isEmpty()) {
					current = myBottomStack.peek();
					if (current.equals("Mushrooms")) {
						myBottomStack.pop();
					} else {
						temp.push(myBottomStack.pop());
					}
				}
				while (!temp.isEmpty()) {
					myBottomStack.push(temp.pop());
				}
		
				while(!myTopStack.isEmpty()) {
					current = myTopStack.peek();
					if (current.equals("Picles") || current.equals("Lettuce") 
						|| current.equals("Tomato") || current.equals("Onion")) {
						
						myTopStack.pop();
					} else {
						temp.push(myTopStack.pop());
					}
				}
				while (!temp.isEmpty()) {
					myTopStack.push(temp.pop());
				}
				break;

			default:
				break;
		}
	}

	protected void addIngredient(final String theType) {
		
	}
	
	protected void removeIngredient(final String theType) {
		
	}
	
	public String toString() {
		return new String();
	}
}
