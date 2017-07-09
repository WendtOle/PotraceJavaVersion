package refactored;

import General.Bitmap;
import General.Path;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
public class decomposeTest {

    @Test
    public void test_findnextWithNextPointInSameLine() {
        Bitmap testBitmap = new Bitmap(70,1);
        BitmapHandlerInterface manipulator = new BitmapHandler(testBitmap);
        manipulator.setPixel(new Point(65,0));

        NextFilledPixelFinder nextFilledPixelFinder = new NextFilledPixelFinder(testBitmap);
        assertTrue("found sth: ", nextFilledPixelFinder.isThereAFilledPixel());
        assertEquals("found point: ",new Point(65,0),nextFilledPixelFinder.getPositionOfNextFilledPixel());
    }

    @Test
    public void test_findnextWithNextPointNotInSameLine() {
        Bitmap testBitmap = new Bitmap(128,2);
        BitmapHandlerInterface manipulator = new BitmapHandler(testBitmap);
        manipulator.setPixel(new Point(97,0));
        manipulator.setPixel(new Point(99,0));

        NextFilledPixelFinder nextFilledPixelFinder = new NextFilledPixelFinder(testBitmap);

        assertTrue("found sth: ", nextFilledPixelFinder.isThereAFilledPixel());
        assertEquals("found point: ", new Point(97,0),nextFilledPixelFinder.getPositionOfNextFilledPixel());
    }

    @Test
    public void test_findPath_boundary() {
        Bitmap testBitmap = new Bitmap(128,1);
        BitmapHandlerInterface manipulator = new BitmapHandler(testBitmap);
        manipulator.setPixel(new Point(63,0));
        manipulator.setPixel(new Point(64,0));

        Point[] expectedPath = new Point[]{ new Point(63,1), new Point(63,0),
                new Point(64,0),new Point(65,0),new Point(65,1),
                new Point(64,1)};

        FindPath findPath = new FindPath(testBitmap,new Point(63,0),43, TurnPolicyEnum.MINORITY);
        Path result = findPath.getPath();
        for (int i = 0 ; i < expectedPath.length; i ++)
            assertEquals(expectedPath[i],result.priv.pt[i]);
    }
}