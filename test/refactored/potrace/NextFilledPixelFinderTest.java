package refactored.potrace;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        assertEquals(expectedPosition.x,actualPosition.x);
        assertEquals(expectedPosition.y, actualPosition.y);
    }

    @Test
    public void findNextPixelInSameLineButNotInFirstWord() {
        bitmapHandler.setPixel(new Point(65,99));
        NextFilledPixelFinder pixelFinder = new NextFilledPixelFinder(bitmap);
        assertTrue(pixelFinder.isThereAFilledPixel());
        Point expectedPosition = new Point(65,99);
        Point actualPosition = pixelFinder.getPositionOfNextFilledPixel();
        assertEquals(expectedPosition.x,actualPosition.x);
        assertEquals(expectedPosition.y, actualPosition.y);
    }

    @Test
    public void findNextPixelNotInSameLine() {
        bitmapHandler.setPixel(new Point(65,98));
        NextFilledPixelFinder pixelFinder = new NextFilledPixelFinder(bitmap);
        assertTrue(pixelFinder.isThereAFilledPixel());
        Point expectedPosition = new Point(65,98);
        Point actualPosition = pixelFinder.getPositionOfNextFilledPixel();
        assertEquals(expectedPosition.x,actualPosition.x);
        assertEquals(expectedPosition.y, actualPosition.y);
    }
}
