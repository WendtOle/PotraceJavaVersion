package refactored.potrace.BitmapDecomposition;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 02.07.17.
 */
public class BitmapMaskCreationTest {

    @Test
    public void testCreatingMaskForFirstPixel(){
        assertEquals(0x8000000000000000l, BitMask.getOnePixelMaskForPosition(0));
    }

    @Test
    public void testCreatingMaskForFirstPixelOverBound(){
        assertEquals(0x8000000000000000l, BitMask.getOnePixelMaskForPosition(64));
    }

    @Test
    public void testCreatingMaskForLastPixel(){
        assertEquals(0x1l, BitMask.getOnePixelMaskForPosition(63));
    }

    @Test
    public void testCreatingMaskForLastPixelOverBound(){
        assertEquals(0x1l, BitMask.getOnePixelMaskForPosition(-1));
    }

    @Test
    public void testCreatingMaskForAPixel(){
        assertEquals(0x8l, BitMask.getOnePixelMaskForPosition(60));
    }

    @Test
    public void testCreatingMaskForAllPixels() {
        assertEquals(-1l, BitMask.getMultiplePixelMaskUntilPosition(64));
    }

    @Test
    public void testCreatingMaskForFirstTwoPixels() {
        assertEquals(0xc000000000000000l, BitMask.getMultiplePixelMaskUntilPosition(2));
    }

    @Test
    public void testCreatingMaskForAllPixelWithoutLastTwo() {
        assertEquals(0xfffffffffffffffcl, BitMask.getMultiplePixelMaskUntilPosition(62));
    }
}
