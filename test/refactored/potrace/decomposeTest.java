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
        testBitmap.setPixelToValue(65,0,true);

        Point point = new Point(0,0);
        assertTrue("found sth: ", (point = testBitmap.findNextFilledPixel(point)) != null);
        assertEquals("found point: ",new Point(65,0),point);
    }

    @Test
    public void test_findnextWithNextPointNotInSameLine() {
        Bitmap testBitmap = new Bitmap(128,2);
        testBitmap.setPixelToValue(97,0,true);
        testBitmap.setPixelToValue(99,0,true);

        Point point = new Point(0,1);
        assertTrue("found sth: ", (point = testBitmap.findNextFilledPixel(point)) != null);
        assertEquals("found point: ", new Point(97,0),point);
    }

    @Test
    public void test_findPath_boundary() {
        Bitmap testBitmap = new Bitmap(128,1);
        testBitmap.setPixelToValue(63,0,true);
        testBitmap.setPixelToValue(64,0,true);

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
        testBitmap.setWholeBitmapToSpecificValue(1);
        testBitmap.clearExcessPixelsOfBitmap();
        testBitmap.setPixelToValue(1,1,false);

        FindPath findPath = new FindPath(testBitmap,new Point(0,3),43,4);
        Path path = findPath.getPath();
        testBitmap.invertPathOnBitmap(path);

        assertEquals(false, testBitmap.getPixelValue(0,0));
        assertEquals(false, testBitmap.getPixelValue(1,0));
        assertEquals(false, testBitmap.getPixelValue(2,0));
        assertEquals(false, testBitmap.getPixelValue(0,1));
        assertEquals(true, testBitmap.getPixelValue(1,1));
        assertEquals(false, testBitmap.getPixelValue(2,1));
        assertEquals(false, testBitmap.getPixelValue(0,2));
        assertEquals(false, testBitmap.getPixelValue(1,3));
        assertEquals(false, testBitmap.getPixelValue(2,3));
    }
}