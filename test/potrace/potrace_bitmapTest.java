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
        BitMapManipulator creator = new BitMapManipulator(2,2);
        creator.addBlob(new Point(1,1),true);
        potrace_bitmap testBitMap = creator.getBitmap();

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
        BitMapManipulator creator = new BitMapManipulator(70,1);
        creator.addPolygon(new Point(60,0),new Point(67,0),true);
        potrace_bitmap testBitMap = creator.getBitmap();

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
        BitMapManipulator creator = new BitMapManipulator(70,2);
        creator.addPolygon(new Point(62,1), new Point(65,0),true);
        potrace_bitmap originalBitmap = creator.getBitmap();
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
        BitMapManipulator creator = new BitMapManipulator(2,2);
        creator.addPolygon(new Point(0,1), new Point(1,0),true);
        creator.addBlob(new Point(0,1),false);
        potrace_bitmap bitMap = creator.getBitmap();


        Assert.assertEquals(false,bitMap.BM_GET(0,1));
        Assert.assertEquals(true,bitMap.BM_GET(0,0));
        Assert.assertEquals(true,bitMap.BM_GET(1,1));
        Assert.assertEquals(true,bitMap.BM_GET(1,0));
    }

}

