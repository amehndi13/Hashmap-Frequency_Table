/**
 * Anuj Mehndiratta.
 * JHED: amehndi1
 * 600.226 Data Structures, Fall 2015, Project 3
 *
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FrequencyTableTest {
    static FrequencyTable<String, String> testTable; 
    static String[] testStringArray = {"zro", "one", "two", "tre", "for", "fyv", "six"};
    static String[] testStringArray2 = {"0", "1", "2", "3", "4", "5", "6"};
    
    @Before
    public void setup() {
        testTable = new FrequencyTable<String, String>(.4f);
    }
    
    @Test
    public void testClear() {
        assertTrue(testTable.isEmpty());
        for (int i = 0; i < testStringArray.length; i++) {
            testTable.insert(testStringArray[i], testStringArray2[i]);
        }
        int size = testTable.size();
        assertEquals(size, testTable.size());
        assertTrue(size == testStringArray.length);
        testTable.clear();
        assertTrue(testTable.isEmpty());
        assertTrue(testTable.size() == 0);
        assertTrue(testTable.countAll() == 0);
        for (int i = 0; i < testStringArray.length; i++) {
            assertEquals("[]", testTable.getAll(testStringArray[i]).toString());
            assertFalse(testTable.contains(testStringArray[i], testStringArray2[i]));
            assertTrue(testTable.countFirst(testStringArray[i]) == 0);
            assertTrue(testTable.countPair(testStringArray[i], testStringArray2[i]) == 0);
            assertTrue(testTable.isEmpty());
            
        }
        
    }
    
    @Test
    public void testContains() {
        int size = testTable.size();
        assertTrue(size == 0); 
        assertTrue(testTable.isEmpty()); 
        for (int i = 0; i < testStringArray.length; i++) {
            testTable.insert(testStringArray[i], testStringArray2[i]);
            size++;
            assertTrue(size == testTable.size());
        }
        for (int i = 0; i < testStringArray.length; i++) {
            assertTrue(testTable.contains(testStringArray[i], testStringArray2[i]));
            assertFalse(testTable.isEmpty());
            assertTrue(testTable.size() != 0);
        }
        for (int i = 0; i < testStringArray.length; i++) {
            testTable.remove(testStringArray[i], testStringArray2[i]);
            size--;
            assertTrue(size == testTable.size());
            assertFalse(testTable.contains(testStringArray[i], testStringArray2[i]));
        }
        for (int i = 0; i < testStringArray.length; i++) {
            assertEquals("[]", testTable.getAll(testStringArray[i]).toString());
            assertFalse(testTable.contains(testStringArray[i], testStringArray2[i]));
            assertTrue(testTable.countFirst(testStringArray[i]) == 0);
            assertTrue(testTable.countPair(testStringArray[i], testStringArray2[i]) == 0);
            assertTrue(testTable.isEmpty());
            
        }
        
        testTable.clear();
        assertTrue(testTable.isEmpty());
        assertEquals(testTable.size(), 0);
    }

     @Test
    public void testEmptyFT() {
        testEmptyMetrics(.7f);
        testEmptyMetrics(.4f);
        String test1 = "Anuj";
        String test2 = "Mehndiratta";
        assertEquals("[]", testTable.getAll("Anuj").toString());
        testTable.remove(test1, test2);
        assertFalse(testTable.contains(test1, test2));
        assertTrue(testTable.isEmpty());
        assertEquals(testTable.size(), 0);
        assertEquals("[]", testTable.getAll(test2).toString());
        assertEquals(testTable.countAll(), 0);
        assertEquals(testTable.countFirst(test1), 0);
        assertEquals(testTable.countPair(test1, test2), 0);
        assertEquals(testTable.countPair(test2, test1), 0); 
        testTable.clear();
        assertTrue(testTable.isEmpty());
        assertEquals(testTable.size(), 0); 
    }

    private void testEmptyMetrics(float lf) {
        FrequencyTable<Integer, String> em = new FrequencyTable<Integer, String>(lf);
        assertTrue(em.isEmpty());
        assertTrue(em.size() == 0);
        
    }
    
    
    @Test
    public void testCounts() {
        for (int i = 0; i < testStringArray.length; i++) {
            testTable.insert(testStringArray[i], testStringArray2[i]);
            assertTrue(testTable.contains(testStringArray[i], testStringArray2[i]));
            assertFalse(testTable.isEmpty());
        }
        for (int i = 0; i < testStringArray.length; i++) {
            assertTrue(testTable.contains(testStringArray[i], testStringArray2[i]));
            assertTrue(testTable.countPair(testStringArray[i], testStringArray[i]) == 0);
            assertFalse(testTable.contains(testStringArray[i], testStringArray[i]));
            assertTrue(testTable.countPair(testStringArray[i], testStringArray2[i]) == 1);
                         
        }
        for (int i = 0; i < testStringArray.length; i++) {
            testTable.insert(testStringArray[0], testStringArray2[i]);
            testTable.insert(testStringArray[1], testStringArray2[i]);
            testTable.insert(testStringArray[2], testStringArray2[i]);
        }
        assertEquals(testTable.countFirst(testStringArray[0]), 8);
        assertEquals(testTable.countFirst(testStringArray[1]), 8);
        assertEquals(testTable.countFirst(testStringArray[2]), 8);
        assertTrue(testTable.countFirst(testStringArray[3]) == 1);
        assertTrue(testTable.countFirst(testStringArray[4]) == 1);
        assertTrue(testTable.countFirst(testStringArray[5]) == 1);
        assertTrue(testTable.countFirst(testStringArray[6]) == 1);
        for (int i = 0; i < 3; i++) {
            assertEquals(testTable.countPair(testStringArray[i], testStringArray2[i]), 2);
            assertEquals(testTable.countFirst(testStringArray[i]), 8);
        }
        for (int i = 3; i < testStringArray.length; i++) {
            assertEquals(testTable.countPair(testStringArray[i], testStringArray2[i]), 1);
            assertEquals(testTable.countFirst(testStringArray[i]), 1);
        }
    }
    
    @Test
    public void testGetAll() {
        testTable.clear();
        assertTrue(testTable.isEmpty());
        assertTrue(testTable.size() == 0);
        for (int i = 0; i < testStringArray.length; i++) {
            assertEquals("[]", testTable.getAll(testStringArray[i]).toString());
        }
        for (int i = 0; i < testStringArray.length; i++) {
            testTable.insert(testStringArray[i], testStringArray2[i]);
            assertNotNull(testTable.getAll(testStringArray[i]));
            assertEquals("[" + testStringArray2[i] + "]", testTable.getAll(testStringArray[i]).toString());
        }
        testTable.clear();
        for (int i = 0; i < testStringArray.length; i++) {
            testTable.insert(testStringArray[0], testStringArray2[i]);
        }
        testTable.clear();
        assertTrue(testTable.isEmpty());
        assertEquals(0, testTable.size());
        
    }
    
    @Test
    public void testSize() {
        testTable.clear();
        assertTrue(testTable.isEmpty());
        assertEquals(0, testTable.size());
        int size = 0;
        for (int i = 0; i < testStringArray.length; i++) {
            testTable.insert(testStringArray[i], testStringArray2[i]);
            size++;
            assertEquals(testTable.size(), size);
            assertEquals(testTable.countAll(), size);
        }
        for (int i = 0; i < testStringArray.length; i++) {
            testTable.insert(testStringArray[i], testStringArray2[i]);
            assertEquals(testTable.size(), size); 
            assertEquals(testTable.countAll(), size + i + 1); 
        }
        assertEquals(testTable.size(), size);
        assertEquals(testTable.size(), 7);
        assertEquals(testTable.countAll(), 2 * size);
        assertEquals(testTable.countAll(), 14);
        assertTrue(testTable.countAll() != testTable.size());
        testTable.insert(testStringArray[0], testStringArray2[1]);
        size++;
        assertEquals(testTable.size(), size);
        testTable.insert(testStringArray[0], testStringArray2[1]);
        assertEquals(testTable.size(), size);
        assertEquals(testTable.countAll(), 16);
        assertEquals(testTable.size(), 8);
        testTable.remove(testStringArray[0], testStringArray2[1]);
        assertEquals(testTable.countAll(), 15);
        assertEquals(testTable.size(), 8);
        testTable.remove(testStringArray[0], testStringArray2[1]);
        assertEquals(testTable.countAll(), 14);
        assertEquals(testTable.size(), 7);
        testTable.clear();
    }

    @Test
    public void testRemove() {
        int size = 0;
        for (int i = 0; i < testStringArray.length; i++) {
            testTable.insert(testStringArray[i], testStringArray2[i]);
            testTable.insert(testStringArray[i], testStringArray2[i]);
        }
        assertTrue(testTable.size() == 7);
        assertTrue(testTable.countAll() == 14);
        int count = 14;
        size = 7;
        for (int i = 0; i < testStringArray.length; i++) {
            testTable.remove(testStringArray[i], testStringArray2[i]);
            count--;
            assertTrue(testTable.size() == size);
            assertTrue(testTable.countAll() == count);
        }
        for (int i = 0; i < testStringArray.length; i++) {
            testTable.remove(testStringArray[i], testStringArray2[i]);
            count--;
            size--;
            assertTrue(testTable.size() == size);
            assertTrue(testTable.countAll() == count);
        }
        assertTrue(testTable.isEmpty());
        assertTrue(testTable.countAll() == 0);
        assertTrue(testTable.size() == 0);
        testTable.clear();
        assertTrue(testTable.countAll() == 0);
        assertTrue(testTable.size() == 0);
        assertTrue(testTable.isEmpty());
    }

     @Test
    public void testInsert() {
        testTable.clear();
        assertTrue(testTable.isEmpty());
        assertEquals(testTable.size(), 0);
        testTable.clear();
        for (int i = 0; i < testStringArray.length; i++) {
            assertFalse(testTable.contains(testStringArray[i], testStringArray2[i])); 
            assertEquals(testTable.countPair(testStringArray[i], testStringArray2[i]), 0);
            testTable.insert(testStringArray[i], testStringArray2[i]); 
            assertEquals(testTable.countPair(testStringArray[i], testStringArray2[i]), 1);
            assertTrue(testTable.contains(testStringArray[i], testStringArray2[i])); 
        }
        for (int i = 0; i < testStringArray.length; i++) {
            assertTrue(testTable.contains(testStringArray[i], testStringArray2[i]));
            assertEquals(testTable.countPair(testStringArray[i], testStringArray2[i]), 1);
            testTable.insert(testStringArray[i], testStringArray2[i]);
            assertEquals(testTable.countPair(testStringArray[i], testStringArray2[i]), 2); 
            assertTrue(testTable.contains(testStringArray[i], testStringArray2[i]));
        }
        for (int i = 0; i < testStringArray.length; i++) {
            assertEquals(testTable.countFirst(testStringArray[i]), 2);
        }
        assertFalse(testTable.contains(testStringArray[0], testStringArray2[1]));
        testTable.insert(testStringArray[0], testStringArray2[1]);
        assertTrue(testTable.contains(testStringArray[0], testStringArray2[1]));
        assertEquals(testTable.countPair(testStringArray[0], testStringArray2[0]), 2); 
        assertEquals(testTable.countPair(testStringArray[0], testStringArray2[1]), 1); 
        assertEquals(testTable.countFirst(testStringArray[0]), 3); 
        assertEquals(testTable.countAll(), 15);
        assertEquals(testTable.size(), 8);
        testTable.clear();
        assertTrue(testTable.isEmpty());
    }
    
}

