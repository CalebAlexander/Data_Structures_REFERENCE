import java.util.Collection;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Caleb Alexander
 * @userid calexander49
 * @GTID 903133971
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot add a null "
                    + "Collection to the AVL");
        } else {
            for (T node : data) {
                if (node == null) {
                    throw new java.lang.IllegalArgumentException("Cannot add "
                            + "null data to the AVL");
                } else {
                    add(node);
                }
            }
        }
    }

    /**
     * Add the data as a leaf to the AVL. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot add null"
                    + " data to the Binary Search Tree.");
        } else {
            root = recursiveAdd(root, data);
        }
    }

    /**
     *  Recursive helper function for the add method.  Checks the value
     *  of nodes and determines whether to go left or right
     *
     * @param node node that we are checking the value of
     * @param data data that we want to insert
     * @return node that we added
     */
    private AVLNode<T> recursiveAdd(AVLNode<T> node, T data) {
        if (node == null) {
            node = new AVLNode<>(data);
            node.setHeight(0);
            node.setBalanceFactor(0);
            size++;
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(recursiveAdd(node.getRight(), data));
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(recursiveAdd(node.getLeft(), data));
        }
        // Check for Balance
        update(node);
        node = checkBalance(node);
        return node;
    }

    /**
     *  Helper function that checks the balance factor
     * @param node node to be checked
     * @return return checked node
     */
    private AVLNode<T> checkBalance(AVLNode<T> node) {
        // If out of balance
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(llRotation(node.getLeft()));
            }
            return rrRotation(node);
        } else if (node.getBalanceFactor() < -1) { //Right Heavy
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(rrRotation(node.getRight()));
            }
            return llRotation(node);
        }
        return node;
    }

    /**
     *  Helper for the LL rotation
     * @param node parent node to be rotated
     * @return rotated node
     */
    private AVLNode<T> llRotation(AVLNode<T> node) {
        // Does this update the top node??
        AVLNode<T> child = node.getRight();
        node.setRight(child.getLeft());
        child.setLeft(node);
        update(node);
        update(child);
        return child;
    }

    /**
     *  Helper function for the LR rotation
     * @param node parent node to be rotated
     */
    /**private void lrRotation(AVLNode<T> node) {
        AVLNode<T> dummy = node.getLeft().getRight();
        node.getLeft().setRight(dummy.getLeft());
        dummy.setLeft(node.getLeft());
        node.setLeft(dummy);
        rrRotation(node);
    }*/

    /**
     *  Helper function for the RL Rotation
     * @param node parent node to be rotated
     */
    /**private void rlRotation(AVLNode<T> node) {
        AVLNode<T> dummy = node.getRight().getLeft();
        node.getRight().setLeft(dummy.getRight());
        dummy.setLeft(node.getRight());
        node.setRight(dummy);
        llRotation(node);
    }*/

    /**
     *  Helper for the RR Rotation
     * @param node parent node to be rotated
     * @return node that was rotated
     */
    private AVLNode<T> rrRotation(AVLNode<T> node) {
        AVLNode<T> child = node.getLeft();
        node.setLeft(child.getRight());
        child.setRight(node);
        update(node);
        update(child);
        return child;
    }

    /**
     *  Helper function to update node heights
     *
     * @param node node whos height we are updating
     */
    private void update(AVLNode<T> node) {
        int left = (node.getLeft() == null
                ? -1 : node.getLeft().getHeight());
        int right = (node.getRight() == null
                ? -1 : node.getRight().getHeight());
        if (left > right) {
            node.setHeight(left + 1);
        } else {
            node.setHeight(right + 1);
        }

        int lH = (node.getLeft() == null ? -1 : node.getLeft().getHeight());
        int rH = (node.getRight() == null ? -1 : node.getRight().getHeight());
        node.setBalanceFactor(lH - rH);
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     * 1: The data is a leaf. In this case, simply remove it.
     * 2: The data has one child. In this case, simply replace the node with
     * the child node.
     * 3: The data has 2 children. For this assignment, use the predecessor to
     * replace the data you are removing, not the sucessor.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not in the tree
     * @param data data to remove from the tree
     * @return the data removed from the tree.  Do not return the same data
     * that was passed in. Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot add null"
                    + " data to the Binary Search Tree.");
        } else {
            AVLNode<T> helperNode = new AVLNode<>(null);
            root = recursiveRemove(root, helperNode, data);
            return helperNode.getData();
        }
    }

    /**
     * Recursive helper function for the remove method. Checks the data and
     * replaces, recurses, or checks the successor if necessary.
     *
             * @param node node to be checked
     * @param helper helper node holding the data that was removed
     * @param data data to be removed
     * @return node that was checked (pointer reinforcement)
     */
    private AVLNode<T> recursiveRemove(
            AVLNode<T> node, AVLNode<T> helper, T data) {
        if (node == null) {
            throw new java.util.NoSuchElementException("The data to be "
                    + "removed from the tree does not exist in the tree.");
        } else if (data.compareTo(node.getData()) == 0) {
            helper.setData(node.getData());
            size--;
            // if we found the right node, check how to adjust the tree
            if (node.getLeft() == null && node.getRight() == null) {
                // if no children just return null so the leaf is removed
                return null;
            } else if (node.getLeft() != null && node.getRight() == null) {
                // if one child just return the child
                return node.getLeft();
            } else if (node.getRight() != null && node.getLeft() == null) {
                // if one child just return the child
                return node.getRight();
            } else {
                AVLNode<T> predecessorHelper = new AVLNode<>(null);
                node.setLeft(predecessor(node.getLeft(), predecessorHelper));
                node.setData(predecessorHelper.getData());
            }
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(recursiveRemove(node.getLeft(), helper, data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(recursiveRemove(node.getRight(), helper, data));
        }
        update(node);
        checkBalance(node);
        return node;
    }

    /**
     *  Successor helper function for the remove recursiveRemove method.
     *  Goes all the way to the left and get the right value.
     *
     * @param node node checked if has left
     * @param predecessorHelper node that will save the removed nodes value
     * @return the node that is set to the right
     */
    private AVLNode<T> predecessor(
            AVLNode<T> node, AVLNode<T> predecessorHelper) {
        if (node.getRight() == null) {
            predecessorHelper.setData(node.getData());
            return node.getLeft();
        } else {
            node.setRight(predecessor(node.getRight(), predecessorHelper));
        }
        update(node);
        checkBalance(node);
        return node;
    }
    /**
     * Returns the data in the tree matching the parameter passed in.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data data to get in the AVL tree
     * @return the data in the tree equal to the parameter.  Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot remove null"
                    + " data from the AVL Tree.");
        } else {
            AVLNode<T> helperNode = new AVLNode<>(null);
            root = recursiveGet(root, helperNode, data);
            return helperNode.getData();
        }
    }
    /**
     *
     * @param node node who's value we are checking
     * @param helper node who saves the value of the matching node
     * @param data data to be checked
     * @return return the pointer reinforced node
     */
    private AVLNode<T> recursiveGet(
            AVLNode<T> node, AVLNode<T> helper, T data) {
        if (node == null) {
            throw new java.util.NoSuchElementException("The data to be "
                    + "retrieved from the tree does not exist in the tree.");
        } else if (data.compareTo(node.getData()) == 0) {
            helper.setData(node.getData());
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(recursiveGet(node.getLeft(), helper, data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(recursiveGet(node.getRight(), helper, data));
        }
        return node;
    }

    /**
     * Returns whether or not the parameter is contained within the tree.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data data to find in the AVL tree
     * @return whether or not the parameter is contained within the tree
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The AVL Tree "
                    + "cannot contain null data which was inputted.");
        }
        try {
            T testData = get(data);
            return true;
        } catch (java.util.NoSuchElementException e) {
            return false;
        }
    }

    /**
     * In your BST homework, you worked with the concept of the successor, the
     * smallest data that is larger than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the successor of the data
     * passed in. There are 2 cases to consider:
     * 1: The right subtree is non-empty. In this case, the successor is the
     * leftmost node of the right subtree.
     * 2: The right subtree is empty. In this case, the successor is the lowest
     * ancestor of the node containing {@code data} whose left child is also
     * an ancestor of {@code data}.
     *
     * For example, in the tree below, the successor of 76 is 81, and the
     * successor of 40 is 76.
     *
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not in the tree
     * @param data the data to find the successor of
     * @return the successor of {@code data}. If there is no larger data than
     * the one given, return null.
     */
    public T successor(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot get the "
                    + "successor of null data.");
        }
        return recursor(root, data, null);
    }

    /**
     *  Helper function to find the node to success
     * @param node current node being checked
     * @param data data we are checking for
     * @param successor previous successor
     * @return successor data
     */
    private T recursor(AVLNode<T> node, T data, T successor) {
        if (node == null) {
            throw new java.util.NoSuchElementException("The data to be "
                    + "retrieved from the tree does not exist in the tree.");
        } else if (data.compareTo(node.getData()) == 0) {
            if (node.getRight() == null) {
                return successor;
            }
            return successorHelper(node.getRight(), data);
        } else if (data.compareTo(node.getData()) < 0) {
            return recursor(node.getLeft(), data, node.getData());
        } else if (data.compareTo(node.getData()) > 0) {
            return recursor(node.getRight(), data, successor);
        }
        return data;
    }

    /**
     *  Helper function fo find the successor of a node
     * @param node node being checked
     * @param data data being checked for
     * @return return successor data
     */
    private T successorHelper(AVLNode<T> node, T data) {
        if (node.getLeft() == null) {
            return node.getData();
        }
        return successorHelper(node.getLeft(), data);
    }

    /**
     * Return the height of the root of the tree.
     * 
     * This method does not need to traverse the tree since this is an AVL.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return (root == null ? -1 : root.getHeight());
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Get the number of elements in the tree.
     *
     * DO NOT USE OR MODIFY THIS METHOD!
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the root of the tree. Normally, you wouldn't do this, but it's
     * necessary to grade your code.
     *
     * DO NOT USE OR MODIFY THIS METHOD!
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
