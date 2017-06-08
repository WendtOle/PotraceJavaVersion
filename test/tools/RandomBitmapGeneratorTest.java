package tools;

import Tools.RandomBitmapGenerator;
import org.junit.Test;
import potrace.bitmap;

import static org.junit.Assert.assertTrue;

/**
 * Created by andreydelany on 07.06.17.
 */
public class RandomBitmapGeneratorTest {
    @Test
    public void checkThatDimensionsAreInRangeOfFewRandomlyGeneratedBitmaps(){
        int maxWithHeight = 100;
        for (int i = 0; i < 10; i ++){
            RandomBitmapGenerator bitmapGenerator = new RandomBitmapGenerator(maxWithHeight,maxWithHeight,0.1);
            bitmap bitmap = bitmapGenerator.getRandomBitmap();
            assertTrue("problem With Width: ", bitmap.w < maxWithHeight);
            assertTrue("problem With Height: ", bitmap.h < maxWithHeight);
        }
    }
}
