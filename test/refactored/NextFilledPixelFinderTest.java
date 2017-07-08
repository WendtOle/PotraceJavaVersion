package refactored;

import org.junit.Before;
import org.junit.Test;
import refactored.Bitmap;
import refactored.BitmapHandler;
import refactored.BitmapHandlerInterface;
import refactored.NextFilledPixelFinder;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by andreydelany on 04.07.17.
 */
public class NextFilledPixelFinderTest {

    Bitmap bitmap = new Bitmap(100,100);
    BitmapHandlerInterface bitmapHandler;

    @Before
    public void prepare() {
        bitmapHandler = new BitmapHandler(bitmap);
    }

    @Test
    public void findNextPixelInSameLineInSameWord() {
        bitmapHandler.setPixel(new Point(3,99));
        NextFilledPixelFinder pixelFinder = new NextFilledPixelFinder(bitmap);
        assertTrue(pixelFinder.isThereAFilledPixel());
        Point expectedPosition = new Point(3,99);
        Point actualPosition = pixelFinder.getPositionOfNextFilledPixel();
        assertEquals(expectedPosition,actualPosition);
    }

    @Test
    public void findNextPixelInSameLineButNotInFirstWord() {
        bitmapHandler.setPixel(new Point(65,99));
        NextFilledPixelFinder pixelFinder = new NextFilledPixelFinder(bitmap);
        assertTrue(pixelFinder.isThereAFilledPixel());
        Point expectedPosition = new Point(65,99);
        Point actualPosition = pixelFinder.getPositionOfNextFilledPixel();
        assertEquals(expectedPosition,actualPosition);
    }

    @Test
    public void findNextPixelNotInSameLine() {
        bitmapHandler.setPixel(new Point(65,98));
        NextFilledPixelFinder pixelFinder = new NextFilledPixelFinder(bitmap);
        assertTrue(pixelFinder.isThereAFilledPixel());
        Point expectedPosition = new Point(65,98);
        Point actualPosition = pixelFinder.getPositionOfNextFilledPixel();
        assertEquals(expectedPosition,actualPosition);
    }

    @Test
    public void findTwoFilledPixel() {
        bitmapHandler.setPixel(new Point(5,98));
        NextFilledPixelFinder pixelFinder = new NextFilledPixelFinder(bitmap);
        assertTrue(pixelFinder.isThereAFilledPixel());
        assertEquals(new Point(5,98),pixelFinder.getPositionOfNextFilledPixel());

        bitmapHandler.clearCompleteBitmap();
        bitmapHandler.setPixel(new Point(65,6));
        assertTrue(pixelFinder.isThereAFilledPixel());
        assertEquals(new Point(65,6),pixelFinder.getPositionOfNextFilledPixel());

        bitmapHandler.clearCompleteBitmap();
        assertFalse(pixelFinder.isThereAFilledPixel());
    }

    @Test
    public void thereIsNoFilledPixelToFind(){
        NextFilledPixelFinder pixelFinder = new NextFilledPixelFinder(bitmap);
        assertFalse(pixelFinder.isThereAFilledPixel());
    }

    @Test
    public void throwingAExceptionWhenTryingToExcessNotFoundPixel() {
        NextFilledPixelFinder pixelFinder = new NextFilledPixelFinder(bitmap);
        assertFalse(pixelFinder.isThereAFilledPixel());

        boolean exceptionWasThrown = false;
        try {
            pixelFinder.getPositionOfNextFilledPixel();
        } catch (RuntimeException e){
            exceptionWasThrown = true;
        }
        assertTrue(exceptionWasThrown);
    }
}