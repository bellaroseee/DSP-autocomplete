package autocomplete;

import java.util.Objects;

public class SimpleTerm implements Term {
    private final String query;
    private final long weight;

    // initializes a term with the given query string and weight
    public SimpleTerm(String query, long weight) {
        if (query == null) {
            throw new IllegalArgumentException("the query is empty");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("the weight cannot be negative");
        }
        this.query = query;
        this.weight = weight;
    }

    // returns the first r characters of this query
    public String queryPrefix(int r) {
        String queryPrefix = "";
        for (int i = 0; i < r; i++) {
            queryPrefix += (query.charAt(i));
        }
        return queryPrefix;
    }

    // returns this term's query
    public String query() {
        return query;
    }

    // return this term's weight
    public long weight() {
        return weight;
    }

    @Override
    public String toString() {
        return "SimpleTerm{" +
                "query='" + query + '\'' +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleTerm that = (SimpleTerm) o;
        return weight == that.weight &&
                Objects.equals(query, that.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, weight);
    }

}
