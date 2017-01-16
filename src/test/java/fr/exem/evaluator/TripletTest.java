package fr.exem.evaluator;

import org.junit.Test;

import static org.junit.Assert.*;

public class TripletTest {

    @Test
    public void getField() throws Exception {
        assertEquals(2067.93, Triplet.getField("000F01012C0207FE03"), 0.01);
    }

    @Test
    public void testInterpret() throws Exception {
        assertEquals(15, Triplet.getX("000F01012C0207FE03"));
        assertEquals(300, Triplet.getY("000F01012C0207FE03"));
        assertEquals(2046, Triplet.getZ("000F01012C0207FE03"));
    }

    @Test
    public void hexToDec() throws Exception {
        assertEquals(15, Triplet.hexatoDec("000F"));
        assertEquals(300, Triplet.hexatoDec("012C"));
        assertEquals(2046, Triplet.hexatoDec("07FE"));
    }

    @Test
    public void getTrameSring() throws Exception {
        assertEquals("000F", Triplet.getXTrame("000F01012C0207FE03"));
        assertEquals("012C", Triplet.getYTrame("000F01012C0207FE03"));
        assertEquals("07FE", Triplet.getZTrame("000F01012C0207FE03"));
    }
}