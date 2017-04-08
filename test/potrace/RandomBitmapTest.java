package potrace;

import Tools.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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

        BitmapImporter bitmapImporter = new BitmapImporter("error");
        ArrayList<potrace_bitmap> bitmaps = bitmapImporter.importAllBitmaps();
        Object[][] testParameters = new Object[bitmaps.size()][2];
        for(int i = 0; i < bitmaps.size(); i ++) {
            potrace_path shouldPath = PathFinder.findOriginalBitmap(bitmaps.get(i));
            potrace_path actualPath = decompose.bm_to_pathlist(bitmaps.get(i),new potrace_param());
            testParameters[i] = new Object[] {countPathes(shouldPath),countPathes(actualPath)};
        }
        return Arrays.asList(testParameters);
    }

    public RandomBitmapTest(int expectedAmountOfPathes, int actualAmountOfPathes) {
        this.expectedAmountOfPathes = expectedAmountOfPathes;
        this.actualAmountOfPathes = actualAmountOfPathes;
    }

    public static void findNewErrorBitmap() {
        RandomBitmapGenerator bitmapGenerator = new RandomBitmapGenerator(200,200,0.6);
        int tryCounter = 0;
        BetterBitmap bitmap;

        do {
            tryCounter ++;
            bitmap = bitmapGenerator.getRandomBitmap();
        } while(isBitmapCorrectAnalyzed(bitmap));

        System.out.println("Needed " + tryCounter + " tries to find a bitmap which throws an error.");
        BitmapExporter bitmapExporter = new BitmapExporter("error","errorBitmap");
        bitmapExporter.export(bitmap);
    }

    private static boolean isBitmapCorrectAnalyzed(potrace_bitmap bitmap) {
        potrace_path shouldPath = PathFinder.findOriginalBitmap(bitmap);
        potrace_path actualPath = decompose.bm_to_pathlist(bitmap,new potrace_param());
        if (isOnePathAmountNull(shouldPath, actualPath))
            return true;
        int should = countPathes(shouldPath);
        int actual = countPathes(actualPath);
        return should == actual;
    }

    private static boolean isOnePathAmountNull(potrace_path firstPath, potrace_path secondPath) {
        return firstPath == null ^ secondPath == null;
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
