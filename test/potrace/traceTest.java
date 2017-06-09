package potrace;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class traceTest {

    @Test
    public void testDorth_infty(){
        dpoint firstPoint = new dpoint(2.0,2.0);
        dpoint secondPoint = new dpoint(3.0,3.0);
        assertEquals(new Point(-1,1),trace.dorth_infty(firstPoint,secondPoint));
    }

    @Test
    public void testDPara() {
        dpoint firstPoint = new dpoint(2.0,2.0);
        dpoint secondPoint = new dpoint(3.0,3.0);
        dpoint thirdPoint = new dpoint(2.0,2.0);
        assertEquals(0.0,trace.dpara(firstPoint,secondPoint,thirdPoint),0.000001);
    }

    @Test
    public void testDDenom() {
        dpoint firstPoint = new dpoint(2.0,2.0);
        dpoint secondPoint = new dpoint(3.0,3.0);
        assertEquals(2.0,trace.ddenom(firstPoint,secondPoint),0.000001);
    }

    @Test
    public void testCyclicWithCyclicPoints() {
        assertTrue(trace.cyclic(0,1,2));
    }

    @Test
    public void testCyclicWithNotCyclicPoints() {
        assertFalse(trace.cyclic(4,3,2));
    }

    @Test
    public void testQuadform(){
        quadform quadform = new quadform();
        for(int i = 0; i < 3 ; i++)
            for( int j = 0; j < 3; j++)
                quadform.content[i][j] = 1.0;

        assertEquals(16.0,trace.quadform(quadform,new dpoint(2.0,1.0)),0.000001);
    }

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
        trace.calc_sums(path.priv);

        assertTrue(isSumsEqual(new Double[]{0.,0.,0.,0.,0.},path.priv.sums[0]));
        assertTrue(isSumsEqual(new Double[]{0.,0.,0.,0.,0.},path.priv.sums[1]));
        assertTrue(isSumsEqual(new Double[]{0.,-1.,0.,0.,1.},path.priv.sums[2]));
        assertTrue(isSumsEqual(new Double[]{1.,-2.,1.,-1.,2.},path.priv.sums[3]));
        assertTrue(isSumsEqual(new Double[]{2.,-2.,2.,-1.,2.},path.priv.sums[4]));
    }

    public boolean isSumsEqual(Double[] should ,sums actual) {
        if (actual.x == should[0] && actual.y == should[1] && actual.x2 == should[2] && actual.xy == should[3] && actual.y2 == should[4])
            return true;
        return false;
    }
}
