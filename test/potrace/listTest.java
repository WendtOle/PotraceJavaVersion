package potrace;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by andreydelany on 07/03/2017.
 */
public class listTest {
    path first, second, third, fourth;

    @Before
    public void initializePathes() {
        first = new path();
        second = new path();
        third = new path();
        fourth = new path();
    }

    @Test
    public void testMethod_elementInsertAtTheLastNextOfList_WithInsertingNullAdTheEnd() {
        first = list.elementInsertAtTheLastNextOfList(first, null);
        assertEquals(null, first.next);
    }

    @Test
    public void testMethod_elementInsertAtTheLastNextOfList_WithInsertOnPathAtTheEnd() {
        first = list.elementInsertAtTheLastNextOfList(second,first);
        assertEquals(second,first.next);
    }

    @Test
    public void testMethod_elementInsertAtTheLastNextOfList_WithInsertingABuchOfPathes(){
        first = list.elementInsertAtTheLastNextOfList(second,first);
        first = list.elementInsertAtTheLastNextOfList(third,first);
        first = list.elementInsertAtTheLastNextOfList(fourth,first);
        assertEquals(fourth, first.next.next.next);
    }

    @Test
    public void testMethod_elementInsertAtTheLastNextOfList_WithInsertingAListOfPathesAtTheEnd_ShouldFail(){
        path listOfSecondToFourht = second;
        listOfSecondToFourht = list.elementInsertAtTheLastNextOfList(third,listOfSecondToFourht);
        listOfSecondToFourht = list.elementInsertAtTheLastNextOfList(fourth,listOfSecondToFourht);

        first = list.elementInsertAtTheLastNextOfList(listOfSecondToFourht,first);
        assertEquals(null, first.next.next);
    }

    @Test
    public void testMethod_listInsertAtTheLastNextOfList_WithInsertingNullAdTheEnd() {
        first = list.listInsertAtTheLastNextOfList(first, null);
        assertEquals(null, first.next);
    }

    @Test
    public void testMethod_listInsertAtTheLastNextOfList_WithInsertOnPathAtTheEnd() {
        first = list.listInsertAtTheLastNextOfList(second,first);
        assertEquals(second,first.next);
    }

    @Test
    public void testMethod_listInsertAtTheLastNextOfList_WithInsertingABuchOfPathes(){
        first = list.listInsertAtTheLastNextOfList(second,first);
        first = list.elementInsertAtTheLastNextOfList(third,first);
        first = list.listInsertAtTheLastNextOfList(fourth,first);
        assertEquals(fourth, first.next.next.next);
    }

    @Test
    public void testMethod_listInsertAtTheLastNextOfList_WithInsertingAListOfPathesAtTheEnd(){
        path listOfSecondToFourht = second;
        listOfSecondToFourht = list.listInsertAtTheLastNextOfList(third,listOfSecondToFourht);
        listOfSecondToFourht = list.listInsertAtTheLastNextOfList(fourth,listOfSecondToFourht);

        first = list.listInsertAtTheLastNextOfList(listOfSecondToFourht,first);
        assertEquals(fourth, first.next.next.next);
    }


}