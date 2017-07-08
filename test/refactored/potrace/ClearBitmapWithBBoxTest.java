package refactored.potrace;

import org.junit.Before;
import org.junit.Test;
import refactored.potrace.*;

import java.awt.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by andreydelany on 05.07.17.
 */
public class ClearBitmapWithBBoxTest {

    Bitmap bitmap = new Bitmap(70,4);
    BitmapHandlerInterface bitmapHandler;

    @Before
    public void prepareBitmap() {
        bitmapHandler = new BitmapHandler(bitmap);
        bitmapHandler.setPixel(new Point(0,0));
        bitmapHandler.setPixel(new Point(0,1));
        bitmapHandler.setPixel(new Point(1,1));
        bitmapHandler.setPixel(new Point(63,1));
        bitmapHandler.setPixel(new Point(2,2));
        bitmapHandler.setPixel(new Point(3,3));
        bitmapHandler.setPixel(new Point(65,0));
        bitmapHandler.setPixel(new Point(65,1));
        bitmapHandler.setPixel(new Point(65,2));
        bitmapHandler.setPixel(new Point(65,3));
    }

    @Test
    public void testToClearOnPixelBBox() {
        BoundingBox bbox = new BoundingBox();
        bbox.x0 = 1;
        bbox.x1 = 2;
        bbox.y0 = 1;
        bbox.y1 = 2;

        ClearBitmapWithBBox bitmapClearer = new ClearBitmapWithBBox(bitmap);
        bitmapClearer.clearBitmapWithBBox(bbox);

        assertTrue(bitmapHandler.isPixelFilled(new Point(0,0)));
        assertFalse(bitmapHandler.isPixelFilled(new Point(1,1)));
        assertTrue(bitmapHandler.isPixelFilled(new Point(2,2)));
        assertTrue(bitmapHandler.isPixelFilled(new Point(3,3)));
    }

    @Test
    public void testToClearTwoPerTwoBBox() {
        BoundingBox bbox = new BoundingBox();
        bbox.x0 = 1;
        bbox.x1 = 3;
        bbox.y0 = 1;
        bbox.y1 = 3;

        ClearBitmapWithBBox bitmapClearer = new ClearBitmapWithBBox(bitmap);
        bitmapClearer.clearBitmapWithBBox(bbox);

        assertTrue(bitmapHandler.isPixelFilled(new Point(0,0)));
        assertFalse(bitmapHandler.isPixelFilled(new Point(1,1)));
        assertFalse(bitmapHandler.isPixelFilled(new Point(2,2)));
        assertTrue(bitmapHandler.isPixelFilled(new Point(3,3)));
    }

    @Test
    public void testToClearOnePixelOverWordBoundary() {
        BoundingBox bbox = new BoundingBox();
        bbox.x0 = 63;
        bbox.x1 = 66;
        bbox.y0 = 1;
        bbox.y1 = 2;

        ClearBitmapWithBBox bitmapClearer = new ClearBitmapWithBBox(bitmap);
        bitmapClearer.clearBitmapWithBBox(bbox);

        assertFalse(bitmapHandler.isPixelFilled(new Point(65,1)));
    }

    @Test
    public void testClearCompleteTwoLinesOfBitmap() {
        BoundingBox bbox = new BoundingBox();
        bbox.x0 = 0;
        bbox.x1 = 71;
        bbox.y0 = 1;
        bbox.y1 = 3;

        ClearBitmapWithBBox bitmapClearer = new ClearBitmapWithBBox(bitmap);
        bitmapClearer.clearBitmapWithBBox(bbox);

        assertFalse(bitmapHandler.isPixelFilled(new Point(1,1)));
        assertFalse(bitmapHandler.isPixelFilled(new Point(2,2)));
        assertFalse(bitmapHandler.isPixelFilled(new Point(65,1)));
        assertFalse(bitmapHandler.isPixelFilled(new Point(65,2)));
    }

    @Test
    public void testThatEveryPixelInSameLineIsAlsoClearedButNotOtherWords() {
        BoundingBox bbox = new BoundingBox();
        bbox.x0 = 1;
        bbox.x1 = 2;
        bbox.y0 = 1;
        bbox.y1 = 2;

        ClearBitmapWithBBox bitmapClearer = new ClearBitmapWithBBox(bitmap);
        bitmapClearer.clearBitmapWithBBox(bbox);

        assertFalse(bitmapHandler.isPixelFilled(new Point(0,1)));
        assertFalse(bitmapHandler.isPixelFilled(new Point(1,1)));
        assertFalse(bitmapHandler.isPixelFilled(new Point(63,1)));
        assertTrue(bitmapHandler.isPixelFilled(new Point(65,1)));
    }

}
