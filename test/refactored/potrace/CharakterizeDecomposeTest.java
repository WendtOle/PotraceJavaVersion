package refactored.potrace;

import AdditionalCode.BitmapTranslater;
import AdditionalCode.Input.JSONDeEncoder;
import AdditionalCode.Path;
import AdditionalCode.PathTranslator;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import original.potrace.Bitmap;
import original.potrace.Param;
import original.potrace.PotraceLib;

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

    Path arrayOfPathes;
    Path actualFirstPath;

    @Parameterized.Parameters(name = "Testing {index}. Bitmap")
    public static Collection testData() {
        return Arrays.asList(getTestParameters("testPictures"));
    }

    private static Object[][] getTestParameters(String folderNameOfTestPictures) {
        Object [][] testParameters = null;
        try {
            File[] bitmapFiles = new File(folderNameOfTestPictures).listFiles((dir, name) -> {
                        return name.toLowerCase().endsWith(".json");
                    }
            );

            testParameters = new Object[bitmapFiles.length][];
            for (int i = 0; i < bitmapFiles.length; i++) {
                try {
                    original.potrace.Bitmap bitmap = BitmapTranslater.translateBitmapForOriginalCode(JSONDeEncoder.readBitmapFromJSon(bitmapFiles[i]));
                    Path path = JSONDeEncoder.readTestDataFromJSon(bitmapFiles[i]);
                    testParameters[i] = new Object[]{bitmap,path};
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
                                     Path expectedPath) {

        this.arrayOfPathes = expectedPath;
        this.actualFirstPath = PathTranslator.originalPathToGeneralPath(PotraceLib.potrace_trace(new Param(),bitmap));
    }

    @Test
    public void checkThatAllPathesAreTheSame() {
        checkThatThereAreSameNumberOfPathes();

        int index = 0;
        Path currentExpectedPath = arrayOfPathes;
        Path currentActualPath = actualFirstPath;
        comparePathes(index,currentExpectedPath,currentActualPath);

        while(currentExpectedPath.next != null) {
            currentExpectedPath = currentExpectedPath.next;
            currentActualPath = currentActualPath.next;
            index ++;
            comparePathes(index,currentExpectedPath,currentActualPath);
        }
    }

    private void checkThatThereAreSameNumberOfPathes() {
        assertEquals("Number of Pathes", countPathes(arrayOfPathes),countPathes(actualFirstPath));
    }

    private int countPathes(Path path) {
        int actualAmountOfPathes = 1;
        Path currentPath = path;
        while (currentPath.next != null) {
            actualAmountOfPathes ++;
            currentPath = currentPath.next;
        }
        return actualAmountOfPathes;
    }

    private void comparePathes(int indexOfPath, Path expectedPath, Path actualPath){
        compareSiblingChildStructure(indexOfPath,expectedPath,actualPath);
        compareGeneralPathInformations(indexOfPath,expectedPath,actualPath);
        comparePointsOfPath(indexOfPath,expectedPath,actualPath);
    }

    private void compareSiblingChildStructure(int indexOfPath, Path expectedPath, Path actualPath){
        assertEquals("Childlist (" + indexOfPath+")", expectedPath.hasChild,actualPath.hasChild);
        assertEquals("Sibling (" + indexOfPath+")", expectedPath.hasSibling,actualPath.hasSibling);
    }

    private void compareGeneralPathInformations(int indexOfPath, Path expectedPath, Path actualPath){
        assertEquals("Area ("+indexOfPath+")",expectedPath.area,actualPath.area);
        assertEquals("Sign ("+indexOfPath+")",expectedPath.sign,actualPath.sign);
        assertEquals("Length ("+indexOfPath+")",expectedPath.length,actualPath.length);
    }

    private void comparePointsOfPath(int indexOfPath, Path expectedPath, Path actualPath){
        for(int indexOfCurrentPoint = 0; indexOfCurrentPoint < expectedPath.pt.length; indexOfCurrentPoint ++){
            Point expectedCurrentPoint = expectedPath.pt[indexOfCurrentPoint];
            Point acutalCurrentPoint = actualPath.pt[indexOfCurrentPoint];
            assertEquals("Point - " + indexOfCurrentPoint + " ("+indexOfPath + ")",expectedCurrentPoint,acutalCurrentPoint);
        }
    }
}