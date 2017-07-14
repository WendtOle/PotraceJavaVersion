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

/**
 * Created by andreydelany on 05.07.17.
 */

@RunWith(Parameterized.class)
public class ClearPathWithBoundingBoxTest {

    BitmapHandlerInterface bitmapHandler;
    Point[] pointsOfPath, controlPointsThatShouldBeCleared;


    @Parameterized.Parameters(name = "{index}")
    public static Collection testData(){
        return Arrays.asList(new Object[][]{
                {new Bitmap(4,4),
                        new Point[]{new Point(1,1),new Point(2,2)},
                        new Point[]{new Point(0,1),new Point(3,1),new Point(0,2),new Point(3,2)}},
                {new Bitmap(128,2),
                        new Point[]{new Point(63,0),new Point(64,0),new Point(63,1),new Point(64,1)},
                        new Point[]{new Point(0,0),new Point(0,1),new Point(127,0),new Point(127,1)}}
        });
    }

    public ClearPathWithBoundingBoxTest(Bitmap bitmap, Point[] pointsOfPath, Point[] controlPoints){
        initializeFields(bitmap, pointsOfPath, controlPoints);
        fillBitmapWithPixels(pointsOfPath, controlPoints);
        findPathAndClearItWithBoundingBox(bitmap);
    }

    private void initializeFields(Bitmap bitmap, Point[] pointsOfPath, Point[] controllPoints) {
        this.bitmapHandler = new BitmapHandler(bitmap);
        this.pointsOfPath = pointsOfPath;
        this.controlPointsThatShouldBeCleared = controllPoints;
    }

    private void fillBitmapWithPixels(Point[] pointsOfPath, Point[] controllPoints) {
        setPixels(pointsOfPath);
        setPixels(controllPoints);
    }

    private void setPixels(Point[] pixels) {
        for(Point currentPixel : pixels)
            bitmapHandler.setPixel(currentPixel);
    }

    private void findPathAndClearItWithBoundingBox(Bitmap bitmap) {
        Path path = findPath(bitmap);
        clearPathWithBoundingBox(bitmap, path);
    }

    private Path findPath(Bitmap bitmap) {
        FindPath pathFinder =  new FindPath(bitmap, pointsOfPath[0], new PathFindingCharacteristics(TurnPolicyEnum.MINORITY,PathKindEnum.POSITIV));
        return pathFinder.getPath();
    }

    private void clearPathWithBoundingBox(Bitmap bitmap, Path path) {
        ClearPathWithBoundingBox bitmapWithBoundingBoxClearer = new ClearPathWithBoundingBox(bitmap);
        bitmapWithBoundingBoxClearer.clear(new PathBoundingBox(path));
    }

    @Test
    public void checkThatPointsOfPathAreCleared(){
        for(Point currentPointOfPath : pointsOfPath)
            assertFalse(currentPointOfPath.toString(), bitmapHandler.isPixelFilled(currentPointOfPath));
    }

    @Test
    public void checkThatControlPointsAreAlsoCleared(){
        for(Point currentControllPoint : controlPointsThatShouldBeCleared)
            assertFalse(currentControllPoint.toString(),bitmapHandler.isPixelFilled(currentControllPoint));
    }
}
