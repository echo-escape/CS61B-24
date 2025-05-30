public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        if (isRed(node.left) && isRed(node.right)) {
            node.isBlack = false;
            node.left.isBlack = true;
            node.right.isBlack = true;
        }
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        if (node == null) return null;
        RBTreeNode<T> temp = null;
        if (node.left.right != null) {
            temp = node.left.right;
        }
        RBTreeNode<T> newNode = node.left;
        newNode.right = node;
        newNode.right.left = temp;

        boolean nodeIsBlack = node.isBlack;
        node.isBlack = newNode.isBlack;
        newNode.isBlack = nodeIsBlack;
        return newNode;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        if (node == null) return null;
        RBTreeNode<T> temp = null;
        if (node.right.left != null) {
            temp = node.right.left;
        }
        RBTreeNode<T> newNode = node.right;
        newNode.left = node;
        newNode.left.right = temp;

        boolean nodeIsBlack = node.isBlack;
        node.isBlack = newNode.isBlack;
        newNode.isBlack = nodeIsBlack;
        return newNode;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // TODO: Insert (return) new red leaf node.
        RBTreeNode<T> newNode = new RBTreeNode<>(false, item, null, null);

        // TODO: Handle normal binary search tree insertion.
        if (node == null) {
            return newNode;
        }
        int cmp = item.compareTo(node.item);
        if (cmp < 0) {
            node.left = insert(node.left, item);
        }
        else if (cmp > 0) {
            node.right = insert(node.right, item);
        }

        // TODO: Rotate left operation
        if (node.isBlack && isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        if (node.isBlack && isRed(node.left) && isRed(node.left.right)) {
            node.left = rotateLeft(node.left);
        }

        // TODO: Rotate right operation
        if (node.isBlack && isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }

        // TODO: Color flip
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }


        return node; //fix this return statement
    }

}
