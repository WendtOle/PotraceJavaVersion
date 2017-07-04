package refactored.potrace;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 04.07.17.
 */
public class PathOnBitmapInverterTest {

    @Test
    public void test_xor_path() {
        /*
        Bitmap testBitmap = new Bitmap(3,3);
        BitmapHandlerInterface manipulator = new BitmapHandler(testBitmap);
        manipulator.setWholeBitmapToSpecificValue(1);
        manipulator.clearExcessPixelsOfBitmap();
        manipulator.setPixelToValue(new Point(1,1),false);

        FindPath findPath = new FindPath(testBitmap,new Point(0,3),43,4);
        Path path = findPath.getPath();
        PathOnBitmapInverter inverter = new PathOnBitmapInverter(testBitmap);
        inverter.invertPathOnBitmap(path);

        assertEquals(false, manipulator.getPixelValue(new Point(0,0)));
        assertEquals(false, manipulator.getPixelValue(new Point(1,0)));
        assertEquals(false, manipulator.getPixelValue(new Point(2,0)));
        assertEquals(false, manipulator.getPixelValue(new Point(0,1)));
        assertEquals(true, manipulator.getPixelValue(new Point(1,1)));
        assertEquals(false, manipulator.getPixelValue(new Point(2,1)));
        assertEquals(false, manipulator.getPixelValue(new Point(0,2)));
        assertEquals(false, manipulator.getPixelValue(new Point(1,3)));
        assertEquals(false, manipulator.getPixelValue(new Point(2,3)));
        */
        //TOdo
    }

    @Test
    public void testXorToRefFirstIF() {
        Bitmap testBitmap = new Bitmap(2,1);
        BitmapHandlerInterface manipulator = new BitmapHandler(testBitmap);
        manipulator.setPixel(new Point(0,0));
        manipulator.setPixel(new Point(1,0));
        PathOnBitmapInverter inverter = new PathOnBitmapInverter(testBitmap);
        inverter.invertBitsInWordsWhichAreInRangeFromXToXAInLine(0,0,1);
        assertEquals("firstPixel: ", false, manipulator.isPixelFilled(new Point(0,0)));
        assertEquals("secondPixel: ", false, manipulator.isPixelFilled(new Point(1,0)));
    }

    @Test
    public void testXorToRefHorizontalSecondIF() {
        Bitmap testBitmap = new Bitmap(70,1);
        BitmapHandlerInterface manipulator = new BitmapHandler(testBitmap);
        manipulator.setPixel(new Point(68,0));
        manipulator.setPixel(new Point(69,0));
        PathOnBitmapInverter inverter = new PathOnBitmapInverter(testBitmap);
        inverter.invertBitsInWordsWhichAreInRangeFromXToXAInLine(70,0,0);
        assertEquals(-1l,testBitmap.words[0]);
        assertEquals("64: ", true, manipulator.isPixelFilled(new Point(64,0)));
        assertEquals("65: ", true, manipulator.isPixelFilled(new Point(65,0)));
        assertEquals("66: ", true, manipulator.isPixelFilled(new Point(66,0)));
        assertEquals("67: ", true, manipulator.isPixelFilled(new Point(67,0)));
        assertEquals("68: ", false, manipulator.isPixelFilled(new Point(68,0)));
        assertEquals("69: ", false, manipulator.isPixelFilled(new Point(69,0)));
    }

    @Test
    public void testXorToRefHorizontalThirdIF() {
        Bitmap testBitmap = new Bitmap(2,1);
        BitmapHandlerInterface manipulator = new BitmapHandler(testBitmap);
        manipulator.setPixel(new Point(0,0));
        manipulator.setPixel(new Point(1,0));
        PathOnBitmapInverter inverter = new PathOnBitmapInverter(testBitmap);
        inverter.invertBitsInWordsWhichAreInRangeFromXToXAInLine(2,0,0);
        assertEquals("firstPixel: ", false, manipulator.isPixelFilled(new Point(0,0)));
        assertEquals("secondPixel: ", false, manipulator.isPixelFilled(new Point(1,0)));
    }

    @Test
    public void xor_to_ref() {
        Bitmap testBitmap = new Bitmap(128,1);
        BitmapHandlerInterface manipulator = new BitmapHandler(testBitmap);
        manipulator.setPixel(new Point(68,0));
        manipulator.setPixel(new Point(70,0));
        PathOnBitmapInverter inverter = new PathOnBitmapInverter(testBitmap);
        inverter.invertBitsInWordsWhichAreInRangeFromXToXAInLine(5,0,65);
        //TODO where is the assert?
    }
}
