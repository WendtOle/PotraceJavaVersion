package potrace;

public class list {

    //Original Macro:
    /*
    #define list_insert_beforehook(elt, hook) \
    MACRO_BEGIN elt->next = *hook; *hook = elt; hook=&elt->next; MACRO_END
     */

    public static path unefficient_list_insert_beforehook(path elt, path list) {
        if (elt != null) {
            if (list != null) {
                path current = list;
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
        return list;
    }

    public static path putElementWhereNextIsNull(path elt, path list) {
        if (elt != null) {
            if (list != null) {
                path current = list;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = elt;
                return list;
            } else {
                return elt;
            }
        }
        return list;
    }
}
