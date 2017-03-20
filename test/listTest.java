import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by andreydelany on 07/03/2017.
 */
public class listTest {
    @Test
    public void test_list_insert_beforehook() throws Exception {
        potrace_path first = new potrace_path();
        first = list.unefficient_list_insert_beforehook(first, null);
        assertEquals(null, first.next);
        potrace_path second = new potrace_path();
        first = list.unefficient_list_insert_beforehook(second,first);
        potrace_path third = new potrace_path();
        first = list.unefficient_list_insert_beforehook(third,first);
        potrace_path fourth = new potrace_path();
        first = list.unefficient_list_insert_beforehook(fourth,first);
        assertEquals(fourth, first.next.next.next);
    }

}