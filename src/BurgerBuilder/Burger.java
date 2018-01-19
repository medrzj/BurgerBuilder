package BurgerBuilder;

/**
 * Constructs the burger with the correct order of ingredients. 
 * 
 * @author Charlie Grumer, Jessica Medrzycki
 * @version January 20, 2018
 *
 */
public class Burger {
	private static final int PATTY_COUNT_MAX = 3;
	private static final int PATTY_COUNT_MIN = 1;
	
	private int myPattyCount;
	private boolean myIsBaronBurger;
	private final MyStack<String> myBottomStack; // top is cheese
	private final MyStack<String> myTopStack; // top is additional patties
	private String myPattyType;
	
	/**
	 * Initializes the burger fields. 
	 * @param theWorks	the baron burger status. 
	 */
	protected Burger(final boolean theWorks) {
		myPattyCount = 1; // default patty count
		myIsBaronBurger = theWorks;
		myBottomStack = new MyStack<>();
		myTopStack = new MyStack<>();
		myPattyType = "Beef";
		buildBurger(theWorks);
	}
	
	/**
	 * Builds the two stacks, the top holding everything from the top to 
	 * the veggies or extra patties, the bottom stack holding the bottom to 
	 * the cheese. (Cheese must go on the bottom patty!)
	 * 
	 * @param theWorks	the baron burger status.
	 */
	private void buildBurger(final boolean theWorks) {
		if (theWorks) {
			myBottomStack.push("Bun");
			myBottomStack.push("Ketchup");
			myBottomStack.push("Mustard");
			myBottomStack.push("Mushrooms");
			myBottomStack.push(myPattyType);
			myBottomStack.push("Pepperjack");
			myBottomStack.push("Mozzarella");
			myBottomStack.push("Cheddar");
			
			myTopStack.push("Pickle");
			myTopStack.push("Bun");
			myTopStack.push("Mayonnaise");
			myTopStack.push("Baron-Sauce");
			myTopStack.push("Lettuce");
			myTopStack.push("Tomato");
			myTopStack.push("Onions");

		} else {
			myBottomStack.push("Bun");
			myBottomStack.push(myPattyType);
			
			myTopStack.push("Bun");
		}
	}
	 
	/**
	 * Changes the patties in the burger to the specified patty type. 
	 * @param thePattyType	the type of patty to change in the stack.
	 */
	protected void changePatties(final String thePattyType) {
		//store the old patty type 
		final String oldPatty = myPattyType;
		
		//update current patty type
		myPattyType = thePattyType;
		
		
		if (myPattyCount > 1) {
			while(myTopStack.peek().equals(oldPatty)) {
				myTopStack.pop();
			}
			//can happen 1 or 2 times since max patty amount on
			// top stack is 2
			for (int i = 0; i < myPattyCount-1; i++) {
				myTopStack.push(thePattyType);
			}
		} 
		
		myBottomStack.pop();
		myBottomStack.push(thePattyType);
		
	}
	
	/**
	 * Adds one patty to the top of the top stack. 
	 */
	protected void addPatty() {
		if (myPattyCount < PATTY_COUNT_MAX) {
			myPattyCount++;
			myTopStack.push(myPattyType);
		}
	}
	
	/**
	 * Removes an additional patty from myTopStack, if it exists.
	 */
	protected void removePatty() {
		if (myPattyCount > PATTY_COUNT_MIN) {
			myPattyCount--;
			myTopStack.pop(); // remove top patty
		}
	}
	
	/**
	 * Adds a category to the stack. 
	 * @param theType	the type of category to add.
	 */
	protected void addCategory(final String theType) {
		String current;
		final MyStack<String> temp = new MyStack<>();

		switch (theType) {
		
			case("Cheese"):
				myBottomStack.push("Cheddar");
				myBottomStack.push("Mozarella");
				myBottomStack.push("Pepperjack");
				break;
			case("Sauce"):
				current = myTopStack.peek();
			
				//loop to get to the top bun
				while(!current.equals("Bun")) {
					temp.push(myTopStack.pop());
					current = myTopStack.peek();
				}
				myTopStack.push("Mayonnaise");
				myTopStack.push("Baron-Sauce");
				
				//move the ingredients back on the stack
				while (!temp.isEmpty()) {
					myTopStack.push(temp.pop());
				}
				
				
				current = myBottomStack.peek();
				//loop to get to the bottom bun
				while(!current.equals("Bun")) {
					temp.push(myBottomStack.pop());
					current = myBottomStack.peek();
				}
				myBottomStack.push("Ketchup");
				myBottomStack.push("Mustard");
				
				//move the ingredients back on the stack
				while (!temp.isEmpty()) {
					myBottomStack.push(temp.pop());
				}
				
				break;
			
			case("Veggies"):
				
			
				while(!myTopStack.isEmpty()) {
					temp.push(myTopStack.pop());
				}
				myTopStack.push("Pickle");
				current = temp.peek();
				
				//IS THIS LOOP CORRECT?
				//loop to get to the top bun, mayonnaise or the baron sauce. 
				while(current.equals("Bun") || current.equals("Mayonnaise") 
						|| current.equals("Baron-Sauce")) {
					myTopStack.push(temp.pop());
					current = temp.peek();
				}
				myTopStack.push("Lettuce");
				myTopStack.push("Tomato");
				myTopStack.push("Onions");
				
				//move the ingredients back on the stack
				while (!temp.isEmpty()) {
					myTopStack.push(temp.pop());
				}
			
			
				current = myBottomStack.peek();
				//loop to get to the bottom bun
				while(!current.equals("Bun") && !current.equals("Ketchup") 
						&& !current.equals("Mustard") ) {
					temp.push(myBottomStack.pop());
					current = myBottomStack.peek();
				}
				myBottomStack.push("Mushrooms");
			
				//move the ingredients back on the stack
				while (!temp.isEmpty()) {
				myBottomStack.push(temp.pop());
				}
			
				break;
			
			default:
				break;
		}
	}

	/**
	 * Removes an entire category from both the top and bottom 
	 * stacks for the burger.
	 * @param theType	the type of category to remove.
	 */
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
					if (current.equals("Pickle") || current.equals("Lettuce") 
						|| current.equals("Tomato") || current.equals("Onions")) {
						
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

	/**
	 * Adds an ingredient to the stack.
	 * Doesn't add in any extra buns or patties.
	 * @param theType	the ingredient to add.
	 */
	protected void addIngredient(final String theType) {
		String current;
		final MyStack<String> temp = new MyStack<>();

		switch (theType) {
		
			case("Ketchup"):
				current = myBottomStack.peek();
				while(!current.equals("Bun")) {
					temp.push(myBottomStack.pop());
				}
				myBottomStack.push("Ketchup");
				while (!temp.isEmpty()) {
					myTopStack.push(temp.pop());
				}
				break;
				
			case("Mustard"):
				current = myBottomStack.peek();
				while(!current.equals("Bun") && !current.equals("Ketchup")) {
					temp.push(myBottomStack.pop());
				}
				myBottomStack.push("Mustard");
				while (!temp.isEmpty()) {
					myTopStack.push(temp.pop());
				}
				
				break;
			case("Mushrooms"):
				
				current = myBottomStack.peek();
				while(!current.equals("Bun") && !current.equals("Ketchup")
						&& !current.equals("Mustard")) {
					temp.push(myBottomStack.pop());
				}
				myBottomStack.push("Mushrooms");
				while (!temp.isEmpty()) {
					myTopStack.push(temp.pop());
				}
				break;
			case("Cheddar"):
				current = myBottomStack.peek();
				while(!current.equals(myPattyType) && !current.equals("Mushrooms")
						&& !current.equals("Mustard") && !current.equals("Ketchup")
						&& !current.equals("Bun")) {
					temp.push(myBottomStack.pop());
				}
				myBottomStack.push("Mozarella");
				while (!temp.isEmpty()) {
					myBottomStack.push(temp.pop());
				}
				break;
				
			case("Mozarella"):
				current = myBottomStack.peek();
				while(!current.equals("Cheddar") && !current.equals(myPattyType)
						&& !current.equals("Mushrooms")
						&& !current.equals("Mustard") && !current.equals("Ketchup")
						&& !current.equals("Bun")) {
					temp.push(myBottomStack.pop());
				}
				myBottomStack.push("Mozarella");
				while (!temp.isEmpty()) {
					myBottomStack.push(temp.pop());
				}
				break;
				
			case("Pepperjack"):
				myBottomStack.push(theType);
				break;
				
			case("Onions"):
				if (myPattyCount == 1) {
					myTopStack.push("Onions");
				} else {
					current = myTopStack.peek();
					while(!current.equals(myPattyType) && !current.equals("Tomato")
							&& !current.equals("Lettuce")
							&& !current.equals("Baron-Sauce") && !current.equals("Mayonnaise")
							&& !current.equals("Bun")) {
						temp.push(myTopStack.pop());
					}
					myTopStack.push("Onions");
					while (!temp.isEmpty()) {
						myTopStack.push(temp.pop());
					}
				}
				break;
			case("Tomato"):
				current = myTopStack.peek();
				while(!current.equals("Bun") && !current.equals("Mayonnaise")
						&& !current.equals("Baron-Sauce") && !current.equals("Lettuce")) {
					temp.push(myTopStack.pop());
				}
				myTopStack.push("Tomato");
				while (!temp.isEmpty()) {
					myTopStack.push(temp.pop());
				}
				break;
			case("Lettuce"):
				current = myTopStack.peek();
				while(!current.equals("Bun") && !current.equals("Mayonnaise")
						&& !current.equals("Baron-Sauce")) {
					temp.push(myTopStack.pop());
				}
				myTopStack.push("Lettuce");
				while (!temp.isEmpty()) {
					myTopStack.push(temp.pop());
				}
				break;
			case("Baron-Sauce"):
				current = myTopStack.peek();
				while(!current.equals("Bun") && !current.equals("Mayonnaise")) {
					temp.push(myTopStack.pop());
				}
				myTopStack.push("Baron-Sauce");
				while (!temp.isEmpty()) {
					myTopStack.push(temp.pop());
				}
				break;
				
			case("Mayonnaise"):
				current = myTopStack.peek();
				while(!current.equals("Bun")) {
					temp.push(myTopStack.pop());
				}
				myTopStack.push("Mayonnaise");
				while (!temp.isEmpty()) {
					myTopStack.push(temp.pop());
				}
				break;
			
			case("Pickle"):
				while(!myTopStack.isEmpty()) {
					temp.push(myTopStack.pop());
				}
				myTopStack.push("Pickle");
				while (!temp.isEmpty()) {
					myTopStack.push(temp.pop());
				}
				break;
			default:
				break;
		}
	}
	
	protected void removeIngredient(final String theType) {
		
	}
	
	public String toString() {
		return new String();
	}
}
