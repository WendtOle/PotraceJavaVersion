package Potrace.refactored;

import AdditionalCode.FileInputOutput.BitmapImporter;
import Potrace.General.Bitmap;
import Potrace.General.Param;
import Potrace.General.Path;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 13.07.17.
 */
public class FindPathesOnBitmapTest {

    Bitmap bitmap;
    Path pathList;

    @Before
    public void prepare(){
        BitmapImporter importer = new BitmapImporter("determineHierachyTestPicture.png","testPictures");
        bitmap = importer.getBitmap();
        FindPathsOnBitmap findPathsOnBitmap = new FindPathsOnBitmap(bitmap,new Param());
        pathList = findPathsOnBitmap.getPathList();
    }

    @Test
    public void testFirstPath() {
        assertEquals(new Point(0,12),pathList.priv.pt[0]);
        assertEquals(new Point(0,11),pathList.priv.pt[1]);
        assertEquals(new Point(0,10),pathList.priv.pt[2]);
        assertEquals(new Point(1,10),pathList.priv.pt[3]);
    }

    @Test
    public void testSecondPath() {
        Path secondPath = pathList.next;
        assertEquals(new Point(0,9),secondPath.priv.pt[0]);
        assertEquals(new Point(0,8),secondPath.priv.pt[1]);
        assertEquals(new Point(0,7),secondPath.priv.pt[2]);
        assertEquals(new Point(0,6),secondPath.priv.pt[3]);
    }

    @Test
    public void testThirdPath() {
        Path thirdPath = pathList.next.next;
        assertEquals(new Point(3,9),thirdPath.priv.pt[0]);
        assertEquals(new Point(3,8),thirdPath.priv.pt[1]);
        assertEquals(new Point(3,7),thirdPath.priv.pt[2]);
        assertEquals(new Point(3,6),thirdPath.priv.pt[3]);
    }

    @Test
    public void testFourthPath() {
        Path fourthPath = pathList.next.next.next;
        assertEquals(new Point(12,9),fourthPath.priv.pt[0]);
        assertEquals(new Point(12,8),fourthPath.priv.pt[1]);
        assertEquals(new Point(12,7),fourthPath.priv.pt[2]);
        assertEquals(new Point(12,6),fourthPath.priv.pt[3]);
    }

    @Test
    public void testFifthPath() {
        Path fifthPath = pathList.next.next.next.next;
        assertEquals(new Point(4,8),fifthPath.priv.pt[0]);
        assertEquals(new Point(4,7),fifthPath.priv.pt[1]);
        assertEquals(new Point(4,6),fifthPath.priv.pt[2]);
        assertEquals(new Point(4,5),fifthPath.priv.pt[3]);
    }

    @Test
    public void testSixthPath() {
        Path sixthPath = pathList.next.next.next.next.next;
        assertEquals(new Point(5,7),sixthPath.priv.pt[0]);
        assertEquals(new Point(5,6),sixthPath.priv.pt[1]);
        assertEquals(new Point(5,5),sixthPath.priv.pt[2]);
        assertEquals(new Point(6,5),sixthPath.priv.pt[3]);
    }

    @Test
    public void testSeventhPath() {
        Path seventhPath = pathList.next.next.next.next.next.next;
        assertEquals(new Point(0,2),seventhPath.priv.pt[0]);
        assertEquals(new Point(0,1),seventhPath.priv.pt[1]);
        assertEquals(new Point(0,0),seventhPath.priv.pt[2]);
        assertEquals(new Point(1,0),seventhPath.priv.pt[3]);
    }
}
