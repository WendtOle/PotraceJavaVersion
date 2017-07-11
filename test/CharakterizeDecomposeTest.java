import AdditionalCode.Input.JSONDeEncoder;
import General.Bitmap;
import General.DecompositionInterface;
import General.Param;
import General.Path;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import original.Decompose;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static TestMethods.AssertPathes.assertEqualPathes;
import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 08/04/2017.
 */

@RunWith(Parameterized.class)
public class CharakterizeDecomposeTest {
    Path expectedPath;
    Path actualOriginalPath;
    Path actualRefactoredPath;

    @Parameterized.Parameters(name = "Testing {index}. BitmapManipulator")
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
                    Bitmap bitmap = JSONDeEncoder.readBitmapFromJSon(bitmapFiles[i]);
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

    public CharakterizeDecomposeTest(Bitmap bitmap, Path expectedPath) {
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
        Path currentExpectedPath = expectedPath;
        checkThatThereAreSameNumberOfPaths(actualPath);
        int index = 0;
        assertEqualPathes(index,currentExpectedPath,actualPath);

        while(currentExpectedPath.next != null) {
            currentExpectedPath = currentExpectedPath.next;
            actualPath = actualPath.next;
            index ++;
            assertEqualPathes(index,currentExpectedPath,actualPath);
        }
    }

    private void checkThatThereAreSameNumberOfPaths(Path actualPath) {
        assertEquals("Number of Pathes", countPaths(expectedPath), countPaths(actualPath));
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