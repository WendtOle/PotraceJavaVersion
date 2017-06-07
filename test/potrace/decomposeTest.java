package potrace;

import BitmapLibrary.*;
import Tools.*;
import org.junit.Assert;
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

        Point point = new Point(0,0);
        decompose.findnext(testBitmap,point);
        assertEquals(new Point(65,0),point);
    }

    @Test
    public void test_findnextWithNextPointNotInSameLine() throws Exception {
        BetterBitmap testBitmap = new BetterBitmap(128,2);
        testBitmap.addBlob(new Point(97,1),true);
        testBitmap.addBlob(new Point(99,1),true);

        Point point = new Point(0,0);
        assertEquals(false,decompose.findnext(testBitmap,point));
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

        path result = decompose.findpath(testBitmap,63,1,43,4);
        for (int i = 0 ; i < expectedPoints.length; i ++)
            comparePoints(expectedPoints[i],result.priv.pt[i]);
    }

    @Test
    public void test_xor_path_xor_to_ref_one() {
        BetterBitmap testBitmap = new DefaultBitmapSimpleBoxWithExtension();

        path path = decompose.findpath(testBitmap,1,3,43,4);
        decompose.xor_path(testBitmap,path);
        assertEquals(0,testBitmap.map[0]);
        assertEquals(0,testBitmap.map[1]);
        assertEquals(0,testBitmap.map[2]);
        assertEquals(0,testBitmap.map[3]);
    }

    @Test
    public void test_xor_path_xor_to_ref_two() {
        BetterBitmap testBitmap = new DefaultBitmapBoxOnPotraceWordBoundary();

        path path = decompose.findpath(testBitmap,63,2,43,4);
        decompose.xor_path(testBitmap,path);
        assertEquals(0,testBitmap.map[0]);
        assertEquals(0,testBitmap.map[1]);
        assertEquals(0,testBitmap.map[2]);
        assertEquals(0,testBitmap.map[3]);
    }

    @Test
    public void test_setbbox_path() {
        bbox box = new bbox();
        BetterBitmap testBitmap = new DefaultBitmapSimpleBoxWithExtension();

        path path = decompose.findpath(testBitmap,1,3,43,4);

        decompose.setbbox_path(box,path);
        assertEquals(0,box.x0);
        assertEquals(4,box.x1);
        assertEquals(0,box.y0);
        assertEquals(4,box.y1);
    }

    @Test
    public void test_setbbox_path_extended() {
        bbox box = new bbox();
        BetterBitmap testBitmap = new DefaultBitmapBoxOnPotraceWordBoundary();

        path path = decompose.findpath(testBitmap,63,1,43,4);

        decompose.setbbox_path(box,path);
        assertEquals(63,box.x0);
        assertEquals(65,box.x1);
        assertEquals(0,box.y0);
        assertEquals(2,box.y1);
    }

    @Test
    public void test_clear_bm_with_bbox() {
        bbox box = new bbox();
        box.x0 = 25;
        box.x1 = 70;
        box.y0 = 0;
        box.y1 = 2;
        DefaultBitmapBoxOnPotraceWordBoundary testBitmap = new DefaultBitmapBoxOnPotraceWordBoundary();

        decompose.clear_bm_with_bbox(testBitmap,box);

        assertTrue(testBitmap.map[0] == 0);
        assertTrue(testBitmap.map[1] == 0);
        assertTrue(testBitmap.map[2] == 0);
        assertTrue(testBitmap.map[3] == 0);
    }

    @Test
    public void test_pathlist_to_tree_first() {
        BetterBitmap testBitmap = new DefaultBitmapWithSimpleChildenAndSiblings();

        path outerPath = decompose.findpath(testBitmap,0,4,43,4);
        path firstInnerPath = decompose.findpath(testBitmap,1,3,45,4);
        path secondInnerPath = decompose.findpath(testBitmap,3,3,45,4);

        outerPath.next = firstInnerPath;
        firstInnerPath.next = secondInnerPath;

        decompose.pathlist_to_tree(outerPath,testBitmap);

        PolygonArchitecturePrinter printer = new PolygonArchitecturePrinter(outerPath);
        printer.print();

        assertEquals(outerPath,outerPath);

        assertEquals(firstInnerPath,outerPath.next);
        assertEquals(firstInnerPath,outerPath.childlist);
        assertEquals(null,outerPath.sibling);

        assertEquals(secondInnerPath,outerPath.next.next);
        assertEquals(null,outerPath.next.childlist);
        assertEquals(secondInnerPath,outerPath.next.sibling);

        assertEquals(secondInnerPath,outerPath.childlist.next);
        assertEquals(null,outerPath.childlist.childlist);
        assertEquals(secondInnerPath,outerPath.childlist.sibling);

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

        path restructuredOuterPath = decompose.bm_to_pathlist(testBitmap,new param());

        assertEquals(true,true);
        assertEquals(42, restructuredOuterPath.area);
        assertEquals(20, restructuredOuterPath.childlist.area);
        assertEquals(2, restructuredOuterPath.childlist.childlist.area);
        assertEquals(null, restructuredOuterPath.childlist.childlist.childlist);
        assertEquals(2, restructuredOuterPath.childlist.childlist.sibling.area);
    }

    @Test
    public void test_pathlist_to_tree_third() {
        BetterBitmap originalBitmap = new DefaultBitmapWithChildrenAndSiblings();
        bitmap bitmap = originalBitmap.bm_dup();

        path bigOuterPath = decompose.findpath(bitmap, 0,8,43,4);
        path smallOuterPath = decompose.findpath(bitmap, 6,8,43,4);
        decompose.xor_path(bitmap,bigOuterPath);
        path middlePath = decompose.findpath(bitmap,1,7,45,4);
        decompose.xor_path(bitmap,middlePath);
        path firstInnerPaht = decompose.findpath(bitmap,2,6,43,4);
        path secondInnerPaht = decompose.findpath(bitmap,4,4,43,4);

        bigOuterPath.next = middlePath;
        middlePath.next = firstInnerPaht;
        firstInnerPaht.next = secondInnerPaht;
        secondInnerPaht.next = smallOuterPath;

        decompose.pathlist_to_tree(bigOuterPath,originalBitmap);

        assertEquals(bigOuterPath,bigOuterPath);
        assertEquals(middlePath,bigOuterPath.next);
        assertEquals(middlePath, bigOuterPath.childlist);
        assertEquals(smallOuterPath, bigOuterPath.sibling);

        assertEquals(smallOuterPath, bigOuterPath.next.next);
        assertEquals(firstInnerPaht, bigOuterPath.next.childlist);
        assertEquals(null, bigOuterPath.next.sibling);

        assertEquals(smallOuterPath, bigOuterPath.childlist.next);
        assertEquals(firstInnerPaht, bigOuterPath.childlist.childlist);
        assertEquals(null, bigOuterPath.childlist.sibling);

        assertEquals(firstInnerPaht, bigOuterPath.sibling.next);
        assertEquals(null, bigOuterPath.sibling.childlist);
        assertEquals(null, bigOuterPath.sibling.sibling);

        assertEquals(firstInnerPaht, bigOuterPath.next.next.next);
        assertEquals(null, bigOuterPath.next.next.childlist);
        assertEquals(null, bigOuterPath.next.next.sibling);

        assertEquals(secondInnerPaht, bigOuterPath.next.childlist.next);
        assertEquals(null, bigOuterPath.next.childlist.childlist);
        assertEquals(secondInnerPaht, bigOuterPath.next.childlist.sibling);

        assertEquals(firstInnerPaht, bigOuterPath.childlist.next.next);
        assertEquals(null, bigOuterPath.childlist.next.childlist);
        assertEquals(null, bigOuterPath.childlist.next.sibling);

        assertEquals(secondInnerPaht, bigOuterPath.childlist.childlist.next);
        assertEquals(null, bigOuterPath.childlist.childlist.childlist);
        assertEquals(secondInnerPaht, bigOuterPath.childlist.childlist.sibling);

        assertEquals(secondInnerPaht, bigOuterPath.sibling.next.next);
        assertEquals(null, bigOuterPath.sibling.next.childlist);
        assertEquals(secondInnerPaht, bigOuterPath.sibling.next.sibling);

        assertEquals(secondInnerPaht, bigOuterPath.next.next.next.next);
        assertEquals(null, bigOuterPath.next.next.next.childlist);
        assertEquals(secondInnerPaht, bigOuterPath.next.next.next.sibling);

        assertEquals(null, bigOuterPath.next.childlist.next.next);
        assertEquals(null, bigOuterPath.next.childlist.next.childlist);
        assertEquals(null, bigOuterPath.next.childlist.next.sibling);

        assertEquals(null, bigOuterPath.next.childlist.sibling.next);
        assertEquals(null, bigOuterPath.next.childlist.sibling.childlist);
        assertEquals(null, bigOuterPath.next.childlist.sibling.sibling);

        assertEquals(secondInnerPaht, bigOuterPath.childlist.next.next.next);
        assertEquals(null, bigOuterPath.childlist.next.next.childlist);
        assertEquals(secondInnerPaht, bigOuterPath.childlist.next.next.sibling);

        assertEquals(null, bigOuterPath.childlist.childlist.next.next);
        assertEquals(null, bigOuterPath.childlist.childlist.next.childlist);
        assertEquals(null, bigOuterPath.childlist.childlist.next.sibling);

        assertEquals(null, bigOuterPath.childlist.childlist.sibling.next);
        assertEquals(null, bigOuterPath.childlist.childlist.sibling.childlist);
        assertEquals(null, bigOuterPath.childlist.childlist.sibling.sibling);

        assertEquals(null, bigOuterPath.sibling.next.next.next);
        assertEquals(null, bigOuterPath.sibling.next.next.childlist);
        assertEquals(null, bigOuterPath.sibling.next.next.sibling);

        assertEquals(null, bigOuterPath.sibling.next.sibling.next);
        assertEquals(null, bigOuterPath.sibling.next.sibling.childlist);
        assertEquals(null, bigOuterPath.sibling.next.sibling.sibling);
    }

    @Test
    public void test_specialCaseInPathlistToTree(){
        BetterBitmap newBitmap = new DefaultBitmapSpiegelverkehrtesL();
        path result = decompose.bm_to_pathlist(newBitmap, new param());

        //check wether all three pathes are there

        assertEquals(4,result.area);
        assertEquals(9,result.next.area);
        assertEquals(4,result.next.next.area);
    }

    @Test
    public void test_specialCaseInPathlistToTreeSecond(){
        BetterBitmap newBitmap = new DefaultBitmapSpiegelverkehrtesLExtended();
        path result = decompose.bm_to_pathlist(newBitmap, new param());

        //check wether all three pathes are there
        assertEquals(4,result.area);
        assertEquals(2,result.next.area);
        assertEquals(9,result.next.next.area);
        assertEquals(3,result.next.next.next.area);
    }
}

