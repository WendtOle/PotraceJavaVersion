import AdditionalCode.Input.JSONDeEncoder;
import General.*;
import org.json.simple.parser.ParseException;
import org.junit.Ignore;
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


    Path expectedPath;
    Path[] actualFirstPath;

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

    public CharakterizeDecomposeTest(Bitmap bitmap,
                                     Path expectedPath) {

        this.expectedPath = expectedPath;
        this.actualFirstPath = new Path[DecompositionEnum.values().length];
        for (int i = 0; i < DecompositionEnum.values().length; i++) {
            DecompositionInterface decomposer = DecompositionEnum.values()[i].getDecomposer();
            this.actualFirstPath[i] = decomposer.getPathList(bitmap, new Param());
        }
    }

    @Ignore
    @Test
    public void checkThatAllPathesAreTheSame() {

        Path currentExpectedPath = expectedPath;
        for(Path currentActualPath : actualFirstPath){
            checkThatThereAreSameNumberOfPathes(currentActualPath);
            int index = 0;
            assertEqualPathes(index,currentExpectedPath,currentActualPath);

            while(currentExpectedPath.next != null) {
                currentExpectedPath = currentExpectedPath.next;
                currentActualPath = currentActualPath.next;
                index ++;
                assertEqualPathes(index,currentExpectedPath,currentActualPath);
            }
        }

    }

    private void checkThatThereAreSameNumberOfPathes(Path actualPath) {
        assertEquals("Number of Pathes", countPathes(expectedPath),countPathes(actualPath));
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