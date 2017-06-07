import Tools.PathCounter;
import org.junit.Before;
import org.junit.Test;
import potrace.list;
import potrace.path;

import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PathCounterTest {
    PathCounter pathCounter;
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
        pathCounter = new PathCounter(listOfPathes);
    }

    @Test
    public void testGetAmountOfPahtesInList () {
        int amount = pathCounter.getAmountOfPathes();
        assertEquals(4,amount);
    }

    @Test
    public void testGettingPathesWithIndex() {
        assertEquals(first, pathCounter.getPathAtIndex(0));
        assertEquals(second, pathCounter.getPathAtIndex(1));
        assertEquals(third, pathCounter.getPathAtIndex(2));
        assertEquals(fourth, pathCounter.getPathAtIndex(3));
    }
}
