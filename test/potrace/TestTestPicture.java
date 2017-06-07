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
    PathCounter pathIterator;
    int amountOfPathesInList;
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
        path path  = potraceLib.potrace_trace(new param(),bitmap);
        pathIterator = new PathCounter(path);
        amountOfPathesInList = pathIterator.getAmountOfPathes();
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
        assertEquals(expectedAmountOfPahtes,amountOfPathesInList);
    }

    @Test
    public void testAreaOfPaths() {
        for (int i = 0; i < amountOfPathesInList ; i ++) {
            assertArea(i);
        }
        assertTrue(true);
    }

    private void assertArea(int index) {
        int currentExpectedValue = areasOfPathes[index];
        int currentActualValue = pathIterator.getPathAtIndex(index).area;
        assertInGeneral(currentExpectedValue,currentActualValue,index);

    }

    private void assertInGeneral(int expected, int actual, int position){
        assertInGeneral(expected,actual,position+"");
    }

    private void assertInGeneral(int expected, int actual, String position) {
        String errorMessage =  "was: " + actual +
                " but should be: " + expected + " at index: " + position;
        assertEquals(errorMessage,expected,actual);
    }

    @Test
    public void testSignsOfPathes() {
        for (int i = 0; i < amountOfPathesInList ; i ++) {
            assertSign(i);
        }
        assertTrue(true);
    }

    private void assertSign(int index) {
        int currentExpectedValue = signsOfPathes[index];
        int currentActualValue = pathIterator.getPathAtIndex(index).sign;
        assertInGeneral(currentExpectedValue,currentActualValue,index);
    }

    @Test
    public void testLenghtOfCurve() {
        for (int i = 0; i < amountOfPathesInList ; i ++) {
            assertLength(i);
        }
        assertTrue(true);
    }

    public void assertLength(int index) {
        int currentExpectedValue = lengthOfCurve[index];
        int currentActualValue = pathIterator.getPathAtIndex(index).curve.n;
        assertInGeneral(currentExpectedValue,currentActualValue,index);
    }

    @Test
    public void testTagsOfCurve() {
        for (int i = 0; i < amountOfPathesInList ; i ++) {
            int[] expectedTagsForCurrentCurve = expectedTagsOfCurve[i];
            int[] actualTagsForCurrentcurve = pathIterator.getPathAtIndex(i).curve.tag;
            for (int j = 0; i < expectedTagsForCurrentCurve.length; i++) {
                int expectedTagOfCurrentCurve = expectedTagsForCurrentCurve[j];
                int actualTagOfCurrentCurve = actualTagsForCurrentcurve[j];
                assertInGeneral(expectedTagOfCurrentCurve,actualTagOfCurrentCurve,"[" + i + "]["+j+"]");
            }
        }
        assertTrue(true);
    }

    @Test
    public void testPointsOfCurves() {
        for (int counter = 0; counter < amountOfPathesInList; counter ++) {
            double[][][] expectedPointsOfCurrentCurve = expectedPointsOfCurve[counter];
            dpoint[][] actualPointsForCurrentcurve = pathIterator.getPathAtIndex(counter).curve.c;

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
        }
        assertTrue(true);
    }

    @Test
    public void testChildsAndSiblings() {
        for(int i = 0; i < amountOfPathesInList; i++) {
            assertEquals("Child in Path: " + i,(expectedChildsAndSiblings[i][0]),(pathIterator.getPathAtIndex(i).childlist != null));
            assertEquals("Sibling in Path: " + i,(expectedChildsAndSiblings[i][1]),(pathIterator.getPathAtIndex(i).sibling != null));
        }
    }

    @Test
    public void testPrivInformations() {
        for(int i = 0; i < amountOfPathesInList; i++) {
            assertEquals("LengthOf PrivPath Nummer: " + i,(expectedPrivInformations[i][0]),(pathIterator.getPathAtIndex(i).priv.len));
            assertEquals("XCoordinate PrivPath Nummer: " + i,(expectedPrivInformations[i][1]),(pathIterator.getPathAtIndex(i).priv.x0));
            assertEquals("YCoordinate PrivPath Nummer: " + i,(expectedPrivInformations[i][2]),(pathIterator.getPathAtIndex(i).priv.y0));
            assertEquals("M PrivPath Nummer: " + i,(expectedPrivInformations[i][3]),(pathIterator.getPathAtIndex(i).priv.m));
        }
    }
}