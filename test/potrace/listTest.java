package potrace;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by andreydelany on 07/03/2017.
 */
public class listTest {
    @Test
    public void test_list_insert_beforehook() throws Exception {
        path first = new path();
        first = list.unefficient_list_insert_beforehook(first, null);
        Assert.assertEquals(null, first.next);
        path second = new path();
        first = list.unefficient_list_insert_beforehook(second,first);
        path third = new path();
        first = list.unefficient_list_insert_beforehook(third,first);
        path fourth = new path();
        first = list.unefficient_list_insert_beforehook(fourth,first);
        Assert.assertEquals(fourth, first.next.next.next);
    }

}