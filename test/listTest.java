import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by andreydelany on 07/03/2017.
 */
public class listTest {
    @Test
    public void list_insert_beforehook() throws Exception {
        potrace_path first = new potrace_path();
        potrace_path second = new potrace_path();
        potrace_path third = new potrace_path();
        potrace_path fourth = new potrace_path();
        first.next = second;
        second.next = third;

        potrace_path result = list.list_insert_beforehook(fourth,first);
    }

}