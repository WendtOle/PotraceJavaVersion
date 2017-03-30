package potrace;

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

      /*  BitMapManipulator creator = new BitMapManipulator(findPathTestBitmap);
        creator.addPolygon(new Point(0,3),new Point(1,1), true);
        creator.addBlob(new Point(1,3),false);
        creator.addBlob(new Point(0,1),false);
        creator.addBlob(new Point(0,0),true);
        creator.addBlob(new Point(2,1),true);
        creator.addBlob(new Point(3,2),true);
*/

        findPathTestBitmap.map[3] = 0x8000000000000000L;
        findPathTestBitmap.map[2] = 0xc000000000000000L;
        findPathTestBitmap.map[1] = 0x6000000000000000L;
        findPathTestBitmap.map[0] = 0x9000000000000000L;
    }


    @Test
    public void test_findnextWithNextPointInSameLine() throws Exception {
        potrace_bitmap testBitmap = new potrace_bitmap(70,1);
        testBitmap = BitMapManipulator.addBlob(testBitmap,new Point(65,0),true);

        Point startPoint = new Point(0,0);
        Point foundPoint = decompose.findnext(testBitmap,startPoint);
        assertEquals(new Point(65,0),foundPoint);
    }

    @Test
    public void test_findnextWithNextPointNotInSameLine() throws Exception {
        potrace_bitmap testBitmap = new potrace_bitmap(128,2);
        testBitmap.map[3] = 0x4900000000000000L;
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
        testBitmap.map[3] = 0x7000000000000000L;
        testBitmap.map[2] = 0xb000000000000000L;
        testBitmap.map[1] = 0xd000000000000000L;
        testBitmap.map[0] = 0xe000000000000000L;
        BitmapPrinter bitmapPrinter = new BitmapPrinter(testBitmap);
        bitmapPrinter.print();
        Assert.assertEquals(true, decompose.majority(testBitmap,2,2));
    }

    @Test
    public void test_majorityWhereIsNoMajority() throws Exception {
        potrace_bitmap testBitmap = new potrace_bitmap(4,4);
        testBitmap.map[3] = 0x3000000000000000L;
        testBitmap.map[2] = 0x3000000000000000L;
        testBitmap.map[1] = 0xc000000000000000L;
        testBitmap.map[0] = 0xc000000000000000L;
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

    @Test
    public void test_findPath_boundary() throws Exception {
        potrace_bitmap testBitmap = new potrace_bitmap(128,1);
        testBitmap.map[0] = 1;
        testBitmap.map[1] = 0x8000000000000000L;

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
        potrace_bitmap testBitmap = new potrace_bitmap(4,4);
        testBitmap.map[0] = 0x1000000000000000L;
        testBitmap.map[1] = 0x6000000000000000L;
        testBitmap.map[2] = 0x6000000000000000L;
        testBitmap.map[3] = 0xc000000000000000L;

        potrace_path path = decompose.findpath(testBitmap,1,3,43,4);
        potrace_bitmap resultBitmap = decompose.xor_path(testBitmap,path);
        assertEquals(0,resultBitmap.map[0]);
        assertEquals(0,resultBitmap.map[1]);
        assertEquals(0,resultBitmap.map[2]);
        assertEquals(0,resultBitmap.map[3]);
    }

    @Test
    public void test_xor_path_xor_to_ref_two() {
        potrace_bitmap testBitmap = new potrace_bitmap(128,2);
        testBitmap.map[0] = 1;
        testBitmap.map[1] = 0x8000000000000000L;
        testBitmap.map[2] = 1;
        testBitmap.map[3] = 0x8000000000000000L;

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
        potrace_bitmap testBitmap = new potrace_bitmap(4,4);
        testBitmap.map[0] = 0x1000000000000000L;
        testBitmap.map[1] = 0x6000000000000000L;
        testBitmap.map[2] = 0x6000000000000000L;
        testBitmap.map[3] = 0xc000000000000000L;
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
        potrace_bitmap testBitmap = new potrace_bitmap(128,2);
        testBitmap.map[0] = 1;
        testBitmap.map[1] = 0x8000000000000000L;
        testBitmap.map[2] = 1;
        testBitmap.map[3] = 0x8000000000000000L;
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
        potrace_bitmap testBitmap = new potrace_bitmap(128,2);
        testBitmap.map[0] = 1;
        testBitmap.map[1] = 0x8000000000000000L;
        testBitmap.map[2] = 1;
        testBitmap.map[3] = 0x8000000000000000L;

        testBitmap = decompose.clear_bm_with_bbox(testBitmap,box);

        assertTrue(testBitmap.map[0] == 0);
        assertTrue(testBitmap.map[1] == 0);
        assertTrue(testBitmap.map[2] == 0);
        assertTrue(testBitmap.map[3] == 0);
    }

    @Test
    public void test_pathlist_to_tree_first() {
        potrace_bitmap testBitmap = new potrace_bitmap(5,4);
        testBitmap.map[0] = 0xf800000000000000L;
        testBitmap.map[1] = 0xa800000000000000L;
        testBitmap.map[2] = 0xa800000000000000L;
        testBitmap.map[3] = 0xf800000000000000L;

        //potrace_param param = new potrace_param();
        //potrace_path correctPath = decompose.bm_to_pathlist(testBitmap,param);

        potrace_path outerPath = decompose.findpath(testBitmap,0,4,43,4);
        potrace_path firstInnerPath = decompose.findpath(testBitmap,1,3,45,4);
        potrace_path secondInnerPath = decompose.findpath(testBitmap,3,3,45,4);

        outerPath.next = firstInnerPath;
        firstInnerPath.next = secondInnerPath;

        potrace_path restructuredOuterPath = decompose.pathlist_to_tree(outerPath,testBitmap);

        assertEquals(outerPath.childlist,firstInnerPath);
        assertEquals(outerPath.childlist.sibling, secondInnerPath);

    }

    @Test
    public void test_bm_to_pathlist() {
        potrace_bitmap testBitmap = new potrace_bitmap(7,6);
        testBitmap.map[0] = 0xfe00000000000000L;
        testBitmap.map[1] = 0x8200000000000000L;
        testBitmap.map[2] = 0xaa00000000000000L;
        testBitmap.map[3] = 0xaa00000000000000L;
        testBitmap.map[4] = 0x8200000000000000L;
        testBitmap.map[5] = 0xfe00000000000000L;

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
        potrace_bitmap newBitmap = new potrace_bitmap(8,8);
        newBitmap.map[7]= 0xfb00000000000000L;            //  X X X X X o X X
        newBitmap.map[6]= 0x8800000000000000L;            //  X o o o X o o o
        newBitmap.map[5]= 0xae00000000000000L;            //  X o X o X X X o
        newBitmap.map[4]= 0xa200000000000000L;            //  X o X o o o X o
        newBitmap.map[3]= 0x8a00000000000000L;            //  X o o o X o X o
        newBitmap.map[2]= 0xba00000000000000L;            //  X o X X X o X o
        newBitmap.map[1]= 0x8200000000000000L;            //  X o o o o o X o
        newBitmap.map[0]= 0xfe00000000000000L;            //  X X X X X X X o

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

        assertEquals(smallOuterPath,bigOuterPath.sibling);
        assertEquals(null,smallOuterPath.childlist);
        assertEquals(middlePath,bigOuterPath.childlist);
        assertEquals(null,middlePath.sibling);
        assertEquals(firstInnerPaht,middlePath.childlist);
        assertEquals(secondInnerPaht,firstInnerPaht.sibling);
        assertEquals(null,firstInnerPaht.childlist);
        assertEquals(secondInnerPaht,firstInnerPaht.sibling);
    }
}

