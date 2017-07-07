package refactored.potrace.BitmapDecomposition;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 05.07.17.
 */
public class AssertPoints {

    public static void assertEqualPoints(String message, Point expectedPoint, Point actualPoint) {
        assertTwoPoints(" -",expectedPoint,actualPoint);
    }

    public static void assertEqualPoints(Point expectedPoint, Point actualPoint) {
        assertTwoPoints("",expectedPoint,actualPoint);
    }

    private static void assertTwoPoints(String message, Point expectedPoint, Point actualPoint) {
        assertEquals(message + " x",expectedPoint.x,actualPoint.x);
        assertEquals(message + " y",expectedPoint.y,actualPoint.y);
    }
}
