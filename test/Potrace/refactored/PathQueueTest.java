package Potrace.refactored;

import Potrace.General.Path;
import TestUtils.PathRowCreator;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by andreydelany on 06.07.17.
 */
public class PathQueueTest {

    PathRowCreator pathRowCreator = new PathRowCreator();
    PathQueueInterface pathQueue;

    @Test
    public void testPathQueueUpdatingWhenDontFoundAnyChildrenOrSiblings() {
        pathQueue = new PathQueue(pathRowCreator.getPathRow(4));
        pathQueue.getNextPathes();
        pathQueue.updateQueue(pathRowCreator.getPathRow(1));

        assertFalse(pathQueue.stillNeedToProcessPathes());
    }

    @Test
    public void testPathQueueUpdatingWithOnlyChildren() {
        pathQueue = new PathQueue(pathRowCreator.getPathRow(2));;
        updateQueueWithAmountOfPathes(3);

        assertThatPathQueueGivesYouTheChildren();
    }

    private void assertThatPathQueueGivesYouTheChildren() {
        assertTrue(pathQueue.stillNeedToProcessPathes());
        Path[] result = pathQueue.getNextPathes();
        assertTrue(result[0].area == 10);
        assertTrue(result[1].area == 11);
    }

    @Test
    public void testPathQueueUpdatingWithOnlySiblings() {
        pathQueue = new PathQueue(pathRowCreator.getPathRow(2));;
        updateQueueWithAmountOfPathes(4);

        assertThatPathGivesYouTheSiblings();
    }

    private void assertThatPathGivesYouTheSiblings() {
        assertTrue(pathQueue.stillNeedToProcessPathes());
        Path[] result = pathQueue.getNextPathes();
        assertTrue(result[0].area == 10);
        assertTrue(result[1].area == 11);
        assertTrue(result[1].next.area == 12);
    }

    private void updateQueueWithAmountOfPathes(int amountOfPathes) {
        pathQueue.getNextPathes();
        pathQueue.updateQueue(pathRowCreator.getPathRow(amountOfPathes,9));
    }

    @Test
    public void testThatPathQueueGivesYouFirstTheChildrenAndThenTheSiblings() {
        pathQueue = new PathQueue(pathRowCreator.getPathRow(2));;

        updateQueueWithChildrenAndSiblings();
        assertThatPathQueueGivesYouFirstTheChildren();

        assumeThatAllChildrenWhereProcessedAndUpdateQueue();
        assertThatPathQueueGivesYouTheSiblings();
    }

    private void updateQueueWithChildrenAndSiblings() {
        pathQueue.getNextPathes();
        Path siblings = pathRowCreator.getPathRow(3,9);
        Path children = pathRowCreator.getPathRow(2,20);
        siblings.childlist = children;
        pathQueue.updateQueue(siblings);
    }

    private void assertThatPathQueueGivesYouFirstTheChildren() {
        assertTrue(pathQueue.stillNeedToProcessPathes());
        Path[] result = pathQueue.getNextPathes();
        assertTrue(result[0].area == 20);
        assertTrue(result[1].area == 21);
    }

    private void assumeThatAllChildrenWhereProcessedAndUpdateQueue() {
        pathQueue.updateQueue(pathRowCreator.getPathRow(1,21));
    }

    private void assertThatPathQueueGivesYouTheSiblings() {
        assertTrue(pathQueue.stillNeedToProcessPathes());
        Path[] result = pathQueue.getNextPathes();
        assertTrue(result[0].area == 10);
        assertTrue(result[1].area == 11);
    }
}