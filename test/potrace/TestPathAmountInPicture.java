package potrace;

import BitmapLibrary.*;
import Tools.BitmapImporter;
import Tools.PathCounter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by andreydelany on 08/04/2017.
 */

@RunWith(Parameterized.class)
public class TestPathAmountInPicture {

    int expectedAmountOfPahtes;
    int[] areasOfPathes;
    int[] signsOfPathes;
    int[] lengthOfCurve;
    int[][] expectedTagsOfCurve;
    boolean[][] expectedChildsAndSiblings;
    double[][][][] expectedPointsOfCurve;
    int[][] expectedPrivInformations;
    potrace_path path;
    String testPictureFolderName = "testPictures";

    @Parameterized.Parameters(name = "Testing {index}. Bitmap")
    public static Collection testData() {
        Object[][] testParameters = TestDataForTestPictures.getTestParameters();
        return Arrays.asList(testParameters);
    }

    public TestPathAmountInPicture(String nameOfTestPicture,
                                   int amountOfPathes,
                                   int[] areasOfPathes,
                                   int[] signsOfPathes,
                                   int[] lengthOfCurve,
                                   int[][]expectedTagsOfCurve,
                                   boolean[][] expectedChildsAndSiblings,
                                   double[][][][] expectedPointsOfCurve,
                                   int[][] expectedPrivInformations) {

        potrace_bitmap bitmap = BitmapImporter.importBitmap(nameOfTestPicture,testPictureFolderName);
        this.path = PotraceLib.potrace_trace(new potrace_param(),bitmap);
        this.expectedAmountOfPahtes = amountOfPathes;
        this.areasOfPathes = areasOfPathes;
        this.signsOfPathes = signsOfPathes;
        this.lengthOfCurve = lengthOfCurve;
        this.expectedTagsOfCurve = expectedTagsOfCurve;
        this.expectedChildsAndSiblings = expectedChildsAndSiblings;
        this.expectedPointsOfCurve = expectedPointsOfCurve;
        this.expectedPrivInformations = expectedPrivInformations;
    }

    @Test
    public void testAmountOfPaths() {
        PathCounter pathIterator = new PathCounter(path);
        int counter = 1;
        while (pathIterator.hasNext()) {
            counter ++;
            pathIterator.goToNextPath();
        }
        assertEquals(expectedAmountOfPahtes,counter);
    }

    @Test
    public void testAreaOfPaths() {
        PathCounter pathIterator = new PathCounter(path);
        int counter = 0;
        while (pathIterator.hasNext()) {
            if (areasOfPathes[counter] != pathIterator.getCurrentPaht().area) {
                System.out.println("was: " + pathIterator.getCurrentPaht().area +
                        " but should be: " + areasOfPathes[counter] + " at index: " + counter);
                assertTrue(false);
            }
            counter ++;
            pathIterator.goToNextPath();
        }
        assertTrue(true);
    }

    @Test
    public void testSignsOfPathes() {
        PathCounter pathIterator = new PathCounter(path);
        int counter = 0;
        while (pathIterator.hasNext()) {
            if (signsOfPathes[counter] != pathIterator.getCurrentPaht().sign) {
                System.out.println("was: " + pathIterator.getCurrentPaht().sign +
                        " but should be: " + signsOfPathes[counter] + " at index: " + counter);
                assertTrue(false);
            }
            counter ++;
            pathIterator.goToNextPath();
        }
        assertTrue(true);
    }

    @Test
    public void testLenghtOfCurve() {
        PathCounter pathIterator = new PathCounter(path);
        int counter = 0;
        while (pathIterator.hasNext()) {
            if (lengthOfCurve[counter] != pathIterator.getCurrentPaht().curve.n) {
                System.out.println("was: " + pathIterator.getCurrentPaht().curve.n +
                        " but should be: " + lengthOfCurve[counter] + " at index: " + counter);
                assertTrue(false);
            }
            counter ++;
            pathIterator.goToNextPath();
        }
        assertTrue(true);
    }

    @Test
    public void testTagsOfCurve() {
        PathCounter pathIterator = new PathCounter(path);
        int counter = 0;
        while (pathIterator.hasNext()) {
            int[] expectedTagsForCurrentCurve = expectedTagsOfCurve[counter];
            int[] actualTagsForCurrentcurve = pathIterator.getCurrentPaht().curve.tag;
            for (int i = 0; i < expectedTagsForCurrentCurve.length; i++) {
                if (expectedTagsForCurrentCurve[i] != actualTagsForCurrentcurve[i]) {
                    System.out.println("was: " + actualTagsForCurrentcurve[i] + " but should be: " + expectedTagsForCurrentCurve[i] + " at index: [" + counter + "]["+i+"]");
                    assertTrue(false);
                }
            }
            counter ++;
            pathIterator.goToNextPath();
        }
        assertTrue(true);
    }

    @Test
    public void testPointsOfCurves() {
        PathCounter pathIterator = new PathCounter(path);
        int counter = 0;
        while (pathIterator.hasNext()) {
            double[][][] expectedPointsOfCurrentCurve = expectedPointsOfCurve[counter];
            potrace_dpoint[][] actualPointsForCurrentcurve = pathIterator.getCurrentPaht().curve.c;

            for (int currentBezierIndex = 0; currentBezierIndex < expectedPointsOfCurrentCurve.length; currentBezierIndex++) {
                double [][] expectedPointsOfCurrentBezierCurve = expectedPointsOfCurrentCurve [currentBezierIndex];
                potrace_dpoint[] actualPointsOfCurrentBezierCurve = actualPointsForCurrentcurve[currentBezierIndex];
                for (int i = 0; i < 3; i++) {
                    double[] expectedPoint = expectedPointsOfCurrentBezierCurve[i];
                    potrace_dpoint actualPoint = actualPointsOfCurrentBezierCurve[i];
                    assertEquals("XCoordinate -> curve: " + counter +
                            " bezierCurve number: " + currentBezierIndex +
                            " and there the: " + i + ". Point",
                            expectedPoint[0], actualPoint.x, 0.001);
                    assertEquals("YCoordinate -> curve: " + counter +
                            " bezierCurve number: " + currentBezierIndex +
                            " and there the: " + i + ". Point",
                            expectedPoint[1], actualPoint.y, 0.001);
                }
            }
            counter ++;
            pathIterator.goToNextPath();
        }
        assertTrue(true);
    }

    @Test
    public void testChildsAndSiblings() {
        PathCounter pathIterator = new PathCounter(path);
        int counter = 0;
        while (pathIterator.hasNext()) {
            assertEquals("Child in Path: " + counter,(expectedChildsAndSiblings[counter][0]),(pathIterator.getCurrentPaht().childlist != null));
            assertEquals("Sibling in Path: " + counter,(expectedChildsAndSiblings[counter][1]),(pathIterator.getCurrentPaht().sibling != null));
            counter ++;
            pathIterator.goToNextPath();
        }
    }

    @Test
    public void testPrivInformations() {
        PathCounter pathIterator = new PathCounter(path);
        int counter = 0;
        while (pathIterator.hasNext()) {
            assertEquals("LengthOf PrivPath Nummer: " + counter,(expectedPrivInformations[counter][0]),(pathIterator.getCurrentPaht().priv.len));
            assertEquals("XCoordinate PrivPath Nummer: " + counter,(expectedPrivInformations[counter][1]),(pathIterator.getCurrentPaht().priv.x0));
            assertEquals("YCoordinate PrivPath Nummer: " + counter,(expectedPrivInformations[counter][2]),(pathIterator.getCurrentPaht().priv.y0));
            assertEquals("M PrivPath Nummer: " + counter,(expectedPrivInformations[counter][3]),(pathIterator.getCurrentPaht().priv.m));
            counter ++;
            pathIterator.goToNextPath();
        }
    }
}