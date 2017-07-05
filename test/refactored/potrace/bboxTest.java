package refactored.potrace;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 24.06.17.
 */
public class bboxTest {
    @Test
    public void test_setbbox_path() {
        BBox box = new BBox();
        Bitmap testBitmap = new Bitmap(3,3);
        BitmapHandlerInterface manipulator = new BitmapHandler(testBitmap);
        manipulator.setPixel(new Point(1,1));

        FindPath findPath = new FindPath(testBitmap,new Point(2,2),43,TurnPolicyEnum.MINORITY);
        Path path = findPath.getPath();

        box.setToBoundingBoxOfPath(path);
        assertEquals(2,box.x0);
        assertEquals(3,box.x1);
        assertEquals(1,box.y0);
        assertEquals(2,box.y1);
    }
}