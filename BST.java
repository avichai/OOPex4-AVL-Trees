package oop.ex4.data_structures;

import java.util.Iterator;

/**
 * a class that represent a binary search tree.
 * 
 * @author Avichai
 *
 */
public class BST implements Iterable<Integer> {

	private static final int NOT_FOUND = -1;
	private static final int HIGHT_OF_VERTEX_WITH_ONE_OR_TWO_SONS = 1;
	private static final int INITIAL_HIGHT_FOR_ROOT_OF_TREE = 0;
	private static final int INITIAL_NUM_ELEMET_IN_TREE = 0;
	private Node headTree;
	private int size;

	/**
	 * The default constructor.
	 */
	public BST() {
		this.headTree = null;
		this.size = INITIAL_NUM_ELEMET_IN_TREE;
	}

	/**
	 * A constructor that builds the tree by adding the elements in the input
	 * array one by one. If a value appears more than once in the list, only the
	 * first appearance is added.
	 * 
	 * @param data
	 *            the values to add to tree.
	 */
	public BST(int[] data) {
		if (data != null) {
			this.headTree = null;
			this.size = INITIAL_NUM_ELEMET_IN_TREE;
			for (int number : data) {
				add(number);
			}
		}
	}

	/**
	 * A copy constructor that creates a deep copy of the given BST. This means
	 * that for every node or any other internal object of the given tree, a
	 * new, identical object, is instantiated for the new tree (the internal
	 * object is not simply referenced from it). The new tree must contain all
	 * the values of the given tree, but not necessarily in the same structure.
	 * 
	 * @param bst
	 *            a binary search tree.
	 */
	public BST(BST bst) {
		if (bst != null) {
			this.headTree = null;
			this.size = INITIAL_NUM_ELEMET_IN_TREE;
			Iterator<Integer> iterator = bst.iterator();
			while (iterator.hasNext()) {
				int toAdd = iterator.next();
				add(toAdd);
			}
		}
	}

	/**
	 * Add a new node with the given key to the tree.
	 * 
	 * @param newValue
	 *            the value of the new node to add.
	 * @return true if the value to add is not already in the tree and it was
	 *         successfully added, false otherwise.
	 */
	public boolean add(int newValue) {
		if (this.headTree == null) {
			this.headTree = new Node(newValue, null, null, null,
					INITIAL_HIGHT_FOR_ROOT_OF_TREE);
			this.size++;
			return true;
		} else {
			return dealWithInsertionNotInRoot(newValue);
		}
	}

	/**
	 * deal with insertion not to the root of the tree.
	 * 
	 * @param newValue
	 *            the value of the new node to add.
	 * @return true if the value to add is not already in the tree and it was
	 *         successfully added, false otherwise.
	 */
	private boolean dealWithInsertionNotInRoot(int newValue) {
		Node head = this.headTree;
		while (true) {
			if (newValue == head.getValue()) {
				return false;
			} else if (newValue < head.getValue()) {
				if (head.getLeftSon() == null) {
					dealWithLeftSonOnInsertion(head, newValue);
					break;
				} else {
					head = head.getLeftSon();
				}
			} else {
				if (head.getRightSon() == null) {
					dealWithRightSonOnInsertion(head, newValue);
					break;
				} else {
					head = head.getRightSon();
				}
			}
		}
		this.size++;
		return true;
	}

	/**
	 * deal when inserting new value to the right son of a given node.
	 * 
	 * @param nodeToInsertTo
	 *            the node we wish to insert the new value to it's right son.
	 * @param newValue
	 *            the value of the new node to add.
	 */
	private void dealWithRightSonOnInsertion(Node nodeToInsertTo, int newValue) {
		if (nodeToInsertTo != null) {
			nodeToInsertTo.setRightSon(new Node(newValue, nodeToInsertTo, null,
					null, Constant.INITIAL_HIGHT_FOR_LEAF_IN_TREE));
			if (nodeToInsertTo.getLeftSon() == null) {
				nodeToInsertTo.setHight(HIGHT_OF_VERTEX_WITH_ONE_OR_TWO_SONS);
			}
			rotateAfterInsertionOrDeletion(nodeToInsertTo.getRightSon(),
					Constant.INSERT);
		}
	}

	/**
	 * deal when inserting new value to the left son of a given node.
	 * 
	 * @param nodeToInsertTo
	 *            the node we wish to insert the new value to it's left son.
	 * @param newValue
	 *            the value of the new node to add.
	 */
	private void dealWithLeftSonOnInsertion(Node nodeToInsertTo, int newValue) {
		if (nodeToInsertTo != null) {
			nodeToInsertTo.setLeftSon(new Node(newValue, nodeToInsertTo, null,
					null, Constant.INITIAL_HIGHT_FOR_LEAF_IN_TREE));
			if (nodeToInsertTo.getRightSon() == null) {
				nodeToInsertTo.setHight(HIGHT_OF_VERTEX_WITH_ONE_OR_TWO_SONS);
			}
			rotateAfterInsertionOrDeletion(nodeToInsertTo.getLeftSon(),
					Constant.INSERT);
		}
	}

	/*
	 * updates the heights fields in the nodes on the tree from a certain node
	 * up to a certain node.
	 * 
	 * @param node
	 *            the node we wish to start updating from.
	 * @param endNode
	 *            the node we wish to end updating at.(null for the root of the
	 *            tree)
	 */
	static void updateHights(Node node, Node endNode) {
		if (node != null) {
			Node cur = node;
			while (cur != endNode) {
				if (cur.getRightSon() == null && cur.getLeftSon() == null) {
					cur.setHight(Constant.INITIAL_HIGHT_FOR_LEAF_IN_TREE);
				} else if (cur.getRightSon() == null) {
					cur.setHight(cur.getLeftSon().getHight() + 1);
				} else if (cur.getLeftSon() == null) {
					cur.setHight(cur.getRightSon().getHight() + 1);
				} else {
					cur.setHight(Math.max(cur.getLeftSon().getHight(), cur
							.getRightSon().getHight()) + 1);
				}
				cur = cur.getFather();
			}
		}
	}

	/*
	 * in bst only updates the height all the way to the root.
	 * 
	 * @param insertedNode
	 * @param insertOrDelete
	 */
	void rotateAfterInsertionOrDeletion(Node insertedNode,
			String insertOrDelete) {
		updateHights(insertedNode, null);
	}

	/*
	 * changing the root of the tree.
	 * 
	 * @param newRoot
	 *            the node of the new root of the tree.
	 */
	void setRoot(Node newRoot) {
		this.headTree = newRoot;
	}

	/**
	 * Check whether the tree contains the given input value.
	 * 
	 * @param searchVal
	 *            val the value to search for.
	 * @return the depth of the node (0 for the root) with the given value if it
	 *         was found in the tree, −1 otherwise.
	 */
	public int contains(int searchVal) {
		Node searchedNode = findNode(searchVal);
		if (searchedNode == null) {
			return NOT_FOUND;
		}
		int i = 0;
		while (searchedNode != this.headTree) {
			i++;
			searchedNode = searchedNode.getFather();
		}
		return i;
	}

	/**
	 * finds a node that has the given value.
	 * 
	 * @param searchVal
	 *            the value to search.
	 * @return the node that has the given value and null otherwise.
	 */
	private Node findNode(int searchVal) {
		Node head = this.headTree;
		while (head != null) {
			int nodeValue = head.getValue();
			if (nodeValue == searchVal) {
				break;
			} else if (nodeValue > searchVal) {
				head = head.getLeftSon();
			} else {
				head = head.getRightSon();
			}
		}
		return head;
	}

	/**
	 * Removes the node with the given value from the tree, if it exists.
	 * 
	 * @param toDelete
	 *            the value to remove from the tree.
	 * @return true if the given value was found and deleted, false otherwise.
	 */
	public boolean delete(int toDelete) {
		Node nodeToDelete = findNode(toDelete);
		if (nodeToDelete == null) {
			return false;
		}
		if (nodeToDelete.getLeftSon() == null
				|| nodeToDelete.getRightSon() == null) {
			if (nodeToDelete.getLeftSon() != null
					|| nodeToDelete.getRightSon() != null) {
				dealWithDeleteWhenNodeHasOneSon(nodeToDelete);

			} else {
				dillWithDeleteWhenNodeHasZeroSons(nodeToDelete);
			}
			rotateAfterInsertionOrDeletion(nodeToDelete.getFather(),
					Constant.DELETE);

		} else {
			// at this point nodeTodelete has a right son so it must have a
			// successor.
			Node Succecor = Successor(nodeToDelete);
			int temp = Succecor.getValue();
			delete(temp);
			nodeToDelete.setValue(temp);
			return true;
		}
		this.size--;
		return true;
	}

	/**
	 * deals when deleting a node that has only one son.
	 * 
	 * @param nodeToDelete
	 *            the node we wish to delete.
	 */
	private void dealWithDeleteWhenNodeHasOneSon(Node nodeToDelete) {
		if (nodeToDelete != null) {
			if (nodeToDelete.getFather() == null) {
				if (nodeToDelete.getLeftSon() != null) {
					this.headTree = nodeToDelete.getLeftSon();
				} else {
					this.headTree = nodeToDelete.getRightSon();
				}
				this.headTree.setFather(null);
			} else {
				dealWithOneSonWhenFathersNotNull(nodeToDelete);
			}
		}
	}

	/**
	 * delete node when it's father is not null.
	 * 
	 * @param nodeToDelete
	 *            to node we wish to delete.
	 */
	private static void dealWithOneSonWhenFathersNotNull(Node nodeToDelete) {
		if (nodeToDelete != null) {
			if (nodeToDelete.getFather().getLeftSon() == nodeToDelete) {
				if (nodeToDelete.getLeftSon() != null) {
					nodeToDelete.getFather().setLeftSon(
							nodeToDelete.getLeftSon());
					nodeToDelete.getLeftSon().setFather(
							nodeToDelete.getFather());
				} else {
					nodeToDelete.getFather().setLeftSon(
							nodeToDelete.getRightSon());
					nodeToDelete.getRightSon().setFather(
							nodeToDelete.getFather());
				}
			} else {
				if (nodeToDelete.getLeftSon() != null) {
					nodeToDelete.getFather().setRightSon(
							nodeToDelete.getLeftSon());
					nodeToDelete.getLeftSon().setFather(
							nodeToDelete.getFather());
				} else {
					nodeToDelete.getFather().setRightSon(
							nodeToDelete.getRightSon());
					nodeToDelete.getRightSon().setFather(
							nodeToDelete.getFather());
				}
			}
		}
	}

	/**
	 * delete node that has no sons.
	 * 
	 * @param nodeToDelete
	 *            the node we wish to delete.
	 */
	private void dillWithDeleteWhenNodeHasZeroSons(Node nodeToDelete) {
		if (nodeToDelete != null) {
			if (nodeToDelete.getFather() == null) {
				this.headTree = null;
			} else {
				if (nodeToDelete.getFather().getLeftSon() == nodeToDelete) {
					nodeToDelete.getFather().setLeftSon(null);
				} else {
					nodeToDelete.getFather().setRightSon(null);
				}
			}
		}
	}

	/**
	 * find the successor of a given node.
	 * 
	 * @param nodeToFindSuccOF
	 *            the node we wish to find its successor.
	 * @return the successor of the node if found and null otherwise.
	 */
	static Node Successor(Node nodeToFindSuccOF) {
		if (nodeToFindSuccOF != null) {
			if (nodeToFindSuccOF.getRightSon() != null) {
				return min(nodeToFindSuccOF.getRightSon());
			} else {
				Node prev = nodeToFindSuccOF;
				Node cur = nodeToFindSuccOF.getFather();
				while (cur != null && cur.getRightSon() == prev) {
					prev = cur;
					cur = cur.getFather();
				}
				return cur;
			}
		}
		return null;
	}

	/**
	 * finds the node with the minimum value in the tree.
	 * 
	 * @param root
	 *            the base of the tree.
	 * @return the minimum of the tree if possible, and null otherwise.
	 */
	static Node min(Node root) {
		if (root != null) {
			Node cur = root;
			while (cur.getLeftSon() != null) {
				cur = cur.getLeftSon();
			}
			return cur;
		}
		return null;
	}

	/**
	 * 
	 * @return the number of nodes in the tree.
	 */
	public int size() {
		return this.size;
	}

	/**
	 * 
	 * @return an iterator on the Avl Tree. The returned iterator iterates over
	 *         the tree nodes in an ascending order, and does NOT implement the
	 *         remove() method.
	 */
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			Node minInTree = min(getHead());

			@Override
			public boolean hasNext() {
				if (this.minInTree == null) {
					return false;
				}
				return true;
			}

			@Override
			public Integer next() {
				int temp = this.minInTree.getValue();
				this.minInTree = Successor(this.minInTree);
				return temp;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	/**
	 * 
	 * @return the root of the tree.
	 */
	Node getHead() {
		return this.headTree;
	}

}
