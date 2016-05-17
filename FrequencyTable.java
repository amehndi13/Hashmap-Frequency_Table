/**
 * Anuj Mehndiratta.
 * JHED: amehndi1
 * 600.226 Data Structures, Fall 2015, Project 3
 *
 */
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Iterator;

/** Create a table to keep track of the frequency of paired elements,
 *  where the type of the second pair must be comparable.
 *  Contains an inner class of CountablePair objects.
 *  @param <F> the data type of the first element in each pair
 *  @param <S> the data type of the second element in each pair
 */
public class FrequencyTable<F, S extends Comparable<? super S>> {

    /** Frequency table. */
    private HashMapOpen<F, TreeSet<CountablePair<F, S>>> hashMapOpen;
    /** Max load factor. */
    private float maxLoadFactor;
    /** Sum. */
    private int sum;
    /** Number of unique countable pairs. */
    private int numItems;

    /** Create a new FrequencyTable.
     *  @param maxLoad the maximum load at all times for the inner
     *  table that uses open addressing for the first slots.
     */
    public FrequencyTable(float maxLoad) {
        this.maxLoadFactor = maxLoad;
        this.hashMapOpen = new HashMapOpen<F, 
                TreeSet<CountablePair<F, S>>>(maxLoad);
        this.sum = 0;
        this.numItems = 0;
    }

    /** Insert this pair of values into the table, creating a new
     *  CountablePair if none of the existing ones match, or updating
     *  a matching one.
     *  @param first the first data element of the pair
     *  @param second the second data value
     */
    public void insert(F first, S second) {
        CountablePair<F, S> countablePair = 
                new CountablePair<F, S>(first, second);
        TreeSet<CountablePair<F, S>> treeSet = this.hashMapOpen.get(first);
        if (treeSet == null) {
            treeSet = new TreeSet<CountablePair<F, S>>();
            this.hashMapOpen.put(first, treeSet);
        }
        if (treeSet.contains(countablePair)) { 
            countablePair = treeSet.ceiling(countablePair);
            countablePair.update(1);
        } else {
            countablePair.update(1);
            treeSet.add(countablePair);
            this.numItems++;
        }
        this.sum++;
    }

    /** Remove (one count) of this pair from the table, decrementing
     *  the count by one and deleting the CountablePair entirely if
     *  the count is 0.
     *  @param first the first data element of the pair
     *  @param second the second data value
     */
    public void remove(F first, S second) {
        CountablePair<F, S> countablePair = 
                new CountablePair<F, S>(first, second);
        TreeSet<CountablePair<F, S>> treeSet = this.hashMapOpen.get(first);
        if (treeSet != null && treeSet.contains(countablePair)) {
            countablePair = treeSet.ceiling(countablePair);
            countablePair.update(-1);
            if (countablePair.count() == 0) {
                treeSet.remove(countablePair);
                this.numItems--;
            }
            this.sum--;
        }
    }

    /** See if a pair is in the table.
     *  @param first the first value of the pair
     *  @param second the second data value
     *  @return true if there, false otherwise
     */
    public boolean contains(F first, S second) {
        CountablePair<F, S> countablePair = 
                new CountablePair<F, S>(first, second);
        TreeSet<CountablePair<F, S>> treeSet = this.hashMapOpen.get(first);
        if (treeSet != null && treeSet.contains(countablePair)) {
            return true;
        }
        return false;
    }

    /** Count the frequency of a particular pair.
     *  @param first the first element in the pair
     *  @param second the second element
     *  @return the count
     */
    public long countPair(F first, S second) {
        CountablePair<F, S> countablePair = 
                new CountablePair<F, S>(first, second);
        TreeSet<CountablePair<F, S>> treeSet = this.hashMapOpen.get(first);
        if (treeSet == null || !treeSet.contains(countablePair)) {
            return 0;
        }
        countablePair = treeSet.ceiling(countablePair);
        return countablePair.count();
    }

    /** Count the cumulative frequency of pairs with a particular first element.
     *  @param first the first element in the pair
     *  @return the count
     */
    public long countFirst(F first) {
        int counter = 0;
        TreeSet<CountablePair<F, S>> treeSet = this.hashMapOpen.get(first);
        if (treeSet != null) { 
            Iterator<CountablePair<F, S>> iterator = treeSet.iterator(); 
            while (iterator.hasNext()) {
                counter += iterator.next().count();
            }
        }
        return counter;
    }

    /** Count the cumulative frequency of all pairs in the table.
     *  @return the count
     */
    public long countAll() {
        return this.sum;
    }

    /** Return an ordered set of all the second values associated with
     *  a particular first value in the table.
     *  @param first the first value of the pairs we are exploring
     *  @return an ordered set of all the second values paired with first
     */
    public SortedSet<S> getAll(F first) {
        SortedSet<S> sortedSet = new TreeSet<S>();
        TreeSet<CountablePair<F, S>> treeSet = this.hashMapOpen.get(first);
        if (treeSet != null) { 
            Iterator<CountablePair<F, S>> iterator = treeSet.iterator(); 
            while (iterator.hasNext()) {
                sortedSet.add(iterator.next().second());
            }
        }
        return sortedSet;
    }

    /** How many unique CountablePairs are in the table? (not frequencies)
     *  @return the number of pairs
     */
    public int size() { // add treeset size's
        return this.numItems;
    }

    /** Empty the table of all CountablePairs.
     */
    public void clear() {
        this.hashMapOpen = new HashMapOpen<F, 
                TreeSet<CountablePair<F, S>>>(this.maxLoadFactor);
        this.sum = 0;
        this.numItems = 0;
    }

    /** Check if the table is empty.
     *  @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return this.sum == 0;
    }

}