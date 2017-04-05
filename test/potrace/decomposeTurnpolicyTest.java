package potrace;

import BitmapLibrary.*;
import Tools.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
/**
 * Created by andreydelany on 05/04/2017.
 */
public class decomposeTurnpolicyTest {

    DefaultBitMapForFindingDifferentPathes findPathTestBitmap = new DefaultBitMapForFindingDifferentPathes();

    private void comparePathes(Point[] expectedPoints ,potrace_path actualPoints) {
        for (int i = 0 ; i < expectedPoints.length; i ++)
            comparePoints(expectedPoints[i],actualPoints.priv.pt[i]);
    }

    private void comparePoints(Point should, Point actual) {
        Assert.assertEquals(should.x, actual.x);
        Assert.assertEquals(should.y, actual.y);
    }

    @Test
    public void test_findPath_minority() throws Exception {
        potrace_path result = decompose.findpath(findPathTestBitmap,0,4,43,4);
        comparePathes(findPathTestBitmap.getLongPath(),result);
    }

    @Test
    public void test_findPath_majority() throws Exception {
        potrace_path result = decompose.findpath(findPathTestBitmap,0,4,43,5);
        comparePathes(findPathTestBitmap.getShortPath(),result);
    }

    @Test
    public void test_findPath_random() throws Exception {
        potrace_path result = decompose.findpath(findPathTestBitmap,0,4,43,6);
        comparePathes(findPathTestBitmap.getShortPath(),result);
    }

    @Test
    public void test_findPath_right() throws Exception {
        potrace_path result = decompose.findpath(findPathTestBitmap,0,4,43,3);
        comparePathes(findPathTestBitmap.getLongPath(),result);
    }

    @Test
    public void test_findPath_left() throws Exception {
        potrace_path result = decompose.findpath(findPathTestBitmap,0,4,43,2);
        comparePathes(findPathTestBitmap.getShortPath(),result);
    }

    @Test
    public void test_findPath_white() throws Exception {
        potrace_path result = decompose.findpath(findPathTestBitmap,0,4,43,1);
        comparePathes(findPathTestBitmap.getShortPath(),result);
    }

    @Test
    public void test_findPath_black() throws Exception {
        potrace_path result = decompose.findpath(findPathTestBitmap,0,4,43,1);
        comparePathes(findPathTestBitmap.getShortPath(),result);
    }
}
