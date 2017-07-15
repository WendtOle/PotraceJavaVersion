package Potrace.refactored;

import AdditionalCode.FileInputOutput.BitmapImporter;
import Potrace.General.Bitmap;
import Potrace.General.Param;
import Potrace.General.Path;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChildrenAndSiblingFinderTest {
    Bitmap bitmap;
    Path pathList;

    @Before
    public void prepare(){
        BitmapImporter importer = new BitmapImporter("testPictures");
        bitmap = importer.getBitmap("determineHierarchyTestPicture.png");
        FindAllPathsOnBitmap findPathsOnBitmap = new FindAllPathsOnBitmap(bitmap,new Param());
        pathList = findPathsOnBitmap.getPathList();
        ChildrenAndSiblingFinder childrenAndSiblingFinder = new ChildrenAndSiblingFinder(pathList,bitmap);
        pathList = childrenAndSiblingFinder.getTreeTransformedPathStructure();
    }

    @Test
    public void testFirstPath_PathAbove(){
        assertEquals(28,pathList.area);
    }

    @Test
    public void testSiblingOfPathAbove_LeftPath(){
        assertEquals(12,pathList.next.area);
    }

    @Test
    public void testSiblingOfLeftPath_FirstInsidePath(){
        assertEquals(24,pathList.next.next.area);
    }

    @Test
    public void testSiblingOfFirstInsidePath_PathBelow(){
        assertEquals(28,pathList.next.next.next.area);
    }

    @Test
    public void testChildOfLeftPath_OutsidePath(){
        assertEquals(48,pathList.next.childlist.area);
    }

    @Test
    public void testSiblingOfOutsidePath_RightPass(){
        assertEquals(12,pathList.next.childlist.next.area);
    }

    @Test
    public void testChildOfOutsidePath_SecondInsidePath(){
        assertEquals(8,pathList.next.childlist.childlist.area);
    }
}
