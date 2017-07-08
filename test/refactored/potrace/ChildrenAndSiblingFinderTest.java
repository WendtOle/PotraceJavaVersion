package refactored.potrace;

import AdditionalCode.Input.JSONDeEncoder;
import General.BitmapInterface;
import General.Path;
import org.junit.Before;
import refactored.potrace.*;

import java.awt.*;
import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 05.07.17.
 */
public class ChildrenAndSiblingFinderTest {

    Bitmap bitmap;
    Path path;

    @Before
    public void importBitmap() {
        try {
            BitmapInterface bitmap = JSONDeEncoder.readBitmapFromJSon(new File("testPictures/11.jsonn"));
            this.bitmap = new Bitmap(bitmap.getWidth(),bitmap.getHeight());
            this.bitmap.words = bitmap.getWords();
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Before
    public void preparePathes(){
        NextFilledPixelFinder filledPixelFinder = new NextFilledPixelFinder(bitmap);
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(bitmap);
        while(filledPixelFinder.isThereAFilledPixel()) {
            Point startPointOfPath = filledPixelFinder.getPositionOfNextFilledPixel();
            int signOfPath = bitmapHandler.isPixelFilled(startPointOfPath) ? '+' : '-';
            FindPath pathFinder = new FindPath(bitmap,startPointOfPath,signOfPath, TurnPolicyEnum.RIGHT);
            Path foundPath = pathFinder.getPath();
            Path currentPath = path;
            while(currentPath != null){
                currentPath = currentPath.next;
            }
            currentPath = foundPath;
            PathInverter inverter = new PathInverter(bitmap);
            inverter.invertPathOnBitmap(foundPath);
        }
    }

//TODO
    public void test() {
        Path currentPath = path;
        int counter = 1;
        while(currentPath.next != null) {
            counter ++;
            currentPath = currentPath.next;
        }
        assertEquals(9,counter);
    }

}
