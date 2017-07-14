package Potrace.refactored;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BitmapMaskCreationTest {

    @Test
    public void testCreatingMaskForFirstPixel(){
        assertEquals(0x8000000000000000L, BitMask.getOnePixelMaskForPosition(0));
    }

    @Test
    public void testCreatingMaskForFirstPixelOverBound(){
        assertEquals(0x8000000000000000L, BitMask.getOnePixelMaskForPosition(64));
    }

    @Test
    public void testCreatingMaskForLastPixel(){
        assertEquals(0x1L, BitMask.getOnePixelMaskForPosition(63));
    }

    @Test
    public void testCreatingMaskForLastPixelOverBound(){
        assertEquals(0x1L, BitMask.getOnePixelMaskForPosition(-1));
    }

    @Test
    public void testCreatingMaskForAPixel(){
        assertEquals(0x8L, BitMask.getOnePixelMaskForPosition(60));
    }

    @Test
    public void testCreatingMaskForAllPixels() {
        assertEquals(-1L, BitMask.getMultiplePixelMaskFromStartUntilPosition(64));
    }

    @Test
    public void testCreatingMaskForFirstTwoPixels() {
        assertEquals(0xc000000000000000L, BitMask.getMultiplePixelMaskFromStartUntilPosition(2));
    }

    @Test
    public void testCreatingMaskForAllPixelWithoutLastTwo() {
        assertEquals(0xfffffffffffffffcL, BitMask.getMultiplePixelMaskFromStartUntilPosition(62));
    }
}
