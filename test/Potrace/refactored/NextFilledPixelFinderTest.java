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

        assertEquals(filledPixel,pixelFinder.getPositionOfNextFilledPixel());
    }

    @Test
    public void findNextPixelInSameLineButNotInFirstWord() {
        Point filledPixel = new Point(65,99);
        pixelFinder = getFilledPixelFinderForTesting(filledPixel);

        assertEquals(filledPixel,pixelFinder.getPositionOfNextFilledPixel());
    }

    @Test
    public void findNextPixelNotInSameLine() {
        Point filledPixel = new Point(65,98);
        pixelFinder = getFilledPixelFinderForTesting(filledPixel);

        assertEquals(filledPixel,pixelFinder.getPositionOfNextFilledPixel());
    }

    @Test
    public void findTwoPixelWithSamePixelFinder() {
        Point firstFilledPixel = new Point(5,98);
        Point secondFilledPixel = new Point(65,6);
        pixelFinder = getFilledPixelFinderForTesting(firstFilledPixel);

        assertEquals(firstFilledPixel,pixelFinder.getPositionOfNextFilledPixel());

        setAnotherPixelToBitmap(secondFilledPixel);

        assertEquals(secondFilledPixel,pixelFinder.getPositionOfNextFilledPixel());
    }

    @Test
    public void thereIsNoFilledPixelToFind(){
        NextFilledPixelFinder pixelFinder = new NextFilledPixelFinder(new Bitmap(100,100));

        assertEquals(new NoFilledPixelFound(),pixelFinder.getPositionOfNextFilledPixel());
    }

    @Test
    public void throwingAExceptionWhenTryingToExcessNotFoundPixel() {
        NextFilledPixelFinder pixelFinder = new NextFilledPixelFinder(new Bitmap(100,100));

        assertEquals(new NoFilledPixelFound(),pixelFinder.getPositionOfNextFilledPixel());
    }

    private NextFilledPixelFinder getFilledPixelFinderForTesting(Point pixel){
        bitmapHandler.setPixel(pixel);
        return new NextFilledPixelFinder(bitmap);
    }

    private void setAnotherPixelToBitmap(Point pixel){
        bitmapHandler.clearCompleteBitmap();
        bitmapHandler.setPixel(pixel);
    }
}