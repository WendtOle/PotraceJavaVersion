package potrace;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class auxiliaryTest {
    @Test
    public void testMethod_Mod_withPositivInput() {
        assertEquals(4,auxiliary.mod(4,9));
        assertEquals(7,auxiliary.mod(57,10));
    }

    @Test
    public void testMethod_Mod_withNegativInput() {
        assertEquals(5,auxiliary.mod(-4,9));
        assertEquals(3,auxiliary.mod(-57,10));
    }

    @Test
    public void testMethod_Sign_withPostivInput() {
        assertEquals(1,auxiliary.sign(4));
        assertEquals(1,auxiliary.sign(4.4));
        assertEquals(1,auxiliary.sign(0.1));
    }

    @Test
    public void testMethod_Sign_withNegativInput() {
        assertEquals(-1,auxiliary.sign(-4));
        assertEquals(-1,auxiliary.sign(-4.3));
        assertEquals(-1,auxiliary.sign(-0.1));
    }

    @Test
    public void testMethod_Sign_withZeroAsInput() {
        assertEquals(0,auxiliary.sign(0));
        assertEquals(0,auxiliary.sign(0.0));
    }

    @Test
    public void testMethod_Abs() {
        assertEquals(5637,auxiliary.abs(5637));
        assertEquals(5637,auxiliary.abs(-5637));
    }

    @Test
    public void testMethod_Floordiv_withPositivInput() {
        assertEquals(0,auxiliary.floordiv(5,6));
        assertEquals(1,auxiliary.floordiv(8,5));
    }

    @Test
    public void testMethod_Floordiv_withNegativInput() {
        assertEquals(-1,auxiliary.floordiv(-5,6));
        assertEquals(-2,auxiliary.floordiv(-8,5));
    }

    @Test
    public void testMethod_Min() {
        assertEquals(5,auxiliary.min(5,6));
        assertEquals(5,auxiliary.min(8,5));
        assertEquals(5,auxiliary.min(5,5));
        assertEquals(-8,auxiliary.min(-8,5));
        assertEquals(-5,auxiliary.min(8,-5));
    }

    @Test
    public void testMethod_Interval()  {
        dpoint firstPoint = new dpoint(1.2,3.4);
        dpoint secondPoint = new dpoint(3.5,8.1);
        double lampda = 0.5;

        dpoint expected = new dpoint(2.3499999999999996,5.75);
        dpoint actual = auxiliary.interval(lampda,firstPoint,secondPoint);

        assertEquals(expected.x, actual.x,0);
        assertEquals(expected.y, actual.y,0);
    }
}
