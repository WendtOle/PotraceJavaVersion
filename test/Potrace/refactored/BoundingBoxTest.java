package Potrace.refactored;

import Potrace.General.Bitmap;
import Potrace.General.Path;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
/**
 * Created by andreydelany on 24.06.17.
 */
public class BoundingBoxTest {
    @Test
    public void test_setbbox_path() {
        Bitmap testBitmap = new Bitmap(3,3);
        BitmapHandlerInterface manipulator = new BitmapHandler(testBitmap);
        manipulator.setPixel(new Point(1,1));

        FindPath findPath = new FindPath(testBitmap,new Point(1,1),43, TurnPolicyEnum.MINORITY);
        Path path = findPath.getPath();

        BoundingBox box = new BoundingBox(path);
        assertEquals(1,box.x0);
        assertEquals(2,box.x1);
        assertEquals(1,box.y0);
        assertEquals(2,box.y1);
    }
}