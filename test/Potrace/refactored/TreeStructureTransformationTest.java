package Potrace.refactored;

import AdditionalCode.FileInputOutput.BitmapImporter;
import Potrace.General.Bitmap;
import Potrace.General.Param;
import Potrace.General.Path;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TreeStructureTransformationTest {
    Bitmap bitmap;
    Path pathList;
    @Before
    public void prepare(){
        BitmapImporter importer = new BitmapImporter("testPictures");
        bitmap = importer.getBitmap("determineHierarchyTestPicture.png");
        FindAllPathsOnBitmap findPathsOnBitmap = new FindAllPathsOnBitmap(bitmap,new Param());
        pathList = findPathsOnBitmap.getPathList();
        ListToTreeTransformation treeStructureTransformation = new ListToTreeTransformation(pathList,bitmap);
        pathList = treeStructureTransformation.getTreeStructure();
    }

    @Test
    public void testFirstPath_PathAbove() {
        assertEquals(28,pathList.area);
    }

    @Test
    public void testSecondPath_PathLeft() {
        assertEquals(12,pathList.next.area);
    }

    @Test
    public void testThirdPath_OutsidePath() {
        assertEquals(48,pathList.next.next.area);
    }

    @Test
    public void testFourthPath_FirstInsidePath() {
        assertEquals(24,pathList.next.next.next.area);
    }

    @Test
    public void testFifthPath_RightPath() {
        assertEquals(12,pathList.next.next.next.next.area);
    }

    @Test
    public void testSixthPath_PathBelow() {
        assertEquals(28,pathList.next.next.next.next.next.area);
    }

    @Test
    public void testSixthPath_SecondInsidePath() {
        assertEquals(8,pathList.next.next.next.next.next.next.area);
    }
}
