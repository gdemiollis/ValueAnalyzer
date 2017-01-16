package fr.exem.evaluator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class CircleBufferTest {

    private CircleBuffer circleBuffer;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        circleBuffer = CircleBuffer.newInstance()
                .withSize(5)
                .withSampleSize(3)
                .build();
    }

    @Test
    public void testPushValue() throws Exception {
        assertEquals(-1, circleBuffer.getValue(0), 0);
        assertEquals(-1, circleBuffer.getValue(1), 0);
        assertEquals(-1, circleBuffer.getValue(2), 0);
        assertEquals(-1, circleBuffer.getValue(3), 0);
        assertEquals(-1, circleBuffer.getValue(4), 0);
        circleBuffer.pushValue(0);
        assertEquals(0, circleBuffer.getValue(0), 0);
        assertEquals(-1, circleBuffer.getValue(1), 0);
        assertEquals(-1, circleBuffer.getValue(2), 0);
        assertEquals(-1, circleBuffer.getValue(3), 0);
        assertEquals(-1, circleBuffer.getValue(4), 0);
        circleBuffer.pushValue(1);
        assertEquals(0, circleBuffer.getValue(0), 0);
        assertEquals(1, circleBuffer.getValue(1), 0);
        assertEquals(-1, circleBuffer.getValue(2), 0);
        assertEquals(-1, circleBuffer.getValue(3), 0);
        assertEquals(-1, circleBuffer.getValue(4), 0);
        circleBuffer.pushValue(2);
        assertEquals(0, circleBuffer.getValue(0), 0);
        assertEquals(1, circleBuffer.getValue(1), 0);
        assertEquals(2, circleBuffer.getValue(2), 0);
        assertEquals(-1, circleBuffer.getValue(3), 0);
        assertEquals(-1, circleBuffer.getValue(4), 0);
        circleBuffer.pushValue(3);
        assertEquals(0, circleBuffer.getValue(0), 0);
        assertEquals(1, circleBuffer.getValue(1), 0);
        assertEquals(2, circleBuffer.getValue(2), 0);
        assertEquals(3, circleBuffer.getValue(3), 0);
        assertEquals(-1, circleBuffer.getValue(4), 0);
        circleBuffer.pushValue(4);
        assertEquals(0, circleBuffer.getValue(0), 0);
        assertEquals(1, circleBuffer.getValue(1), 0);
        assertEquals(2, circleBuffer.getValue(2), 0);
        assertEquals(3, circleBuffer.getValue(3), 0);
        assertEquals(4, circleBuffer.getValue(4), 0);
        circleBuffer.pushValue(5);
        assertEquals(5, circleBuffer.getValue(0), 0);
        assertEquals(1, circleBuffer.getValue(1), 0);
        assertEquals(2, circleBuffer.getValue(2), 0);
        assertEquals(3, circleBuffer.getValue(3), 0);
        assertEquals(4, circleBuffer.getValue(4), 0);
    }

    @Test
    public void testGetAverage() throws Exception {
        circleBuffer.pushValue(0);
        circleBuffer.pushValue(1);
        circleBuffer.pushValue(2);
        circleBuffer.pushValue(3);
        circleBuffer.pushValue(4);
        assertEquals(2, circleBuffer.getFullAverage(), 0);
    }

    @Test
    public void getDisplayableAverage_withLessThanSampleSizeValue_throwException() throws Exception {
        assertEquals(-1, circleBuffer.getDisplayableAverage(), 0.01);
        circleBuffer.pushValue(0);
        assertEquals(-1, circleBuffer.getDisplayableAverage(), 0.01);
        circleBuffer.pushValue(0);
    }

    @Test
    public void getDisplayableAverage() throws Exception {
        circleBuffer.pushValue(0);
        circleBuffer.pushValue(1);
        circleBuffer.pushValue(2);
        assertEquals(1, circleBuffer.getDisplayableAverage(), 0);
        assertEquals(1, circleBuffer.getFullAverage(), 0);
        circleBuffer.pushValue(3);
        assertEquals(2, circleBuffer.getDisplayableAverage(), 0);
        assertEquals(1.5, circleBuffer.getFullAverage(), 0);
        circleBuffer.pushValue(4);
        assertEquals(3, circleBuffer.getDisplayableAverage(), 0);
        assertEquals(2, circleBuffer.getFullAverage(), 0);
        circleBuffer.pushValue(5);
        assertEquals(4, circleBuffer.getDisplayableAverage(), 0);
        assertEquals(3, circleBuffer.getFullAverage(), 0);
        circleBuffer.pushValue(6);
        assertEquals(5, circleBuffer.getDisplayableAverage(), 0);
        assertEquals(4, circleBuffer.getFullAverage(), 0);
    }

    @Test
    public void getFullAverage() throws Exception {
        circleBuffer.pushValue(0);
        assertEquals(-1, circleBuffer.getFullAverage(), 0);
        circleBuffer.pushValue(1);
        assertEquals(-1, circleBuffer.getFullAverage(), 0);
        circleBuffer.pushValue(2);
        assertEquals(1, circleBuffer.getFullAverage(), 0);
        circleBuffer.pushValue(3);
        assertEquals(1.5, circleBuffer.getFullAverage(), 0);
        circleBuffer.pushValue(4);
        assertEquals(2, circleBuffer.getFullAverage(), 0);
        circleBuffer.pushValue(5);
        assertEquals(3, circleBuffer.getFullAverage(), 0);
        circleBuffer.pushValue(6);
        assertEquals(4, circleBuffer.getFullAverage(), 0);
    }

    @Test
    public void getLastValue() throws Exception {
        circleBuffer.pushValue(0);
        assertEquals(0, circleBuffer.getLastValue(), 0);
        circleBuffer.pushValue(1);
        assertEquals(1, circleBuffer.getLastValue(), 0);
        circleBuffer.pushValue(2);
        assertEquals(2, circleBuffer.getLastValue(), 0);
        circleBuffer.pushValue(3);
        assertEquals(3, circleBuffer.getLastValue(), 0);
        circleBuffer.pushValue(4);
        assertEquals(4, circleBuffer.getLastValue(), 0);
        circleBuffer.pushValue(5);
        assertEquals(5, circleBuffer.getLastValue(), 0);
        circleBuffer.pushValue(6);
        assertEquals(6, circleBuffer.getLastValue(), 0);
    }

    @Test
    public void getDifference_asFirstValue_throwException() throws Exception {
        assertEquals(-1, circleBuffer.getDifferentiel(5), 0.01);
    }

    @Test
    public void getDifference() throws Exception {
        circleBuffer.pushValue(0);
        circleBuffer.pushValue(1);
        circleBuffer.pushValue(2);
        assertEquals(4, circleBuffer.getDifferentiel(5), 0);
        circleBuffer.pushValue(3);
        assertEquals(3, circleBuffer.getDifferentiel(5), 0);
        circleBuffer.pushValue(4);
        assertEquals(2, circleBuffer.getDifferentiel(5), 0);
        circleBuffer.pushValue(5);
        assertEquals(1, circleBuffer.getDifferentiel(5), 0);
        circleBuffer.pushValue(6);
        assertEquals(0, circleBuffer.getDifferentiel(5), 0);
    }
}