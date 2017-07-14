package Potrace.refactored;

import Potrace.General.Bitmap;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by andreydelany on 04.07.17.
 */
public class BitmapHandlerTest {

    @Test
    public void testSettingPixel(){
        Bitmap bitmap = new Bitmap(10,10);
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(bitmap);
        bitmapHandler.setPixel(new Point(0,0));

        assertEquals(bitmap.map[0],0x8000000000000000l);
    }

    @Test
    public void testGettingPixelFromLegalLocation(){
        Bitmap bitmap = new Bitmap(10,10);
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(bitmap);
        bitmapHandler.setPixel(new Point(0,0));

        assertTrue(bitmapHandler.isPixelFilled(new Point(0,0)));
    }

    @Test
    public void testGettingPixelFromIlLegalLocation(){
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(new Bitmap(10,10));

        assertFalse(bitmapHandler.isPixelFilled(new Point(-1,0)));
    }

    @Test
    public void testGettingPixelThatWasntEverSet(){
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(new Bitmap(10,10));

        assertFalse(bitmapHandler.isPixelFilled(new Point(0,0)));
    }

    @Test
    public void testClearingExcessPixelWhenConstructingABitmapHandler(){
        Bitmap bitmap = new Bitmap(65,1);
        bitmap.map[1] = -1;
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(bitmap);

        assertEquals(bitmap.map[1],0x8000000000000000l);
    }

    @Test
    public void testClearCompleteBitmap() throws Exception {
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(new Bitmap(10,10));
        bitmapHandler.setPixel(new Point(0,1));
        bitmapHandler.setPixel(new Point(4,4));

        bitmapHandler.clearCompleteBitmap();

        assertFalse(bitmapHandler.isPixelFilled(new Point(0,1)));
        assertFalse(bitmapHandler.isPixelFilled(new Point(4,4)));
    }

    @Test
    public void testIsThereAFilledPixelInWordWhenThereIsActualOne(){
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(new Bitmap(10,10));
        bitmapHandler.setPixel(new Point(0,0));

        assertTrue(bitmapHandler.areThereFilledPixelInWord(new Point(0,0)));
    }

    @Test
    public void testIsThereAFilledPixelInWordWhenThereIsActualNone(){
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(new Bitmap(10,10));

        assertFalse(bitmapHandler.areThereFilledPixelInWord(new Point(0,0)));
    }

    @Test
    public void testClearingCompleteWord(){
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(new Bitmap(10,10));
        bitmapHandler.setPixel(new Point(0,0));
        bitmapHandler.clearPotraceWord(new Point(0,0));

        assertFalse(bitmapHandler.areThereFilledPixelInWord(new Point(0,0)));
    }

    @Test
    public void testFlippingCompleteWordWithMask(){
        long mask = 1l;
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(new Bitmap(64,1));
        bitmapHandler.setPixel(new Point(62,0));
        bitmapHandler.setPixel(new Point(63,0));
        bitmapHandler.invertPotraceWordWithMask(new Point(0,0),mask);

        assertTrue(bitmapHandler.isPixelFilled(new Point(62,0)));
        assertFalse(bitmapHandler.isPixelFilled(new Point(63,0)));
    }

    @Test
    public void testGetBeginningIndexOfWordWithPixel(){
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(new Bitmap(10,10));

        assertEquals(0,bitmapHandler.getBeginningIndexOfWordWithPixel(new Point(3,0)));
        assertEquals(0,bitmapHandler.getBeginningIndexOfWordWithPixel(new Point(63,0)));
        assertEquals(64,bitmapHandler.getBeginningIndexOfWordWithPixel(new Point(70,0)));
        assertEquals(64,bitmapHandler.getBeginningIndexOfWordWithPixel(new Point(120,0)));
    }

    @Test
    public void testIsPixelStillInBitmap(){
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(new Bitmap(1,1));

        assertTrue(bitmapHandler.isPixelInBitmap(new Point(0,0)));
        assertFalse(bitmapHandler.isPixelInBitmap(new Point(1,1)));
        assertFalse(bitmapHandler.isPixelInBitmap(new Point(0,1)));
        assertFalse(bitmapHandler.isPixelInBitmap(new Point(1,0)));
    }
}
