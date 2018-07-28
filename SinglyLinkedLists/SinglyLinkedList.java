/**
 * Your implementation of a non-circular singly linked list with a tail pointer.
 *
 * @author Caleb Alexander
 * @userid calexander49
 * @GTID 903133971
 * @version 1.0
 */
public class SinglyLinkedList<T> {
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    /**
     * Adds the element to the index specified.
     *
     * Adding to indices 0 and {@code size} should be O(1), all other cases are
     * O(n).
     *
     * @param index the requested index for the new element
     * @param data the data for the new element
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        LinkedListNode<T> newNode = new LinkedListNode<T>(data);
        if (index == 0) {
            newNode.setNext(head);
            head = newNode;
            if (index == size) {
                tail = newNode;
            }
        } else {
            LinkedListNode<T> temp = head;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.getNext();
            }
            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
            if (index == size) {
                tail = newNode;
            }
        }

        size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    public void addToFront(T data) {
        LinkedListNode<T> newNode = new LinkedListNode<T>(data, head);
        head = newNode;
        size++;
        if (size == 1) {
            tail = head;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    public void addToBack(T data) {
        LinkedListNode<T> newNode = new LinkedListNode<>(data);
        if (size == 0) {
            head = newNode;
            tail = head;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the element from the index specified.
     *
     * Removing from index 0 should be O(1), all other cases are O(n).
     *
     * @param index the requested index to be removed
     * @return the data formerly located at index
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index >= size
     */
    public T removeAtIndex(int index) {
        T outValue;
        if (size <= 0) {
            outValue = null;
        } else if (index == 0) {
            outValue = head.getData();
            head = head.getNext();
            size--;
        } else {
            LinkedListNode<T> temp = head;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.getNext();
            }
            outValue = temp.getNext().getData();
            temp.setNext(temp.getNext().getNext());
            size--;
        }

        return outValue;
    }

    /**
     * Removes and returns the element at the front of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the front, null if empty list
     */
    public T removeFromFront() {
        T outValue;
        if (size <= 0) {
            outValue = null;
            head = null;
        } else {
            LinkedListNode<T> temp = head;
            outValue = temp.getData();
            head = temp.getNext();
            size--;
            if (size == 0) {
                head = null;
                tail = null;
            }
        }

        return outValue;
    }

    /**
     * Removes and returns the element at the back of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(n) for all cases.
     *
     * @return the data formerly located at the back, null if empty list
     */
    public T removeFromBack() {
        T outValue;
        if (size <= 0) {
            outValue = null;
        } else if (size == 1) {
            outValue = head.getData();
            head = null;
            tail = null;
        } else {
            LinkedListNode<T> temp = head;
            for (int i = 0; i < size - 2; i++) {
                temp = temp.getNext();
            }
            outValue = temp.getNext().getData();
            temp.setNext(null);
            tail = temp;
            size--;
        }

        return outValue;
    }

    /**
     * Returns the index of the first occurrence of the passed in data in the
     * list or -1 if it is not in the list.
     *
     * If data is in the head, should be O(1). In all other cases, O(n).
     *
     * @param data the data to search for
     * @throws java.lang.IllegalArgumentException if data is null
     * @return the index of the first occurrence or -1 if not in the list
     */
    public int indexOf(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException();
        } else if (size != 0) {
            LinkedListNode<T> temp = head;
            for (int i = 0; i < size; i++) {
                if (temp.getData().equals(data)) {
                    return i;
                }
                temp = temp.getNext();
            }
        }

        return -1;
    }

    /**
     * Returns the element at the specified index.
     *
     * Getting the head and tail should be O(1), all other cases are O(n).
     *
     * @param index the index of the requested element
     * @return the object stored at index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        T outValue;
        if (index < 0 || index >= size) {
            outValue = null;
            throw new java.lang.IndexOutOfBoundsException();
        } else {
            LinkedListNode<T> temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
            outValue = temp.getData();
        }

        return outValue;
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length {@code size} holding all of the objects in
     * this list in the same order
     */
    public Object[] toArray() {
        Object[] outArray = new Object[size];
        LinkedListNode<T> temp = head;
        for (int i = 0; i < size; i++) {
            outArray[i] = temp.getData();
            temp = temp.getNext();
        }

        return outArray;
    }

    /**
     * Returns a boolean value indicating if the list is empty.
     *
     * Must be O(1) for all cases.
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        if (head == null) {
            return true;
        }
        return false;
    }

    /**
     * Clears the list of all data and resets the size.
     *
     * Must be O(1) for all cases.
     */
    public void clear() {
        size = 0;
        head = null;
        tail = null;
    }

    /**
     * Returns the number of elements in the list.
     *
     * Runs in O(1) for all cases.
     * 
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the head node of the linked list.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the head of the linked list
     */
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the linked list.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the tail of the linked list
     */
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}