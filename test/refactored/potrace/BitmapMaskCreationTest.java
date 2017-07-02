package refactored.potrace;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 02.07.17.
 */
public class BitmapMaskCreationTest {

    @Test
    public void testCreatingMaskForFirstPixel(){
        assertEquals(0x8000000000000000l,BitmapPixelHandler.getOnePixelMaskForPosition(0));
    }

    @Test
    public void testCreatingMaskForFirstPixelOverBound(){
        assertEquals(0x8000000000000000l,BitmapPixelHandler.getOnePixelMaskForPosition(64));
    }

    @Test
    public void testCreatingMaskForLastPixel(){
        assertEquals(0x1l,BitmapPixelHandler.getOnePixelMaskForPosition(63));
    }

    @Test
    public void testCreatingMaskForLastPixelOverBound(){
        assertEquals(0x1l,BitmapPixelHandler.getOnePixelMaskForPosition(-1));
    }

    @Test
    public void testCreatingMaskForAPixel(){
        assertEquals(0x8l,BitmapPixelHandler.getOnePixelMaskForPosition(60));
    }

    @Test
    public void testCreatingMaskForAllPixels() {
        assertEquals(-1l,BitmapPixelHandler.getMultiplePixelMaskUntilPosition(64));
    }

    @Test
    public void testCreatingMaskForFirstTwoPixels() {
        assertEquals(0xc000000000000000l,BitmapPixelHandler.getMultiplePixelMaskUntilPosition(2));
    }

    @Test
    public void testCreatingMaskForAllPixelWithoutLastTwo() {
        assertEquals(0xfffffffffffffffcl,BitmapPixelHandler.getMultiplePixelMaskUntilPosition(62));
    }
}
