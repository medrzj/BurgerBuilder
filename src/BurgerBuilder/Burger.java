package BurgerBuilder;

/**
 * Constructs the burger with the correct order of ingredients. 
 * 
 * @author Charlie Grumer, Jessica Medrzycki
 * @version January 22, 2018
 *
 */
public class Burger {
	private static final int PATTY_COUNT_MAX = 3;
	private static final int PATTY_COUNT_MIN = 1;
	
	private int myPattyCount;
	private boolean myIsBaronBurger;
	private final MyStack<String> myBottomStack; // top is cheese (or first patty)
	private final MyStack<String> myTopStack; // top is additional patties (if applicable)
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
			myBottomStack.push("Cheddar");
			myBottomStack.push("Mozzarella");
			myBottomStack.push("Pepperjack");
			
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
			for (int i = 0; i < myPattyCount - 1; i++) {
				myTopStack.push(thePattyType);
			}
		} 
		final MyStack<String> temp = new MyStack<>();
		// remove and store possible cheeses
		while (!myBottomStack.peek().equals(oldPatty)) {
			temp.push(myBottomStack.pop());
		}
		// remove old patty
		myBottomStack.pop();
		// add new patty
		myBottomStack.push(thePattyType);
		// if cheeses were removed put them back
		if (!temp.isEmpty()) {
			refillStack(myBottomStack, temp);
		}
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
				refillStack(myTopStack, temp);
				
				current = myBottomStack.peek();
				//loop to get to the bottom bun
				while(!current.equals("Bun")) {
					temp.push(myBottomStack.pop());
					current = myBottomStack.peek();
				}
				myBottomStack.push("Ketchup");
				myBottomStack.push("Mustard");
				
				//move the ingredients back on the stack
				refillStack(myBottomStack, temp);
				
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
				refillStack(myTopStack, temp);
			
			
				current = myBottomStack.peek();
				//loop to get to the bottom bun
				while(!current.equals("Bun") && !current.equals("Ketchup") 
						&& !current.equals("Mustard") ) {
					temp.push(myBottomStack.pop());
					current = myBottomStack.peek();
				}
				myBottomStack.push("Mushrooms");
			
				//move the ingredients back on the stack
				refillStack(myBottomStack, temp);
			
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
				refillStack(myBottomStack, temp);
		
				while(!myTopStack.isEmpty()) {
					current = myTopStack.peek();
					if (current.equals("Baron-Sauce") || current.equals("Mayonnaise")) {
						myTopStack.pop();
					} else {
						temp.push(myTopStack.pop());
					}
				}
				refillStack(myTopStack, temp);
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
				refillStack(myBottomStack, temp);
		
				while(!myTopStack.isEmpty()) {
					current = myTopStack.peek();
					if (current.equals("Pickle") || current.equals("Lettuce") 
						|| current.equals("Tomato") || current.equals("Onions")) {
						
						myTopStack.pop();
					} else {
						temp.push(myTopStack.pop());
					}
				}
				refillStack(myTopStack, temp);

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
				refillStack(myTopStack, temp);

				break;
				
			case("Mustard"):
				current = myBottomStack.peek();
				while(!current.equals("Bun") && !current.equals("Ketchup")) {
					temp.push(myBottomStack.pop());
				}
				myBottomStack.push("Mustard");
				refillStack(myTopStack, temp);

				
				break;
			case("Mushrooms"):
				
				current = myBottomStack.peek();
				while(!current.equals("Bun") && !current.equals("Ketchup")
						&& !current.equals("Mustard")) {
					temp.push(myBottomStack.pop());
				}
				myBottomStack.push("Mushrooms");
				refillStack(myBottomStack, temp);
				break;
			case("Cheddar"):
				current = myBottomStack.peek();
				while(!current.equals(myPattyType) && !current.equals("Mushrooms")
						&& !current.equals("Mustard") && !current.equals("Ketchup")
						&& !current.equals("Bun")) {
					temp.push(myBottomStack.pop());
				}
				myBottomStack.push("Mozarella");
				refillStack(myBottomStack, temp);
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
				refillStack(myBottomStack, temp);
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
					refillStack(myTopStack, temp);

				}
				break;
			case("Tomato"):
				current = myTopStack.peek();
				while(!current.equals("Bun") && !current.equals("Mayonnaise")
						&& !current.equals("Baron-Sauce") && !current.equals("Lettuce")) {
					temp.push(myTopStack.pop());
				}
				myTopStack.push("Tomato");
				refillStack(myTopStack, temp);

				break;
			case("Lettuce"):
				current = myTopStack.peek();
				while(!current.equals("Bun") && !current.equals("Mayonnaise")
						&& !current.equals("Baron-Sauce")) {
					temp.push(myTopStack.pop());
				}
				myTopStack.push("Lettuce");
				refillStack(myTopStack, temp);

				break;
			case("Baron-Sauce"):
				current = myTopStack.peek();
				while(!current.equals("Bun") && !current.equals("Mayonnaise")) {
					temp.push(myTopStack.pop());
				}
				myTopStack.push("Baron-Sauce");
				refillStack(myTopStack, temp);

				break;
				
			case("Mayonnaise"):
				current = myTopStack.peek();
				while(!current.equals("Bun")) {
					temp.push(myTopStack.pop());
				}
				myTopStack.push("Mayonnaise");
				refillStack(myTopStack, temp);

				break;
			
			case("Pickle"):
				while(!myTopStack.isEmpty()) {
					temp.push(myTopStack.pop());
				}
				myTopStack.push("Pickle");
				refillStack(myTopStack, temp);

				break;
			default:
				break;
		}
	}
	
	/**
	 * Removes the ingredient from the stack.
	 * @param theType	the ingredient to remove.
	 */
	protected void removeIngredient(final String theType) {
		String current;
		final MyStack<String> temp = new MyStack<>();

		switch (theType) {
		
			case("Ketchup"):
				current = myBottomStack.peek();
				while(!current.equals("Ketchup") && !current.equals("Bun")) {
					temp.push(myBottomStack.pop());
				}
				if(current.equals("Ketchup")) {
					myBottomStack.pop();
				}
				refillStack(myBottomStack, temp);
				break;
				
			case("Mustard"):
				current = myBottomStack.peek();
				while(!current.equals("Ketchup") && !current.equals("Bun") && !current.equals("Mustard")) {
					temp.push(myBottomStack.pop());
				}
				if(!current.equals("Mustard")) {
					myBottomStack.pop();
				}
				refillStack(myBottomStack, temp);
				break;
				
			case("Mushrooms"):
				current = myBottomStack.peek();
				while(!current.equals("Ketchup") && !current.equals("Bun") && !current.equals("Mustard") && !current.equals("Mushrooms")) {
					temp.push(myBottomStack.pop());
				}
				if(!current.equals("Mushrooms")) {
					myBottomStack.pop();
				}
				refillStack(myBottomStack, temp);
				break;
				
			case("Cheddar"):
				current = myBottomStack.peek();
				// There is ALWAYS a patty of some sort
				while(!current.equals("Cheddar") && !current.equals(myPattyType)) {
					temp.push(myBottomStack.pop());
				}
				if(!current.equals("Cheddar")) {
					myBottomStack.pop();
				}
				refillStack(myBottomStack, temp);
				break;
				
			case("Mozarella"):
				current = myBottomStack.peek();
				// There is ALWAYS a patty of some sort -- no need to check beyond
				while(!current.equals("Mozarella") && !current.equals("Cheddar") && !current.equals(myPattyType)) {
					temp.push(myBottomStack.pop());
				}
				if(!current.equals("Mozarella")) {
					myBottomStack.pop();
				}
				refillStack(myBottomStack, temp);
				break;
				
			case("Pepperjack"):
				current = myBottomStack.peek();
				// There is ALWAYS a patty of some sort -- no need to check beyond
				while(!current.equals("Pepperjack") && !current.equals("Mozarella") && !current.equals("Cheddar") && !current.equals(myPattyType)) {
					temp.push(myBottomStack.pop());
				}
				if(!current.equals("Pepperjack")) {
					myBottomStack.pop();
				}
				refillStack(myBottomStack, temp);
				break;
				
			case("Onions"):
				current = myTopStack.peek();
				while(!current.equals("Bun") && !current.equals("Mayonnaise") && !current.equals("Baron-Sauce") && !current.equals("Lettuce") && !current.equals("Onions") && !current.equals("Tomato")) {
					temp.push(myTopStack.pop());
				}
				if(current.equals("Onions")) {
					myTopStack.pop();
				}
				refillStack(myTopStack, temp);
				break;
				
			case("Tomato"):
				current = myTopStack.peek();
				while(!current.equals("Bun") && !current.equals("Mayonnaise") && !current.equals("Baron-Sauce") && !current.equals("Lettuce") && !current.equals("Tomato")) {
					temp.push(myTopStack.pop());
				}
				if(current.equals("Tomato")) {
					myTopStack.pop();
				}
				refillStack(myTopStack, temp);
				break;
				
			case("Lettuce"):
				current = myTopStack.peek();
				while(!current.equals("Bun") && !current.equals("Mayonnaise") && !current.equals("Baron-Sauce") && !current.equals("Lettuce")) {
					temp.push(myTopStack.pop());
				}
				if(current.equals("Lettuce")) {
					myTopStack.pop();
				}
				refillStack(myTopStack, temp);
				break;
				
			case("Baron-Sauce"):
				current = myTopStack.peek();
				while(!current.equals("Bun") && !current.equals("Mayonnaise") && !current.equals("Baron-Sauce")) {
					temp.push(myTopStack.pop());
				}
				if(current.equals("Baron-Sauce")) {
					myTopStack.pop();
				}
				refillStack(myTopStack, temp);
				break;
				
			case("Mayonnaise"):
				current = myTopStack.peek();
				while(!current.equals("Bun") && !current.equals("Mayonnaise")) {
					temp.push(myTopStack.pop());
				}
				if(current.equals("Mayonnaise")) {
					myTopStack.pop();
				}
				refillStack(myTopStack, temp);
				break;

			case("Pickle"): 			
				current = myTopStack.peek();
				if(current.equals("Pickle")) {
					myTopStack.pop();
				}
				break;
			
			default:
				break;
		}
	}
	
	private void refillStack(final MyStack<String> theToFill, final MyStack<String> theFilled) {
		while(!theFilled.isEmpty()) {
			theToFill.push(theFilled.pop());
		}
	}
	/**
	 * Helper method that combines the two stacks to make the one
	 * burger stack for printing. 
	 * @return	the entire burger stack
	 */
	protected MyStack combineStacks() {
		MyStack tempTop = new MyStack<String>();
		MyStack tempBottom = new MyStack<String>();
		while (!myTopStack.isEmpty()) {
			myBottomStack.push(myTopStack.pop());
		}
		return myBottomStack;
	}
	
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(combineStacks().toString());
		sb.append("]");
		return sb.toString();
	}
}
