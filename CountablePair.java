/**
 * Anuj Mehndiratta.
 * JHED: amehndi1
 * 600.226 Data Structures, Fall 2015, Project 3
 *
 */

/** This class is used to create pairs that can be counted in a
 *  situation when many duplicates are expected.
 *  @author CS226 Fall 2015
 *  @param <F> the data type of the first element
 *  @param <S> the data type of the second comparable element
 */

public class CountablePair<F, S extends Comparable<? super S>> 
        implements Comparable<CountablePair<F, S>> {

    /** First element in pair. **/
    private F first;
    /** Second element in pair. **/
    private S second;
    /** Frequency of the pair. **/
    private long count;

    /** Create a countable pair.
     *  @param f the first value in the pair
     *  @param s the second data member
     */
    public CountablePair(F f, S s) {
        this.first = f;
        this.second = s;
        this.count = 0;
    }
       
    /** Get the first element.
     *  @return the first value
     */
    public F first() {
        return this.first;
    }

    /** Get the second element.
     *  @return the second value
     */
    public S second() {
        return this.second;
    }

    /** Get a count of the number of occurrences of this pair
     *  within some structure.  Initial count should be 0.
     *  @return the count
     */
    public long count() {
        return this.count;
    }
    
    /** Update the number of occurrences of this pair.
     *  @param delta the amount of change to the current count; 
     *  delta positive will increase, a negative delta will decrease.
     *  @return the new value (same as calling count() after this)
     */
    public long update(int delta) {
        this.count += delta;
        return this.count;
    }

    /** Check if two pairs are the same, ignoring the count.
     *  @param o the pair with which to compare this
     *  @return true if same, false otherwise
     */
    public boolean equals(Object o) {
        CountablePair other = null;
        try {
            other = (CountablePair) o;
        } catch (ClassCastException e) {
            return false;
        }
        return this.first.equals(other.first) 
            && this.second.equals(other.second);
    }

    /** Compute and return a hashcode based on both first and second.
     *  @return the hash code value
     */
    public int hashCode() {
        return this.first.hashCode() + this.second.hashCode();
    }

    @Override
    public int compareTo(CountablePair<F, S> other) {
        return this.second.compareTo(other.second());
    } 
}
