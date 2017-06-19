package potraceRefactored;

import org.junit.Test;
import potraceOriginal.Bitmap;

import static org.junit.Assert.*;

public class bitmapTest {

    @Test
    public void creatingBitmapWithEmptyConstructorTest() {
        potraceOriginal.Bitmap testBitmap = new potraceOriginal.Bitmap();
        assertEquals("width: ", 0,testBitmap.w);
        assertEquals("height: ", 0,testBitmap.h);
        assertEquals("dy: ", 0,testBitmap.dy);
        assertEquals("map: ", null,testBitmap.map);
    }

    @Test
    public void creatingBitmapWithNormalConstructorTest() {
        potraceOriginal.Bitmap testBitmap = new potraceOriginal.Bitmap(100,100);
        assertEquals("width: ", 100,testBitmap.w);
        assertEquals("height: ", 100,testBitmap.h);
        assertEquals("dy: ", 2,testBitmap.dy);
        assertEquals("map: ", 200,testBitmap.map.length);
    }

    @Test
    public void checkingThatConstantsAreCorrect() {
        assertEquals("Pixel in Word: ",64, potraceOriginal.Bitmap.PIXELINWORD);
        assertEquals("All Bits: ", -1l, potraceOriginal.Bitmap.BM_ALLBITS);
        assertEquals("First Bit: ", 0x80000000, potraceOriginal.Bitmap.BM_HIBIT);
    }

    @Test
    public void testBmSafeAndBmRange() {
        potraceOriginal.Bitmap testBitmap = new potraceOriginal.Bitmap(10,10);
        assertTrue("first inside: ", potraceOriginal.Bitmap.bm_safe(testBitmap,0,0));
        assertTrue("second inside: ", potraceOriginal.Bitmap.bm_safe(testBitmap,9,9));
        assertFalse("first outside: ", potraceOriginal.Bitmap.bm_safe(testBitmap,10,10));
        assertFalse("second outside: ", potraceOriginal.Bitmap.bm_safe(testBitmap,10,1));
        assertFalse("third outside: ", potraceOriginal.Bitmap.bm_safe(testBitmap,-1,1));
    }

    @Test
    public void testMaskFuntion() {
        assertEquals("at position 0: ",0x8000000000000000l, potraceOriginal.Bitmap.bm_mask(0));
        assertEquals("at position 1: ",0x4000000000000000l, potraceOriginal.Bitmap.bm_mask(1));
        assertEquals("at position 64 -> 0: ",0x8000000000000000l, potraceOriginal.Bitmap.bm_mask(64));
        assertEquals("at position 63: ",0x1, potraceOriginal.Bitmap.bm_mask(63));
    }

    @Test
    public void testBMPutAndBMGetFunction() {
        potraceOriginal.Bitmap smallTestBitmap = new potraceOriginal.Bitmap(10,10);
        potraceOriginal.Bitmap.BM_PUT(smallTestBitmap,0,0,true);
        assertEquals("with one potraceOriginal word in line: ",true, potraceOriginal.Bitmap.BM_GET(smallTestBitmap,0,0));

        potraceOriginal.Bitmap bigTestBitmap = new potraceOriginal.Bitmap(100,100);
        potraceOriginal.Bitmap.BM_PUT(bigTestBitmap,99,99,true);
        assertEquals("with more than one potraceOriginal word in line: ",true, potraceOriginal.Bitmap.BM_GET(bigTestBitmap,99,99));
    }

    @Test
    public void testBMClearFuntion() {
        potraceOriginal.Bitmap smallTestBitmap = new potraceOriginal.Bitmap(10,10);
        potraceOriginal.Bitmap.BM_PUT(smallTestBitmap,0,0,true);
        potraceOriginal.Bitmap.BM_PUT(smallTestBitmap,0,0,false);
        assertEquals(false, potraceOriginal.Bitmap.BM_GET(smallTestBitmap,0,0));
    }

    @Test
    public void test_bm_clear() throws Exception {
        potraceOriginal.Bitmap testBitMap = new potraceOriginal.Bitmap(10,10);
        potraceOriginal.Bitmap.bm_clear(testBitMap,1);
        assertEquals(true, potraceOriginal.Bitmap.BM_GET(testBitMap,0,0));
        assertEquals(true, potraceOriginal.Bitmap.BM_GET(testBitMap,9,9));
        potraceOriginal.Bitmap.bm_clear(testBitMap,0);
        assertEquals(false, potraceOriginal.Bitmap.BM_GET(testBitMap,0,0));
        assertEquals(false, potraceOriginal.Bitmap.BM_GET(testBitMap,4,4));
    }

    @Test
    public void test_bm_dup() throws Exception {
        potraceOriginal.Bitmap originalBitmap = new potraceOriginal.Bitmap(10,10);
        Bitmap copiedBitmap = originalBitmap.bm_dup();

        assertFalse("reference: ", originalBitmap == copiedBitmap);
        assertArrayEquals("map: ",originalBitmap.map,copiedBitmap.map);
        assertEquals("dy: ",originalBitmap.dy, copiedBitmap.dy);
        assertEquals("height: ",originalBitmap.h, copiedBitmap.h);
        assertEquals("width: ",originalBitmap.w, copiedBitmap.w);
    }
}

