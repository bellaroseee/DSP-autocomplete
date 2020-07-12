package autocomplete;

//import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LinearRangeSearch implements Autocomplete {
    private ArrayList<Term> storage = new ArrayList<>();

    /**
     * Validates and stores the given terms.
     * @throws IllegalArgumentException if terms is null or contains null
     */
    // stores the terms for future autocompletion by the allMatches method
    public LinearRangeSearch(Collection<Term> terms) {
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
        ArrayList<Term> allMatches = new ArrayList<>();
        if (prefix.length() == 0) {
            allMatches.addAll(storage);
            allMatches.sort(Term::compareToByReverseWeightOrder);
            return allMatches;
        }
        for (int i = 0; i < storage.size(); i++) {
            if (prefix.equals(storage.get(i).queryPrefix(prefix.length()))) {
                allMatches.add(storage.get(i));
            }
        }
        allMatches.sort(Term::compareToByReverseWeightOrder);
        return allMatches;
    }
}

