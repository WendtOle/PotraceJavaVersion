package refactored.potrace;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 05.07.17.
 */
public class AssertPathes {

    public static void assertEqualPathes(int message, AdditionalCode.Path expectedPath, AdditionalCode.Path actualPath) {
        compareSiblingChildStructure(message,expectedPath,actualPath);
        compareGeneralPathInformations(message,expectedPath,actualPath);
        comparePointsOfPath(message,expectedPath,actualPath);
    }

    private static void compareSiblingChildStructure(int indexOfPath, AdditionalCode.Path expectedPath, AdditionalCode.Path actualPath){
        assertEquals("Childlist (" + indexOfPath+")", expectedPath.hasChild,actualPath.hasChild);
        assertEquals("Sibling (" + indexOfPath+")", expectedPath.hasSibling,actualPath.hasSibling);
    }

    private static void compareGeneralPathInformations(int indexOfPath, AdditionalCode.Path expectedPath, AdditionalCode.Path actualPath){
        assertEquals("Area ("+indexOfPath+")",expectedPath.area,actualPath.area);
        assertEquals("Sign ("+indexOfPath+")",expectedPath.sign,actualPath.sign);
        assertEquals("Length ("+indexOfPath+")",expectedPath.length,actualPath.length);
    }

    private static void comparePointsOfPath(int indexOfPath, AdditionalCode.Path expectedPath, AdditionalCode.Path actualPath){
        for(int indexOfCurrentPoint = 0; indexOfCurrentPoint < expectedPath.pt.length; indexOfCurrentPoint ++){
            Point expectedCurrentPoint = expectedPath.pt[indexOfCurrentPoint];
            Point acutalCurrentPoint = actualPath.pt[indexOfCurrentPoint];
            assertEquals("Point - " + indexOfCurrentPoint + " ("+indexOfPath + ")",expectedCurrentPoint,acutalCurrentPoint);
        }
    }
}
