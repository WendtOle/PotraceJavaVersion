package refactored.potrace;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class bitmapTest {

    @Test
    public void creatingBitmapWithEmptyConstructorTest() {
        Bitmap testBitmap = new Bitmap();
        assertEquals("width: ", 0,testBitmap.width);
        assertEquals("height: ", 0,testBitmap.height);
        assertEquals("potraceWordsInOneLine: ", 0,testBitmap.wordsPerScanLine);
        assertEquals("potraceWords: ", null,testBitmap.words);
    }

    @Test
    public void creatingBitmapWithNormalConstructorTest() {
        Bitmap testBitmap = new Bitmap(100,100);
        assertEquals("width: ", 100,testBitmap.width);
        assertEquals("height: ", 100,testBitmap.height);
        assertEquals("potraceWordsInOneLine: ", 2,testBitmap.wordsPerScanLine);
        assertEquals("potraceWords: ", 200,testBitmap.words.length);
    }

    @Test
    public void checkingThatConstantsAreCorrect() {
        assertEquals("Pixel in Word: ",64, Bitmap.PIXELINWORD);
        assertEquals("All Bits: ", -1l, Bitmap.BM_ALLBITS);
        assertEquals("First Bit: ", 0x80000000, Bitmap.BM_HIBIT);
    }

    @Test
    public void testBmSafeAndBmRange() {
        Bitmap testBitmap = new Bitmap(10,10);
        assertTrue("first inside: ", testBitmap.isPixelInRange(new Point(0,0)));
        assertTrue("second inside: ", testBitmap.isPixelInRange(new Point(9,9)));
        assertFalse("first outside: ", testBitmap.isPixelInRange(new Point(10,10)));
        assertFalse("second outside: ", testBitmap.isPixelInRange(new Point(10,1)));
        assertFalse("third outside: ", testBitmap.isPixelInRange(new Point(-1,1)));
    }

    @Test
    public void testMaskFuntion() {
        Bitmap bitmap = new Bitmap();
        assertEquals("at position 0: ",0x8000000000000000l, bitmap.getMaskForPosition(0));
        assertEquals("at position 1: ",0x4000000000000000l, bitmap.getMaskForPosition(1));
        assertEquals("at position 64 -> 0: ",0x8000000000000000l, bitmap.getMaskForPosition(64));
        assertEquals("at position 63: ",0x1, bitmap.getMaskForPosition(63));
    }

    @Test
    public void testBMPutAndBMGetFunction() {
        Bitmap smallTestBitmap = new Bitmap(10,10);
        BitmapManipulator manipulator = new BitmapManipulator(smallTestBitmap);
        manipulator.setPixelToValue(new Point(0,0),true);
        assertEquals("with one original word in line: ",true, smallTestBitmap.getPixelValue(new Point(0,0)));

        Bitmap bigTestBitmap = new Bitmap(100,100);
        manipulator = new BitmapManipulator(bigTestBitmap);
        manipulator.setPixelToValue(new Point(99,99),true);
        assertEquals("with more than one original word in line: ",true,bigTestBitmap.getPixelValue(new Point(99,99)));
    }

    @Test
    public void testBMClearFuntion() {
        Bitmap smallTestBitmap = new Bitmap(10,10);
        BitmapManipulator manipulator = new BitmapManipulator(smallTestBitmap);
        manipulator.setPixelToValue(new Point(0,0),true);
        manipulator.setPixelToValue(new Point(0,0),false);
        assertEquals(false, smallTestBitmap.getPixelValue(new Point(0,0)));
    }

    @Test
    public void test_bm_clear() throws Exception {
        Bitmap testBitMap = new Bitmap(10,10);
        BitmapManipulator manipulator = new BitmapManipulator(testBitMap);
        manipulator.setWholeBitmapToSpecificValue(1);
        assertEquals(true, testBitMap.getPixelValue(new Point(0,0)));
        assertEquals(true, testBitMap.getPixelValue(new Point(9,9)));
        manipulator.setWholeBitmapToSpecificValue(0);
        assertEquals(false, testBitMap.getPixelValue(new Point(0,0)));
        assertEquals(false, testBitMap.getPixelValue(new Point(4,4)));
    }

    @Test
    public void test_bm_dup() throws Exception {
        Bitmap originalBitmap = new Bitmap(10,10);
        Bitmap copiedBitmap = originalBitmap.duplicate();

        assertFalse("reference: ", originalBitmap == copiedBitmap);
        assertArrayEquals("potraceWords: ",originalBitmap.words,copiedBitmap.words);
        assertEquals("potraceWordsInOneLine: ",originalBitmap.wordsPerScanLine, copiedBitmap.wordsPerScanLine);
        assertEquals("height: ",originalBitmap.height, copiedBitmap.height);
        assertEquals("width: ",originalBitmap.width, copiedBitmap.width);
    }

    @Test
    public void test_clear_bm_with_bbox() {
        BBox box = new BBox();
        box.x0 = 2;
        box.x1 = 3;
        box.y0 = 1;
        box.y1 = 2;
        Bitmap testBitmap = new Bitmap(3,3);
        BitmapManipulator manipulator = new BitmapManipulator(testBitmap);
        manipulator.setPixelToValue(new Point(0,2),true);
        manipulator.setPixelToValue(new Point(1,1),true);

        testBitmap.clearBitmapWithBBox(box);

        assertTrue("firstline: ",testBitmap.words[0] == 0);
        assertTrue("secondline: ",testBitmap.words[1] == 0);
        assertTrue("thirdline: ",testBitmap.words[2] == 0x8000000000000000l);
    }

    @Test
    public void testMajorityFunction() {
        Bitmap testBitmap = new Bitmap(4,4);
        Point observationPoint = new Point(testBitmap.width /2,testBitmap.height /2);
        Point[] points = new Point[]{new Point(0,0),new Point(2,2),new Point(2,0),new Point(3,1),
                new Point(1,0), new Point(2,1), new Point(3,3),new Point (2,3),
                new Point(0,2), new Point(3,0), new Point(1,3), new Point(1,2),
                new Point(3,2),new Point(1,1),new Point(0,3), new Point(0,1)};

        Boolean[] expectedOutcomes = new Boolean[]{true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false};

        BitmapManipulator manipulator = new BitmapManipulator(testBitmap);
        manipulator.setWholeBitmapToSpecificValue(1);

        for(int i = 0; i < 16; i++) {
            manipulator.setPixelToValue(points[i],false);
            assertEquals("i: " + i,expectedOutcomes[i], testBitmap.getMajorityValueAtIntersection(observationPoint.x, observationPoint.y));
        }
    }

    @Test
    public void testXorToRefFirstIF() {
        Bitmap testBitmap = new Bitmap(2,1);
        BitmapManipulator manipulator = new BitmapManipulator(testBitmap);
        manipulator.setPixelToValue(new Point(0,0),true);
        manipulator.setPixelToValue(new Point(1,0),true);
        testBitmap.invertBitsInWordsWhichAreInRangeFromXToXAInLine(0,0,1);
        assertEquals("firstPixel: ", false, testBitmap.getPixelValue(new Point(0,0)));
        assertEquals("secondPixel: ", false, testBitmap.getPixelValue(new Point(1,0)));
    }

    @Test
    public void testXorToRefHorizontalSecondIF() {
        Bitmap testBitmap = new Bitmap(70,1);
        BitmapManipulator manipulator = new BitmapManipulator(testBitmap);
        manipulator.setPixelToValue(new Point(68,0),true);
        manipulator.setPixelToValue(new Point(69,0),true);
        testBitmap.invertBitsInWordsWhichAreInRangeFromXToXAInLine(70,0,0);
        assertEquals(-1l,testBitmap.words[0]);
        assertEquals("64: ", true, testBitmap.getPixelValue(new Point(64,0)));
        assertEquals("65: ", true, testBitmap.getPixelValue(new Point(65,0)));
        assertEquals("66: ", true, testBitmap.getPixelValue(new Point(66,0)));
        assertEquals("67: ", true, testBitmap.getPixelValue(new Point(67,0)));
        assertEquals("68: ", false, testBitmap.getPixelValue(new Point(68,0)));
        assertEquals("69: ", false, testBitmap.getPixelValue(new Point(69,0)));
    }

    @Test
    public void testXorToRefHorizontalThirdIF() {
        Bitmap testBitmap = new Bitmap(2,1);
        BitmapManipulator manipulator = new BitmapManipulator(testBitmap);
        manipulator.setPixelToValue(new Point(0,0),true);
        manipulator.setPixelToValue(new Point(1,0),true);
        testBitmap.invertBitsInWordsWhichAreInRangeFromXToXAInLine(2,0,0);
        assertEquals("firstPixel: ", false, testBitmap.getPixelValue(new Point(0,0)));
        assertEquals("secondPixel: ", false, testBitmap.getPixelValue(new Point(1,0)));
    }

    @Test
    public void xor_to_ref() {
        Bitmap testBitmap = new Bitmap(128,1);
        BitmapManipulator manipulator = new BitmapManipulator(testBitmap);
        manipulator.setPixelToValue(new Point(68,0),true);
        manipulator.setPixelToValue(new Point(70,0),true);
        testBitmap.invertBitsInWordsWhichAreInRangeFromXToXAInLine(5,0,65);
    }
}