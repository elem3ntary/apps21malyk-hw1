package ua.edu.ucu.tempseries;

import junit.framework.TestCase;
import org.junit.Test;

public class GreaterTest extends TestCase {

    @Test
    public void testIsSuitable() {
        Greater greater = new Greater();
        assertEquals(true, greater.isSuitable(7,6));
    }
}