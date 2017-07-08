package refactored.potrace;

import org.junit.Test;
import refactored.potrace.Bitmap;
import refactored.potrace.BitmapHandler;
import refactored.potrace.BitmapHandlerInterface;

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
                new Object[]{"x Outside Bound",new Point(-1,1),false},
                new Object[]{"should work (1)", new Point(1,1),true},
                new Object[]{"should Work(2)", new Point(99,99),true},
                new Object[]{"at same position again",new Point(99,99),true},
                new Object[]{"y ouside bound",new Point(9,101),false}
        };
        for (int i = 0; i < testDaten.length; i ++) {
            String message = (String)testDaten[i][0];
            Point pixelToTest = new Point((Point)testDaten[i][1]);
            boolean expectedResult = (boolean) testDaten[i][2];

            bitmapHandler.setPixel(pixelToTest);
            assertEquals(message,expectedResult,bitmapHandler.isPixelFilled(pixelToTest));
        }
    }

    @Test
    public void testGettingPixel(){
        Object[][] testDaten = new Object[][]{
                new Object[]{"x outside bound",new Point(-1,1),false,false},
                new Object[]{"not set and want to get",new Point(1,1),false,false},
                new Object[]{"set and get -> should work",new Point(1,1),true,true},
                new Object[]{"y outside bound",new Point(2,100),true,false}
        };
        for (int i = 0; i < testDaten.length; i ++) {
            String message = (String)testDaten[i][0];
            Point pixelToTest = new Point((Point)testDaten[i][1]);
            boolean setPixel = (boolean) testDaten[i][2];
            boolean expectedResult = (boolean) testDaten[i][3];

            if(setPixel)
                bitmapHandler.setPixel(pixelToTest);
            assertEquals(message,expectedResult,bitmapHandler.isPixelFilled(pixelToTest));
        }
    }

    @Test
    public void testClearingExcessPixelWhenConstructingABitmapHandler(){
        Bitmap bitmap = new Bitmap(65,1);
        bitmap.words[1] = -1;
        BitmapHandlerInterface bitmapHandler = new BitmapHandler(bitmap);
        assertEquals(bitmap.words[1],0x8000000000000000l);
    }

    @Test
    public void test_bm_clear() throws Exception {
        bitmapHandler.setPixel(new Point(0,1));
        bitmapHandler.setPixel(new Point(4,4));

        bitmapHandler.clearCompleteBitmap();

        assertEquals(false, bitmapHandler.isPixelFilled(new Point(0,1)));
        assertEquals(false, bitmapHandler.isPixelFilled(new Point(4,4)));
    }
}
