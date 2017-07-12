package Potrace.refactored;

import Potrace.General.Path;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by andreydelany on 12.07.17.
 */
public class TreeStructureTransformationTest extends HierachyBaseTest{

    public void putPathsInLine(){
        allPaths = pathAbove;
        pathAbove.next = pathLeft;
        pathLeft.next = referencePath;
        referencePath.next = pathRight;
        pathRight.next = pathInside;
        pathInside.next = pathBelow;
    }

    @Test
    public void testThatPassesAreInRightOrder() {
        TreeStructurTransformationInterface transformer = new TreeStructurTransformation(allPaths, bitmap);
        Path result = transformer.getTreeStructure();

        assertTrue(result == pathAbove);

        assertTrue(result.next == pathLeft);
        assertTrue(result.sibling == pathLeft);

        assertTrue(result.next.next == referencePath);
        assertTrue(result.next.sibling == referencePath);

        assertTrue(result.next.next.next == pathInside);
        assertTrue(result.next.next.childlist == pathInside);
        assertTrue(result.next.next.sibling == pathRight);

        assertTrue(result.next.next.next.next == pathBelow);
        assertTrue(result.next.next.next.sibling == pathBelow);
    }

}
