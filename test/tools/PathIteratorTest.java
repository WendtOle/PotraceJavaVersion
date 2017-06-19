package tools;

import Tools.PathIterator;
import org.junit.Before;
import org.junit.Test;
import potraceOriginal.List;
import potraceOriginal.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PathIteratorTest {
    PathIterator pathIterator;
    Path first = new Path();
    Path second = new Path();
    Path third = new Path();
    Path fourth = new Path();

    @Before
    public void initializePathes() {
        Path listOfPathes;
        listOfPathes = List.elementInsertAtTheLastNextOfList(second, first);
        listOfPathes = List.elementInsertAtTheLastNextOfList(third, listOfPathes);
        listOfPathes = List.elementInsertAtTheLastNextOfList(fourth, listOfPathes);
        pathIterator = new PathIterator(listOfPathes);
    }

    @Test
    public void testGetAmountOfPahtesInList () {
        int amount = pathIterator.getAmountOfPathes();
        assertEquals(4,amount);
    }

    @Test
    public void testGettingPathesWithIndex() {
        assertEquals(first, pathIterator.getPathAtIndex(0));
        assertEquals(second, pathIterator.getPathAtIndex(1));
        assertEquals(third, pathIterator.getPathAtIndex(2));
        assertEquals(fourth, pathIterator.getPathAtIndex(3));
    }
}
