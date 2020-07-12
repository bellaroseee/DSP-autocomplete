package autocomplete;

//import java.time.temporal.Temporal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BinaryRangeSearch implements Autocomplete {
    private ArrayList<Term> storage = new ArrayList<>();

    /**
     * Validates and stores the given terms.
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public BinaryRangeSearch(Collection<Term> terms) {
        if (terms == null) {
            throw new IllegalArgumentException("terms is null");
        }
        for (Term t : terms) {
            if (t == null) {
                throw new IllegalArgumentException("term is null");
            } else {
                storage.add(t);
            }
        }
        Collections.sort(storage);
    }

    /**
     * Returns all terms that start with the given prefix, in descending order of weight.
     * @throws IllegalArgumentException if prefix is null
     */
    public List<Term> allMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("prefix is null");
        }
        Term t = new SimpleTerm(prefix, 0);
        int first = Collections.binarySearch(storage, t, Term.byPrefixOrder(prefix.length())); //get the first match
        ArrayList<Term> allMatches = new ArrayList<>();
        if (prefix.length() == 0) {
            allMatches.addAll(storage);
            allMatches.sort(Term::compareToByReverseWeightOrder);
            return allMatches;
        }
        if (first < 0) {
            return allMatches;
        }
        //when building the allMatches list, want to put from leftEnd to rightEnd
        //use for loop to find left end first
        //use for loop to find right end
        //then go from i to j and add to the matches list sequentially
        int leftEnd = first;
        int rightEnd = first;
        for (int i = first - 1; i >= 0; i--) {
            if (storage.get(i).compareToByPrefixOrder(t, prefix.length()) != 0) {
                leftEnd = i + 1;
                break;
            }
        }
        for (int j = first; j < storage.size(); j++) {
            if (storage.get(j).compareToByPrefixOrder(t, prefix.length()) != 0) {
                rightEnd = j - 1;
                break;
            }
        }
        for (int k = leftEnd; k <= rightEnd; k++) {
            allMatches.add(storage.get(k));
        }

        //        allMatches.add(storage.get(first));
        //        for (int i = first - 1; i >= 0; i--) {
        //            if (storage.get(i).compareToByPrefixOrder(t, prefix.length()) == 0) {
        //                allMatches.add(storage.get(i));
        //            } else {
        //                break;
        //            }
        //        }
        //        for (int j = first + 1; j < storage.size(); j++) {
        //            if (storage.get(j).compareToByPrefixOrder(t, prefix.length()) == 0) {
        //                allMatches.add(storage.get(j));
        //            } else {
        //                break;
        //            }
        //        }

        allMatches.sort(Term::compareToByReverseWeightOrder);
        return allMatches;
    }

}
