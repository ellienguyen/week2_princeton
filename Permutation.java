/**
 * Created by admin on 4/24/17.
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        int n = Integer.parseInt(args[0]);
        String[] input = StdIn.readAllStrings();
        for (int i = 0; i < input.length; i++) {
            q.enqueue(input[i]);
        }
        for (int i = 0; i < n; i++) {
            StdOut.println(q.dequeue());
        }

    }

}
