package potrace;

import Tools.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by andreydelany on 11/04/2017.
 */
public class TestPathReductionOfPathlistToTree {

    int MAXWIDTH = 200;
    int MAXHeight= 200;

    @Test
    public void testAmountOfPathesAfterPathReduction() {
        assertTrue(findNewErrorBitmap());
    }

    public boolean findNewErrorBitmap() {

        for(double noiseRatio = 0.1; noiseRatio < 1.0; noiseRatio += 0.1) {
            if (!placeHolder(noiseRatio))
                return false;
        }
        return true;
    }

    private boolean placeHolder(double noiseRatio) {
        RandomBitmapGenerator bitmapGenerator = new RandomBitmapGenerator(MAXWIDTH,MAXHeight,noiseRatio);
        int tryCounter = 0;
        BetterBitmap bitmap;

        do {
            tryCounter ++;
            bitmap = bitmapGenerator.getRandomBitmap();
            if(!isBitmapCorrectAnalyzed(bitmap)) {
                BitmapExporter bitmapExporter = new BitmapExporter("PathAmountDoesntMatch","errorBitmap");
                bitmapExporter.export(bitmap);
                return false;
            }
        } while(tryCounter < 100);
        return true;
    }

    private boolean isBitmapCorrectAnalyzed(bitmap bitmap) {
        path shouldPath = PathFinder.findOriginalBitmap(bitmap);
        path actualPath = decompose.bm_to_pathlist(bitmap,new param());
        if (noPathesfound(shouldPath, actualPath))
            return true;

        PathCounter pathIteratorShould = new PathCounter(shouldPath);
        int shouldCounter = 1;
        while (pathIteratorShould.hasNext()) {
            shouldCounter ++;
            pathIteratorShould.goToNextPath();
        }

        PathCounter pathIteratorActual = new PathCounter(actualPath);
        int actualCounter = 1;
        while (pathIteratorActual.hasNext()) {
            actualCounter ++;
            pathIteratorActual.goToNextPath();
        }

        return shouldCounter == actualCounter;
    }

    private static boolean noPathesfound(path firstPath, path secondPath) {
        return firstPath == null && secondPath == null;
    }
}
