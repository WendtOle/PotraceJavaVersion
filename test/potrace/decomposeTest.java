package potrace;

import Tools.BitmapPrinter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by andreydelany on 20/03/2017.
 */
public class decomposeTest {

    Point[] expectedPointsForBigPath = new Point[20];
    Point[] expectedPointsForSmallPath = new Point[12];
    potrace_bitmap findPathTestBitmap = new potrace_bitmap(4,4);

    @Before
    public void before() {
        expectedPointsForBigPath[0] = new Point(0,4);
        expectedPointsForBigPath[1] = new Point(0,3);
        expectedPointsForBigPath[2] = new Point(0,2);
        expectedPointsForBigPath[3] = new Point(1,2);
        expectedPointsForBigPath[4] = new Point(1,1);
        expectedPointsForBigPath[5] = new Point(0,1);
        expectedPointsForBigPath[6] = new Point(0,0);
        expectedPointsForBigPath[7] = new Point(1,0);
        expectedPointsForBigPath[8] = new Point(1,1);
        expectedPointsForBigPath[9] = new Point(2,1);
        expectedPointsForBigPath[10] = new Point(3,1);
        expectedPointsForBigPath[11] = new Point(3,0);
        expectedPointsForBigPath[12] = new Point(4,0);
        expectedPointsForBigPath[13] = new Point(4,1);
        expectedPointsForBigPath[14] = new Point(3,1);
        expectedPointsForBigPath[15] = new Point(3,2);
        expectedPointsForBigPath[16] = new Point(2,2);
        expectedPointsForBigPath[17] = new Point(2,3);
        expectedPointsForBigPath[18] = new Point(1,3);
        expectedPointsForBigPath[19] = new Point(1,4);

        expectedPointsForSmallPath[0] = new Point(0,4);
        expectedPointsForSmallPath[1] = new Point(0,3);
        expectedPointsForSmallPath[2] = new Point(0,2);
        expectedPointsForSmallPath[3] = new Point(1,2);
        expectedPointsForSmallPath[4] = new Point(1,1);
        expectedPointsForSmallPath[5] = new Point(2,1);
        expectedPointsForSmallPath[6] = new Point(3,1);
        expectedPointsForSmallPath[7] = new Point(3,2);
        expectedPointsForSmallPath[8] = new Point(2,2);
        expectedPointsForSmallPath[9] = new Point(2,3);
        expectedPointsForSmallPath[10] = new Point(1,3);
        expectedPointsForSmallPath[11] = new Point(1,4);

        findPathTestBitmap.map[3] = 0x80000000;
        findPathTestBitmap.map[2] = 0xc0000000;
        findPathTestBitmap.map[1] = 0x60000000;
        findPathTestBitmap.map[0] = 0x90000000;
    }

    @Test
    public void test_findnextWithNextPointInSameLine() throws Exception {
        potrace_bitmap testBitmap = new potrace_bitmap(40,2);
        testBitmap.map[3] = 0x49000000;
        testBitmap.map[2] = 0;
        testBitmap.map[1] = 0x49000000;
        testBitmap.map[0] = 0;

        Point startPoint = new Point(0,0);
        Point foundPoint = decompose.findnext(testBitmap,startPoint);
        assertEquals(new Point(33,0),foundPoint);
    }

    @Test
    public void test_findnextWithNextPointNotInSameLine() throws Exception {
        potrace_bitmap testBitmap = new potrace_bitmap(40,2);
        testBitmap.map[3] = 0x49000000;
        testBitmap.map[2] = 0;
        testBitmap.map[1] = 0;
        testBitmap.map[0] = 0;

        Point startPoint = new Point(0,0);
        Point foundPoint = decompose.findnext(testBitmap,startPoint);
        assertEquals(null,foundPoint);
    }

    @Test
    public void test_detrand() throws Exception {
        Assert.assertEquals(false, decompose.detrand(20,4));
    }

    @Test
    public void test_majorityWhereIsActualMajority() throws Exception {
        potrace_bitmap testBitmap = new potrace_bitmap(4,4);
        testBitmap.map[3] = 0x70000000;
        testBitmap.map[2] = 0xb0000000;
        testBitmap.map[1] = 0xd0000000;
        testBitmap.map[0] = 0xe0000000;
        BitmapPrinter bitmapPrinter = new BitmapPrinter(testBitmap);
        bitmapPrinter.print();
        Assert.assertEquals(true, decompose.majority(testBitmap,2,2));
    }

    @Test
    public void test_majorityWhereIsNoMajority() throws Exception {
        potrace_bitmap testBitmap = new potrace_bitmap(4,4);
        testBitmap.map[3] = 0x30000000;
        testBitmap.map[2] = 0x30000000;
        testBitmap.map[1] = 0xc0000000;
        testBitmap.map[0] = 0xc0000000;
        BitmapPrinter bitmapPrinter = new BitmapPrinter(testBitmap);
        bitmapPrinter.print();
        Assert.assertEquals(false, decompose.majority(testBitmap,2,2));
    }

    private void comparePoints(Point should, Point actual) {
        Assert.assertEquals(should.x, actual.x);
        Assert.assertEquals(should.y, actual.y);
    }

    @Test
    public void test_findPath_minority() throws Exception {
        potrace_path result = decompose.findpath(findPathTestBitmap,0,4,43,4);
        for (int i = 0 ; i < expectedPointsForBigPath.length; i ++)
            comparePoints(expectedPointsForBigPath[i],result.priv.pt[i]);
    }

    @Test
    public void test_findPath_majority() throws Exception {
        potrace_path result = decompose.findpath(findPathTestBitmap,0,4,43,5);
        for (int i = 0 ; i < expectedPointsForSmallPath.length; i ++)
            comparePoints(expectedPointsForSmallPath[i],result.priv.pt[i]);
    }

    @Test
    public void test_findPath_random() throws Exception {
        potrace_path result = decompose.findpath(findPathTestBitmap,0,4,43,6);
        for (int i = 0 ; i < expectedPointsForSmallPath.length; i ++)
            comparePoints(expectedPointsForSmallPath[i],result.priv.pt[i]);
    }

    @Test
    public void test_findPath_right() throws Exception {
        potrace_path result = decompose.findpath(findPathTestBitmap,0,4,43,3);
        for (int i = 0 ; i < expectedPointsForBigPath.length; i ++)
            comparePoints(expectedPointsForBigPath[i],result.priv.pt[i]);
    }

    @Test
    public void test_findPath_left() throws Exception {
        potrace_path result = decompose.findpath(findPathTestBitmap,0,4,43,2);
        for (int i = 0 ; i < expectedPointsForSmallPath.length; i ++)
            comparePoints(expectedPointsForSmallPath[i],result.priv.pt[i]);
    }

    @Test
    public void test_findPath_white() throws Exception {
        potrace_path result = decompose.findpath(findPathTestBitmap,0,4,43,1);
        for (int i = 0 ; i < expectedPointsForSmallPath.length; i ++)
            comparePoints(expectedPointsForSmallPath[i],result.priv.pt[i]);
    }

    @Test
    public void test_findPath_black() throws Exception {
        potrace_path result = decompose.findpath(findPathTestBitmap,0,4,43,1);
        for (int i = 0 ; i < expectedPointsForSmallPath.length; i ++)
            comparePoints(expectedPointsForSmallPath[i],result.priv.pt[i]);
    }

}

