package potrace;

import Tools.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static Tools.Importer.importBitmap;
import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 08/04/2017.
 */

@RunWith(Parameterized.class)
public class RandomBitmapTest {
    static ArrayList<potrace_bitmap> bitmaps = new ArrayList<>();
    int expectedAmountOfPathes;
    int actualAmountOfPathes;


    @Parameterized.Parameters(name = "Testing {index}. Bitmap")
    public static Collection primeNumbers() {
        findNewErrorBitmap();
        int alreadyExistingFiles = new File(System.getProperty("user.dir") + File.separator + "error").list().length;
        Object[][] testParameters = new Object[alreadyExistingFiles][2];
        for (int i = 1; alreadyExistingFiles >= i; i++) {
            potrace_bitmap bitmap = Importer.importBitmap(System.getProperty("user.dir") + File.separator + "error" + File.separator + "errorBitmap" + i + ".bmp");
            potrace_path shouldPath = PathFinder.findOriginalBitmap(bitmap);
            potrace_path actualPath = decompose.bm_to_pathlist(bitmap,new potrace_param());
            int expectedAmountOfPathes = countPathes(shouldPath);
            int actualAmountOfPathes = countPathes(actualPath);
            testParameters[i-1] = new Object[]{expectedAmountOfPathes,actualAmountOfPathes};
        }
        return Arrays.asList(testParameters);
    }

    public RandomBitmapTest(int expectedAmountOfPathes, int actualAmountOfPathes) {
        this.expectedAmountOfPathes = expectedAmountOfPathes;
        this.actualAmountOfPathes = actualAmountOfPathes;
    }

    public static void findNewErrorBitmap() {
        RandomBitmapGenerator bitmapGenerator = new RandomBitmapGenerator(200,200,0.6);
        BetterBitmap bitmap = bitmapGenerator.getRandomBitmap();
        int tryCounter = 1;
        potrace_path shouldPath = PathFinder.findOriginalBitmap(bitmap);
        potrace_path actualPath = decompose.bm_to_pathlist(bitmap,new potrace_param());
        while (shouldPath == null || actualPath == null || countPathes(shouldPath) == countPathes(actualPath)) {
            tryCounter ++;
            shouldPath = PathFinder.findOriginalBitmap(bitmap);
            actualPath = decompose.bm_to_pathlist(bitmap,new potrace_param());
            bitmap = bitmapGenerator.getRandomBitmap();
        }
        System.out.println("Needed " + tryCounter + " tries to find a bitmap which throws an error.");
        BitmapExporter bitmapExporter = new BitmapExporter("error","errorBitmap");
        bitmapExporter.export(bitmap);
    }

    @Test
    public void testAmountOfPaths() {
        assertEquals(expectedAmountOfPathes,actualAmountOfPathes);
    }

    private static int countPathes(potrace_path startPath) {
        int counter = 1;
        potrace_path currentPath = startPath;
        while (currentPath.next != null) {
            counter ++;
            currentPath = currentPath.next;
        }
        return counter;
    }
}
