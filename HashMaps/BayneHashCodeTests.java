import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class BayneHashCodeTests {
    private HashMap<Integer, String> map;
    private static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        map = new HashMap<>();
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.put(3, "D");
        map.put(4, "E");
    }

    @Test
    public void testPutWithDuplicateAndRemoved() {
        map.put(17, "Same index as E when hashing with default table size");
        map.remove(4);
        String putResult = map.put(17, "This line could create duplicate key entries");
        assertNotEquals(null, putResult);
        assertEquals("Same index as E when hashing with default table size", putResult);
    }

    @Test
    public void testPutWithWraparound() {
        map.put(12, "Last index with default size");
        map.put(25, "Index 0 with wraparound");
        String putResult = map.put(25, "bweh");
        assertEquals("Index 0 with wraparound", putResult);
    }

    @Test
    public void testRemoveWithWrapAround() {
        map.put(12, "Last index with default size");
        map.put(25, "Index 0 with wraparound");
        String rmResult = map.remove(25);
        assertEquals(map.getTable()[5].getValue(), rmResult);
        //the above line works because  the entry is just flagged as removed
        //rather than actually nulled out from the list
    }

    @Test
    public void testGetWithWrapAround() {
        map.put(12, "Last index with default size");
        map.put(25, "Index 0 with wraparound");
        map.put(25, "bweh");
        String getResult = map.get(25);
        assertEquals(map.getTable()[5].getValue(), getResult);
    }

    @Test
    public void testContainsKeyWithWrapAround() {
        map.put(12, "Last index with default size");
        map.put(25, "Index 0 with wraparound");
        map.put(25, "bweh");
        assertTrue(map.containsKey(25));
        assertTrue(map.containsKey(12));
    }
}
