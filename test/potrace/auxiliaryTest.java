package potrace;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 21/03/2017.
 */
public class auxiliaryTest {
    @Test
    public void testMod_positivInput() throws Exception {
        assertEquals(4,auxiliary.mod(4,9));
        assertEquals(7,auxiliary.mod(57,10));
    }

    @Test
    public void testMod_negativInput() throws Exception {
        assertEquals(5,auxiliary.mod(-4,9));
        assertEquals(3,auxiliary.mod(-57,10));
    }

    @Test
    public void testSign_postiv() throws Exception {
        assertEquals(1,auxiliary.sign(4));
        assertEquals(1,auxiliary.sign(4.4));
        assertEquals(1,auxiliary.sign(0.1));
    }

    @Test
    public void testSign_negativ() throws Exception {
        assertEquals(-1,auxiliary.sign(-4));
        assertEquals(-1,auxiliary.sign(-4.3));
        assertEquals(-1,auxiliary.sign(-0.1));
    }

    @Test
    public void testSign_zero() throws Exception {
        assertEquals(0,auxiliary.sign(0));
        assertEquals(0,auxiliary.sign(0.0));
    }

    @Test
    public void testAbs() throws Exception {
        assertEquals(5637,auxiliary.abs(5637));
        assertEquals(5637,auxiliary.abs(-5637));
    }

    @Test
    public void testFloordiv_pos() throws Exception {
        assertEquals(0,auxiliary.floordiv(5,6));
        assertEquals(1,auxiliary.floordiv(8,5));
    }

    @Test
    public void testFloordiv_neg() throws Exception {
        assertEquals(-1,auxiliary.floordiv(-5,6));
        assertEquals(-2,auxiliary.floordiv(-8,5));
    }

    @Test
    public void testMin() throws Exception {
        assertEquals(5,auxiliary.min(5,6));
        assertEquals(5,auxiliary.min(8,5));
        assertEquals(5,auxiliary.min(5,5));
        assertEquals(-8,auxiliary.min(-8,5));
        assertEquals(-5,auxiliary.min(8,-5));
    }

    @Test
    public void testInterval() throws Exception {
        dpoint firstPoint = new dpoint(1.2,3.4);
        dpoint secondPoint = new dpoint(3.5,8.1);
        double lampda = 0.5;

        dpoint expected = new dpoint(2.3499999999999996,5.75);
        dpoint actual = auxiliary.interval(lampda,firstPoint,secondPoint);

        assertEquals(expected.x, actual.x,0);
        assertEquals(expected.y, actual.y,0);
    }
}
