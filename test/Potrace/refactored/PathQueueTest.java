package Potrace.refactored;

import org.junit.Test;
import Potrace.General.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by andreydelany on 06.07.17.
 */
public class PathQueueTest {


    private Path getPathRow(int lengthOfRow, int startIndex){
        if (lengthOfRow <= 0){
            return null;
        } else {
            Path firstPath = new Path();
            firstPath.area = 0 + startIndex;
            Path previousPath = firstPath;
            for (int i = 1; i < lengthOfRow; i++){
                Path path = new Path();
                path.area = i + startIndex;
                previousPath.next = path;
                previousPath = path;
            }
            return firstPath;
        }
    }

    private Path getPathRow(int lengthOfRow){
        return getPathRow(lengthOfRow,0);
    }


    @Test
    public void testCreatingEmptyPathRow() {
        assertEquals(null,getPathRow(0));
    }

    @Test
    public void testQueueWithLengthOne() {
        assertTrue(getPathRow(1) != null);
        assertTrue(getPathRow(1).next == null);
    }

    @Test
    public void testQueueCreatorQueueOfLenthTwo() {
        assertTrue(getPathRow(2).next != null);
    }

    @Test
    public void testIdentifier(){
        assertEquals(3,getPathRow(3,3).area);
        assertEquals(4,getPathRow(3,3).next.area);
        assertEquals(5,getPathRow(3,3).next.next.area);
    }

    @Test
    public void testPathQueueUpdatingWhenDontFoundAnyChildrenOrSiblings() {
        PathQueueInterface pathQueue = new PathQueue(getPathRow(4,0));
        assertTrue(pathQueue.stillNeedToProcessPathes());
        Path[] result = pathQueue.getNextPathes();
        assertTrue(result[0].area == 0);
        assertTrue(result[1].area == 1);

        pathQueue.updateQueue(getPathRow(1,0));

        assertFalse(pathQueue.stillNeedToProcessPathes());
    }

    @Test
    public void testPathQueueUpdatingWithOnlyChildren() {
        PathQueueInterface pathQueue = new PathQueue(getPathRow(2,0));;
        pathQueue.getNextPathes();
        pathQueue.updateQueue(getPathRow(3,9));
        assertTrue(pathQueue.stillNeedToProcessPathes());
        Path[] result = pathQueue.getNextPathes();
        assertTrue(result[0].area == 10);
        assertTrue(result[1].area == 11);
    }

    @Test
    public void testPathQueueUpdatingWithOnlySiblings() {
        PathQueueInterface pathQueue = new PathQueue(getPathRow(2,0));;
        pathQueue.getNextPathes();
        pathQueue.updateQueue(getPathRow(4,9));
        assertTrue(pathQueue.stillNeedToProcessPathes());
        Path[] result = pathQueue.getNextPathes();
        assertTrue(result[0].area == 10);
        assertTrue(result[1].area == 11);
        assertTrue(result[1].next.area == 12);
    }

    @Test
    public void testThatPathQueueGivesYouFirstTheChildrenAndThenTheSiblings() {
        PathQueueInterface pathQueue = new PathQueue(getPathRow(2,0));;
        pathQueue.getNextPathes();
        Path path = getPathRow(3,9);
        path.childlist = getPathRow(3,20);
        pathQueue.updateQueue(path);
        assertTrue(pathQueue.stillNeedToProcessPathes());
        Path[] result = pathQueue.getNextPathes();
        assertTrue(result[0].area == 20);
        assertTrue(result[1].area == 21);
        assertTrue(result[1].next.area == 22);

        path = new Path();
        path.area = 20;
        path.childlist = getPathRow(2,21);

        pathQueue.updateQueue(path);
        assertTrue(pathQueue.stillNeedToProcessPathes());
        result = pathQueue.getNextPathes();
        assertTrue(result[0].area == 21);
        assertTrue(result[1].area == 22);

        pathQueue.updateQueue(getPathRow(1,22));
        assertTrue(pathQueue.stillNeedToProcessPathes());
        result = pathQueue.getNextPathes();
        assertTrue(result[0].area == 10);
        assertTrue(result[1].area == 11);
    }
}
