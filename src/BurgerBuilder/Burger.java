package BurgerBuilder;

public class Burger {
	private static final int PATTY_COUNT_MAX = 3;
	private static final int PATTY_COUNT_MIN = 1;
	
	private int myPattyCount;
	private boolean myIsBaronBurger;
	private final MyStack myBottomStack;
	
	protected Burger(final boolean theWorks) {
		myPattyCount = 1; // default patty count
		myIsBaronBurger = theWorks;
		myBottomStack = new MyStack();
		buildBurger(); 
	}
	
	private void buildBurger() {
		
	}
	
	protected void changePatties(final String thePattyType) {
		
	}
	
	protected void addPatty() {
		if (myPattyCount < PATTY_COUNT_MAX) {
			myPattyCount++;
		}
	}
	
	protected void removePatty() {
		if (myPattyCount > PATTY_COUNT_MIN) {
			myPattyCount--;
		}
	}
	
	protected void addCategory(final String theType) {
		
	}
	
	protected void removeCategory(final String theType) {
		
	}
	
	protected void addIngredient(final String theType) {
		
	}
	
	protected void removeIngredient(final String theType) {
		
	}
	
	public String toString() {
		return new String();
	}
}
