package refactored.potrace;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 02.07.17.
 */
public class BitmapMaskCreationTest {

    BitmapPixelHandler handler = new BitmapPixelHandler(new Bitmap());

    @Test
    public void testCreatingMaskForFirstPixel(){
        assertEquals(0x8000000000000000l,handler.getOnePixelMaskForPosition(0));
    }

    @Test
    public void testCreatingMaskForFirstPixelOverBound(){
        assertEquals(0x8000000000000000l,handler.getOnePixelMaskForPosition(64));
    }

    @Test
    public void testCreatingMaskForLastPixel(){
        assertEquals(0x1l,handler.getOnePixelMaskForPosition(63));
    }

    @Test
    public void testCreatingMaskForLastPixelOverBound(){
        assertEquals(0x1l,handler.getOnePixelMaskForPosition(-1));
    }

    @Test
    public void testCreatingMaskForAPixel(){
        assertEquals(0x8l,handler.getOnePixelMaskForPosition(60));
    }

    @Test
    public void testCreatingMaskForAllPixels() {
        assertEquals(-1l,handler.getMultiplePixelMasUntilPosition(64));
    }

    @Test
    public void testCreatingMaskForFirstTwoPixels() {
        assertEquals(0xc000000000000000l,handler.getMultiplePixelMasUntilPosition(2));
    }

    @Test
    public void testCreatingMaskForAllPixelWithoutLastTwo() {
        assertEquals(0xfffffffffffffffcl,handler.getMultiplePixelMasUntilPosition(62));
    }
}
