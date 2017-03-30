package potrace;

import Tools.BitMapManipulator;
import Tools.BitmapPrinter;
import org.junit.Assert;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by andreydelany on 07/03/2017.
 */
public class potrace_bitmapTest {

    @org.junit.Test
    public void test_bm_get() throws Exception {
        potrace_bitmap testBitMap = new potrace_bitmap(2,2);
        testBitMap = BitMapManipulator.addBlob(testBitMap, new Point(1,1),true);

        Assert.assertEquals(false, testBitMap.BM_GET(1,0));
        Assert.assertEquals(false, testBitMap.BM_GET(0,0));
        Assert.assertEquals(false, testBitMap.BM_GET(0,1));
        Assert.assertEquals(true, testBitMap.BM_GET(1,1));

        Assert.assertEquals(false, testBitMap.BM_GET(-1,0));
        Assert.assertEquals(false, testBitMap.BM_GET(0,-1));
        Assert.assertEquals(false, testBitMap.BM_GET(2,0));
        Assert.assertEquals(false, testBitMap.BM_GET(0,2));
        Assert.assertEquals(false, testBitMap.BM_GET(2,2));
        Assert.assertEquals(false, testBitMap.BM_GET(-1,-1));
    }

    @org.junit.Test
    //TODO wie kann denn eine negatives dy entstehen?
    public void test_bm_size() throws Exception {
        potrace_bitmap testBitMap = new potrace_bitmap(-33,1);
        System.out.println(testBitMap.bm_size());
        Assert.assertEquals(64, (new potrace_bitmap(1,1)).bm_size());
        Assert.assertEquals(128, (new potrace_bitmap(70,1)).bm_size());
        Assert.assertEquals(128, (new potrace_bitmap(4,2)).bm_size());
    }

    @org.junit.Test
    public void test_bm_clear() throws Exception {
        potrace_bitmap testBitMap = new potrace_bitmap(70,1);
        testBitMap = BitMapManipulator.addPolygon(testBitMap, new Point(60,0),new Point(67,0),true);

        potrace_bitmap.bm_clear(testBitMap,0);
        Assert.assertEquals(false, testBitMap.BM_GET(0,0));
        Assert.assertEquals(false, testBitMap.BM_GET(40,2));
        potrace_bitmap.bm_clear(testBitMap,1);
        Assert.assertEquals(true, testBitMap.BM_GET(0,0));
        Assert.assertEquals(false, testBitMap.BM_GET(40,2));
        Assert.assertEquals(false, testBitMap.BM_GET(40,1));
    }

    @org.junit.Test
    public void test_bm_dup() throws Exception {
        potrace_bitmap originalBitmap = new potrace_bitmap(70,2);
        originalBitmap = BitMapManipulator.addPolygon(originalBitmap, new Point(62,1), new Point(65,0),true);
        potrace_bitmap copiedBitmap = originalBitmap.bm_dup();

        //check wether reference is difference
        Assert.assertFalse(originalBitmap == copiedBitmap);
        Assert.assertArrayEquals(originalBitmap.map,copiedBitmap.map);
        Assert.assertEquals(originalBitmap.dy, copiedBitmap.dy);
        Assert.assertEquals(originalBitmap.h, copiedBitmap.h);
        Assert.assertEquals(originalBitmap.w, copiedBitmap.w);
    }

    @org.junit.Test
    public void test_bm_put_negative() throws Exception {
        potrace_bitmap bitMap = new potrace_bitmap(2,2);
        bitMap = BitMapManipulator.addPolygon(bitMap, new Point(0,1), new Point(1,0),true);
        bitMap = BitMapManipulator.addBlob(bitMap, new Point(0,1),false);

        Assert.assertEquals(false,bitMap.BM_GET(0,1));
        Assert.assertEquals(true,bitMap.BM_GET(0,0));
        Assert.assertEquals(true,bitMap.BM_GET(1,1));
        Assert.assertEquals(true,bitMap.BM_GET(1,0));
    }

}

