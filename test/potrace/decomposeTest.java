package potrace;

import BitmapLibrary.*;
import Tools.*;
import org.junit.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class decomposeTest {

    @Test
    public void test_findnextWithNextPointInSameLine() {
        bitmap testBitmap = new bitmap(70,1);
        bitmap.BM_PUT(testBitmap,65,0,true);

        Point point = new Point(0,0);
        assertTrue("found sth: ",decompose.findnext(testBitmap,point));
        assertEquals("found point: ",new Point(65,0),point);
    }

    @Test
    public void test_findnextWithNextPointNotInSameLine() {
        bitmap testBitmap = new bitmap(128,2);
        bitmap.BM_PUT(testBitmap,97,0,true);
        bitmap.BM_PUT(testBitmap,99,0,true);

        Point point = new Point(0,1);
        assertTrue("found sth: ",decompose.findnext(testBitmap,point));
        assertEquals("found point: ", new Point(97,0),point);
    }

    @Test
    public void test_detrand() {
        Assert.assertEquals(false, decompose.detrand(20,4));
    }

    @Test
    public void testMajorityFunction() {
        bitmap testBitmap = new bitmap(4,4);
        Point observationPoint = new Point(testBitmap.w/2,testBitmap.h/2);
        Point[] points = new Point[]{new Point(0,0),new Point(2,2),new Point(2,0),new Point(3,1),
                new Point(1,0), new Point(2,1), new Point(3,3),new Point (2,3),
                new Point(0,2), new Point(3,0), new Point(1,3), new Point(1,2),
                new Point(3,2),new Point(1,1),new Point(0,3), new Point(0,1)};

        Boolean[] expectedOutcomes = new Boolean[]{true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false};

        bitmap.bm_clear(testBitmap,1);

        for(int i = 0; i < 16; i++) {
            bitmap.BM_PUT(testBitmap,points[i].x,points[i].y,false);
            assertEquals("i: " + i,expectedOutcomes[i],decompose.majority(testBitmap, observationPoint.x, observationPoint.y));
        }
    }

    @Test
    public void test_findPath_boundary() {
        bitmap testBitmap = new bitmap(128,1);
        bitmap.BM_PUT(testBitmap,63,0,true);
        bitmap.BM_PUT(testBitmap,64,0,true);

        Point[] expectedPath = new Point[]{ new Point(63,1), new Point(63,0),
                new Point(64,0),new Point(65,0),new Point(65,1),
                new Point(64,1)};

        path result = decompose.findpath(testBitmap,63,1,43,4);
        for (int i = 0 ; i < expectedPath.length; i ++)
            comparePoints(expectedPath[i],result.priv.pt[i]);
    }

    private void comparePoints(Point should, Point actual) {
        Assert.assertEquals(should.x, actual.x);
        Assert.assertEquals(should.y, actual.y);
    }

    @Test
    public void testXorToRefFirstIF() {
        bitmap testBitmap = new bitmap(2,1);
        bitmap.BM_PUT(testBitmap,0,0,true);
        bitmap.BM_PUT(testBitmap,1,0,true);
        decompose.xor_to_ref(testBitmap,0,0,1);
        assertEquals("firstPixel: ", false,bitmap.BM_GET(testBitmap,0,0));
        assertEquals("secondPixel: ", false,bitmap.BM_GET(testBitmap,1,0));
    }

    @Test
    public void testXorToRefHorizontalSecondIF() {
        bitmap testBitmap = new bitmap(70,1);
        bitmap.BM_PUT(testBitmap,68,0,true);
        bitmap.BM_PUT(testBitmap,69,0,true);
        decompose.xor_to_ref(testBitmap,70,0,0);
        assertEquals(-1l,testBitmap.map[0]);
        assertEquals("64: ", true,bitmap.BM_GET(testBitmap,64,0));
        assertEquals("65: ", true,bitmap.BM_GET(testBitmap,65,0));
        assertEquals("66: ", true,bitmap.BM_GET(testBitmap,66,0));
        assertEquals("67: ", true,bitmap.BM_GET(testBitmap,67,0));
        assertEquals("68: ", false,bitmap.BM_GET(testBitmap,68,0));
        assertEquals("69: ", false,bitmap.BM_GET(testBitmap,69,0));
    }

    @Test
    public void testXorToRefHorizontalThirdIF() {
        bitmap testBitmap = new bitmap(2,1);
        bitmap.BM_PUT(testBitmap,0,0,true);
        bitmap.BM_PUT(testBitmap,1,0,true);
        decompose.xor_to_ref(testBitmap,2,0,0);
        assertEquals("firstPixel: ", false,bitmap.BM_GET(testBitmap,0,0));
        assertEquals("secondPixel: ", false,bitmap.BM_GET(testBitmap,1,0));
    }

    @Test
    public void test_xor_path() {
        bitmap testBitmap = new bitmap(3,3);
        bitmap.bm_clear(testBitmap,1);
        bitmap.bm_clearexcess(testBitmap);
        bitmap.BM_PUT(testBitmap,1,1,false);

        path path = decompose.findpath(testBitmap,0,3,43,4);
        decompose.xor_path(testBitmap,path);

        assertEquals(false,bitmap.BM_GET(testBitmap,0,0));
        assertEquals(false,bitmap.BM_GET(testBitmap,1,0));
        assertEquals(false,bitmap.BM_GET(testBitmap,2,0));
        assertEquals(false,bitmap.BM_GET(testBitmap,0,1));
        assertEquals(true,bitmap.BM_GET(testBitmap,1,1));
        assertEquals(false,bitmap.BM_GET(testBitmap,2,1));
        assertEquals(false,bitmap.BM_GET(testBitmap,0,2));
        assertEquals(false,bitmap.BM_GET(testBitmap,1,3));
        assertEquals(false,bitmap.BM_GET(testBitmap,2,3));
    }

    @Test
    public void test_setbbox_path() {
        bbox box = new bbox();
        bitmap testBitmap = new bitmap(3,3);
        bitmap.BM_PUT(testBitmap,1,1,true);

        path path = decompose.findpath(testBitmap,2,2,43,4);

        decompose.setbbox_path(box,path);
        assertEquals(2,box.x0);
        assertEquals(3,box.x1);
        assertEquals(1,box.y0);
        assertEquals(2,box.y1);
    }

    @Test
    public void test_clear_bm_with_bbox() {
        bbox box = new bbox();
        box.x0 = 2;
        box.x1 = 3;
        box.y0 = 1;
        box.y1 = 2;
        bitmap testBitmap = new bitmap(3,3);
        bitmap.BM_PUT(testBitmap,0,2,true);
        bitmap.BM_PUT(testBitmap,1,1,true);

        decompose.clear_bm_with_bbox(testBitmap,box);

        assertTrue("firstline: ",testBitmap.map[0] == 0);
        assertTrue("secondline: ",testBitmap.map[1] == 0);
        assertTrue("thirdline: ",testBitmap.map[2] == 0x8000000000000000l);
    }
}