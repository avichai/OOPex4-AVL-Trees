package oop.ex4.data_structures;

/**
 * a class that represent an AVL tree.
 * 
 * @author Avichai
 *
 */
public class AvlTree extends BST implements Iterable<Integer> {

	private static final int BAD_INPUT = -1;
	private static final int MINIMUM_DIFFERENCE_FOR_VALID_AVL = 1;
	private static final String RIGHT = "right";
	private static final String LEFT = "left";

	/**
	 * The default constructor.
	 */
	public AvlTree() {
		super();
	}

	/**
	 * A constructor that builds the tree by adding the elements in the input
	 * array one by one. If a value appears more than once in the list, only the
	 * first appearance is added.
	 * 
	 * @param data
	 *            the values to add to tree.
	 */
	public AvlTree(int[] data) {
		super(data);
	}

	/**
	 * A copy constructor that creates a deep copy of the given AvlTree. This
	 * means that for every node or any other internal object of the given tree,
	 * a new, identical object, is instantiated for the new tree (the internal
	 * object is not simply referenced from it). The new tree must contain all
	 * the values of the given tree, but not necessarily in the same structure.
	 * 
	 * @param avlTree
	 *            an AVL tree.
	 */
	public AvlTree(AvlTree avlTree) {
		super(avlTree);
	}

	/*
	 * rotates the the tree if needed after insertion or deletion.
	 */
	void rotateAfterInsertionOrDeletion(Node insertedNode,
			String insertOrDelete) {
		if (insertedNode != null && insertOrDelete != null) {
			if (insertOrDelete.equals(Constant.INSERT)
					|| insertOrDelete.equals(Constant.DELETE)) {
				Node nodeToChek = insertedNode;
				while (nodeToChek != null) {
					updateHights(nodeToChek, nodeToChek.getFather());
					boolean wasRotated = checkHightsAndRotate(nodeToChek);
					if (wasRotated && insertOrDelete.equals(Constant.INSERT)) {
						return;
					} else {
						nodeToChek = nodeToChek.getFather();
					}
				}
			}
		}
	}

	/**
	 * check if there is a problem of heights in the given node and fix it.
	 * 
	 * @param nodeToChek
	 *            the node that we wish to finds if it needs to be fixed.
	 * @return true if rotated the tree successfully and false otherwise.
	 */
	private boolean checkHightsAndRotate(Node nodeToChek) {
		if (nodeToChek != null) {
			String lastMove;
			String secondLastMove;
			if (nodeToChek.getLeftSon() == null) {
				if (nodeToChek.getHight() > MINIMUM_DIFFERENCE_FOR_VALID_AVL) {
					lastMove = RIGHT;
					secondLastMove = getSecondLastMove(nodeToChek, lastMove);
					rotate(nodeToChek, lastMove, secondLastMove);
					return true;
				}
			} else if (nodeToChek.getRightSon() == null) {
				if (nodeToChek.getHight() > MINIMUM_DIFFERENCE_FOR_VALID_AVL) {
					lastMove = LEFT;
					secondLastMove = getSecondLastMove(nodeToChek, lastMove);
					rotate(nodeToChek, lastMove, secondLastMove);
					return true;
				}
			} else {
				if (Math.abs(nodeToChek.getLeftSon().getHight()
						- nodeToChek.getRightSon().getHight()) > MINIMUM_DIFFERENCE_FOR_VALID_AVL) {
					lastMove = getLastMove(nodeToChek);
					secondLastMove = getSecondLastMove(nodeToChek, lastMove);
					rotate(nodeToChek, lastMove, secondLastMove);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * rotates the tree.
	 * 
	 * @param nodeToRotate
	 *            the node we wish to do the rotate on.
	 * @param lastMove
	 *            the way for the first node from the given node where the
	 *            violation came from.
	 * @param secondLastMove
	 *            the way for the second node from the given node where the
	 *            violation occurred.
	 */
	private void rotate(Node nodeToRotate, String lastMove,
			String secondLastMove) {
		if (nodeToRotate != null && lastMove != null && secondLastMove != null) {
			if (lastMove.equals(LEFT)) {
				if (secondLastMove.equals(LEFT)) {
					rotateLL(nodeToRotate);
				} else {
					rotateLR(nodeToRotate);
				}
			} else {
				if (secondLastMove.equals(LEFT)) {
					rotateRL(nodeToRotate);
				} else {
					rotateRR(nodeToRotate);
				}
			}
		}
	}

	/**
	 * 
	 * @param nodeToChek
	 *            the node of the violation.
	 * @param lastMove
	 *            the way for the first node from the given node where the
	 *            violation came from.
	 * @return the second move from the given node where the violation occurred.
	 *         (a String).
	 */
	private String getSecondLastMove(Node nodeToChek, String lastMove) {
		if (nodeToChek != null) {
			if (lastMove.equals(RIGHT)) {
				return findSecondMoveOnTheRight(nodeToChek);
			} else {
				return findSecondMoveOnTheLeft(nodeToChek);
			}
		}
		return null;
	}

	/**
	 * finds the second move on the left of the given node.
	 * 
	 * @param nodeToChek
	 *            the violation node.
	 * @return the second move from the given node where the violation occurred.
	 *         (a String).
	 */
	private String findSecondMoveOnTheLeft(Node nodeToChek) {
		if (nodeToChek != null) {
			if (nodeToChek.getLeftSon().getLeftSon() == null) {
				return RIGHT;
			} else if (nodeToChek.getLeftSon().getRightSon() == null) {
				return LEFT;
			} else {
				if (nodeToChek.getLeftSon().getLeftSon().getHight() > nodeToChek
						.getLeftSon().getRightSon().getHight()) {
					return LEFT;
				} else {
					return RIGHT;
				}
			}
		}
		return null;
	}

	/**
	 * finds the second move on the right of the given node.
	 * 
	 * @param nodeToChek
	 *            the violation node.
	 * @return the second move from the given node where the violation occurred.
	 *         (a String).
	 */
	private String findSecondMoveOnTheRight(Node nodeToChek) {
		if (nodeToChek != null) {
			if (nodeToChek.getRightSon().getLeftSon() == null) {
				return RIGHT;
			} else if (nodeToChek.getRightSon().getRightSon() == null) {
				return LEFT;
			} else {
				if (nodeToChek.getRightSon().getLeftSon().getHight() > nodeToChek
						.getRightSon().getRightSon().getHight()) {
					return LEFT;
				} else {
					return RIGHT;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param nodeToChek
	 *            the violation node.
	 * @return the way for the first node from the given node where the
	 *         violation came from when the given node has to sons!
	 */
	private String getLastMove(Node nodeToChek) {
		if (nodeToChek != null) {
			if (nodeToChek.getLeftSon().getHight() > nodeToChek.getRightSon()
					.getHight()) {
				return LEFT;
			}
			return RIGHT;
		}
		return null;
	}

	/**
	 * perform a RL rotation on a given node.
	 * 
	 * @param nodeToRotate
	 *            to node we wish to rotate on.
	 */
	private void rotateRL(Node nodeToRotate) {
		if (nodeToRotate != null) {
			rotateLL(nodeToRotate.getRightSon());
			rotateRR(nodeToRotate);
		}
	}

	/**
	 * perform a RR rotation on a given node.
	 * 
	 * @param nodeToRotate
	 *            to node we wish to rotate on.
	 */
	private void rotateRR(Node nodeToRotate) {
		if (nodeToRotate != null) {
			Node rightSon = nodeToRotate.getRightSon();
			dealWithFatherOfSubTreeAfterRotation(nodeToRotate, rightSon);
			dealWithChangingRightSonAndFatherRR(nodeToRotate, rightSon);
			updateHights(nodeToRotate, nodeToRotate.getFather());
		}
	}

	/**
	 * perform a LR rotation on a given node.
	 * 
	 * @param nodeToRotate
	 *            to node we wish to rotate on.
	 */
	private void rotateLR(Node nodeToRotate) {
		if (nodeToRotate != null) {
			rotateRR(nodeToRotate.getLeftSon());
			rotateLL(nodeToRotate);
		}
	}

	/**
	 * perform a LL rotation on a given node.
	 * 
	 * @param nodeToRotate
	 *            to node we wish to rotate on.
	 */
	private void rotateLL(Node nodeToRotate) {
		if (nodeToRotate != null) {
			Node leftSon = nodeToRotate.getLeftSon();
			dealWithFatherOfSubTreeAfterRotation(nodeToRotate, leftSon);
			dealWithChangingRightSonAndFatherLL(nodeToRotate, leftSon);
			updateHights(nodeToRotate, nodeToRotate.getFather());
		}
	}

	/**
	 * help function for rotating.
	 * 
	 * @param nodeToRotate
	 *            the node to rotate.
	 * @param rightOrLeftSon
	 *            the son of the node to rotate that we wish to work on
	 *            depending on the specific violation.
	 */
	private void dealWithFatherOfSubTreeAfterRotation(Node nodeToRotate,
			Node rightOrLeftSon) {
		if (nodeToRotate != null && rightOrLeftSon != null) {
			if (nodeToRotate.getFather() != null) {
				if (nodeToRotate.getFather().getLeftSon() == nodeToRotate) {
					nodeToRotate.getFather().setLeftSon(rightOrLeftSon);
				} else {
					nodeToRotate.getFather().setRightSon(rightOrLeftSon);
				}
				rightOrLeftSon.setFather(nodeToRotate.getFather());
			} else {
				rightOrLeftSon.setFather(null);
				setRoot(rightOrLeftSon);
			}
		}
	}

	/**
	 * help function for rotating LL.
	 * 
	 * @param nodeToRotate
	 *            the node to rotate.
	 * @param leftSon
	 *            the left son of the node to rotate that we wish to work on.
	 */
	private void dealWithChangingRightSonAndFatherLL(Node nodeToRotate,
			Node leftSon) {
		if (nodeToRotate != null && leftSon != null) {
			nodeToRotate.setLeftSon(leftSon.getRightSon());
			leftSon.setRightSon(nodeToRotate);
			nodeToRotate.setFather(leftSon);
			if (nodeToRotate.getLeftSon() != null) {
				nodeToRotate.getLeftSon().setFather(nodeToRotate);
			}
		}
	}

	/**
	 * help function for rotating RR.
	 * 
	 * @param nodeToRotate
	 *            the node to rotate.
	 * @param rightSon
	 *            the right son of the node to rotate that we wish to work on.
	 */
	private void dealWithChangingRightSonAndFatherRR(Node nodeToRotate,
			Node rightSon) {
		if (nodeToRotate != null && rightSon != null) {
			nodeToRotate.setRightSon(rightSon.getLeftSon());
			rightSon.setLeftSon(nodeToRotate);
			nodeToRotate.setFather(rightSon);

			if (nodeToRotate.getRightSon() != null) {
				nodeToRotate.getRightSon().setFather(nodeToRotate);
			}
		}
	}

	/**
	 * Calculates the minimum number of nodes in an AVL tree of height h.
	 * 
	 * @param h
	 *            the height of the tree (a non−negative number) in question.
	 * @return the minimum number of nodes in an AVL tree of the given height.
	 * and -1 if it was a bad input for the height.
	 */
	public static int findMinNodes(int h) {
		if(h<0){
			return BAD_INPUT;
		}
		if (h == 0) {
			return 1;
		} else if (h == 1) {
			return 2;
		} else {
			return findMinNodes(h - 1) + findMinNodes(h - 2) + 1;
		}
	}
}
