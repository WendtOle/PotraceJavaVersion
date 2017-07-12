package Potrace.refactored;

import AdditionalCode.FileInputOutput.BitmapImporter;
import Potrace.General.Bitmap;
import Potrace.General.Path;
import org.junit.Before;

import java.awt.*;

/**
 * Created by andreydelany on 12.07.17.
 */
public abstract class HierachyBaseTest {
    Bitmap bitmap;
    Path referencePath, pathAbove, pathBelow, pathInside , pathLeft, pathRight;
    Path allPaths;

    @Before
    public void importBitmap(){
        BitmapImporter importer = new BitmapImporter("determineHierachyTestPicture.png","testPictures");
        bitmap = importer.getBitmap();
    }

    @Before
    public void setPathes(){
        findPaths();
        putPathsInLine();
    }

    private void findPaths() {
        FindPath pathFinder = new FindPath(bitmap,new Point(3,8),PathKindEnum.POSITIV, TurnPolicyEnum.MINORITY);
        referencePath = pathFinder.getPath();

        pathFinder = new FindPath(bitmap,new Point(0,11),PathKindEnum.POSITIV,TurnPolicyEnum.MINORITY);
        pathAbove = pathFinder.getPath();

        pathFinder = new FindPath(bitmap,new Point(0,1),PathKindEnum.POSITIV,TurnPolicyEnum.MINORITY);
        pathBelow = pathFinder.getPath();

        pathFinder = new FindPath(bitmap,new Point(5,6),PathKindEnum.POSITIV,TurnPolicyEnum.MINORITY);
        pathInside = pathFinder.getPath();

        pathFinder = new FindPath(bitmap,new Point(0,8),PathKindEnum.POSITIV,TurnPolicyEnum.MINORITY);
        pathLeft = pathFinder.getPath();

        pathFinder = new FindPath(bitmap,new Point(12,8),PathKindEnum.POSITIV,TurnPolicyEnum.MINORITY);
        pathRight = pathFinder.getPath();
    }

    public abstract void putPathsInLine();
}
