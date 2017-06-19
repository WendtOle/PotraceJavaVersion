package potraceOriginal;

import org.junit.*;
import java.awt.*;
import static org.junit.Assert.*;

public class decomposeTest {

    @Test
    public void test_findnextWithNextPointInSameLine() {
        Bitmap testBitmap = new Bitmap(70,1);
        Bitmap.BM_PUT(testBitmap,65,0,true);

        Point point = new Point(0,0);
        assertTrue("found sth: ", Decompose.findnext(testBitmap,point));
        assertEquals("found point: ",new Point(65,0),point);
    }

    @Test
    public void test_findnextWithNextPointNotInSameLine() {
        Bitmap testBitmap = new Bitmap(128,2);
        Bitmap.BM_PUT(testBitmap,97,0,true);
        Bitmap.BM_PUT(testBitmap,99,0,true);

        Point point = new Point(0,1);
        assertTrue("found sth: ", Decompose.findnext(testBitmap,point));
        assertEquals("found point: ", new Point(97,0),point);
    }

    @Test
    public void test_detrand() {
        Assert.assertEquals(false, Decompose.detrand(20,4));
    }

    @Test
    public void testMajorityFunction() {
        Bitmap testBitmap = new Bitmap(4,4);
        Point observationPoint = new Point(testBitmap.w/2,testBitmap.h/2);
        Point[] points = new Point[]{new Point(0,0),new Point(2,2),new Point(2,0),new Point(3,1),
                new Point(1,0), new Point(2,1), new Point(3,3),new Point (2,3),
                new Point(0,2), new Point(3,0), new Point(1,3), new Point(1,2),
                new Point(3,2),new Point(1,1),new Point(0,3), new Point(0,1)};

        Boolean[] expectedOutcomes = new Boolean[]{true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false};

        Bitmap.bm_clear(testBitmap,1);

        for(int i = 0; i < 16; i++) {
            Bitmap.BM_PUT(testBitmap,points[i].x,points[i].y,false);
            assertEquals("i: " + i,expectedOutcomes[i], Decompose.majority(testBitmap, observationPoint.x, observationPoint.y));
        }
    }

    @Test
    public void test_findPath_boundary() {
        Bitmap testBitmap = new Bitmap(128,1);
        Bitmap.BM_PUT(testBitmap,63,0,true);
        Bitmap.BM_PUT(testBitmap,64,0,true);

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
        Bitmap.BM_PUT(testBitmap,0,0,true);
        Bitmap.BM_PUT(testBitmap,1,0,true);
        Decompose.xor_to_ref(testBitmap,0,0,1);
        assertEquals("firstPixel: ", false, Bitmap.BM_GET(testBitmap,0,0));
        assertEquals("secondPixel: ", false, Bitmap.BM_GET(testBitmap,1,0));
    }

    @Test
    public void testXorToRefHorizontalSecondIF() {
        Bitmap testBitmap = new Bitmap(70,1);
        Bitmap.BM_PUT(testBitmap,68,0,true);
        Bitmap.BM_PUT(testBitmap,69,0,true);
        Decompose.xor_to_ref(testBitmap,70,0,0);
        assertEquals(-1l,testBitmap.map[0]);
        assertEquals("64: ", true, Bitmap.BM_GET(testBitmap,64,0));
        assertEquals("65: ", true, Bitmap.BM_GET(testBitmap,65,0));
        assertEquals("66: ", true, Bitmap.BM_GET(testBitmap,66,0));
        assertEquals("67: ", true, Bitmap.BM_GET(testBitmap,67,0));
        assertEquals("68: ", false, Bitmap.BM_GET(testBitmap,68,0));
        assertEquals("69: ", false, Bitmap.BM_GET(testBitmap,69,0));
    }

    @Test
    public void testXorToRefHorizontalThirdIF() {
        Bitmap testBitmap = new Bitmap(2,1);
        Bitmap.BM_PUT(testBitmap,0,0,true);
        Bitmap.BM_PUT(testBitmap,1,0,true);
        Decompose.xor_to_ref(testBitmap,2,0,0);
        assertEquals("firstPixel: ", false, Bitmap.BM_GET(testBitmap,0,0));
        assertEquals("secondPixel: ", false, Bitmap.BM_GET(testBitmap,1,0));
    }

    @Test
    public void test_xor_path() {
        Bitmap testBitmap = new Bitmap(3,3);
        Bitmap.bm_clear(testBitmap,1);
        Bitmap.bm_clearexcess(testBitmap);
        Bitmap.BM_PUT(testBitmap,1,1,false);

        Path path = Decompose.findpath(testBitmap,0,3,43,4);
        Decompose.xor_path(testBitmap,path);

        assertEquals(false, Bitmap.BM_GET(testBitmap,0,0));
        assertEquals(false, Bitmap.BM_GET(testBitmap,1,0));
        assertEquals(false, Bitmap.BM_GET(testBitmap,2,0));
        assertEquals(false, Bitmap.BM_GET(testBitmap,0,1));
        assertEquals(true, Bitmap.BM_GET(testBitmap,1,1));
        assertEquals(false, Bitmap.BM_GET(testBitmap,2,1));
        assertEquals(false, Bitmap.BM_GET(testBitmap,0,2));
        assertEquals(false, Bitmap.BM_GET(testBitmap,1,3));
        assertEquals(false, Bitmap.BM_GET(testBitmap,2,3));
    }

    @Test
    public void test_setbbox_path() {
        BBox box = new BBox();
        Bitmap testBitmap = new Bitmap(3,3);
        Bitmap.BM_PUT(testBitmap,1,1,true);

        Path path = Decompose.findpath(testBitmap,2,2,43,4);

        Decompose.setbbox_path(box,path);
        assertEquals(2,box.x0);
        assertEquals(3,box.x1);
        assertEquals(1,box.y0);
        assertEquals(2,box.y1);
    }

    @Test
    public void test_clear_bm_with_bbox() {
        BBox box = new BBox();
        box.x0 = 2;
        box.x1 = 3;
        box.y0 = 1;
        box.y1 = 2;
        Bitmap testBitmap = new Bitmap(3,3);
        Bitmap.BM_PUT(testBitmap,0,2,true);
        Bitmap.BM_PUT(testBitmap,1,1,true);

        Decompose.clear_bm_with_bbox(testBitmap,box);

        assertTrue("firstline: ",testBitmap.map[0] == 0);
        assertTrue("secondline: ",testBitmap.map[1] == 0);
        assertTrue("thirdline: ",testBitmap.map[2] == 0x8000000000000000l);
    }
}