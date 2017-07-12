package Potrace.refactored;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by andreydelany on 12.07.17.
 */
public class DetermineHierachyTest extends HierachyBaseTest {

    public void putPathsInLine(){
        allPaths = pathAbove;
        pathAbove.next = pathLeft;
        pathLeft.next = pathRight;
        pathRight.next = pathInside;
        pathInside.next = pathBelow;
    }

    @Test
    public void testThatPassesAreInRightOrder(){
        DetermineHierachy determa = new DetermineHierachy(bitmap);
        determa.getHierarchicallyOrderedPathes(referencePath,allPaths);
        assertTrue(referencePath.next == pathInside);
        assertTrue(referencePath.next.next == pathBelow);

        assertTrue(referencePath.childlist == pathAbove);
        assertTrue(referencePath.childlist.next == pathLeft);
        assertTrue(referencePath.childlist.next.next == pathRight);
    }
}

