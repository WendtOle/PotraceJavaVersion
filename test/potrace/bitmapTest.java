package potrace;

import org.junit.*;
import java.awt.*;
import static org.junit.Assert.*;

public class bitmapTest {

    @Test
    public void creatingBitmapWithEmptyConstructorTest() {
        bitmap testBitmap = new bitmap();
        assertEquals("width: ", 0,testBitmap.w);
        assertEquals("height: ", 0,testBitmap.h);
        assertEquals("dy: ", 0,testBitmap.dy);
        assertEquals("map: ", null,testBitmap.map);
    }

    @Test
    public void creatingBitmapWithNormalConstructorTest() {
        bitmap testBitmap = new bitmap(100,100);
        assertEquals("width: ", 100,testBitmap.w);
        assertEquals("height: ", 100,testBitmap.h);
        assertEquals("dy: ", 2,testBitmap.dy);
        assertEquals("map: ", 200,testBitmap.map.length);
    }

    @Test
    public void checkingThatConstantsAreCorrect() {
        assertEquals("Pixel in Word: ",64,bitmap.PIXELINWORD);
        assertEquals("All Bits: ", -1l, bitmap.BM_ALLBITS);
        assertEquals("First Bit: ", 0x80000000, bitmap.BM_HIBIT);
    }

    @Test
    public void testBmSafeAndBmRange() {
        bitmap testBitmap = new bitmap(10,10);
        assertTrue("first inside: ",bitmap.bm_safe(testBitmap,0,0));
        assertTrue("second inside: ",bitmap.bm_safe(testBitmap,9,9));
        assertFalse("first outside: ",bitmap.bm_safe(testBitmap,10,10));
        assertFalse("second outside: ",bitmap.bm_safe(testBitmap,10,1));
        assertFalse("third outside: ",bitmap.bm_safe(testBitmap,-1,1));
    }

    @Test
    public void testMaskFuntion() {
        assertEquals("at position 0: ",0x8000000000000000l,bitmap.bm_mask(0));
        assertEquals("at position 1: ",0x4000000000000000l,bitmap.bm_mask(1));
        assertEquals("at position 64 -> 0: ",0x8000000000000000l,bitmap.bm_mask(64));
        assertEquals("at position 63: ",0x1,bitmap.bm_mask(63));
    }

    @Test
    public void testBMPutAndBMGetFunction() {
        bitmap smallTestBitmap = new bitmap(10,10);
        bitmap.BM_PUT(smallTestBitmap,0,0,true);
        assertEquals("with one potrace word in line: ",true,bitmap.BM_GET(smallTestBitmap,0,0));

        bitmap bigTestBitmap = new bitmap(100,100);
        bitmap.BM_PUT(bigTestBitmap,99,99,true);
        assertEquals("with more than one potrace word in line: ",true,bitmap.BM_GET(bigTestBitmap,99,99));
    }

    @Test
    public void testBMClearFuntion() {
        bitmap smallTestBitmap = new bitmap(10,10);
        bitmap.BM_PUT(smallTestBitmap,0,0,true);
        bitmap.BM_PUT(smallTestBitmap,0,0,false);
        assertEquals(false,bitmap.BM_GET(smallTestBitmap,0,0));
    }

    @Test
    public void test_bm_clear() throws Exception {
        bitmap testBitMap = new bitmap(10,10);
        bitmap.bm_clear(testBitMap,1);
        assertEquals(true, bitmap.BM_GET(testBitMap,0,0));
        assertEquals(true, bitmap.BM_GET(testBitMap,9,9));
        bitmap.bm_clear(testBitMap,0);
        assertEquals(false, bitmap.BM_GET(testBitMap,0,0));
        assertEquals(false, bitmap.BM_GET(testBitMap,4,4));
    }

    @Test
    public void test_bm_dup() throws Exception {
        bitmap originalBitmap = new bitmap(10,10);
        bitmap copiedBitmap = originalBitmap.bm_dup();

        assertFalse("reference: ", originalBitmap == copiedBitmap);
        assertArrayEquals("map: ",originalBitmap.map,copiedBitmap.map);
        assertEquals("dy: ",originalBitmap.dy, copiedBitmap.dy);
        assertEquals("height: ",originalBitmap.h, copiedBitmap.h);
        assertEquals("width: ",originalBitmap.w, copiedBitmap.w);
    }
}

