package oop.ex4.data_structures;
/**
 * Class that represent a node in a tree.
 * 
 * @author Avichai
 *
 */
public class Node {
	private int value;
	private Node father;
	private Node leftSon;
	private Node rightSon;
	private int height;
	/**
	 * Constructor for a new Node.
	 * 
	 * @param value
	 *            the value this node holds.
	 * @param father
	 *            the father of this node.
	 * @param leftSon
	 *            the left son of this node.
	 * @param rightSon
	 *            the right son of this node.
	 * @param height
	 *            the height of this node.
	 */
	public Node(int value, Node father, Node leftSon, Node rightSon, int height) {
		this.value = value;
		this.father = father;
		this.leftSon = leftSon;
		this.rightSon = rightSon;
		this.height = height;
	}

	/**
	 * 
	 * @return the hight of this node.
	 */
	public int getHight() {
		return height;
	}

	/**
	 * change the height of this node.
	 * 
	 * @param hight
	 *            the new height
	 */
	public void setHight(int hight) {
		this.height = hight;
	}

	/**
	 * 
	 * @return the value of this node.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * change the value of this node.
	 * 
	 * @param value
	 *            the new value for this node.
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * 
	 * @return the father of this node.
	 */
	public Node getFather() {
		return father;
	}

	/**
	 * change the father of this node.
	 * 
	 * @param father
	 *            the new father for this node.
	 */
	public void setFather(Node father) {
		this.father = father;
	}

	/**
	 * 
	 * @return the left son of this node.
	 */
	public Node getLeftSon() {
		return leftSon;
	}

	/**
	 * change the left son of this node.
	 * 
	 * @param leftSon
	 *            the new left son.
	 */
	public void setLeftSon(Node leftSon) {
		this.leftSon = leftSon;
	}

	/**
	 * 
	 * @return the right son of this son.
	 */
	public Node getRightSon() {
		return rightSon;
	}

	/**
	 * change the right son of this node.
	 * 
	 * @param rightSon
	 *            the new right son.
	 */
	public void setRightSon(Node rightSon) {
		this.rightSon = rightSon;
	}
}
