package refactored.potrace;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 04.07.17.
 */
public class BitmapHandlerTest {
    BitmapHandlerInterface bitmapHandler = new BitmapHandler(new Bitmap(100,100));

    @Test
    public void testSettingPixelAndGettingPixel(){
        Object[][] testDaten = new Object[][]{
                new Object[]{new Point(-1,1),false},
                new Object[]{new Point(1,1),true},
                new Object[]{new Point(99,99),true},
                new Object[]{new Point(9,101),false}
        };
        for (int i = 0; i < testDaten.length; i ++) {
            bitmapHandler.setPixel((Point)testDaten[i][0]);
            assertEquals("setPixel Test " + i,(Boolean)testDaten[i][1],bitmapHandler.isPixelFilled((Point)testDaten[i][0]));
        }
    }

    @Test
    public void testGettingPixel(){
        Object[][] testDaten = new Object[][]{
                new Object[]{new Point(-1,1),false,false},
                new Object[]{new Point(1,1),false,false},
                new Object[]{new Point(1,1),true,true},
                new Object[]{new Point(100,2),true,false}
        };
        for (int i = 0; i < testDaten.length; i ++) {
            Point pixelToTest = new Point((Point)testDaten[i][0]);
            boolean setPixel = (boolean) testDaten[i][1];
            boolean expectedResult = (boolean) testDaten[i][2];

            if(setPixel)
                bitmapHandler.setPixel(pixelToTest);
            assertEquals("getPixel Test " + i,expectedResult,bitmapHandler.isPixelFilled(pixelToTest));
        }
    }
}
