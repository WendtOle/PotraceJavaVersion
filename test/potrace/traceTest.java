package potrace;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class traceTest {

    public boolean isSumsEqual(Double[] should ,sums actual) {
        if (actual.x == should[0] && actual.y == should[1] && actual.x2 == should[2] && actual.xy == should[3] && actual.y2 == should[4])
            return true;
        return false;
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
