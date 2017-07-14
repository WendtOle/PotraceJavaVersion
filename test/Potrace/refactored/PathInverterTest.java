package Potrace.refactored;

import Potrace.General.Bitmap;
import Potrace.General.Path;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by andreydelany on 04.07.17.
 */

@RunWith(Parameterized.class)
public class PathInverterTest {

    Point[] pointsOfPath;
    Point[] controllPoints;
    BitmapHandlerInterface bitmapHandler;

    @Parameterized.Parameters(name = "{index}")
    public static Collection testdata(){
        return Arrays.asList(new Object[][]{
                {new Bitmap(64, 1),
                        new Point[]{new Point(0, 0), new Point(1, 0)},
                        new Point[]{new Point(63, 0),new Point(3,0)}},
                {new Bitmap(64, 1),
                        new Point[]{new Point(62, 0), new Point(63, 0)},
                        new Point[]{new Point(0, 0)}},
                {new Bitmap(128, 1),
                        new Point[]{new Point(63, 0), new Point(64, 0)},
                        new Point[]{new Point(0, 0), new Point(127, 0)}},
                {new Bitmap(64, 2),
                        new Point[]{new Point(20, 0), new Point(21, 0),new Point(20, 1),new Point(21, 1)},
                        new Point[]{new Point(18, 0), new Point(18, 1),new Point(23, 0), new Point(23, 1)}}
        });
    }

    public PathInverterTest(Bitmap bitmap, Point[] pointsOfPath, Point[] controllPoints){
        initializeFields(bitmap, pointsOfPath, controllPoints);
        findPathAndInvertIt(bitmap);
    }

    private void initializeFields(Bitmap bitmap, Point[] pointsOfPath, Point[] controllPoints) {
        this.pointsOfPath = pointsOfPath;
        this.controllPoints = controllPoints;
        this.bitmapHandler = new BitmapHandler(bitmap);
        setPixelsOnBitmap();
    }

    private void setPixelsOnBitmap() {
        setPixels(pointsOfPath);
        setPixels(controllPoints);
    }

    private void setPixels(Point[] pixels) {
        for(Point currentPixel : pixels)
            bitmapHandler.setPixel(currentPixel);
    }

    private void findPathAndInvertIt(Bitmap bitmap) {
        Path pathToInvert = findPath(bitmap);
        invertPath(bitmap, pathToInvert);
    }

    private void invertPath(Bitmap bitmap, Path pathToInvert) {
        PathInverter inverter = new PathInverter(bitmap);
        inverter.invertPathOnBitmap(pathToInvert);
    }

    private Path findPath(Bitmap bitmap) {
        FindPath pathFinder = new FindPath(bitmap, pointsOfPath[0], new PathFindingCharacteristics(TurnPolicyEnum.MINORITY, PathKindEnum.POSITIV));
        return pathFinder.getPath();
    }

    @Test
    public void checkThatPointsOfPathAreInverted(){
        for(Point currentPointOfPath : pointsOfPath)
            assertFalse(currentPointOfPath.toString(), bitmapHandler.isPixelFilled(currentPointOfPath));
    }

    @Test
    public void checkThatControllPointsAreNotInverted(){
        for(Point currentControllPoint : controllPoints)
            assertTrue(currentControllPoint.toString(),bitmapHandler.isPixelFilled(currentControllPoint));
    }
}
