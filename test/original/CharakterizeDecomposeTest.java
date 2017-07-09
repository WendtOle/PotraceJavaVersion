package original;

import AdditionalCode.Input.JSONDeEncoder;
import General.Bitmap;
import General.Param;
import General.Path;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

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

    Path arrayOfPathes;
    Path actualFirstPath;

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
                } catch (org.json.simple.parser.ParseException e) {
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
        Decompose decompser = new Decompose();
        this.actualFirstPath = decompser.getPathList(bitmap,new Param());
    }

    @Test
    public void checkThatAllPathesAreTheSame() {
        checkThatThereAreSameNumberOfPathes();

        int index = 0;
        Path currentExpectedPath = arrayOfPathes;
        Path currentActualPath = actualFirstPath;
        assertEqualPathes(index,currentExpectedPath,currentActualPath);

        while(currentExpectedPath.next != null) {
            currentExpectedPath = currentExpectedPath.next;
            currentActualPath = currentActualPath.next;
            index ++;
            assertEqualPathes(currentExpectedPath,currentActualPath);
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
}