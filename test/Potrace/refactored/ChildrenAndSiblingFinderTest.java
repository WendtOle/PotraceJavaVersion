package Potrace.refactored;

import Potrace.General.Path;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by andreydelany on 05.07.17.
 */
public class ChildrenAndSiblingFinderTest extends HierachyBaseTest{

    public void putPathsInLine(){
        allPaths = pathAbove;
        pathAbove.next = pathLeft;
        pathLeft.next = referencePath;
        referencePath.next = pathRight;
        pathRight.next = pathInside;
        pathInside.next = pathBelow;
    }

    @Test
    public void testThatPassesAreInRightOrder(){
        ChildrenAndSiblingFinder finder = new ChildrenAndSiblingFinder(allPaths,bitmap);

        Path result = finder.getTreeTransformedPathStructure();

        assertTrue(result == pathAbove);
        assertTrue(result.next == pathLeft);
        assertTrue(result.next.next == pathBelow);
        assertTrue(result.next.childlist == referencePath);
        assertTrue(result.next.childlist.next == pathRight);
        assertTrue(result.next.childlist.childlist == pathInside);
    }

}
