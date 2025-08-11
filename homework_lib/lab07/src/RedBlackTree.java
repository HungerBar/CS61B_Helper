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
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        boolean tag;

        RBTreeNode<T>  root = node.left;
        node.left = root.right;
        root.right = node;

        tag = node.isBlack;
        node.isBlack = root.isBlack;
        root.isBlack = tag;

        return root;

    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        boolean tag;

        RBTreeNode<T>  root = node.right;
        node.right = root.left;
        root.left = node;
        tag = node.isBlack;
        node.isBlack = root.isBlack;
        root.isBlack = tag;

        return root;
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
        RBTreeNode<T> n = new RBTreeNode<>(false, item);

        if(root == null) {
            return n;
        }

        RBTreeNode<T> parent = normalInsertHelper(node, item);
        if (parent.item.compareTo(item) < 0) {
            parent.right = n;
        }else if (parent.item.compareTo(item) > 0) {
            parent.left = n;
        }else{
            return node;
        }

        // TODO: Rotate left operation
        if(ifRotateleft(parent)) {
            parent = rotateLeft(parent);
            if(parent.left == root){
                return parent;
            }else{
                return node;
            }
        }

        RBTreeNode<T> k = normalInsertHelper(node, parent.item);
        if(k == null){
            if(ifRotateright(parent)) {
                k = rotateLeft(node);
            }
            if(ifflip(parent)) {
                flipColors(parent);
            }

            return parent;
        }
        // TODO: Rotate right operation
        // TODO: Color flip
        while(k != node && ifUpwards(parent) ) {

            RBTreeNode<T> fk = normalInsertHelper(node, k.item);

            if(ifRotateleft(parent)) {
                parent = rotateLeft(parent);
                if(parent.left == root){
                    return parent;
                }else{
                    return node;
                }
            }

            if(ifDoAll(parent)) {
                k.left = rotateLeft(parent);
            }
            if(ifRotateright(k)) {
                parent = rotateRight(k);
            }
            if(ifflip(parent)) {
                flipColors(parent);
            }

            if(fk.left !=null) {
                if(fk.left.item.compareTo(k.item) == 0) {
                    fk.left = parent;
                }else{
                    fk.right = parent;
                }
            }else{
                fk.right = parent;
            }

            parent = fk;
            if(parent == node){
                if(ifflip(parent)) {
                    flipColors(parent);
                }else{
                    parent = rotateLeft(parent);
                }
                return parent;
            }
            k = normalInsertHelper(node, parent.item);

        }
        if(k == node){
            if(ifDoAll(parent)) {
                k.left = rotateLeft(parent);
            }
            if(ifRotateright(k)) {
                k = rotateRight(k);
            }
            if(ifflip(k)) {
                flipColors(k);
            }

            return k;
        }

        return node;
    }



    private RBTreeNode<T> normalInsertHelper(RBTreeNode<T> node, T item) {
        RBTreeNode<T> parent,n;
        parent = node;
        if (parent.item.compareTo(item) == 0) {
            return null;
        }
        n = node;
        while(n != null) {
            if(n.item.compareTo(item) < 0) {
                parent = n;
                n = n.right;
            }else if (n.item.compareTo(item) > 0) {
                parent = n;
                n = n.left;
            }else {
                return parent;
            }
        }

        return parent;
    }

    private boolean ifRotateleft(RBTreeNode<T> p) {
        if (isRed(p.right) && !isRed(p.left) && !isRed(p)) {
            return true;
        }
        return false;
    }

    private boolean ifRotateright(RBTreeNode<T> p) {
        if(isRed(p.left) && isRed(p.left.left)) {
            return true;
        }
        return false;
    }

    private boolean ifflip(RBTreeNode<T> p) {
        if (isRed(p.right) && isRed(p.left)) {
            return true;
        }
        return false;
    }

    private boolean ifDoAll(RBTreeNode<T> p) {
        if (isRed(p) && isRed(p.right)) {
            return true;
        }
        return false;
    }

    private boolean ifin(RBTreeNode<T> n , T item) {
        while(n != null) {
            if(n.item.compareTo(item) < 0) {
                n = n.right;
            }else if (n.item.compareTo(item) > 0) {
                n = n.left;
            }else {
                return true;
            }
        }
        return false;
    }

    private boolean ifUpwards(RBTreeNode<T> n) {
        if(isRed(n.right) || (isRed(n.left) && isRed(n))){
            return true;
        }return false;
    }


}
