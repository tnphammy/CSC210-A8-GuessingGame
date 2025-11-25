import java.lang.reflect.InaccessibleObjectException;

/** Implements decision tree 
 * @author Tammy Pham
*/
public class DecisionTree extends BinaryTree<String>{

    /** The value of the node */
    protected String data;
    /** The node to the left */
    protected DecisionTree left;
    /** The node to the right */
    protected DecisionTree right;

    /**
     * Simple constructor to create a leaf node
     * @param string the value the node contains
     */
    public DecisionTree(String data) {
        super(data);
    }

    /**
     * Constructor with three values that only allows DecisionTree subtype
     * @param data the root node
     * @param left the left node
     * @param right the right node
     */
    public DecisionTree(String data, DecisionTree left, DecisionTree right) {
        super(data);
        this.setLeft(left); // makes sure that left is of the subtype
        this.setRight(right); // makes sure that right is of the subtype
    }

    /**
     * Copy constructor copies entire tree structure 
     * @param ogTree
     */
    public DecisionTree(DecisionTree ogTree) {
        super(ogTree.getData());
        this.setLeft(ogTree.getLeft());
        this.setRight(ogTree.getRight());

    }


    /** Accessor for node data */
    public String getData() {
	    if (super.getData() instanceof String) {
            return super.getData();
        }
        else {
            throw new UnsupportedOperationException("The data entered is not of type String.");
        }
    }

    /** Accessor for left child */
    public DecisionTree getLeft() {
      	return (DecisionTree)super.getLeft();
    }

    /** Accessor for right child */
    public DecisionTree getRight() {
        return (DecisionTree)super.getRight();
    }

    /** Manipulator for node data */
    public void setData(String data) {
        super.setData(data);
    }

    /** Manipulator for left child */
    public void setLeft(BinaryTree<String> left) {
        if(left == null || left instanceof DecisionTree) {
            super.setLeft(left);
        }
        else {
            throw new UnsupportedOperationException();
        }
    }

    /** Manipulator for right child */
    public void setRight(BinaryTree<String> right) {
        if(right == null || right instanceof DecisionTree) {
            super.setRight(right);
        }
        else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Follows a string representation of a path containing Y's and N's and returns the corresponding node
     * @param path The direction to the node
     * @return the resulting node
     */
    public DecisionTree followPath(String path) {
        DecisionTree curr = this;
        // traverse the tree
        for(int i = 0; i < path.length(); i++) {
            String direction = String.valueOf((path.charAt(i))).toUpperCase();
            if (direction.equals("Y")) {
                curr = curr.getLeft();
            }
            else if (direction.equals("N")) {
                curr = curr.getRight();
            }
            else {
                throw new InaccessibleObjectException("The node you're trying to reach does not exist.");
            }
        }
        return curr;
    }


}