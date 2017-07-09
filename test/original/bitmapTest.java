package original;

import General.Bitmap;
import org.junit.Test;

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
        assertTrue("first inside: ", BitmapManipulator.bm_safe(testBitmap,0,0));
        assertTrue("second inside: ", BitmapManipulator.bm_safe(testBitmap,9,9));
        assertFalse("first outside: ", BitmapManipulator.bm_safe(testBitmap,10,10));
        assertFalse("second outside: ", BitmapManipulator.bm_safe(testBitmap,10,1));
        assertFalse("third outside: ", BitmapManipulator.bm_safe(testBitmap,-1,1));
    }

    @Test
    public void testMaskFuntion() {
        assertEquals("at position 0: ",0x8000000000000000l, BitmapManipulator.bm_mask(0));
        assertEquals("at position 1: ",0x4000000000000000l, BitmapManipulator.bm_mask(1));
        assertEquals("at position 64 -> 0: ",0x8000000000000000l, BitmapManipulator.bm_mask(64));
        assertEquals("at position 63: ",0x1, BitmapManipulator.bm_mask(63));
    }

    @Test
    public void testBMPutAndBMGetFunction() {
        Bitmap smallTestBitmap = new Bitmap(10,10);
        BitmapManipulator.BM_PUT(smallTestBitmap,0,0,true);
        assertEquals("with one original word in line: ",true, BitmapManipulator.BM_GET(smallTestBitmap,0,0));

        Bitmap bigTestBitmap = new Bitmap(100,100);
        BitmapManipulator.BM_PUT(bigTestBitmap,99,99,true);
        assertEquals("with more than one original word in line: ",true, BitmapManipulator.BM_GET(bigTestBitmap,99,99));
    }

    @Test
    public void testBMClearFuntion() {
        Bitmap smallTestBitmap = new Bitmap(10,10);
        BitmapManipulator.BM_PUT(smallTestBitmap,0,0,true);
        BitmapManipulator.BM_PUT(smallTestBitmap,0,0,false);
        assertEquals(false, BitmapManipulator.BM_GET(smallTestBitmap,0,0));
    }

    @Test
    public void test_bm_clear() throws Exception {
       Bitmap testBitMap = new Bitmap(10,10);
        BitmapManipulator.bm_clear(testBitMap,1);
        assertEquals(true, BitmapManipulator.BM_GET(testBitMap,0,0));
        assertEquals(true, BitmapManipulator.BM_GET(testBitMap,9,9));
        BitmapManipulator.bm_clear(testBitMap,0);
        assertEquals(false, BitmapManipulator.BM_GET(testBitMap,0,0));
        assertEquals(false, BitmapManipulator.BM_GET(testBitMap,4,4));
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
}

