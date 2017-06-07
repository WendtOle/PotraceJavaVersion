package potrace;

public class list {

    //Original Macro:
    /*
    #define elementInsertAtTheLastNextOfList(elt, hook) \
    MACRO_BEGIN elt->next = *hook; *hook = elt; hook=&elt->next; MACRO_END
     */

    public static path elementInsertAtTheLastNextOfList(path elementToAdd, path list) {
        if (elementToAdd != null) {
            if (list != null) {
                path current = list;
                while (current.next != null) {
                    current = current.next;
                }
                elementToAdd.next = null;
                current.next = elementToAdd;
                return list;
            } else {
                elementToAdd.next = null;
                return elementToAdd;
            }
        }
        return list;
    }

    public static path listInsertAtTheLastNextOfList(path listToAdd, path list) {
        if (listToAdd != null) {
            if (list != null) {
                path current = list;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = listToAdd;
                return list;
            } else {
                return listToAdd;
            }
        }
        return list;
    }
}
