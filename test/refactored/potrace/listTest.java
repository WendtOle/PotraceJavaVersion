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
        first = List.insertElementAtTheEndOfList(first, null);
        assertEquals(null, first.next);
    }

    @Test
    public void testMethod_elementInsertAtTheLastNextOfList_WithInsertOnPathAtTheEnd() {
        first = List.insertElementAtTheEndOfList(second,first);
        assertEquals(second,first.next);
    }

    @Test
    public void testMethod_elementInsertAtTheLastNextOfList_WithInsertingABuchOfPathes(){
        first = List.insertElementAtTheEndOfList(second,first);
        first = List.insertElementAtTheEndOfList(third,first);
        first = List.insertElementAtTheEndOfList(fourth,first);
        assertEquals(fourth, first.next.next.next);
    }

    @Test
    public void testMethod_elementInsertAtTheLastNextOfList_WithInsertingAListOfPathesAtTheEnd_ShouldFail(){
        Path listOfSecondToFourht = second;
        listOfSecondToFourht = List.insertElementAtTheEndOfList(third,listOfSecondToFourht);
        listOfSecondToFourht = List.insertElementAtTheEndOfList(fourth,listOfSecondToFourht);

        first = List.insertElementAtTheEndOfList(listOfSecondToFourht,first);
        assertEquals(null, first.next.next);
    }

    @Test
    public void testMethod_listInsertAtTheLastNextOfList_WithInsertingNullAdTheEnd() {
        first = List.insertListAtTheEndOfList(first, null);
        assertEquals(null, first.next);
    }

    @Test
    public void testMethod_listInsertAtTheLastNextOfList_WithInsertOnPathAtTheEnd() {
        first = List.insertListAtTheEndOfList(second,first);
        assertEquals(second,first.next);
    }

    @Test
    public void testMethod_listInsertAtTheLastNextOfList_WithInsertingABuchOfPathes(){
        first = List.insertListAtTheEndOfList(second,first);
        first = List.insertElementAtTheEndOfList(third,first);
        first = List.insertListAtTheEndOfList(fourth,first);
        assertEquals(fourth, first.next.next.next);
    }

    @Test
    public void testMethod_listInsertAtTheLastNextOfList_WithInsertingAListOfPathesAtTheEnd(){
        Path listOfSecondToFourht = second;
        listOfSecondToFourht = List.insertListAtTheEndOfList(third,listOfSecondToFourht);
        listOfSecondToFourht = List.insertListAtTheEndOfList(fourth,listOfSecondToFourht);

        first = List.insertListAtTheEndOfList(listOfSecondToFourht,first);
        assertEquals(fourth, first.next.next.next);
    }


}