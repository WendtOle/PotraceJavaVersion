/**
 * Created by andreydelany on 07/03/2017.
 */
public class list {
    static potrace_path unefficient_list_insert_beforehook(potrace_path elt, potrace_path list) {
        if (list != null) {
            potrace_path current = list;
            while (current.next != null) {
                current = current.next;
            }
            elt.next = null;
            current.next = elt;
            return list;
        } else {
            elt.next = null;
            return elt;
        }
    }
}
