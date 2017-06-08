package tools;

import Tools.PathIterator;
import org.junit.Before;
import org.junit.Test;
import potrace.list;
import potrace.path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PathIteratorTest {
    PathIterator pathIterator;
    path first = new path();
    path second = new path();
    path third = new path();
    path fourth = new path();

    @Before
    public void initializePathes() {
        path listOfPathes;
        listOfPathes = list.elementInsertAtTheLastNextOfList(second, first);
        listOfPathes = list.elementInsertAtTheLastNextOfList(third, listOfPathes);
        listOfPathes = list.elementInsertAtTheLastNextOfList(fourth, listOfPathes);
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
