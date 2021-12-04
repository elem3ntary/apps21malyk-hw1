package ua.edu.ucu.tempseries;

import junit.framework.TestCase;
import org.junit.Test;

public class LesserTest extends TestCase {

    @Test
    public void testIsSuitable() {
        Lesser lesser = new Lesser();
        assertEquals(true, lesser.isSuitable(1,2));
    }
}