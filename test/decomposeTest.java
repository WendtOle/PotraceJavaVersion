import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by andreydelany on 20/03/2017.
 */
public class decomposeTest {
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
        assertEquals(false,decompose.detrand(20,4));
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
        assertEquals(true,decompose.majority(testBitmap,2,2));
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
        assertEquals(false,decompose.majority(testBitmap,2,2));
    }

    @Test
    public void test_xor_to_ref() throws Exception {
        potrace_bitmap testBitmap = new potrace_bitmap(4,4);
        testBitmap.map[3] = 0x30000000;
        testBitmap.map[2] = 0x30000000;
        testBitmap.map[1] = 0xc0000000;
        testBitmap.map[0] = 0xc0000000;
        BitmapPrinter bitmapPrinter = new BitmapPrinter(testBitmap);
        bitmapPrinter.print();



        assertEquals(false,decompose.majority(testBitmap,2,2));
    }
}

