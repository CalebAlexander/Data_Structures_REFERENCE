import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of a binary search tree.
 *
 * @author Caleb Alexander
 * @userid calexander49
 * @GTID 903133971
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    public Comparable[] toArray(){
        Comparable[] array = new Comparable[size];
        array[0] = root.getData();
        return array;
    }
    /**
     * A no-argument constructor that should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular
     * for loop will not work here. What other type of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.isEmpty()) {
            throw new java.lang.IllegalArgumentException("Cannot create "
                    + "a Binary Search Tree with null or empty data");
        } else {
            size = 0;
            for (T node : data) {
                add(node);
            }
        }
    }

    /**
     * Add the data as a leaf in the BST. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     * 
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
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
    private BSTNode<T> recursiveAdd(BSTNode<T> node, T data) {
        if (node == null) {
            node = new BSTNode<>(data);
            size++;
            return node;
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(recursiveAdd(node.getRight(), data));
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(recursiveAdd(node.getLeft(), data));
        }
        return node;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data.
     * You must use recursion to find and remove the successor (you will likely
     * need an additional helper method to handle this case efficiently).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot remove null"
                    + " data from the Binary Search Tree.");
        } else {
            BSTNode<T> helperNode = new BSTNode<>(null); // data should get reset ideally
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
    private BSTNode<T> recursiveRemove(BSTNode<T> node, BSTNode<T> helper, T data) {
        if (node == null) {
            throw new java.util.NoSuchElementException("The data to be removed from"
                    + "the tree does not exist in the tree.");
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
                BSTNode<T> successorHelper = new BSTNode<>(null);
                node.setRight(successor(node.getRight(), successorHelper));
                node.setData(successorHelper.getData());
            }
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(recursiveRemove(node.getLeft(), helper, data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(recursiveRemove(node.getRight(), helper, data));
        }
        return node;
    }

    /**
     *  Successor helper function for the remove recursiveRemove method.
     *  Goes all the way to the left and get the right value.
     *
     * @param node node checked if has left
     * @param successorHelper node that will save the removed nodes value
     * @return the node that is set to the right
     */
    private BSTNode<T> successor(BSTNode<T> node, BSTNode<T> successorHelper) {
        if (node.getLeft() == null) {
            successorHelper.setData(node.getData());
            return node.getRight();
        } else {
            node.setLeft(successor(node.getLeft(), successorHelper));
        }
        return node;
    }
    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot remove null"
                    + " data from the Binary Search Tree.");
        } else {
            BSTNode<T> helperNode = new BSTNode<>(null);
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
    private BSTNode<T> recursiveGet(BSTNode<T> node, BSTNode<T> helper, T data) {
        if (node == null) {
            throw new java.util.NoSuchElementException("The data to be retrieved from"
                    + "the tree does not exist in the tree.");
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
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The Binary Search Tree "
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
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> output = new ArrayList<>();
        if (root != null) {
            recursivePreOrder(root, output);
        }
        return output;
    }

    /**
     *  Recursive helper function for the preorder traversal
     *
     * @param node node to be added to the list
     * @param list list that we are adding nodes to
     */
    private void recursivePreOrder(BSTNode<T> node, List<T> list) {
        // Root, Left, Right
        list.add(node.getData());
        if (node.getLeft() != null) {
            recursivePreOrder(node.getLeft(), list);
        }
        if (node.getRight() != null) {
            recursivePreOrder(node.getRight(), list);
        }
    }



    /**
     * Should run in O(n).
     *
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> output = new ArrayList<>();
        if (root != null) {
            recursiveInOrder(root, output);
        }
        return output;
    }

    /**
     *  Recursive helper function for inorder traversal
     *
     * @param node node to be added to the list
     * @param list list that we are adding node data to
     */
    private void recursiveInOrder(BSTNode<T> node, List<T> list) {
        // Left, Root, Right
        if (node.getLeft() != null) {
            recursiveInOrder(node.getLeft(), list);
        }
        list.add(node.getData());
        if (node.getRight() != null) {
            recursiveInOrder(node.getRight(), list);
        }
    }

    /**
     * Should run in O(n).
     *
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> output = new ArrayList<>();
        if (root != null) {
            recursivePostOrder(root, output);
        }
        return output;
    }

    /**
     *  Recursive helper function for post order traversal
     *
     * @param node node that we are adding to the list
     * @param list list that we add nodes to
     */
    private void recursivePostOrder(BSTNode<T> node, List<T> list) {
        // Left, Right, Root
        if (node.getLeft() != null) {
            recursivePostOrder(node.getLeft(), list);
        }
        if (node.getRight() != null) {
            recursivePostOrder(node.getRight(), list);
        }
        list.add(node.getData());
    }
    /**
     * Generate a level-order traversal of the tree.
     *
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     *
     * Should run in O(n).
     *
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> list = new ArrayList<>();
        queue.add(root);
        iterativeLevelOrder(queue, list);
        return list;
    }

    private void iterativeLevelOrder(Queue<BSTNode<T>> queue, List<T> list) {
        BSTNode<T> temp = new BSTNode<>(null);
        while (!queue.isEmpty()) {
            temp = queue.peek();
            list.add(temp.getData());
            if (temp.getLeft() != null) {
                queue.add(temp.getLeft());
            }
            if (temp.getRight() != null) {
                queue.add(temp.getRight());
            }
            queue.remove();
        }
    }

    /**
     * Clears the tree.
     *
     * Should run in O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     *
     * Should be calculated in O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return recursiveHeight(root,0);
    }

    private int recursiveHeight(BSTNode<T> node, int height) {
        if (node.getRight() == null && node.getLeft() == null) {
            return 0;
        } else {
            int leftHeight = 0;
            int rightHeight = 0;
            if (node.getLeft() != null) {
                leftHeight= recursiveHeight(node.getLeft(), height);
            }
            if (node.getRight() != null) {
                rightHeight= recursiveHeight(node.getRight(), height);
            }
            if (leftHeight > rightHeight) {
                return leftHeight + 1;
            } else {
                return rightHeight + 1;
            }
        }
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
