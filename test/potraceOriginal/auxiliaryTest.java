package potraceOriginal;

import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.assertEquals;

public class auxiliaryTest {
    @Test
    public void testMethod_Mod_withPositivInput() {
        assertEquals(4, Auxiliary.mod(4,9));
        assertEquals(7, Auxiliary.mod(57,10));
    }

    @Test
    public void testMethod_Mod_withNegativInput() {
        assertEquals(5, Auxiliary.mod(-4,9));
        assertEquals(3, Auxiliary.mod(-57,10));
    }

    @Test
    public void testMethod_Sign_withPostivInput() {
        assertEquals(1, Auxiliary.sign(4));
        assertEquals(1, Auxiliary.sign(4.4));
        assertEquals(1, Auxiliary.sign(0.1));
    }

    @Test
    public void testMethod_Sign_withNegativInput() {
        assertEquals(-1, Auxiliary.sign(-4));
        assertEquals(-1, Auxiliary.sign(-4.3));
        assertEquals(-1, Auxiliary.sign(-0.1));
    }

    @Test
    public void testMethod_Sign_withZeroAsInput() {
        assertEquals(0, Auxiliary.sign(0));
        assertEquals(0, Auxiliary.sign(0.0));
    }

    @Test
    public void testMethod_Abs() {
        assertEquals(5637, Auxiliary.abs(5637));
        assertEquals(5637, Auxiliary.abs(-5637));
    }

    @Test
    public void testMethod_Floordiv_withPositivInput() {
        assertEquals(0, Auxiliary.floordiv(5,6));
        assertEquals(1, Auxiliary.floordiv(8,5));
    }

    @Test
    public void testMethod_Floordiv_withNegativInput() {
        assertEquals(-1, Auxiliary.floordiv(-5,6));
        assertEquals(-2, Auxiliary.floordiv(-8,5));
    }

    @Test
    public void testMethod_Min() {
        assertEquals(5, Auxiliary.min(5,6));
        assertEquals(5, Auxiliary.min(8,5));
        assertEquals(5, Auxiliary.min(5,5));
        assertEquals(-8, Auxiliary.min(-8,5));
        assertEquals(-5, Auxiliary.min(8,-5));
    }

    @Test
    public void testMethod_Interval()  {
        Point2D.Double firstPoint = new Point2D.Double(1.2,3.4);
        Point2D.Double secondPoint = new Point2D.Double(3.5,8.1);
        double lampda = 0.5;

        Point2D.Double expected = new Point2D.Double(2.3499999999999996,5.75);
        Point2D.Double actual = Auxiliary.interval(lampda,firstPoint,secondPoint);

        assertEquals(expected.x, actual.x,0);
        assertEquals(expected.y, actual.y,0);
    }
}
