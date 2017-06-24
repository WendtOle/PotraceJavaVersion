package refactored.potrace;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 24.06.17.
 */
public class bboxTest {
    @Test
    public void test_setbbox_path() {
        BBox box;
        Bitmap testBitmap = new Bitmap(3,3);
        testBitmap.BM_PUT(1,1,true);

        Path path = Decompose.findpath(testBitmap,2,2,43,4);

        box = new BBox(path);
        assertEquals(2,box.x0);
        assertEquals(3,box.x1);
        assertEquals(1,box.y0);
        assertEquals(2,box.y1);
    }
}
