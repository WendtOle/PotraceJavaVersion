package refactored.potrace.BitmapDecomposition;

import org.junit.Test;
import refactored.potrace.Bitmap;
import refactored.potrace.Path;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 24.06.17.
 */
public class bboxTest {
    @Test
    public void test_setbbox_path() {
        BoundingBox box = new BoundingBox();
        Bitmap testBitmap = new Bitmap(3,3);
        BitmapHandlerInterface manipulator = new BitmapHandler(testBitmap);
        manipulator.setPixel(new Point(1,1));

        FindPath findPath = new FindPath(testBitmap,new Point(1,1),43, TurnPolicyEnum.MINORITY);
        Path path = findPath.getPath();

        box.setToBoundingBoxOfPath(path);
        assertEquals(1,box.x0);
        assertEquals(2,box.x1);
        assertEquals(1,box.y0);
        assertEquals(2,box.y1);
    }
}