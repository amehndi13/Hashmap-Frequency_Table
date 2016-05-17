/**
 * Anuj Mehndiratta.
 * JHED: amehndi1
 * 600.226 Data Structures, Fall 2015, Project 3
 *
 */
import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.AbstractMap;
/** Base implementation of Java7 Map using linear probe open addressing.
 *  @author CS226 Fall 2015
 *  @param <K> the base type of the keys
 *  @param <V> the base type of the values
 */
public class HashMapOpen<K, V> implements Map<K, V> {

    /* Custom methods --------------------------------- */

    /** Create an empty open addressing hash map implemention with capacity 5.
     *  @param maxLoad the maximum load factor, 0 < maxLoad < 1
     */

     /** Array of keys. */
    public K[] keyArray;
    /** Array of values. */
    public V[] valueArray;
    /** Array of ghosts. */
    public boolean[] ghostsArray;
    /** Max load factor. */
    public float maxLoadFactor;
    /** Number of items. */
    public int numItems;
    /** Initial size. */
    public final int initialSize = 5;
    /** Size of the hashmap. */
    public int size;
    /** Number of ghosts. */
    public int ghosts = 0;

    /** HashMap Constructor.
     * @param maxLoad max load factor.
     */
    public HashMapOpen(float maxLoad) {
        this.size = this.initialSize;
        this.keyArray = (K[]) new Object[this.size];
        this.valueArray = (V[]) new Object[this.size];
        this.ghostsArray = new boolean[this.size];
        this.maxLoadFactor = maxLoad;
        this.numItems = 0;
        for (int i = 0; i < this.size; i++) {
            this.ghostsArray[i] = false;    
        }
    }

    /** Get the maximum load factor.
     *  @return the load factor
     */
    public float getMaxLoad() {
        return this.maxLoadFactor;
    }

    /** Get the current load factor.
     *  @return the load factor
     */
    public float getLoad() {
        return (float) this.numItems / this.size;
    }

    /** Get the table capacity (total # of slots).
     *  @return the capacity
     */

    public int getCapacity() {
        return this.size;
    }

    /** Rehash the entries to a new table size.
     *  @param cap the capacity of the table after rehashing, cap > size()
     */
    public void rehash(int cap) {
        K[] oldKeys = this.keyArray;
        V[] oldValues = this.valueArray;
        this.keyArray = (K[]) new Object[cap];
        this.valueArray = (V[]) new Object[cap];
        this.numItems = 0;
        this.ghosts = 0;
        this.ghostsArray = new boolean[cap];
        for (int a = 0; a < cap; a++) {
            this.ghostsArray[a] = false;
        }
        int x = this.size;
        this.size = cap;
        for (int i = 0; i < x; i++) {
            if (oldKeys[i] != null && !this.ghostsArray[i]) {
                int newPos = oldKeys[i].hashCode() % cap;
                this.put(oldKeys[i], oldValues[i]);
            }
        }
    }
    

     /** Calculate the offset for the next position in which to 
     *  search             
     *  for or insert an entry, using linear probing.  
     *  The actual position searched should be 
     *  k.hashCode() + probe(k, i) for some key k.
     *  @param key the key being inserted
     *  @param i the iteration attempt (probe number)
     *  @return the offset (to be added to the key's hashcode)
     */  
    public int probe(K key, int i) {
        return i;
    }

    /* Methods from the Map interface ----------------  */
    @Override
    public void clear() {
        this.keyArray = (K[]) new Object[this.size];
        this.valueArray = (V[]) new Object[this.size];
        this.ghostsArray = new boolean[this.size];
        this.numItems = 0;
    }
    @Override
    public boolean containsKey(Object key) {
        K keyTest = (K) key;
        int index = keyTest.hashCode() % this.size; 
        if (this.keyArray[index] != null 
                && this.keyArray[index].equals(keyTest)) {
            return true;
        } else {
            for (int i = 0; i < this.size; i++) {
                index = (index + 1) % this.size;
                if (this.keyArray[index] != null 
                        && this.keyArray[index].equals(keyTest)) {
                    return true;
                }
            }
        } 
        return false;
    }
    @Override
    public boolean containsValue(Object value) {
        V valueTest = (V) value;
        for (int i = 0; i < this.size; i++) {
            if (this.valueArray[i] != null 
                        && this.valueArray[i].equals(value)) {
                return true;
            }        
        }    
        return false;
    }
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Map.Entry<K, V> temp;
        Set<Map.Entry<K, V>> entries = new HashSet<Map.Entry<K, V>>();
        for (int i = 0; i < this.keyArray.length; i++) {
            if (this.keyArray[i] != null && !this.ghostsArray[i]) { 
                temp = new AbstractMap.SimpleEntry(this.keyArray[i], 
                            this.valueArray[i]);
                entries.add(temp);
            }
        }
        return entries;
    }
    @Override
    public boolean equals(Object m) {
        if (m instanceof HashMapOpen) {
            HashMapOpen temp = (HashMapOpen) m;
            if (temp.maxLoadFactor == this.maxLoadFactor) {
                for (int i = 0; i < this.size; i++) {
                    if (this.keyArray[i] != null 
                                && temp.keyArray[i] != null 
                                && this.valueArray[i] != null 
                                && temp.valueArray[i] != null 
                                && !temp.keyArray[i].equals(this.keyArray[i])
                                && !temp.valueArray[i].equals(this.
                                        valueArray[i])) {
                        return false;
                    }
                }
                return true; 
            }
        }  
        return false;  
    }
    @Override
    public V get(Object key) {
        int index = key.hashCode() % this.size; 
        if (this.keyArray[index] != null && this.keyArray[index].equals(key)) {
            return this.valueArray[index];
        } else {
            for (int i = 0; i < this.size; i++) {
                index = (index + 1) % this.size;
                if (this.keyArray[index] != null 
                            && this.keyArray[index].equals(key)) {
                    return this.valueArray[index];
                }
            }
        } 
        return null;
    }
    @Override
    public int hashCode() {
        return this.entrySet().hashCode();
    }
    @Override
    public boolean isEmpty() {
        if (this.numItems == 0) {
            return true;
        }
        return false;
    }
    @Override
    public Set<K> keySet() {
        Set<K> newset = new HashSet<K>();
        for (int i = 0; i < this.size; i++) {
            if (this.keyArray[i] != null) {
                newset.add(this.keyArray[i]);
            }   
        }
        return newset;
    }
    @Override
    public V put(K key, V value) {
           
        boolean notplaced = true;
        int iter = 0;
        while (notplaced) {
            int location = (key.hashCode() + iter) % this.size;
            iter++;
            if (this.keyArray[location] == null) {
                notplaced = false;
                this.numItems++;
                this.keyArray[location] = key;
                this.valueArray[location] = value;
                if (this.ghostsArray[location]) {
                    this.ghosts--;
                    this.ghostsArray[location] = false;
                }
                if (this.getLoad() > this.maxLoadFactor) {
                    this.rehash(this.size * (2 + 1));
                }
            } else if (this.keyArray[location].equals(key)) {
                V temp = this.get(key);
                this.valueArray[location] = value;
                if (this.ghostsArray[location]) {
                    this.ghosts--;
                    this.ghostsArray[location] = false;
                }
                if (this.getLoad() > this.maxLoadFactor) {
                    this.rehash(this.size * (2 + 1));
                }
                return temp;
            }
        }
        
        return null;
    }
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        // optional operation - not required to implement
        throw new UnsupportedOperationException();
    }
    @Override
    public V remove(Object key) {
        if (!this.containsKey(key)) { 
            return null;
        }
        int i = key.hashCode() % this.size;
        if (i < 0) {
            i *= -1;
        }
        while ((this.keyArray[i] != null && !key.equals(this.keyArray[i])) 
                || this.ghostsArray[i]) { 

            i = (i + 1) % this.size;
        } 
        V temp = (V) this.valueArray[i];       
        this.keyArray[i] = null;
        this.valueArray[i] = null;
        this.ghostsArray[i] = true;
        this.numItems--;
        this.ghosts++;
        if (this.getLoad() > this.maxLoadFactor) {
            this.rehash(this.size * (2 + 1));
        } else if (this.ghosts() > this.numItems) {
            this.rehash(this.size);
        }
        return temp;  
    }

    /** Get the number of tombstones currently in the map 
     * (markers left behind when values were deleted).
     *  @return the number
     */
    public int ghosts() {
        return this.ghosts;
    }
    @Override
    public int size() {
        return this.numItems;
    }
    @Override
    public Collection<V> values() {
        ArrayList<V> collectionView = new ArrayList<V>();
        for (int i = 0; i < this.size; i++) {
            if (this.valueArray[i] != null) {
                collectionView.add(this.valueArray[i]);
            }

        }
        return collectionView;
    }

}

