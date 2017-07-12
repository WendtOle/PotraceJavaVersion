package Potrace.refactored;

import Potrace.General.Bitmap;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by andreydelany on 04.07.17.
 */
public class NextFilledPixelFinderTest {

    Bitmap bitmap = new Bitmap(100,100);
    BitmapHandlerInterface bitmapHandler = new BitmapHandler(bitmap);
    NextFilledPixelFinder pixelFinder;

    @Before
    public void clearBitmap(){
        bitmapHandler.clearCompleteBitmap();
    }

    @Test
    public void findNextPixelInSameLineInSameWord() {
        Point filledPixel = new Point(3,99);
        pixelFinder = getFilledPixelFinderForTesting(filledPixel);

        assertTrue(pixelFinder.isThereAFilledPixel());
        assertEquals(filledPixel,pixelFinder.getPositionOfNextFilledPixel());
    }

    @Test
    public void findNextPixelInSameLineButNotInFirstWord() {
        Point filledPixel = new Point(65,99);
        pixelFinder = getFilledPixelFinderForTesting(filledPixel);

        assertTrue(pixelFinder.isThereAFilledPixel());
        assertEquals(filledPixel,pixelFinder.getPositionOfNextFilledPixel());
    }

    @Test
    public void findNextPixelNotInSameLine() {
        Point filledPixel = new Point(65,98);
        pixelFinder = getFilledPixelFinderForTesting(filledPixel);

        assertTrue(pixelFinder.isThereAFilledPixel());
        assertEquals(filledPixel,pixelFinder.getPositionOfNextFilledPixel());
    }

    @Test
    public void findTwoPixelWithSamePixelFinder() {
        Point firstFilledPixel = new Point(5,98);
        Point secondFilledPixel = new Point(65,6);
        pixelFinder = getFilledPixelFinderForTesting(firstFilledPixel);

        assertTrue(pixelFinder.isThereAFilledPixel());
        assertEquals(firstFilledPixel,pixelFinder.getPositionOfNextFilledPixel());

        setAnotherPixelToBitmap(secondFilledPixel);

        assertTrue(pixelFinder.isThereAFilledPixel());
        assertEquals(secondFilledPixel,pixelFinder.getPositionOfNextFilledPixel());
    }

    @Test
    public void thereIsNoFilledPixelToFind(){
        NextFilledPixelFinder pixelFinder = new NextFilledPixelFinder(new Bitmap(100,100));

        assertFalse(pixelFinder.isThereAFilledPixel());
    }

    @Test
    public void throwingAExceptionWhenTryingToExcessNotFoundPixel() {
        NextFilledPixelFinder pixelFinder = new NextFilledPixelFinder(new Bitmap(100,100));

        assertFalse(pixelFinder.isThereAFilledPixel());
        assertTrue(wasExceptionThrown(pixelFinder));
    }

    private NextFilledPixelFinder getFilledPixelFinderForTesting(Point pixel){
        bitmapHandler.setPixel(pixel);
        return new NextFilledPixelFinder(bitmap);
    }

    private void setAnotherPixelToBitmap(Point pixel){
        bitmapHandler.clearCompleteBitmap();
        bitmapHandler.setPixel(pixel);
    }

    private boolean wasExceptionThrown(NextFilledPixelFinder pixelFinder) {
        try {
            pixelFinder.getPositionOfNextFilledPixel();
        } catch (RuntimeException e){
            return true;
        }
        return false;
    }
}