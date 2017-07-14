package Potrace.refactored;

import Potrace.General.Bitmap;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class DirectionHandlerTest {

    Bitmap bitmap;
    DirectionHandler directionHandler;
    PathFindingCharacteristics pathFindingCharacteristics = new PathFindingCharacteristics(TurnPolicyEnum.MINORITY,PathKindEnum.POSITIV);

    @Test
    public void testSimpleTurnToRight(){
        Point[] pixel = new Point[]{new Point(1,1)};
        setBitmap(pixel);
        directionHandler = new DirectionHandler(bitmap, pathFindingCharacteristics);
        directionHandler.turnInNextDirection(pixel[0]);

        assertEquals(new Point(2,1),directionHandler.moveInDirection());
    }

    @Test
    public void testSimpleTurnToLeft() {
        Point[] points = new Point[]{new Point(1,1),new Point(1,0),new Point(0,0)};
        setBitmap(points);
        directionHandler = new DirectionHandler(bitmap, pathFindingCharacteristics);
        directionHandler.turnInNextDirection(points[0]);

        assertEquals(new Point(0,1),directionHandler.moveInDirection());
    }

    @Test
    public void testAmbiguousTurnToLeft(){
        Point[] points = new Point[]{new Point(1,1),new Point(0,0)};
        setBitmap(points);
        directionHandler = new DirectionHandler(bitmap, pathFindingCharacteristics);
        directionHandler.turnInNextDirection(points[0]);

        assertEquals(new Point(0,1),directionHandler.moveInDirection());
    }

    @Test
    public void testAmbiguousTurnToRight(){
        Point[] points = new Point[]{new Point(1,1),new Point(0,0)};
        setBitmap(points);
        directionHandler = new DirectionHandler(bitmap,new PathFindingCharacteristics(TurnPolicyEnum.WHITE,PathKindEnum.POSITIV));
        directionHandler.turnInNextDirection(points[0]);

        assertEquals(new Point(2,1),directionHandler.moveInDirection());
    }

    private void setBitmap(Point[] firstPixel) {
        bitmap = new Bitmap(3,3);
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(bitmap);
        for(Point currentPixel: firstPixel)
            bitmapHandler.setPixel(currentPixel);
    }
}
