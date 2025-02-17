package Potrace.General;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        first = List.elementInsertAtTheLastNextOfList(first, null);
        assertEquals(null, first.next);
    }

    @Test
    public void testMethod_elementInsertAtTheLastNextOfList_WithInsertOnPathAtTheEnd() {
        first = List.elementInsertAtTheLastNextOfList(second,first);
        assertEquals(second,first.next);
    }

    @Test
    public void testMethod_elementInsertAtTheLastNextOfList_WithInsertingABuchOfPathes(){
        first = List.elementInsertAtTheLastNextOfList(second,first);
        first = List.elementInsertAtTheLastNextOfList(third,first);
        first = List.elementInsertAtTheLastNextOfList(fourth,first);
        assertEquals(fourth, first.next.next.next);
    }

    @Test
    public void testMethod_elementInsertAtTheLastNextOfList_WithInsertingAListOfPathesAtTheEnd_ShouldFail(){
        Path listOfSecondToFourht = second;
        listOfSecondToFourht = List.elementInsertAtTheLastNextOfList(third,listOfSecondToFourht);
        listOfSecondToFourht = List.elementInsertAtTheLastNextOfList(fourth,listOfSecondToFourht);

        first = List.elementInsertAtTheLastNextOfList(listOfSecondToFourht,first);
        assertEquals(null, first.next.next);
    }

    @Test
    public void testMethod_listInsertAtTheLastNextOfList_WithInsertingNullAdTheEnd() {
        first = List.listInsertAtTheLastNextOfList(first, null);
        assertEquals(null, first.next);
    }

    @Test
    public void testMethod_listInsertAtTheLastNextOfList_WithInsertOnPathAtTheEnd() {
        first = List.listInsertAtTheLastNextOfList(second,first);
        assertEquals(second,first.next);
    }

    @Test
    public void testMethod_listInsertAtTheLastNextOfList_WithInsertingABuchOfPathes(){
        first = List.listInsertAtTheLastNextOfList(second,first);
        first = List.elementInsertAtTheLastNextOfList(third,first);
        first = List.listInsertAtTheLastNextOfList(fourth,first);
        assertEquals(fourth, first.next.next.next);
    }

    @Test
    public void testMethod_listInsertAtTheLastNextOfList_WithInsertingAListOfPathesAtTheEnd(){
        Path listOfSecondToFourht = second;
        listOfSecondToFourht = List.listInsertAtTheLastNextOfList(third,listOfSecondToFourht);
        listOfSecondToFourht = List.listInsertAtTheLastNextOfList(fourth,listOfSecondToFourht);

        first = List.listInsertAtTheLastNextOfList(listOfSecondToFourht,first);
        assertEquals(fourth, first.next.next.next);
    }
}