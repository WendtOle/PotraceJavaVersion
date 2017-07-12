package Potrace.refactored;

import Potrace.General.Path;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by andreydelany on 12.07.17.
 */
public class NextComponentReconstructionTest {
    Path first;
    Path childOfFirst;
    Path second;
    Path third;
    Path fourth;

    @Before
    public void initializePathsNew(){
        first = new Path();
        childOfFirst = new Path();
        second = new Path();
        third = new Path();
        fourth = new Path();
    }

    @Test
    public void testThatItTakesChildrenForSiblings(){
        first.childlist = childOfFirst;
        first.sibling = second;

        NextComponentReconstruction reconstruction = new NextComponentReconstruction(first);
        Path result = reconstruction.getPathWithReconstructedNext();

        assertTrue(result == first);
        assertTrue(result.next == childOfFirst);
        assertTrue(result.next.next == second);
    }

    @Test
    public void testThatItTakesSiblingsAndChildrenOfChildrenBeforeOwnSiblings(){
        childOfFirst.childlist = second;
        childOfFirst.sibling = third;
        first.childlist = childOfFirst;
        first.sibling = fourth;

        NextComponentReconstruction reconstruction = new NextComponentReconstruction(first);
        Path result = reconstruction.getPathWithReconstructedNext();

        assertTrue(result == first);
        assertTrue(result.next == childOfFirst);
        assertTrue(result.next.next == third);
        assertTrue(result.next.next.next == fourth);
        assertTrue(result.next.next.next.next == second);
    }
}
