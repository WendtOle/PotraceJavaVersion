/**
 * Created by andreydelany on 07/03/2017.
 */
public class list {

    static potrace_path list_insert_beforehook(potrace_path elt, potrace_path hook) {
        elt.next = hook;
        return elt;
    }




}
