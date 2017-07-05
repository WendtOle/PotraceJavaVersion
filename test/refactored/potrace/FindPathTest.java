package refactored.potrace;

import org.junit.Test;

import java.awt.*;

import static refactored.potrace.AssertPathes.assertEqualPathes;

/**
 * Created by andreydelany on 05.07.17.
 */
public class FindPathTest {
    @Test
    public void testFindingOnePixelAsPath() {
        Bitmap bitmap = new Bitmap(10,10);
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(bitmap);
        bitmapHandler.setPixel(new Point(1,1));

        FindPath pathFinder = new FindPath(bitmap,new Point(1,1),43,TurnPolicyEnum.MINORITY);
        AdditionalCode.Path path = new AdditionalCode.Path(1,43,4,false,false,new Point[]{new Point(1,2),new Point(1,1),new Point(2,1),new Point(2,2)},null,null);
        Path actualPath = pathFinder.getPath();
        assertEqualPathes(path,actualPath);
    }

    @Test
    public void testFindingTwoPixelAsPath() {
        Bitmap bitmap = new Bitmap(10,10);
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(bitmap);
        bitmapHandler.setPixel(new Point(1,1));
        bitmapHandler.setPixel(new Point(1,0));

        FindPath pathFinder = new FindPath(bitmap,new Point(1,1),43,TurnPolicyEnum.MINORITY);
        AdditionalCode.Path path = new AdditionalCode.Path(2,43,6,false,false,new Point[]{new Point(1,2),
                new Point(1,1),new Point(1,0),
                new Point(2,0),new Point(2,1),
                new Point(2,2)},null,null);
        Path actualPath = pathFinder.getPath();
        assertEqualPathes(path,actualPath);
    }

    @Test
    public void testFindingTwoPixelOnWordBoundAsPath() {
        Bitmap bitmap = new Bitmap(70,1);
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(bitmap);
        bitmapHandler.setPixel(new Point(63,0));
        bitmapHandler.setPixel(new Point(64,0));

        FindPath pathFinder = new FindPath(bitmap,new Point(63,0),43,TurnPolicyEnum.MINORITY);
        AdditionalCode.Path path = new AdditionalCode.Path(2,43,6,false,false,new Point[]{new Point(63,1),
                new Point(63,0),new Point(64,0),
                new Point(65,0),new Point(65,1),
                new Point(64,1)},null,null);
        Path actualPath = pathFinder.getPath();
        assertEqualPathes(path,actualPath);
    }
}
