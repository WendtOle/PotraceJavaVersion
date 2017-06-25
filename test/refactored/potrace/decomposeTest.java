package refactored.potrace;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class decomposeTest {

    @Test
    public void test_findnextWithNextPointInSameLine() {
        Bitmap testBitmap = new Bitmap(70,1);
        testBitmap.BM_PUT(65,0,true);

        Point point = new Point(0,0);
        assertTrue("found sth: ", Decompose.findnext(testBitmap,point));
        assertEquals("found point: ",new Point(65,0),point);
    }

    @Test
    public void test_findnextWithNextPointNotInSameLine() {
        Bitmap testBitmap = new Bitmap(128,2);
        testBitmap.BM_PUT(97,0,true);
        testBitmap.BM_PUT(99,0,true);

        Point point = new Point(0,1);
        assertTrue("found sth: ", Decompose.findnext(testBitmap,point));
        assertEquals("found point: ", new Point(97,0),point);
    }

    @Test
    public void test_detrand() {
        Assert.assertEquals(false, Decompose.detrand(20,4));
    }

    @Test
    public void test_findPath_boundary() {
        Bitmap testBitmap = new Bitmap(128,1);
        testBitmap.BM_PUT(63,0,true);
        testBitmap.BM_PUT(64,0,true);

        Point[] expectedPath = new Point[]{ new Point(63,1), new Point(63,0),
                new Point(64,0),new Point(65,0),new Point(65,1),
                new Point(64,1)};

        Path result = Decompose.findpath(testBitmap,63,1,43,4);
        for (int i = 0 ; i < expectedPath.length; i ++)
            comparePoints(expectedPath[i],result.priv.pt[i]);
    }

    private void comparePoints(Point should, Point actual) {
        Assert.assertEquals(should.x, actual.x);
        Assert.assertEquals(should.y, actual.y);
    }

    @Test
    public void testXorToRefFirstIF() {
        Bitmap testBitmap = new Bitmap(2,1);
        testBitmap.BM_PUT(0,0,true);
        testBitmap.BM_PUT(1,0,true);
        Decompose.xor_to_ref(testBitmap,0,0,1);
        assertEquals("firstPixel: ", false, testBitmap.BM_GET(0,0));
        assertEquals("secondPixel: ", false, testBitmap.BM_GET(1,0));
    }

    @Test
    public void testXorToRefHorizontalSecondIF() {
        Bitmap testBitmap = new Bitmap(70,1);
        testBitmap.BM_PUT(68,0,true);
        testBitmap.BM_PUT(69,0,true);
        Decompose.xor_to_ref(testBitmap,70,0,0);
        assertEquals(-1l,testBitmap.words[0]);
        assertEquals("64: ", true, testBitmap.BM_GET(64,0));
        assertEquals("65: ", true, testBitmap.BM_GET(65,0));
        assertEquals("66: ", true, testBitmap.BM_GET(66,0));
        assertEquals("67: ", true, testBitmap.BM_GET(67,0));
        assertEquals("68: ", false, testBitmap.BM_GET(68,0));
        assertEquals("69: ", false, testBitmap.BM_GET(69,0));
    }

    @Test
    public void testXorToRefHorizontalThirdIF() {
        Bitmap testBitmap = new Bitmap(2,1);
        testBitmap.BM_PUT(0,0,true);
        testBitmap.BM_PUT(1,0,true);
        Decompose.xor_to_ref(testBitmap,2,0,0);
        assertEquals("firstPixel: ", false, testBitmap.BM_GET(0,0));
        assertEquals("secondPixel: ", false, testBitmap.BM_GET(1,0));
    }

    @Test
    public void test_xor_path() {
        Bitmap testBitmap = new Bitmap(3,3);
        testBitmap.bm_clear_andSetToC(1);
        testBitmap.deleteExcessPixelsOfBitmap();
        testBitmap.BM_PUT(1,1,false);

        Path path = Decompose.findpath(testBitmap,0,3,43,4);
        Decompose.xor_path(testBitmap,path);

        assertEquals(false, testBitmap.BM_GET(0,0));
        assertEquals(false, testBitmap.BM_GET(1,0));
        assertEquals(false, testBitmap.BM_GET(2,0));
        assertEquals(false, testBitmap.BM_GET(0,1));
        assertEquals(true, testBitmap.BM_GET(1,1));
        assertEquals(false, testBitmap.BM_GET(2,1));
        assertEquals(false, testBitmap.BM_GET(0,2));
        assertEquals(false, testBitmap.BM_GET(1,3));
        assertEquals(false, testBitmap.BM_GET(2,3));
    }
}