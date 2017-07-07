package refactored.potrace.BitmapDecomposition;

import org.junit.Before;
import org.junit.Test;
import refactored.potrace.Bitmap;
import refactored.potrace.Path;

import java.awt.*;

import static org.junit.Assert.assertFalse;

/**
 * Created by andreydelany on 04.07.17.
 */
public class PathInverterTest {

    Bitmap bitmapWithLeadingPath = new Bitmap(64,1);
    BitmapHandlerInterface bitmapHandlerWithLeadingPath = new BitmapHandler(bitmapWithLeadingPath);
    Bitmap bitmapWithTrailingPath = new Bitmap(64,1);
    BitmapHandlerInterface bitmapHandlerWithTrailingPath = new BitmapHandler(bitmapWithTrailingPath);
    Bitmap bitmapWithPathInMulitpleWords = new Bitmap(128,1);
    BitmapHandlerInterface bitmapHandlerWithPathInMulitpleWords = new BitmapHandler(bitmapWithPathInMulitpleWords);

    @Before
    public void prepareBitmapWithLeadingPath() {
        bitmapHandlerWithLeadingPath.setPixel(new Point(0,0));
        bitmapHandlerWithLeadingPath.setPixel(new Point(1,0));
    }

    @Before
    public void prepareBitmapWithTrailingPath() {
        bitmapHandlerWithTrailingPath.setPixel(new Point(62,0));
        bitmapHandlerWithTrailingPath.setPixel(new Point(63,0));
    }

    @Before
    public void prepareBitmapInMultipleWords() {
        bitmapHandlerWithPathInMulitpleWords.setPixel(new Point(63,0));
        bitmapHandlerWithPathInMulitpleWords.setPixel(new Point(64,0));
    }

    @Test
    public void testToInvertPathThatIsAtBeginningOfAWord() {
        FindPath pathFinder = new FindPath(bitmapWithLeadingPath,new Point(0,0),43, TurnPolicyEnum.MINORITY);
        Path path = pathFinder.getPath();

        PathInverter inverter = new PathInverter(bitmapWithLeadingPath);
        inverter.invertPathOnBitmap(path);

        assertFalse(bitmapHandlerWithLeadingPath.isPixelFilled(new Point(0,0)));
        assertFalse(bitmapHandlerWithLeadingPath.isPixelFilled(new Point(1,0)));
        assertFalse(bitmapHandlerWithLeadingPath.isPixelFilled(new Point(2,0)));
    }

    @Test
    public void testToInvertPathThatIsAtTheEndOfAWord() {
        FindPath pathFinder = new FindPath(bitmapWithTrailingPath,new Point(62,0),43,TurnPolicyEnum.MINORITY);
        Path path = pathFinder.getPath();

        PathInverter inverter = new PathInverter(bitmapWithTrailingPath);
        inverter.invertPathOnBitmap(path);

        assertFalse(bitmapHandlerWithTrailingPath.isPixelFilled(new Point(61,0)));
        assertFalse(bitmapHandlerWithTrailingPath.isPixelFilled(new Point(62,0)));
        assertFalse(bitmapHandlerWithTrailingPath.isPixelFilled(new Point(63,0)));
    }

    @Test
    public void testToInvertPathThatRangesOfOverBoundOfAWord() {
        FindPath pathFinder = new FindPath(bitmapWithPathInMulitpleWords,new Point(63,0),43,TurnPolicyEnum.MINORITY);
        Path path = pathFinder.getPath();

        PathInverter inverter = new PathInverter(bitmapWithPathInMulitpleWords);
        inverter.invertPathOnBitmap(path);

        assertFalse(bitmapHandlerWithPathInMulitpleWords.isPixelFilled(new Point(62,0)));
        assertFalse(bitmapHandlerWithPathInMulitpleWords.isPixelFilled(new Point(63,0)));
        assertFalse(bitmapHandlerWithPathInMulitpleWords.isPixelFilled(new Point(64,0)));
        assertFalse(bitmapHandlerWithPathInMulitpleWords.isPixelFilled(new Point(65,0)));
    }
}
