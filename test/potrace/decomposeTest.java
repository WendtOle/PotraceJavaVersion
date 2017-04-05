package potrace;

import BitmapLibrary.*;
import Tools.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by andreydelany on 20/03/2017.
 */
public class decomposeTest {

    @Test
    public void test_findnextWithNextPointInSameLine() throws Exception {
        BetterBitmap testBitmap = new BetterBitmap(70,1);
        testBitmap.addBlob(new Point(65,0),true);

        Point startPoint = new Point(0,0);
        Point foundPoint = decompose.findnext(testBitmap,startPoint);
        assertEquals(new Point(65,0),foundPoint);
    }

    @Test
    public void test_findnextWithNextPointNotInSameLine() throws Exception {
        BetterBitmap testBitmap = new BetterBitmap(128,2);
        testBitmap.addBlob(new Point(97,1),true);
        testBitmap.addBlob(new Point(99,1),true);

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
        BetterBitmap testBitmap = new BetterBitmap(4,4);
        testBitmap.fillCompleteBitMap(true);
        testBitmap.addBlob(new Point(0,3),false);
        testBitmap.addBlob(new Point(1,2),false);
        testBitmap.addBlob(new Point(2,2),false);
        testBitmap.addBlob(new Point(3,0),false);

        BitmapPrinter bitmapPrinter = new BitmapPrinter(testBitmap);
        bitmapPrinter.print();
        Assert.assertEquals(true, decompose.majority(testBitmap,2,2));
    }

    @Test
    public void test_majorityWhereIsNoMajority() throws Exception {
        BetterBitmap testBitmap = new BetterBitmap(4,4);
        testBitmap.addPolygon(new Point(2,3), new Point(3,2),true);
        testBitmap.addPolygon(new Point(0,1), new Point(1,0),true);
        BitmapPrinter bitmapPrinter = new BitmapPrinter(testBitmap);
        bitmapPrinter.print();
        Assert.assertEquals(false, decompose.majority(testBitmap,2,2));
    }

    private void comparePoints(Point should, Point actual) {
        Assert.assertEquals(should.x, actual.x);
        Assert.assertEquals(should.y, actual.y);
    }

    @Test
    public void test_findPath_boundary() throws Exception {
        BetterBitmap testBitmap = new BetterBitmap(128,1);
        testBitmap.addPolygon(new Point(63,0), new Point(64,0),true);

        Point[] expectedPoints = new Point[6];
        expectedPoints[0] = new Point(63,1);
        expectedPoints[1] = new Point(63,0);
        expectedPoints[2] = new Point(64,0);
        expectedPoints[3] = new Point(65,0);
        expectedPoints[4] = new Point(65,1);
        expectedPoints[5] = new Point(64,1);

        potrace_path result = decompose.findpath(testBitmap,63,1,43,4);
        for (int i = 0 ; i < expectedPoints.length; i ++)
            comparePoints(expectedPoints[i],result.priv.pt[i]);
    }

    @Test
    public void test_xor_path_xor_to_ref_one() {
        BetterBitmap testBitmap = new DefaultBitmapSimpleBoxWithExtension();

        potrace_path path = decompose.findpath(testBitmap,1,3,43,4);
        potrace_bitmap resultBitmap = decompose.xor_path(testBitmap,path);
        assertEquals(0,resultBitmap.map[0]);
        assertEquals(0,resultBitmap.map[1]);
        assertEquals(0,resultBitmap.map[2]);
        assertEquals(0,resultBitmap.map[3]);
    }

    @Test
    public void test_xor_path_xor_to_ref_two() {
        BetterBitmap testBitmap = new DefaultBitmapBoxOnPotraceWordBoundary();

        potrace_path path = decompose.findpath(testBitmap,63,2,43,4);
        potrace_bitmap resultBitmap = decompose.xor_path(testBitmap,path);
        assertEquals(0,resultBitmap.map[0]);
        assertEquals(0,resultBitmap.map[1]);
        assertEquals(0,resultBitmap.map[2]);
        assertEquals(0,resultBitmap.map[3]);
    }

    @Test
    public void test_setbbox_path() {
        bbox box = new bbox();
        BetterBitmap testBitmap = new DefaultBitmapSimpleBoxWithExtension();

        potrace_path path = decompose.findpath(testBitmap,1,3,43,4);

        bbox resultBox = decompose.setbbox_path(box,path);
        assertEquals(0,resultBox.x0);
        assertEquals(4,resultBox.x1);
        assertEquals(0,resultBox.y0);
        assertEquals(4,resultBox.y1);
    }

    @Test
    public void test_setbbox_path_extended() {
        bbox box = new bbox();
        BetterBitmap testBitmap = new DefaultBitmapBoxOnPotraceWordBoundary();

        potrace_path path = decompose.findpath(testBitmap,63,1,43,4);

        bbox resultBox = decompose.setbbox_path(box,path);
        assertEquals(63,resultBox.x0);
        assertEquals(65,resultBox.x1);
        assertEquals(0,resultBox.y0);
        assertEquals(2,resultBox.y1);
    }

    @Test
    public void test_clear_bm_with_bbox() {
        bbox box = new bbox();
        box.x0 = 25;
        box.x1 = 70;
        box.y0 = 0;
        box.y1 = 2;
        BetterBitmap testBitmap = new DefaultBitmapBoxOnPotraceWordBoundary();

        testBitmap = (BetterBitmap)decompose.clear_bm_with_bbox(testBitmap,box); //FixMe -> It is too Dirty

        assertTrue(testBitmap.map[0] == 0);
        assertTrue(testBitmap.map[1] == 0);
        assertTrue(testBitmap.map[2] == 0);
        assertTrue(testBitmap.map[3] == 0);
    }

    @Test
    public void test_pathlist_to_tree_first() {
        BetterBitmap testBitmap = new DefaultBitmapWithSimpleChildenAndSiblings();

        potrace_path outerPath = decompose.findpath(testBitmap,0,4,43,4);
        potrace_path firstInnerPath = decompose.findpath(testBitmap,1,3,45,4);
        potrace_path secondInnerPath = decompose.findpath(testBitmap,3,3,45,4);

        outerPath.next = firstInnerPath;
        firstInnerPath.next = secondInnerPath;

        potrace_path restructuredOuterPath = decompose.pathlist_to_tree(outerPath,testBitmap);

        PolygonArchitecturePrinter printer = new PolygonArchitecturePrinter(restructuredOuterPath);
        printer.print();

        assertEquals(outerPath,restructuredOuterPath);

        assertEquals(firstInnerPath,restructuredOuterPath.next);
        assertEquals(firstInnerPath,restructuredOuterPath.childlist);
        assertEquals(null,restructuredOuterPath.sibling);

        assertEquals(secondInnerPath,restructuredOuterPath.next.next);
        assertEquals(null,restructuredOuterPath.next.childlist);
        assertEquals(secondInnerPath,restructuredOuterPath.next.sibling);

        assertEquals(secondInnerPath,restructuredOuterPath.childlist.next);
        assertEquals(null,restructuredOuterPath.childlist.childlist);
        assertEquals(secondInnerPath,restructuredOuterPath.childlist.sibling);

    }

    @Test
    public void test_bm_to_pathlist() {
        BetterBitmap testBitmap = new BetterBitmap(7,6);

        testBitmap.fillCompleteBitMap(true);
        testBitmap.addPolygon(new Point(1,4), new Point(5,1), false);
        testBitmap.addPolygon(new Point(2,3), new Point(2,2), true);
        testBitmap.addPolygon(new Point(4,3), new Point(4,2), true);

        /*
         x x x x x x x
         x o o o o o x
         x o x o x o x
         x o x o x o x
         x o o o o o x
         x x x x x x x
         */


        BitmapPrinter printer = new BitmapPrinter(testBitmap);
        printer.print();

        potrace_path restructuredOuterPath = decompose.bm_to_pathlist(testBitmap,new potrace_param());

        assertEquals(true,true);
        assertEquals(42, restructuredOuterPath.area);
        assertEquals(20, restructuredOuterPath.childlist.area);
        assertEquals(2, restructuredOuterPath.childlist.childlist.area);
        assertEquals(null, restructuredOuterPath.childlist.childlist.childlist);
        assertEquals(2, restructuredOuterPath.childlist.childlist.sibling.area);
    }

    @Test
    public void test_pathlist_to_tree_third() {
        BetterBitmap newBitmap = new DefaultBitmapWithChildrenAndSiblings();

        potrace_path bigOuterPath = decompose.findpath(newBitmap, 0,8,43,4);
        potrace_path smallOuterPath = decompose.findpath(newBitmap, 6,8,43,4);
        potrace_bitmap changedBitmap = decompose.xor_path(newBitmap,bigOuterPath);
        potrace_path middlePath = decompose.findpath(changedBitmap,1,7,45,4);
        changedBitmap = decompose.xor_path(changedBitmap,middlePath);
        potrace_path firstInnerPaht = decompose.findpath(changedBitmap,2,6,43,4);
        potrace_path secondInnerPaht = decompose.findpath(changedBitmap,4,4,43,4);

        bigOuterPath.next = middlePath;
        middlePath.next = firstInnerPaht;
        firstInnerPaht.next = secondInnerPaht;
        secondInnerPaht.next = smallOuterPath;

        potrace_path reconstructedPath = decompose.pathlist_to_tree(bigOuterPath,newBitmap);

        assertEquals(bigOuterPath,reconstructedPath);
        assertEquals(middlePath,reconstructedPath.next);
        assertEquals(middlePath, reconstructedPath.childlist);
        assertEquals(smallOuterPath, reconstructedPath.sibling);

        assertEquals(smallOuterPath, reconstructedPath.next.next);
        assertEquals(firstInnerPaht, reconstructedPath.next.childlist);
        assertEquals(null, reconstructedPath.next.sibling);

        assertEquals(smallOuterPath, reconstructedPath.childlist.next);
        assertEquals(firstInnerPaht, reconstructedPath.childlist.childlist);
        assertEquals(null, reconstructedPath.childlist.sibling);

        assertEquals(firstInnerPaht, reconstructedPath.sibling.next);
        assertEquals(null, reconstructedPath.sibling.childlist);
        assertEquals(null, reconstructedPath.sibling.sibling);

        assertEquals(firstInnerPaht, reconstructedPath.next.next.next);
        assertEquals(null, reconstructedPath.next.next.childlist);
        assertEquals(null, reconstructedPath.next.next.sibling);

        assertEquals(secondInnerPaht, reconstructedPath.next.childlist.next);
        assertEquals(null, reconstructedPath.next.childlist.childlist);
        assertEquals(secondInnerPaht, reconstructedPath.next.childlist.sibling);

        assertEquals(firstInnerPaht, reconstructedPath.childlist.next.next);
        assertEquals(null, reconstructedPath.childlist.next.childlist);
        assertEquals(null, reconstructedPath.childlist.next.sibling);

        assertEquals(secondInnerPaht, reconstructedPath.childlist.childlist.next);
        assertEquals(null, reconstructedPath.childlist.childlist.childlist);
        assertEquals(secondInnerPaht, reconstructedPath.childlist.childlist.sibling);

        assertEquals(secondInnerPaht, reconstructedPath.sibling.next.next);
        assertEquals(null, reconstructedPath.sibling.next.childlist);
        assertEquals(secondInnerPaht, reconstructedPath.sibling.next.sibling);

        assertEquals(secondInnerPaht, reconstructedPath.next.next.next.next);
        assertEquals(null, reconstructedPath.next.next.next.childlist);
        assertEquals(secondInnerPaht, reconstructedPath.next.next.next.sibling);

        assertEquals(null, reconstructedPath.next.childlist.next.next);
        assertEquals(null, reconstructedPath.next.childlist.next.childlist);
        assertEquals(null, reconstructedPath.next.childlist.next.sibling);

        assertEquals(null, reconstructedPath.next.childlist.sibling.next);
        assertEquals(null, reconstructedPath.next.childlist.sibling.childlist);
        assertEquals(null, reconstructedPath.next.childlist.sibling.sibling);

        assertEquals(secondInnerPaht, reconstructedPath.childlist.next.next.next);
        assertEquals(null, reconstructedPath.childlist.next.next.childlist);
        assertEquals(secondInnerPaht, reconstructedPath.childlist.next.next.sibling);

        assertEquals(null, reconstructedPath.childlist.childlist.next.next);
        assertEquals(null, reconstructedPath.childlist.childlist.next.childlist);
        assertEquals(null, reconstructedPath.childlist.childlist.next.sibling);

        assertEquals(null, reconstructedPath.childlist.childlist.sibling.next);
        assertEquals(null, reconstructedPath.childlist.childlist.sibling.childlist);
        assertEquals(null, reconstructedPath.childlist.childlist.sibling.sibling);

        assertEquals(null, reconstructedPath.sibling.next.next.next);
        assertEquals(null, reconstructedPath.sibling.next.next.childlist);
        assertEquals(null, reconstructedPath.sibling.next.next.sibling);

        assertEquals(null, reconstructedPath.sibling.next.sibling.next);
        assertEquals(null, reconstructedPath.sibling.next.sibling.childlist);
        assertEquals(null, reconstructedPath.sibling.next.sibling.sibling);
    }
}

