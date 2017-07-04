package refactored.potrace;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class bitmapTest {

    @Test
    public void creatingBitmapWithEmptyConstructorTest() {
        Bitmap testBitmap = new Bitmap();
        assertEquals("width: ", 0,testBitmap.width);
        assertEquals("height: ", 0,testBitmap.height);
        assertEquals("potraceWordsInOneLine: ", 0,testBitmap.wordsPerScanLine);
        assertEquals("potraceWords: ", null,testBitmap.words);
    }

    @Test
    public void creatingBitmapWithNormalConstructorTest() {
        Bitmap testBitmap = new Bitmap(100,100);
        assertEquals("width: ", 100,testBitmap.width);
        assertEquals("height: ", 100,testBitmap.height);
        assertEquals("potraceWordsInOneLine: ", 2,testBitmap.wordsPerScanLine);
        assertEquals("potraceWords: ", 200,testBitmap.words.length);
    }

    @Test
    public void checkingThatConstantsAreCorrect() {
        assertEquals("Pixel in Word: ",64, Bitmap.PIXELINWORD);
        assertEquals("All Bits: ", -1l, Bitmap.BM_ALLBITS);
        assertEquals("First Bit: ", 0x80000000, Bitmap.BM_HIBIT);
    }

    @Test
    public void test_bm_dup() throws Exception {
        Bitmap originalBitmap = new Bitmap(10,10);
        Bitmap copiedBitmap = originalBitmap.duplicate();

        assertFalse("reference: ", originalBitmap == copiedBitmap);
        assertArrayEquals("potraceWords: ",originalBitmap.words,copiedBitmap.words);
        assertEquals("potraceWordsInOneLine: ",originalBitmap.wordsPerScanLine, copiedBitmap.wordsPerScanLine);
        assertEquals("height: ",originalBitmap.height, copiedBitmap.height);
        assertEquals("width: ",originalBitmap.width, copiedBitmap.width);
    }

    @Test
    public void test_clear_bm_with_bbox() {
        BBox box = new BBox();
        box.x0 = 2;
        box.x1 = 3;
        box.y0 = 1;
        box.y1 = 2;
        Bitmap testBitmap = new Bitmap(3,3);
        BitmapHandlerInterface manipulator = new BitmapHandler(testBitmap);
        manipulator.setPixel(new Point(0,2));
        manipulator.setPixel(new Point(1,1));

        ClearBitmapWithBBox bitmapClearer = new ClearBitmapWithBBox(testBitmap);
        bitmapClearer.clearBitmapWithBBox(box);

        assertTrue("firstline: ",testBitmap.words[0] == 0);
        assertTrue("secondline: ",testBitmap.words[1] == 0);
        assertTrue("thirdline: ",testBitmap.words[2] == 0x8000000000000000l);
    }
}