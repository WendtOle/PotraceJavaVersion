package potrace;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class traceTest {

    @Test
    public void testDorth_infty(){
        DPoint firstPoint = new DPoint(2.0,2.0);
        DPoint secondPoint = new DPoint(3.0,3.0);
        assertEquals(new Point(-1,1), Trace.dorth_infty(firstPoint,secondPoint));
    }

    @Test
    public void testDPara() {
        DPoint firstPoint = new DPoint(2.0,2.0);
        DPoint secondPoint = new DPoint(3.0,3.0);
        DPoint thirdPoint = new DPoint(2.0,2.0);
        assertEquals(0.0, Trace.dpara(firstPoint,secondPoint,thirdPoint),0.000001);
    }

    @Test
    public void testDDenom() {
        DPoint firstPoint = new DPoint(2.0,2.0);
        DPoint secondPoint = new DPoint(3.0,3.0);
        assertEquals(2.0, Trace.ddenom(firstPoint,secondPoint),0.000001);
    }

    @Test
    public void testCyclicWithCyclicPoints() {
        assertTrue(Trace.cyclic(0,1,2));
    }

    @Test
    public void testCyclicWithNotCyclicPoints() {
        assertFalse(Trace.cyclic(4,3,2));
    }

    @Test
    public void testQuadform(){
        Quadform quadform = new Quadform();
        for(int i = 0; i < 3 ; i++)
            for( int j = 0; j < 3; j++)
                quadform.content[i][j] = 1.0;

        assertEquals(16.0, Trace.quadform(quadform,new DPoint(2.0,1.0)),0.000001);
    }

    @Test
    public void testXProd() {
        Point firstPoint = new Point(2,2);
        Point secondPoint = new Point(3,3);
        assertEquals(0, Trace.xprod(firstPoint,secondPoint));
    }

    @Test
    public void testCProd() {
        DPoint firstPoint = new DPoint(2.0,2.0);
        DPoint secondPoint = new DPoint(3.0,3.0);
        DPoint thirdPoint = new DPoint(2.0,2.0);
        DPoint fourthPoint = new DPoint(3.0,2.0);
        assertEquals(-1.0, Trace.cprod(firstPoint,secondPoint,thirdPoint,fourthPoint),0.000001);
    }

    @Test
    public void testIProd() {
        DPoint firstPoint = new DPoint(2.0,2.0);
        DPoint secondPoint = new DPoint(3.0,3.0);
        DPoint thirdPoint = new DPoint(2.0,2.0);
        assertEquals(0.0, Trace.iprod(firstPoint,secondPoint,thirdPoint),0.000001);
    }

    @Test
    public void testIProd1() {
        DPoint firstPoint = new DPoint(2.0,2.0);
        DPoint secondPoint = new DPoint(3.0,3.0);
        DPoint thirdPoint = new DPoint(2.0,2.0);
        DPoint fourthPoint = new DPoint(3.0,2.0);
        assertEquals(1.0, Trace.iprod1(firstPoint,secondPoint,thirdPoint,fourthPoint),0.000001);
    }

    @Test
    public void testDdist() {
        DPoint firstPoint = new DPoint(2.0,2.0);
        DPoint secondPoint = new DPoint(3.0,3.0);
        assertEquals(1.41421356, Trace.ddist(firstPoint,secondPoint),0.000001);
    }

    @Test
    public void testbezier() {
        DPoint firstPoint = new DPoint(2.0,2.0);
        DPoint secondPoint = new DPoint(3.0,3.0);
        DPoint thirdPoint = new DPoint(2.0,2.0);
        DPoint fourthPoint = new DPoint(3.0,2.0);
        DPoint resultPoint = Trace.bezier(0.5,firstPoint,secondPoint,thirdPoint,fourthPoint);
        assertEquals("x",2.5,resultPoint.x,0.0000001);
        assertEquals("y",2.375,resultPoint.y,0.0000001);
    }

    @Test
    public void testtangent() {
        DPoint firstPoint = new DPoint(2.0,2.0);
        DPoint secondPoint = new DPoint(3.0,3.0);
        DPoint thirdPoint = new DPoint(2.0,2.0);
        DPoint fourthPoint = new DPoint(3.0,2.0);
        DPoint fifthPoint = new DPoint(2.0,2.0);
        DPoint sixthhPoint = new DPoint(3.0,2.0);
        assertEquals(0.333333333333, Trace.tangent(fifthPoint,secondPoint,thirdPoint,fourthPoint,fifthPoint,sixthhPoint),0.0000001);
    }

    @Test
    public void test_calc_sums() {
        Bitmap testBitmap = new Bitmap(2,2);
        Path path = Decompose.findpath(testBitmap,0,2,43,4);
        Trace.calc_sums(path.priv);

        assertTrue(isSumsEqual(new Double[]{0.,0.,0.,0.,0.},path.priv.sums[0]));
        assertTrue(isSumsEqual(new Double[]{0.,0.,0.,0.,0.},path.priv.sums[1]));
        assertTrue(isSumsEqual(new Double[]{0.,-1.,0.,0.,1.},path.priv.sums[2]));
        assertTrue(isSumsEqual(new Double[]{1.,-2.,1.,-1.,2.},path.priv.sums[3]));
        assertTrue(isSumsEqual(new Double[]{2.,-2.,2.,-1.,2.},path.priv.sums[4]));
    }

    public boolean isSumsEqual(Double[] should , Sums actual) {
        if (actual.x == should[0] && actual.y == should[1] && actual.x2 == should[2] && actual.xy == should[3] && actual.y2 == should[4])
            return true;
        return false;
    }
}
