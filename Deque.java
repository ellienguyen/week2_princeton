/******************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:    java Deque < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/13stacks/tobe.txt
 *
 *  A generic stack, implemented using a linked list. Each stack
 *  element is of type Item.
 *
 *  % more tobe.txt
 *  to be or not to - be - - that - - - is
 *
 *  % java Deque < tobe.txt
 *  to be not that or be (2 left on stack)
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


/**
 *  The {@code Deque} class represents a last-in-first-out (LIFO) stack of
 *  generic items.
 *  It supports the usual <em>push</em> and <em>pop</em> operations, along with methods
 *  for peeking at the top item, testing if the stack is empty, and iterating through
 *  the items in LIFO order.
 *  <p>
 *  This implementation uses a singly-linked list with a non-static nested class for
 *  linked-list nodes. See  for a version that uses a static nested class.
 *  The <em>push</em>, <em>pop</em>, <em>peek</em>, <em>size</em>, and <em>is-empty</em>
 *  operations all take constant time in the worst case.
 *  <p>
 *  For additional documentation,
 *  see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Deque<Item> implements Iterable<Item> {
    private int n;          // size of the stack
    private Node first;     // top of stack
    private Node last;     // top of stack
    private Node prevLast;
    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
    }

    /**
     * Initializes an empty stack.
     */
    public Deque() {
        first = null;
        last = null;
        prevLast = null;
        n = 0;
        assert check();
    }

    /**
     * Is this stack empty?
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in the stack.
     * @return the number of items in the stack
     */
    public int size() {
        return n;
    }

    /**
     * Adds the item to this stack.
     * @param item the item to add
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException("Invalid add call");
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (n == 0) {
            last = first;
        } else if (n == 1) {
            last = oldfirst;
            prevLast = first;
        }
        n++;
        assert check();
    }

    /**
     * Adds the item to this stack.
     * @param item the item to add
     */
    public void addLast(Item item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (n == 0) {
            first = last;
        } else if (n == 1) {
            first = oldLast;
            prevLast = first;
        } else {
            prevLast = oldLast;
        }
        oldLast.next = last;
        n++;
        assert check();
    }

    /**
     * Removes and returns the item most recently added to this stack.
     * @return the item most recently added
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = first.item;        // save item to return
        first = first.next;          // delete first node
        if (n == 1) {
            last = null;
            prevLast = null;
        } else if (n == 2) {
            prevLast = null;
        }
        n--;
        assert check();
        return item;                   // return the saved item
    }

    /**
     * Removes and returns the item most recently added to this stack.
     * @return the item most recently added
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = last.item;        // save item to return
        if (prevLast == null || n == 1) {
            first = null;
            last = null;
        } else if (n == 2) {
            prevLast = null;
            first.next = null;
            last = first;
        } else {
            prevLast.next = null;
        }
        n--;
        assert check();
        return item;                   // return the saved item
    }


    /**
     * Returns an iterator to this stack that iterates through the items in LIFO order.
     * @return an iterator to this stack that iterates through the items in LIFO order.
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


    // check internal invariants
    private boolean check() {

        // check a few properties of instance variable 'first'
        if (n < 0) {
            return false;
        }
        if (n == 0) {
            if (first != null) return false;
            if (last != null) return false;
        }
        else if (n == 1) {
            if (first == null)      return false;
            if (first.next != null) return false;
            if (last == null)      return false;
        }
        else {
            if (first == null)      return false;
            if (first.next == null) return false;
            if (last == null)      return false;
        }

        return true;
    }

    /**
     * Unit tests the {@code Deque} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

    }
}


