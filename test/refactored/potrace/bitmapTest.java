package refactored.potrace;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class bitmapTest {

    @Test
    public void creatingBitmapWithEmptyConstructorTest() {
        Bitmap testBitmap = new Bitmap();
        assertEquals("width: ", 0,testBitmap.w);
        assertEquals("height: ", 0,testBitmap.h);
        assertEquals("potraceWordsInOneLine: ", 0,testBitmap.dy);
        assertEquals("potraceWords: ", null,testBitmap.map);
    }

    @Test
    public void creatingBitmapWithNormalConstructorTest() {
        Bitmap testBitmap = new Bitmap(100,100);
        assertEquals("width: ", 100,testBitmap.w);
        assertEquals("height: ", 100,testBitmap.h);
        assertEquals("potraceWordsInOneLine: ", 2,testBitmap.dy);
        assertEquals("potraceWords: ", 200,testBitmap.map.length);
    }

    @Test
    public void checkingThatConstantsAreCorrect() {
        assertEquals("Pixel in Word: ",64, Bitmap.PIXELINWORD);
        assertEquals("All Bits: ", -1l, Bitmap.BM_ALLBITS);
        assertEquals("First Bit: ", 0x80000000, Bitmap.BM_HIBIT);
    }

    @Test
    public void testBmSafeAndBmRange() {
        Bitmap testBitmap = new Bitmap(10,10);
        assertTrue("first inside: ", testBitmap.bm_safe(0,0));
        assertTrue("second inside: ", testBitmap.bm_safe(9,9));
        assertFalse("first outside: ", testBitmap.bm_safe(10,10));
        assertFalse("second outside: ", testBitmap.bm_safe(10,1));
        assertFalse("third outside: ", testBitmap.bm_safe(-1,1));
    }

    @Test
    public void testMaskFuntion() {
        assertEquals("at position 0: ",0x8000000000000000l, Bitmap.bm_mask(0));
        assertEquals("at position 1: ",0x4000000000000000l, Bitmap.bm_mask(1));
        assertEquals("at position 64 -> 0: ",0x8000000000000000l, Bitmap.bm_mask(64));
        assertEquals("at position 63: ",0x1, Bitmap.bm_mask(63));
    }

    @Test
    public void testBMPutAndBMGetFunction() {
        Bitmap smallTestBitmap = new Bitmap(10,10);
        smallTestBitmap.BM_PUT(0,0,true);
        assertEquals("with one original word in line: ",true, smallTestBitmap.BM_GET(0,0));

        Bitmap bigTestBitmap = new Bitmap(100,100);
        bigTestBitmap.BM_PUT(99,99,true);
        assertEquals("with more than one original word in line: ",true,bigTestBitmap.BM_GET(99,99));
    }

    @Test
    public void testBMClearFuntion() {
        Bitmap smallTestBitmap = new Bitmap(10,10);
        smallTestBitmap.BM_PUT(0,0,true);
        smallTestBitmap.BM_PUT(0,0,false);
        assertEquals(false, smallTestBitmap.BM_GET(0,0));
    }

    @Test
    public void test_bm_clear() throws Exception {
        Bitmap testBitMap = new Bitmap(10,10);
        testBitMap.bm_clear(1);
        assertEquals(true, testBitMap.BM_GET(0,0));
        assertEquals(true, testBitMap.BM_GET(9,9));
        testBitMap.bm_clear(0);
        assertEquals(false, testBitMap.BM_GET(0,0));
        assertEquals(false, testBitMap.BM_GET(4,4));
    }

    @Test
    public void test_bm_dup() throws Exception {
        Bitmap originalBitmap = new Bitmap(10,10);
        Bitmap copiedBitmap = originalBitmap.bm_dup();

        assertFalse("reference: ", originalBitmap == copiedBitmap);
        assertArrayEquals("potraceWords: ",originalBitmap.map,copiedBitmap.map);
        assertEquals("potraceWordsInOneLine: ",originalBitmap.dy, copiedBitmap.dy);
        assertEquals("height: ",originalBitmap.h, copiedBitmap.h);
        assertEquals("width: ",originalBitmap.w, copiedBitmap.w);
    }

    @Test
    public void test_clear_bm_with_bbox() {
        BBox box = new BBox();
        box.x0 = 2;
        box.x1 = 3;
        box.y0 = 1;
        box.y1 = 2;
        Bitmap testBitmap = new Bitmap(3,3);
        testBitmap.BM_PUT(0,2,true);
        testBitmap.BM_PUT(1,1,true);

        testBitmap.clear_bm_with_bbox(box);

        assertTrue("firstline: ",testBitmap.map[0] == 0);
        assertTrue("secondline: ",testBitmap.map[1] == 0);
        assertTrue("thirdline: ",testBitmap.map[2] == 0x8000000000000000l);
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

        testBitmap.bm_clear(1);

        for(int i = 0; i < 16; i++) {
            testBitmap.BM_PUT(points[i].x,points[i].y,false);
            assertEquals("i: " + i,expectedOutcomes[i], testBitmap.majority(observationPoint.x, observationPoint.y));
        }
    }
}