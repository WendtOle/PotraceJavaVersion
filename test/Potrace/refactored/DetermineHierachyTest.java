package Potrace.refactored;

import AdditionalCode.FileInputOutput.BitmapImporter;
import Potrace.General.Bitmap;
import Potrace.General.Path;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by andreydelany on 12.07.17.
 */
public class DetermineHierachyTest {

    Bitmap bitmap;
    Path referencePath, pathAbove, pathBelow, pathInside , pathLeft, pathRight;
    Path pathesToOrder;

    @Before
    public void importBitmap(){
        BitmapImporter importer = new BitmapImporter("determineHierachyTestPicture.png","testPictures");
        bitmap = importer.getBitmap();
    }

    @Before
    public void setPathes(){
        findPathes();
        putPathesInLine();
    }

    private void findPathes() {
        FindPath pathFinder = new FindPath(bitmap,new Point(3,8),43, TurnPolicyEnum.MINORITY);
        referencePath = pathFinder.getPath();

        pathFinder = new FindPath(bitmap,new Point(0,11),43,TurnPolicyEnum.MINORITY);
        pathAbove = pathFinder.getPath();

        pathFinder = new FindPath(bitmap,new Point(0,1),43,TurnPolicyEnum.MINORITY);
        pathBelow = pathFinder.getPath();

        pathFinder = new FindPath(bitmap,new Point(5,6),43,TurnPolicyEnum.MINORITY);
        pathInside = pathFinder.getPath();

        pathFinder = new FindPath(bitmap,new Point(0,8),43,TurnPolicyEnum.MINORITY);
        pathLeft = pathFinder.getPath();

        pathFinder = new FindPath(bitmap,new Point(12,8),43,TurnPolicyEnum.MINORITY);
        pathRight = pathFinder.getPath();
    }


    private void putPathesInLine(){
        pathesToOrder = pathAbove;
        pathAbove.next = pathLeft;
        pathLeft.next = pathRight;
        pathRight.next = pathInside;
        pathInside.next = pathBelow;
    }

    @Test
    public void testThatPassesAreInRightOrder(){
        DetermineHierachy determa = new DetermineHierachy(bitmap);
        determa.getHierarchicallyOrderedPathes(referencePath,pathesToOrder);
        assertTrue(referencePath.next == pathInside);
        assertTrue(referencePath.next.next == pathBelow);

        assertTrue(referencePath.childlist == pathAbove);
        assertTrue(referencePath.childlist.next == pathLeft);
        assertTrue(referencePath.childlist.next.next == pathRight);
    }
}

