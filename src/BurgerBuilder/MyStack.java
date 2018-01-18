package BurgerBuilder;

public class MyStack<StackDataType> {
	private Node<StackDataType> myFirstNode;
	private int myStackSize;
	
	protected MyStack() {
		myFirstNode = null;
		myStackSize = 0;
	}
	
	protected boolean isEmpty() {
		return myFirstNode == null;
	}
	
	protected void push(final StackDataType theData) {
		myFirstNode = new Node<StackDataType>(theData, myFirstNode);
		myStackSize++; // increases saved stack size by 1

	}
	
	protected StackDataType pop() {
		final StackDataType result = myFirstNode.getNodeData(); // saves first node data
		myFirstNode = myFirstNode.getNextNode(); // sets myFirstNode to point at the next node
		myStackSize--; // decreases saved stack size by 1
		return result; // returns saved first node data
	}
	
	protected StackDataType peek() {
		return myFirstNode.getNodeData();
	}
	
	protected int size() {
		return myStackSize;
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		Node<StackDataType> node = myFirstNode;
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
	 * @author Charlie Grumer
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
		
		protected Node<NodeDataType> getNextNode() {
			return myNext;
		}
		
		protected NodeDataType getNodeData() {
			return myData;
		}
		
		public String toString() {
			return myData.toString();
		}
	}
}
