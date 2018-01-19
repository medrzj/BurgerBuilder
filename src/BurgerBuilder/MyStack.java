package BurgerBuilder;

/**
 * Our stack ADT that uses a linked list. 
 * 
 * @author Charlie Grumer, Jessica Medrzycki
 * @param <Type>	the generic type
 */
public class MyStack<Type> {
	
	private Node<Type> myFirstNode;
	private int myStackSize;
	
	
	/**
	 * Constructs a stack.
	 */
	protected MyStack() {
		myFirstNode = null;
		myStackSize = 0;
	}
	
	/**
	 * Returns if the stack is empty or not.
	 * @return	the state of the stack.
	 */
	protected boolean isEmpty() {
		return myFirstNode == null;
	}
	
	/**
	 * Adds the element to the top of the stack.
	 * @param item	the item being added. 
	 */
	protected void push(final Type item) {
		myFirstNode = new Node<Type>(item, myFirstNode);
		myStackSize++; // increases saved stack size by 1

	}
	
	/**
	 * Removes the top element on the stack. 
	 * @return	the element just removed.
	 */
	protected Type pop() {
		final Type result = myFirstNode.getNodeData(); // saves first node data
		myFirstNode = myFirstNode.getNextNode(); // sets myFirstNode to point at the next node
		myStackSize--; // decreases saved stack size by 1
		return result; // returns saved first node data
	}
	
	/**
	 * View the top element in the stack.
	 * @return	the top element.
	 */
	protected Type peek() {
		return myFirstNode.getNodeData();
	}
	
	/**
	 * Returns the size of the stack.
	 * @return	the stack size.
	 */
	protected int size() {
		return myStackSize;
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		Node<Type> node = myFirstNode;
		while (node != null) {
			sb.append(node.toString());
			sb.append(", ");
			node = node.myNext;
		}
		// Math.max because substring(sb.length() - 1) would return a string of negative
		// length if the stack is empty
		return sb.substring(0, Math.max(sb.length() - 1, 0));
	}
	
	/**
	 * Inner class for creating the Node.
	 * @author Charlie Grumer, Jessica Medrzycki
	 * @param <NodeDataType>  the generic type
	 */
	private class Node<NodeDataType> {
		private final Node<NodeDataType> myNext;
		private final NodeDataType myData;
		
		/**
		 * Constructs node from next reference and data
		 * @param theData node data.
		 * @param theNextNode next node reference.
		 */
		private Node(final NodeDataType theData, final Node<NodeDataType> theNextNode) {
			myNext = theNextNode;
			myData = theData;
		}
		
		/**
		 * Constructs node from null next reference and param data.
		 * @param theData node data
		 */
		private Node(final NodeDataType theData) {
			this(theData, null);
		}
		
		/**
		 * Constructs node with null next reference and null data.
		 */
		private Node() {
			this(null, null);
		}
		
		/**
		 * Returns the next node. 
		 * @return
		 */
		protected Node<NodeDataType> getNextNode() {
			return myNext;
		}
		
		/**
		 * Returns the data in the Node.
		 */
		protected NodeDataType getNodeData() {
			return myData;
		}
		
		public String toString() {
			return myData.toString();
		}
	}
}
