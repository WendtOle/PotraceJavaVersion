package potraceOriginal;

import Input.JSONDeEncoder;
import Input.MockupPath;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 08/04/2017.
 */

@RunWith(Parameterized.class)
public class CharakterizeDecomposeTest {

    MockupPath[] arrayOfPathes;
    Path actualFirstPath;

    @Parameterized.Parameters(name = "Testing {index}. Bitmap")
    public static Collection testData() {
        return Arrays.asList(getTestParameters("testPictures"));
    }

    private static Object[][] getTestParameters(String folderNameOfTestPictures) {
        Object [][] testParameters = null;
        try {
            File[] bitmapFiles = new File(folderNameOfTestPictures).listFiles((dir, name) -> {
                        return name.toLowerCase().endsWith(".txt");
                    }
            );

            testParameters = new Object[bitmapFiles.length][];
            for (int i = 0; i < bitmapFiles.length; i++) {
                try {
                    Bitmap bitmap = JSONDeEncoder.readBitmapFromJSon(bitmapFiles[i]);
                    MockupPath[] pathes = JSONDeEncoder.readTestDataFromJSon(bitmapFiles[i]);
                    testParameters[i] = new Object[]{bitmap,pathes};
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return testParameters;
    }

    public CharakterizeDecomposeTest(Bitmap bitmap,
                                     MockupPath[] arrayOfPathes) {

        this.arrayOfPathes = arrayOfPathes;
        this.actualFirstPath  = Decompose.bm_to_pathlist(bitmap,new Param());
    }

    @Test
    public void checkThatAllPathesAreTheSame() {
        checkThatThereAreSameNumberOfPathes();
        Path currentPath = actualFirstPath;
        for (int indexOfCurrentPaht = 0; indexOfCurrentPaht < arrayOfPathes.length; indexOfCurrentPaht ++) {
            MockupPath expectedPath = arrayOfPathes[indexOfCurrentPaht];
            Path actualPath = currentPath;
            comparePathes(indexOfCurrentPaht,expectedPath,actualPath);
            currentPath = currentPath.next;
        }
    }

    private void checkThatThereAreSameNumberOfPathes() {
        int actualAmountOfPathes = 1;
        Path currentPath = actualFirstPath;
        while (currentPath.next != null) {
            actualAmountOfPathes ++;
            currentPath = currentPath.next;
        }
        assertEquals("Number of Pathes", arrayOfPathes.length,actualAmountOfPathes);
    }

    private void comparePathes(int indexOfPath, MockupPath expectedPath, Path actualPath){
        compareSiblingChildStructure(indexOfPath,expectedPath,actualPath);
        compareGeneralPathInformations(indexOfPath,expectedPath,actualPath);
        comparePointsOfPath(indexOfPath,expectedPath,actualPath);
    }

    private void compareSiblingChildStructure(int indexOfPath, MockupPath expectedPath, Path actualPath){
        boolean actualHasChild = actualPath.childlist != null;
        assertEquals("Childlist (" + indexOfPath+")", expectedPath.hasChild,actualHasChild);

        boolean actualHasSibling = actualPath.sibling != null;
        assertEquals("Sibling (" + indexOfPath+")", expectedPath.hasSibling,actualHasSibling);
    }

    private void compareGeneralPathInformations(int indexOfPath, MockupPath expectedPath, Path actualPath){
        assertEquals("Area ("+indexOfPath+")",expectedPath.area,actualPath.area);
        assertEquals("Sign ("+indexOfPath+")",expectedPath.sign,actualPath.sign);
        assertEquals("Length ("+indexOfPath+")",expectedPath.length,actualPath.priv.len);
    }

    private void comparePointsOfPath(int indexOfPath, MockupPath expectedPath, Path actualPath){
        for(int indexOfCurrentPoint = 0; indexOfCurrentPoint < expectedPath.pt.length; indexOfCurrentPoint ++){
            Point expectedCurrentPoint = expectedPath.pt[indexOfCurrentPoint];
            Point acutalCurrentPoint = actualPath.priv.pt[indexOfCurrentPoint];
            assertEquals("Point - " + indexOfCurrentPoint + " ("+indexOfPath + ")",expectedCurrentPoint,acutalCurrentPoint);
        }
    }
}