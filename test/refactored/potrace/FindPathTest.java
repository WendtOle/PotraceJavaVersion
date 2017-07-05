package refactored.potrace;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 05.07.17.
 */
public class FindPathTest {
    @Test
    public void testFindingOnePixelAsPath() {
        Bitmap bitmap = new Bitmap(10,10);
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(bitmap);
        bitmapHandler.setPixel(new Point(1,1));

        FindPath pathFinder = new FindPath(bitmap,new Point(1,0),43,TurnPolicyEnum.MINORITY);
        Path foundPath = pathFinder.getPath();
        assertEquals(1,foundPath.area);
    }
}
