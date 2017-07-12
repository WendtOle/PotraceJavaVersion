package Potrace.refactored;

import Potrace.General.Bitmap;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 12.07.17.
 */
public class DirectionHandlerTest {

    Bitmap bitmap;
    DirectionHandler directionHandler;

    @Test
    public void testInitialDirection(){
        directionHandler = new DirectionHandler(new Bitmap(10,10),TurnPolicyEnum.MINORITY,43);
        assertDirection(new Point(0,-1));
    }

    @Test
    public void testSimpleTurnToRight(){
        Point[] pixel = new Point[]{new Point(1,1)};
        setBitmap(pixel);
        directionHandler = new DirectionHandler(bitmap,TurnPolicyEnum.MINORITY,34);
        directionHandler.turnInNextDirection(pixel[0]);

        assertDirection(new Point(1,0));
    }

    @Test
    public void testSimpleTurnToLeft() {
        Point[] points = new Point[]{new Point(1,1),new Point(1,0),new Point(0,0)};
        setBitmap(points);
        directionHandler = new DirectionHandler(bitmap,TurnPolicyEnum.MINORITY,34);
        directionHandler.turnInNextDirection(points[0]);

        assertDirection(new Point(-1,0));
    }

    @Test
    public void testAmbigousTurnToLeft(){
        Point[] points = new Point[]{new Point(1,1),new Point(0,0)};
        setBitmap(points);
        directionHandler = new DirectionHandler(bitmap,TurnPolicyEnum.MINORITY,34);
        directionHandler.turnInNextDirection(points[0]);

        assertDirection(new Point(-1,0));
    }

    @Test
    public void testAmbigousTurnToRight(){
        Point[] points = new Point[]{new Point(1,1),new Point(0,0)};
        setBitmap(points);
        directionHandler = new DirectionHandler(bitmap,TurnPolicyEnum.WHITE,34);
        directionHandler.turnInNextDirection(points[0]);

        assertDirection(new Point(1,0));
    }

    private void setBitmap(Point[] firstPixel) {
        bitmap = new Bitmap(3,3);
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(bitmap);
        for(Point currentPixel: firstPixel)
            bitmapHandler.setPixel(currentPixel);
    }

    private void assertDirection(Point expectedDirection) {
        assertEquals("horizontalDirection",expectedDirection.x,directionHandler.getHorizontalDirection());
        assertEquals("verticalDirection",expectedDirection.y,directionHandler.getVerticalDirection());
    }
}
