package autocomplete;

import org.junit.Test;

import java.security.PublicKey;

import static org.junit.Assert.*;

public class TermTest {
    @Test
    public void testSimpleCompareTo() {
        Term a = new SimpleTerm("autocomplete", 0);
        SimpleTerm b = new SimpleTerm("me", 0);
        assertTrue(a.compareTo(b) < 0); // "autocomplete" < "me"
    }

    @Test
    public void testNullQuery() {
        Term a = new SimpleTerm("", 0);
    }
}
