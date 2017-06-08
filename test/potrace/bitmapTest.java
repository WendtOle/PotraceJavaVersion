package potrace;

import Tools.BetterBitmap;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by andreydelany on 07/03/2017.
 */
public class bitmapTest {

    @Ignore
    @Test
    public void test_bm_get() throws Exception {
        BetterBitmap testBitMap = new BetterBitmap(2,2);
        testBitMap.addBlob(new Point(1,1),true);

        assertEquals(false, testBitMap.BM_GET(1,0));
        assertEquals(false, testBitMap.BM_GET(0,0));
        assertEquals(false, testBitMap.BM_GET(0,1));
        assertEquals(true, testBitMap.BM_GET(1,1));

        assertEquals(false, testBitMap.BM_GET(-1,0));
        assertEquals(false, testBitMap.BM_GET(0,-1));
        assertEquals(false, testBitMap.BM_GET(2,0));
        assertEquals(false, testBitMap.BM_GET(0,2));
        assertEquals(false, testBitMap.BM_GET(2,2));
        assertEquals(false, testBitMap.BM_GET(-1,-1));
    }

    @Ignore
    @Test
    //TODO wie kann denn eine negatives dy entstehen?
    public void test_bm_size() throws Exception {
        bitmap testBitMap = new bitmap(-33,1);
        System.out.println(testBitMap.bm_size());
        assertEquals(64, (new bitmap(1,1)).bm_size());
        assertEquals(128, (new bitmap(70,1)).bm_size());
        assertEquals(128, (new bitmap(4,2)).bm_size());
    }

    @Ignore
    @Test
    public void test_bm_clear() throws Exception {
        BetterBitmap testBitMap = new BetterBitmap(70,1);
        testBitMap.addPolygon(new Point(60,0),new Point(67,0),true);

        bitmap.bm_clear(testBitMap,0);
        assertEquals(false, testBitMap.BM_GET(0,0));
        assertEquals(false, testBitMap.BM_GET(40,2));
        bitmap.bm_clear(testBitMap,1);
        assertEquals(true, testBitMap.BM_GET(0,0));
        assertEquals(false, testBitMap.BM_GET(40,2));
        assertEquals(false, testBitMap.BM_GET(40,1));
    }

    @Ignore
    @Test
    public void test_bm_dup() throws Exception {
        BetterBitmap originalBitmap = new BetterBitmap(70,2);
        originalBitmap.addPolygon(new Point(62,1), new Point(65,0),true);
        bitmap copiedBitmap = originalBitmap.bm_dup();

        //check wether reference is difference
        assertTrue(originalBitmap != copiedBitmap);
        assertArrayEquals(originalBitmap.map,copiedBitmap.map);
        assertEquals(originalBitmap.dy, copiedBitmap.dy);
        assertEquals(originalBitmap.h, copiedBitmap.h);
        assertEquals(originalBitmap.w, copiedBitmap.w);
    }

    @Ignore
    @Test
    public void test_bm_put_negative() throws Exception {
        BetterBitmap bitMap = new BetterBitmap(2,2);
        bitMap.addPolygon(new Point(0,1), new Point(1,0),true);
        bitMap.addBlob(new Point(0,1),false);

        assertEquals(false,bitMap.BM_GET(0,1));
        assertEquals(true,bitMap.BM_GET(0,0));
        assertEquals(true,bitMap.BM_GET(1,1));
        assertEquals(true,bitMap.BM_GET(1,0));
    }
}

