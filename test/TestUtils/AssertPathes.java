package TestUtils;

import Potrace.General.Path;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 05.07.17.
 */
public class AssertPathes {

    public static void assertEqualPathes(Path expectedPath, Path actualPath) {
        compareSiblingChildStructure(expectedPath,actualPath);
        compareGeneralPathInformations(expectedPath,actualPath);
        comparePointsOfPath(expectedPath,actualPath);
    }

    public static void assertEqualPathes(int message, Path expectedPath, Path actualPath) {
        compareSiblingChildStructure(message,expectedPath,actualPath);
        compareGeneralPathInformations(message,expectedPath,actualPath);
        comparePointsOfPath(message,expectedPath,actualPath);
    }

    private static void compareSiblingChildStructure(int indexOfPath, Path expectedPath, Path actualPath){
        assertEquals("Childlist (" + indexOfPath+")", expectedPath.childlist != null,actualPath.childlist != null);
        assertEquals("Sibling (" + indexOfPath+")", expectedPath.childlist != null,actualPath.childlist != null);
    }

    private static void compareSiblingChildStructure(Path expectedPath, Path actualPath){
        assertEquals("Childlist", expectedPath.childlist != null,actualPath.childlist != null);
        assertEquals("Sibling", expectedPath.sibling != null,actualPath.sibling != null);
    }

    private static void compareGeneralPathInformations(int indexOfPath, Path expectedPath, Path actualPath){
        assertEquals("Area ("+indexOfPath+")",expectedPath.area,actualPath.area);
        assertEquals("Sign ("+indexOfPath+")",expectedPath.sign,actualPath.sign);
        assertEquals("Length ("+indexOfPath+")",expectedPath.priv.len,actualPath.priv.len);
    }

    private static void compareGeneralPathInformations(Path expectedPath, Path actualPath){
        assertEquals("Area",expectedPath.area,actualPath.area);
        assertEquals("Sign",expectedPath.sign,actualPath.sign);
        assertEquals("Length",expectedPath.priv.len,actualPath.priv.len);
    }

    private static void comparePointsOfPath(int indexOfPath, Path expectedPath, Path actualPath){
        for(int indexOfCurrentPoint = 0; indexOfCurrentPoint < expectedPath.priv.pt.length; indexOfCurrentPoint ++){
            Point expectedCurrentPoint = expectedPath.priv.pt[indexOfCurrentPoint];
            Point acutalCurrentPoint = actualPath.priv.pt[indexOfCurrentPoint];
            assertEquals("Point - " + indexOfCurrentPoint + " ("+indexOfPath + ")",expectedCurrentPoint,acutalCurrentPoint);
        }
    }

    private static void comparePointsOfPath(Path expectedPath, Path actualPath){
        for(int indexOfCurrentPoint = 0; indexOfCurrentPoint < expectedPath.priv.pt.length; indexOfCurrentPoint ++){
            Point expectedCurrentPoint = expectedPath.priv.pt[indexOfCurrentPoint];
            Point acutalCurrentPoint = actualPath.priv.pt[indexOfCurrentPoint];
            assertEquals("Point - " + indexOfCurrentPoint ,expectedCurrentPoint,acutalCurrentPoint);
        }
    }
}
