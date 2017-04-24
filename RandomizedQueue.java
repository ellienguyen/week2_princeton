/******************************************************************************
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:    java RandomizedQueue < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/13stacks/tobe.txt
 *
 *  Stack implementation with a resizing array.
 *
 *  % more tobe.txt
 *  to be or not to - be - - that - - - is
 *
 *  % java RandomizedQueue < tobe.txt
 *  to be not that or be (2 left on stack)
 *
 ******************************************************************************/

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 *  The {@code RandomizedQueue} class represents a last-in-first-out (LIFO) stack
 *  of generic items.
 *  It supports the usual <em>push</em> and <em>pop</em> operations, along with methods
 *  for peeking at the top item, testing if the stack is empty, and iterating through
 *  the items in LIFO order.
 *  <p>
 *  This implementation uses a resizing array, which double the underlying array
 *  when it is full and halves the underlying array when it is one-quarter full.
 *  The <em>push</em> and <em>pop</em> operations take constant amortized time.
 *  The <em>size</em>, <em>peek</em>, and <em>is-empty</em> operations takes
 *  constant time in the worst case.
 *  <p>
 *  For additional documentation,
 *  see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;         // array of items
    private int n;            // number of elements on stack


    /**
     * Initializes an empty stack.
     */
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
    }

    /**
     * Is this stack empty?
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Returns the number of items in the stack.
     * @return the number of items in the stack
     */
    public int size() {
        return n;
    }


    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= n;

        // textbook implementation
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;

        // alternative implementation
        // a = java.util.Arrays.copyOf(a, capacity);
    }



    /**
     * Adds the item to this stack.
     * @param item the item to add
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException("Adding null");
        }
        if (n == a.length) resize(2*a.length);    // double size of array if necessary
        a[n++] = item;                            // add item
    }

    /**
     * Removes and returns the item most recently added to this stack.
     * @return the item most recently added
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item;
        if (n == 1) {
            item = a[0];
            n--;
        } else {
            int randomIndex = StdRandom.uniform(0,n);
            item = a[randomIndex];
            a[randomIndex] = a[n-1];
            a[n - 1] = null;
            n--;
        }
        // shrink size of array if necessary
        if (n > 0 && n == a.length/4) resize(a.length/2);
        return item;
    }


    /**
     * Returns (but does not remove) the item most recently added to this stack.
     * @return the item most recently added to this stack
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return a[StdRandom.uniform(0,n)];
    }

    /**
     * Returns an iterator to this stack that iterates through the items in LIFO order.
     * @return an iterator to this stack that iterates through the items in LIFO order.
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i;
        private Item[] array;

        public RandomizedQueueIterator() {
            array = Arrays.copyOf(a, n);
            StdRandom.shuffle(array);
            i = 0;
        }

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return array[i++];
        }
    }


    /**
     * Unit tests the {@code Stack} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        RandomizedQueue<String> strings = new RandomizedQueue<String>();
        strings.enqueue("One");
        for(String e: strings) {
            StdOut.print(e);
        }
    }
}