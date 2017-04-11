package potrace;

import BitmapLibrary.TestPicture01;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 08/04/2017.
 */

@RunWith(Parameterized.class)
public class TestPathAmountInPicture {

    int expectedValue;
    int actualValue;

    @Parameterized.Parameters(name = "Testing {index}. Bitmap")
    public static Collection testData() {

        Object[][] testParameters = new Object[][]{
                TestPicture01.getAmountOfPathesTuple()
        };
        return Arrays.asList(testParameters);
    }

    public TestPathAmountInPicture(int expectedAmountOfPathes, int actualAmountOfPathes) {
        this.expectedValue = expectedAmountOfPathes;
        this.actualValue = actualAmountOfPathes;
    }

    @Test
    public void testAmountOfPaths() {
        assertEquals(expectedValue, actualValue);
    }
}
