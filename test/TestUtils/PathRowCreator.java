package TestUtils;

import Potrace.General.Path;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PathRowCreator {

    Path firstPath;
    Path previousePath;

    public Path getPathRow(int lengthOfRow){
        return getPathRow(lengthOfRow,0);
    }

    public Path getPathRow(int lengthOfRow, int startIndex){
        catchPathRowWithLengthZero(lengthOfRow);
        return createPathWithLegalLength(lengthOfRow, startIndex);
    }

    private Path createPathWithLegalLength(int lengthOfRow, int startIndex) {
        initializeFirstPath(startIndex);
        addRowsToRowPath(lengthOfRow, startIndex);
        return firstPath;
    }

    private void addRowsToRowPath(int lengthOfRow, int startIndex) {
        previousePath = firstPath;
        for (int i = 1; i < lengthOfRow; i++)
            addNewPathToPathRow(startIndex + i);
    }

    private void addNewPathToPathRow(int identifier) {
        Path path = new Path();
        path.area = identifier;
        previousePath.next = path;
        previousePath = path;
    }

    private void initializeFirstPath(int startIndex) {
        firstPath = new Path();
        firstPath.area = startIndex;
    }

    private static void catchPathRowWithLengthZero(int lengthOfRow) {
        if (lengthOfRow <= 0)
            throw new RuntimeException("can not create Path Row with Length Zero");
    }

    @Test
    public void testCreatingPathRowWithLengthOne() {
        assertTrue(getPathRow(1) != null);
        assertTrue(getPathRow(1).next == null);
    }

    @Test
    public void testCreatingPathRowWithLengthTwo() {
        assertTrue(getPathRow(2).next != null);
    }

    @Test
    public void testIdentifierOfPathRow(){
        assertEquals(3,getPathRow(3,3).area);
        assertEquals(4,getPathRow(3,3).next.area);
        assertEquals(5,getPathRow(3,3).next.next.area);
    }
}
