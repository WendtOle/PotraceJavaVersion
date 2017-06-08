package potrace;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class traceTest {

    @Test
    public void testXProd() {
        Point firstPoint = new Point(2,2);
        Point secondPoint = new Point(3,3);
        assertEquals(0,trace.xprod(firstPoint,secondPoint));
    }

    @Test
    public void testCProd() {
        dpoint firstPoint = new dpoint(2.0,2.0);
        dpoint secondPoint = new dpoint(3.0,3.0);
        dpoint thirdPoint = new dpoint(2.0,2.0);
        dpoint fourthPoint = new dpoint(3.0,2.0);
        assertEquals(-1.0,trace.cprod(firstPoint,secondPoint,thirdPoint,fourthPoint),0.000001);
    }

    @Test
    public void testIProd() {
        dpoint firstPoint = new dpoint(2.0,2.0);
        dpoint secondPoint = new dpoint(3.0,3.0);
        dpoint thirdPoint = new dpoint(2.0,2.0);
        assertEquals(0.0,trace.iprod(firstPoint,secondPoint,thirdPoint),0.000001);
    }

    @Test
    public void testIProd1() {
        dpoint firstPoint = new dpoint(2.0,2.0);
        dpoint secondPoint = new dpoint(3.0,3.0);
        dpoint thirdPoint = new dpoint(2.0,2.0);
        dpoint fourthPoint = new dpoint(3.0,2.0);
        assertEquals(1.0,trace.iprod1(firstPoint,secondPoint,thirdPoint,fourthPoint),0.000001);
    }

    @Test
    public void testDdist() {
        dpoint firstPoint = new dpoint(2.0,2.0);
        dpoint secondPoint = new dpoint(3.0,3.0);
        assertEquals(1.41421356,trace.ddist(firstPoint,secondPoint),0.000001);
    }

    @Test
    public void testbezier() {
        dpoint firstPoint = new dpoint(2.0,2.0);
        dpoint secondPoint = new dpoint(3.0,3.0);
        dpoint thirdPoint = new dpoint(2.0,2.0);
        dpoint fourthPoint = new dpoint(3.0,2.0);
        dpoint resultPoint = trace.bezier(0.5,firstPoint,secondPoint,thirdPoint,fourthPoint);
        assertEquals("x",2.5,resultPoint.x,0.0000001);
        assertEquals("y",2.375,resultPoint.y,0.0000001);
    }

    @Test
    public void testtangent() {
        dpoint firstPoint = new dpoint(2.0,2.0);
        dpoint secondPoint = new dpoint(3.0,3.0);
        dpoint thirdPoint = new dpoint(2.0,2.0);
        dpoint fourthPoint = new dpoint(3.0,2.0);
        dpoint fifthPoint = new dpoint(2.0,2.0);
        dpoint sixthhPoint = new dpoint(3.0,2.0);
        assertEquals(0.333333333333,trace.tangent(fifthPoint,secondPoint,thirdPoint,fourthPoint,fifthPoint,sixthhPoint),0.0000001);
    }

    @Test
    public void test_calc_sums() {
        bitmap testBitmap = new bitmap(2,2);
        path path = decompose.findpath(testBitmap,0,2,43,4);
        privepath privepath = trace.calc_sums(path.priv);

        assertTrue(isSumsEqual(new Double[]{0.,0.,0.,0.,0.},privepath.sums[0]));
        assertTrue(isSumsEqual(new Double[]{0.,0.,0.,0.,0.},privepath.sums[1]));
        assertTrue(isSumsEqual(new Double[]{0.,-1.,0.,0.,1.},privepath.sums[2]));
        assertTrue(isSumsEqual(new Double[]{1.,-2.,1.,-1.,2.},privepath.sums[3]));
        assertTrue(isSumsEqual(new Double[]{2.,-2.,2.,-1.,2.},privepath.sums[4]));
    }

    public boolean isSumsEqual(Double[] should ,sums actual) {
        if (actual.x == should[0] && actual.y == should[1] && actual.x2 == should[2] && actual.xy == should[3] && actual.y2 == should[4])
            return true;
        return false;
    }

    @Test
    public void test_calc_lon() {
        bitmap testBitmap = new bitmap(2,2);
        path path = decompose.findpath(testBitmap,0,2,43,4);
        privepath privepath = trace.calc_sums(path.priv);
        privepath = trace.calc_lon(privepath);

        assertEquals(3,privepath.lon[0]);
        assertEquals(0,privepath.lon[1]);
        assertEquals(1,privepath.lon[2]);
        assertEquals(2,privepath.lon[3]);
    }
}
