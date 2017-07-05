package refactored.potrace;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 05.07.17.
 */
public class AssertPathes {

    public static void assertEqualPathes(AdditionalCode.Path expectedPath, Path actualPath) {
        compareSiblingChildStructure(expectedPath,actualPath);
        compareGeneralPathInformations(expectedPath,actualPath);
        comparePointsOfPath(expectedPath,actualPath);
    }

    public static void assertEqualPathes(int message, AdditionalCode.Path expectedPath, AdditionalCode.Path actualPath) {
        compareSiblingChildStructure(message,expectedPath,actualPath);
        compareGeneralPathInformations(message,expectedPath,actualPath);
        comparePointsOfPath(message,expectedPath,actualPath);
    }

    private static void compareSiblingChildStructure(int indexOfPath, AdditionalCode.Path expectedPath, AdditionalCode.Path actualPath){
        assertEquals("Childlist (" + indexOfPath+")", expectedPath.hasChild,actualPath.hasChild);
        assertEquals("Sibling (" + indexOfPath+")", expectedPath.hasSibling,actualPath.hasSibling);
    }

    private static void compareSiblingChildStructure(AdditionalCode.Path expectedPath, Path actualPath){
        assertEquals("Childlist", expectedPath.hasChild,actualPath.childlist != null);
        assertEquals("Sibling", expectedPath.hasSibling,actualPath.sibling != null);
    }

    private static void compareGeneralPathInformations(int indexOfPath, AdditionalCode.Path expectedPath, AdditionalCode.Path actualPath){
        assertEquals("Area ("+indexOfPath+")",expectedPath.area,actualPath.area);
        assertEquals("Sign ("+indexOfPath+")",expectedPath.sign,actualPath.sign);
        assertEquals("Length ("+indexOfPath+")",expectedPath.length,actualPath.length);
    }

    private static void compareGeneralPathInformations(AdditionalCode.Path expectedPath, Path actualPath){
        assertEquals("Area",expectedPath.area,actualPath.area);
        assertEquals("Sign",expectedPath.sign,actualPath.sign);
        assertEquals("Length",expectedPath.length,actualPath.priv.len);
    }

    private static void comparePointsOfPath(int indexOfPath, AdditionalCode.Path expectedPath, AdditionalCode.Path actualPath){
        for(int indexOfCurrentPoint = 0; indexOfCurrentPoint < expectedPath.pt.length; indexOfCurrentPoint ++){
            Point expectedCurrentPoint = expectedPath.pt[indexOfCurrentPoint];
            Point acutalCurrentPoint = actualPath.pt[indexOfCurrentPoint];
            assertEquals("Point - " + indexOfCurrentPoint + " ("+indexOfPath + ")",expectedCurrentPoint,acutalCurrentPoint);
        }
    }

    private static void comparePointsOfPath(AdditionalCode.Path expectedPath, Path actualPath){
        for(int indexOfCurrentPoint = 0; indexOfCurrentPoint < expectedPath.pt.length; indexOfCurrentPoint ++){
            Point expectedCurrentPoint = expectedPath.pt[indexOfCurrentPoint];
            Point acutalCurrentPoint = actualPath.priv.pt[indexOfCurrentPoint];
            assertEquals("Point - " + indexOfCurrentPoint ,expectedCurrentPoint,acutalCurrentPoint);
        }
    }
}
