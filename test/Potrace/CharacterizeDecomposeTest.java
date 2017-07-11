package Potrace;

import AdditionalCode.FileInputOutput.JsonEncoder;
import Potrace.General.Bitmap;
import Potrace.General.DecompositionInterface;
import Potrace.General.Param;
import Potrace.General.Path;
import Potrace.original.Decompose;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import static TestMethods.AssertPathes.assertEqualPathes;
import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 08/04/2017.
 */

@RunWith(Parameterized.class)
public class CharacterizeDecomposeTest {
    Path expectedPath;
    Path actualOriginalPath;
    Path actualRefactoredPath;
    Path actualCurrentPathUnderTest, expectedCurrentPathUnderTest;
    int currentPathPairIndex;

    @Parameterized.Parameters(name = "Testing {index}. Bitmap")
    public static Collection testData() {
        return Arrays.asList(getTestParameters("testPictures"));
    }

    private static Object[][] getTestParameters(String folderNameOfTestPictures) {
        return getAvailableBitmapPathPairs(folderNameOfTestPictures);
    }

    private static Object[][] getAvailableBitmapPathPairs(String folderNameOfTestPictures) {
        File[] bitmapFiles = getAllBitmapFilesForTesting(folderNameOfTestPictures);
        Object[][] testParameters = new Object[bitmapFiles.length][];
        for (int i = 0; i < bitmapFiles.length; i++)
            testParameters[i] = loadBitmapPathPair(bitmapFiles[i]);
        return testParameters;
    }

    private static Object[] loadBitmapPathPair(File bitmapFile){
        JsonEncoder encoder = new JsonEncoder(bitmapFile);
        Bitmap bitmap = encoder.getBitmap();
        Path path = encoder.getPath();
        return new Object[]{bitmap,path};
    }

    private static File[] getAllBitmapFilesForTesting(String folderNameOfTestPictures) {
        return new File(folderNameOfTestPictures).listFiles((dir, name) -> {
                    return name.toLowerCase().endsWith(".json");
                }
        );
    }

    public CharacterizeDecomposeTest(Bitmap bitmap, Path expectedPath) {
        this.expectedPath = expectedPath;
        setActualOriginalPath(bitmap);
        setActualRefactoredPath(bitmap);
    }

    private void setActualOriginalPath(Bitmap bitmap) {
        DecompositionInterface originalDecomposer = new Decompose();
        this.actualOriginalPath = originalDecomposer.getPathList(bitmap,new Param());
    }

    private void setActualRefactoredPath(Bitmap bitmap) {
        DecompositionInterface originalDecomposer = new Decompose();
        this.actualRefactoredPath = originalDecomposer.getPathList(bitmap,new Param());
    }

    @Test
    public void testOriginalDecomposer(){
        checkThatAllPathsAreTheSame(actualOriginalPath);
    }

    @Test
    public void testRefactoredDecomposer(){
        checkThatAllPathsAreTheSame(actualRefactoredPath);
    }

    private void checkThatAllPathsAreTheSame(Path actualPath) {
        testNumberOfPaths(actualPath);
        initializeFirstTestPairOfPaths(actualPath);
        testAllTestPathPairs();
    }

    private void testNumberOfPaths(Path actualPath) {
        assertEquals("Number of Pathes", countPaths(expectedPath), countPaths(actualPath));
    }

    private void initializeFirstTestPairOfPaths(Path actualPath) {
        expectedCurrentPathUnderTest = expectedPath;
        actualCurrentPathUnderTest = actualPath;
        currentPathPairIndex = 0;
    }

    private void testAllTestPathPairs() {
        do {
            compareCurrentTestPathPair();
            setNextPairOfPathsToCompare();
        } while (expectedCurrentPathUnderTest.next != null);
    }

    private void compareCurrentTestPathPair() {
        assertEqualPathes(currentPathPairIndex,expectedCurrentPathUnderTest,actualCurrentPathUnderTest);
    }

    private void setNextPairOfPathsToCompare() {
        expectedCurrentPathUnderTest = expectedCurrentPathUnderTest.next;
        actualCurrentPathUnderTest = actualCurrentPathUnderTest.next;
    }

    private int countPaths(Path path) {
        int actualAmountOfPathes = 1;
        Path currentPath = path;
        while (currentPath.next != null) {
            actualAmountOfPathes ++;
            currentPath = currentPath.next;
        }
        return actualAmountOfPathes;
    }
}