import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Your implementation of HashMap.
 * 
 * @author Caleb Alexander
 * @userid calexander49
 * @GTID 903133971
 * @version 1.0
 */
public class HashMap<K, V> {

    // DO NOT MODIFY OR ADD NEW GLOBAL/INSTANCE VARIABLES
    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = new MapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the HashMap.
     * If an entry in the HashMap already has this key, replace the entry's
     * value with the new one passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * At the start of the method, you should check to see if the array
     * violates the max load factor. For example, let's say the array is of
     * length 5 and the current size is 3 (LF = 0.6). For this example, assume
     * that no elements are removed in between steps. If a non-duplicate key is
     * added, the size will increase to 4 (LF = 0.8), but the resize shouldn't
     * occur until the next put operation.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key key to add into the HashMap
     * @param value value to add into the HashMap
     * @throws IllegalArgumentException if key or value is null
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new java.lang.IllegalArgumentException("Invalid input of"
                    + " key or value.");
        } else {
            if ((double) size / (double) table.length > MAX_LOAD_FACTOR) {
                resizeBackingTable((table.length * 2) + 1);
            }
            MapEntry<K, V> newEntry = new MapEntry<>(key, value);

            int probe = newEntry.getKey().hashCode() % table.length;
            probe = (probe >= 0 ? probe : probe + table.length);
            boolean keepGoing = true;
            boolean savingIndex = false;
            V outValue = null;
            int savedIndex = probe;
            while (keepGoing) {
                if (table[probe] == null) {
                    if (savingIndex) {
                        //outValue = table[probe].getValue();
                        outValue = null;
                        table[savedIndex] = newEntry;
                    } else {
                        table[probe] = newEntry;
                    }
                    keepGoing = false;
                    size++;
                } else {
                    if (!table[probe].isRemoved()) {
                        if (table[probe].getKey().equals(newEntry.getKey())) {
                            outValue = table[probe].getValue();
                            table[probe] = newEntry;
                            keepGoing = false;
                        } else {
                            probe = (probe + 1) % table.length;
                        }
                    } else {
                        if (!table[probe].getKey().equals(newEntry.getKey())) {
                            if (!savingIndex) {
                                savedIndex = probe;
                                savingIndex = true;
                            }
                            probe = (probe + 1) % table.length;
                        } else {
                            outValue = table[probe].getValue();
                            if (savingIndex) {
                                table[savedIndex] = newEntry;
                            } else {
                                table[probe] = newEntry;
                            }
                            keepGoing = false;
                            size++;
                        }
                    }
                }
            }
            //size++;
            return outValue;
        }
    }


    /**
     * Removes the entry with a matching key from the HashMap.
     *
     * @param key the key to remove
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key does not exist
     * @return the value previously associated with the key
     */
    public V remove(K key) {
        if (key == null) {
            throw new java.lang.IllegalArgumentException("Cannot remove "
                    + "a value with a null key.");
        } else {
            int probe = key.hashCode() % table.length;
            probe = (probe >= 0 ? probe : probe + table.length);
            boolean keepGoing = true;
            V outValue = null;
            while (keepGoing) {
                if (table[probe] == null) {
                    throw new java.util.NoSuchElementException("Element with "
                            + "key " + key + " could not be found in the map");
                } else {
                    if (table[probe].isRemoved()) {
                        if (table[probe].getKey().equals(key)) {
                            throw new java.util.NoSuchElementException("Element"
                                    + " with key " + key + " could not be found"
                                    + "in the map");
                        } else {
                            probe = (probe + 1) % table.length;
                        }
                    } else {
                        if (table[probe].getKey().equals(key)) {
                            outValue = table[probe].getValue();
                            table[probe].setRemoved(true);
                            keepGoing = false;
                        } else {
                            probe = (probe + 1) % table.length;
                        }
                    }
                }
            }
            size--;
            return outValue;
        }
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     * @return the value associated with the given key
     */
    public V get(K key) {
        if (key == null) {
            throw new java.lang.IllegalArgumentException("Cannot get the value"
                    + " of a null key.");
        } else {
            int probe = key.hashCode() % table.length;
            probe = (probe >= 0 ? probe : probe + table.length);
            boolean keepLooking = true;
            V outValue = null;
            while (keepLooking) {
                if (table[probe] == null) {
                    throw new java.util.NoSuchElementException("Element with "
                            + "key " + key + " could not be found in the map");
                } else {
                    if (table[probe].isRemoved()) {
                        if (table[probe].getKey().equals(key)) {
                            throw new java.util.NoSuchElementException("Element"
                                    + " with key " + key + " could not be "
                                    + "found in the map");
                        } else {
                            probe = (probe + 1) % table.length;
                        }
                    } else {
                        if (table[probe].getKey().equals(key)) {
                            outValue = table[probe].getValue();
                            keepLooking = false;
                        } else {
                            probe = (probe + 1) % table.length;
                        }
                    }
                }
            }
            return outValue;
        }
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @return whether or not the key is in the map
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new java.lang.IllegalArgumentException("Cannot check "
                    + "contains if input key is null");
        } else {
            try {
                V testValue = get(key);
                return true; // If doesn't throw an error then key exists
            } catch (java.util.NoSuchElementException e) {
                return false; // If throws an error then key not in the map
            }
        }
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * Use {@code java.util.HashSet}.
     *
     * @return set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> outSet = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                outSet.add(table[i].getKey());
            }
        }
        return outSet;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use {@code java.util.ArrayList} or {@code java.util.LinkedList}.
     *
     * You should iterate over the table in order of increasing index and add 
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> outList = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                outList.add(table[i].getValue());
            }
        }
        return outList;
    }

    /**
     * Resize the backing table to {@code length}.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Remember that you cannot just simply copy the entries over to the new
     * array.
     *
     * Also, since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't need to check for duplicates.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is non-positive or less than
     * the number of items in the hash map.
     */
    public void resizeBackingTable(int length) {
        if (length <= 0 || length < size) {
            throw new java.lang.IllegalArgumentException("Cannot resize backing"
                    + "table with invalid new length");
        } else {
            MapEntry<K, V>[] newTable = new MapEntry[length];
            int newIndex = 0;
            for (int i = 0; i < table.length; i++) {
                if (table[i] != null && !table[i].isRemoved()) {
                    newIndex = table[i].getKey().hashCode() % length;
                    newIndex = (newIndex >= 0 ? newIndex : newIndex + length);
                    while (newTable[newIndex] != null) {
                        newIndex = (newIndex + 1) % length;
                    }
                    newTable[newIndex] = table[i];
                }
            }
            table = newTable;
        }
    }

    /**
     * Clears the table and resets it to the default length.
     */
    public void clear() {
        table = new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }
    
    /**
     * Returns the number of elements in the map.
     *
     * DO NOT USE OR MODIFY THIS METHOD!
     *
     * @return number of elements in the HashMap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * DO NOT USE THIS METHOD IN YOUR CODE. IT IS FOR TESTING ONLY.
     *
     * @return the backing array of the data structure, not a copy.
     */
    public MapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

}