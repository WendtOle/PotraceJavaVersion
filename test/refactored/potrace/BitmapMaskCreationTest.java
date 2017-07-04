package refactored.potrace;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 02.07.17.
 */
public class BitmapMaskCreationTest {

    @Test
    public void testCreatingMaskForFirstPixel(){
        assertEquals(0x8000000000000000l,MaskCreator.getOnePixelMaskForPosition(0));
    }

    @Test
    public void testCreatingMaskForFirstPixelOverBound(){
        assertEquals(0x8000000000000000l,MaskCreator.getOnePixelMaskForPosition(64));
    }

    @Test
    public void testCreatingMaskForLastPixel(){
        assertEquals(0x1l,MaskCreator.getOnePixelMaskForPosition(63));
    }

    @Test
    public void testCreatingMaskForLastPixelOverBound(){
        assertEquals(0x1l,MaskCreator.getOnePixelMaskForPosition(-1));
    }

    @Test
    public void testCreatingMaskForAPixel(){
        assertEquals(0x8l,MaskCreator.getOnePixelMaskForPosition(60));
    }

    @Test
    public void testCreatingMaskForAllPixels() {
        assertEquals(-1l,MaskCreator.getMultiplePixelMaskUntilPosition(64));
    }

    @Test
    public void testCreatingMaskForFirstTwoPixels() {
        assertEquals(0xc000000000000000l,MaskCreator.getMultiplePixelMaskUntilPosition(2));
    }

    @Test
    public void testCreatingMaskForAllPixelWithoutLastTwo() {
        assertEquals(0xfffffffffffffffcl,MaskCreator.getMultiplePixelMaskUntilPosition(62));
    }
}
