package Potrace.refactored;

import Potrace.General.Bitmap;
import Potrace.General.Path;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

import static TestUtils.AssertPathes.assertEqualPathes;

/**
 * Created by andreydelany on 05.07.17.
 */

@RunWith(Parameterized.class)
public class FindPathTest {

    Path actualPath, expectedPath;

    @Parameterized.Parameters(name = "{index}")
    public static Collection testData() {
        return Arrays.asList(new Object[][]{
                {new Bitmap(10,10),
                        new Point[]{new Point(1,1)},
                        1,
                        new Point[]{new Point(1,2),new Point(1,1),new Point(2,1),new Point(2,2)}},

                {new Bitmap(10,10),
                        new Point[]{new Point(1,1),new Point(1,0)},
                        2,
                        new Point[]{new Point(1,2),
                                new Point(1,1),new Point(1,0),
                                new Point(2,0),new Point(2,1),
                                new Point(2,2)}},

                {new Bitmap(70,1),
                        new Point[]{new Point(63,0),new Point(64,0)},
                        2,
                        new Point[]{new Point(63,1),
                                new Point(63,0),new Point(64,0),
                                new Point(65,0),new Point(65,1),
                                new Point(64,1)}},

        });
    }

    public FindPathTest(Bitmap bitmap,Point[] pointsOfPath, int area, Point[] pathPointShape){
        setBitmap(bitmap, pointsOfPath);
        setActualPath(bitmap,pointsOfPath);
        setExpectedPath(area, pathPointShape);
    }

    private void setBitmap(Bitmap bitmap, Point[] pointsOfPath) {
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(bitmap);
        for(Point currentPoint : pointsOfPath)
            bitmapHandler.setPixel(currentPoint);
    }

    private void setActualPath(Bitmap bitmap,Point[] pointsOfPath) {
        FindPath pathFinder = new FindPath(bitmap, pointsOfPath[0], new DirectionChooserIdentificator(TurnPolicyEnum.MINORITY,PathKindEnum.POSITIV));
        actualPath = pathFinder.getPath();
    }

    private void setExpectedPath(int area, Point[] pathPointShape) {
        expectedPath = new Path();
        expectedPath.area = area;
        expectedPath.sign = PathKindEnum.POSITIV.getIntRepresentation();
        expectedPath.priv.len = pathPointShape.length;
        expectedPath.priv.pt = pathPointShape;
    }

    @Test
    public void test(){
        assertEqualPathes(expectedPath,actualPath);
    }
}