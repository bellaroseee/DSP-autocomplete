package autocomplete;

import java.util.Comparator;

public interface Term extends Comparable<Term> {

    /** Compares the two terms in lexicographic order by query. */
    default int compareTo(Term that) {
        return this.query().compareTo(that.query());
        //        int length = 0;
        //        if (this.query().length() > that.query().length()) {
        //            length = that.query().length();
        //        } else {
        //            length = this.query().length();
        //        }
        //        for (int i = 0; i < length; i++) {
        //            int comparator = this.query().charAt(i) - that.query().charAt(i);
        //            if (comparator > 0) {
        //                return 1;
        //            } else if (comparator < 0) {
        //                return -1;
        //            }
        //        }
        //        return 0;
    }

    /** Compares to another term, in descending order by weight. */
    default int compareToByReverseWeightOrder(Term that) {
        if (this.weight() < that.weight()) {
            return 1;
        }
        if (this.weight() > that.weight()) {
            return -1;
        }
        return 0;
    }

    /**
     * Compares to another term in lexicographic order, but using only the first r characters
     * of each query. If r is greater than the length of any term's query, compares using the
     * term's full query.
     */
    default int compareToByPrefixOrder(Term that, int r) {
        if (r < 0) {
            throw new IllegalArgumentException("Number of prefix characters cannot be negative: " + r);
        }
        String thisString;
        String thatString;
        if (this.query().length() < r) {
            thisString = this.query();
        } else {
            thisString = this.queryPrefix(r);
        }
        if (that.query().length() < r) {
            thatString = that.query();
        } else {
            thatString = that.queryPrefix(r);
        }
        return thisString.compareTo(thatString);
    }

    /** Returns this term's query. */
    String query();

    /**
     * Returns the first r characters of this query.
     * If r is greater than the length of the query, returns the entire query.
     * @throws IllegalArgumentException if r < 0
     */
    String queryPrefix(int r);

    /** Returns this term's weight. */
    long weight();

    /** Returns a Comparator that orders terms in reverse weight order. */
    static Comparator<Term> byReverseWeightOrder() {
        return Term::compareToByReverseWeightOrder;
    }

    /** Returns a Comparator  that orders terms by prefix order. */
    static Comparator<Term> byPrefixOrder(int r) {
        return (t1, t2) -> t1.compareToByPrefixOrder(t2, r);
    }
}
