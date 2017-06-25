package refactored.potrace;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 07/03/2017.
 */
public class listTest {
    Path first, second, third, fourth;

    @Before
    public void initializePathes() {
        first = new Path();
        second = new Path();
        third = new Path();
        fourth = new Path();
    }

    @Test
    public void testMethod_elementInsertAtTheLastNextOfList_WithInsertingNullAdTheEnd() {
        first = Path.insertElementAtTheEndOfList(first, null);
        assertEquals(null, first.next);
    }

    @Test
    public void testMethod_elementInsertAtTheLastNextOfList_WithInsertOnPathAtTheEnd() {
        first = Path.insertElementAtTheEndOfList(second,first);
        assertEquals(second,first.next);
    }

    @Test
    public void testMethod_elementInsertAtTheLastNextOfList_WithInsertingABuchOfPathes(){
        first = Path.insertElementAtTheEndOfList(second,first);
        first = Path.insertElementAtTheEndOfList(third,first);
        first = Path.insertElementAtTheEndOfList(fourth,first);
        assertEquals(fourth, first.next.next.next);
    }

    @Test
    public void testMethod_elementInsertAtTheLastNextOfList_WithInsertingAListOfPathesAtTheEnd_ShouldFail(){
        Path listOfSecondToFourht = second;
        listOfSecondToFourht = Path.insertElementAtTheEndOfList(third,listOfSecondToFourht);
        listOfSecondToFourht = Path.insertElementAtTheEndOfList(fourth,listOfSecondToFourht);

        first = Path.insertElementAtTheEndOfList(listOfSecondToFourht,first);
        assertEquals(null, first.next.next);
    }

    @Test
    public void testMethod_listInsertAtTheLastNextOfList_WithInsertingNullAdTheEnd() {
        first = Path.insertListAtTheEndOfList(first, null);
        assertEquals(null, first.next);
    }

    @Test
    public void testMethod_listInsertAtTheLastNextOfList_WithInsertOnPathAtTheEnd() {
        first = Path.insertListAtTheEndOfList(second,first);
        assertEquals(second,first.next);
    }

    @Test
    public void testMethod_listInsertAtTheLastNextOfList_WithInsertingABuchOfPathes(){
        first = Path.insertListAtTheEndOfList(second,first);
        first = Path.insertElementAtTheEndOfList(third,first);
        first = Path.insertListAtTheEndOfList(fourth,first);
        assertEquals(fourth, first.next.next.next);
    }

    @Test
    public void testMethod_listInsertAtTheLastNextOfList_WithInsertingAListOfPathesAtTheEnd(){
        Path listOfSecondToFourht = second;
        listOfSecondToFourht = Path.insertListAtTheEndOfList(third,listOfSecondToFourht);
        listOfSecondToFourht = Path.insertListAtTheEndOfList(fourth,listOfSecondToFourht);

        first = Path.insertListAtTheEndOfList(listOfSecondToFourht,first);
        assertEquals(fourth, first.next.next.next);
    }


}