package potrace;

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
public class TestTestPicture {

    int expectedAmountOfPahtes;
    int[] areasOfPathes;
    int[] signsOfPathes;
    int[] lengthOfCurve;
    int[][] expectedTagsOfCurve;
    boolean[][] expectedChildsAndSiblings;
    double[][][][] expectedPointsOfCurve;
    int[][] expectedPrivInformations;
    potrace.path path;
    String testPictureFolderName = "testPictures";

    @Parameterized.Parameters(name = "Testing {index}. Bitmap")
    public static Collection testData() {
        Object[][] testParameters = new Object[][]{
                (new TestDataPicture01()).getTestParameters(),
                (new TestDataPicture02()).getTestParameters()

        };
        return Arrays.asList(testParameters);
    }

    public TestTestPicture(String nameOfTestPicture,
                           int amountOfPathes,
                           int[] areasOfPathes,
                           int[] signsOfPathes,
                           int[] lengthOfCurve,
                           int[][]expectedTagsOfCurve,
                           boolean[][] expectedChildsAndSiblings,
                           double[][][][] expectedPointsOfCurve,
                           int[][] expectedPrivInformations) {

        bitmap bitmap = BitmapImporter.importBitmap(nameOfTestPicture,testPictureFolderName);
        this.path = potraceLib.potrace_trace(new param(),bitmap);
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
            if (areasOfPathes[counter] != pathIterator.getCurrentPath().area) {
                System.out.println("was: " + pathIterator.getCurrentPath().area +
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
            if (signsOfPathes[counter] != pathIterator.getCurrentPath().sign) {
                System.out.println("was: " + pathIterator.getCurrentPath().sign +
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
            if (lengthOfCurve[counter] != pathIterator.getCurrentPath().curve.n) {
                System.out.println("was: " + pathIterator.getCurrentPath().curve.n +
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
            int[] actualTagsForCurrentcurve = pathIterator.getCurrentPath().curve.tag;
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
            dpoint[][] actualPointsForCurrentcurve = pathIterator.getCurrentPath().curve.c;

            for (int currentBezierIndex = 0; currentBezierIndex < expectedPointsOfCurrentCurve.length; currentBezierIndex++) {
                double [][] expectedPointsOfCurrentBezierCurve = expectedPointsOfCurrentCurve [currentBezierIndex];
                dpoint[] actualPointsOfCurrentBezierCurve = actualPointsForCurrentcurve[currentBezierIndex];
                for (int i = 0; i < 3; i++) {
                    double[] expectedPoint = expectedPointsOfCurrentBezierCurve[i];
                    dpoint actualPoint = actualPointsOfCurrentBezierCurve[i];
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
            assertEquals("Child in Path: " + counter,(expectedChildsAndSiblings[counter][0]),(pathIterator.getCurrentPath().childlist != null));
            assertEquals("Sibling in Path: " + counter,(expectedChildsAndSiblings[counter][1]),(pathIterator.getCurrentPath().sibling != null));
            counter ++;
            pathIterator.goToNextPath();
        }
    }

    @Test
    public void testPrivInformations() {
        PathCounter pathIterator = new PathCounter(path);
        int counter = 0;
        while (pathIterator.hasNext()) {
            assertEquals("LengthOf PrivPath Nummer: " + counter,(expectedPrivInformations[counter][0]),(pathIterator.getCurrentPath().priv.len));
            assertEquals("XCoordinate PrivPath Nummer: " + counter,(expectedPrivInformations[counter][1]),(pathIterator.getCurrentPath().priv.x0));
            assertEquals("YCoordinate PrivPath Nummer: " + counter,(expectedPrivInformations[counter][2]),(pathIterator.getCurrentPath().priv.y0));
            assertEquals("M PrivPath Nummer: " + counter,(expectedPrivInformations[counter][3]),(pathIterator.getCurrentPath().priv.m));
            counter ++;
            pathIterator.goToNextPath();
        }
    }
}