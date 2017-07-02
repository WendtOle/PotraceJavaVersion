package refactored.potrace;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class decomposeTest {

    @Test
    public void test_findnextWithNextPointInSameLine() {
        Bitmap testBitmap = new Bitmap(70,1);
        BitmapManipulator manipulator = new BitmapManipulator(testBitmap);
        manipulator.setPixelToValue(new Point(65,0),true);

        NextFilledPixelFinder nextFilledPixelFinder = new NextFilledPixelFinder(testBitmap);
        assertTrue("found sth: ", nextFilledPixelFinder.isThereAFilledPixel());
        assertEquals("found point: ",new Point(65,0),nextFilledPixelFinder.getPositionOfNextFilledPixel());
    }

    @Test
    public void test_findnextWithNextPointNotInSameLine() {
        Bitmap testBitmap = new Bitmap(128,2);
        BitmapManipulator manipulator = new BitmapManipulator(testBitmap);
        manipulator.setPixelToValue(new Point(97,0),true);
        manipulator.setPixelToValue(new Point(99,0),true);

        NextFilledPixelFinder nextFilledPixelFinder = new NextFilledPixelFinder(testBitmap);

        assertTrue("found sth: ", nextFilledPixelFinder.isThereAFilledPixel());
        assertEquals("found point: ", new Point(97,0),nextFilledPixelFinder.getPositionOfNextFilledPixel());
    }

    @Test
    public void test_findPath_boundary() {
        Bitmap testBitmap = new Bitmap(128,1);
        BitmapManipulator manipulator = new BitmapManipulator(testBitmap);
        manipulator.setPixelToValue(new Point(63,0),true);
        manipulator.setPixelToValue(new Point(64,0),true);

        Point[] expectedPath = new Point[]{ new Point(63,1), new Point(63,0),
                new Point(64,0),new Point(65,0),new Point(65,1),
                new Point(64,1)};

        FindPath findPath = new FindPath(testBitmap,new Point(63,1),43,4);
        Path result = findPath.getPath();
        for (int i = 0 ; i < expectedPath.length; i ++)
            comparePoints(expectedPath[i],result.priv.pt[i]);
    }

    private void comparePoints(Point should, Point actual) {
        Assert.assertEquals(should.x, actual.x);
        Assert.assertEquals(should.y, actual.y);
    }

    @Test
    public void test_xor_path() {
        Bitmap testBitmap = new Bitmap(3,3);
        BitmapManipulator manipulator = new BitmapManipulator(testBitmap);
        manipulator.setWholeBitmapToSpecificValue(1);
        manipulator.clearExcessPixelsOfBitmap();
        manipulator.setPixelToValue(new Point(1,1),false);

        FindPath findPath = new FindPath(testBitmap,new Point(0,3),43,4);
        Path path = findPath.getPath();
        PathOnBitmapInverter inverter = new PathOnBitmapInverter(testBitmap);
        inverter.invertPathOnBitmap(path);

        assertEquals(false, testBitmap.getPixelValue(new Point(0,0)));
        assertEquals(false, testBitmap.getPixelValue(new Point(1,0)));
        assertEquals(false, testBitmap.getPixelValue(new Point(2,0)));
        assertEquals(false, testBitmap.getPixelValue(new Point(0,1)));
        assertEquals(true, testBitmap.getPixelValue(new Point(1,1)));
        assertEquals(false, testBitmap.getPixelValue(new Point(2,1)));
        assertEquals(false, testBitmap.getPixelValue(new Point(0,2)));
        assertEquals(false, testBitmap.getPixelValue(new Point(1,3)));
        assertEquals(false, testBitmap.getPixelValue(new Point(2,3)));
    }
}