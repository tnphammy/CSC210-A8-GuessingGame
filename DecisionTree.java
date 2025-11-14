/** Implements decision tree */
public class DecisionTree extends BinaryTree<String>{
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

    /** Manipulator for left child */
    public void setLeft(BinaryTree<String> left) {
        if(left instanceof DecisionTree) {
            super.setLeft(left);
        }
        else {
            throw new UnsupportedOperationException();
        }
    }

    /** Manipulator for right child */
    public void setRight(BinaryTree<String> right) {
        if(right instanceof DecisionTree) {
            super.setRight(right);
        }
        else {
            throw new UnsupportedOperationException();
        }
    }


}