package original.potrace;

public class List {

    //Original Macro:
    /*
    #define elementInsertAtTheLastNextOfList(elt, hook) \
    MACRO_BEGIN elt->next = *hook; *hook = elt; hook=&elt->next; MACRO_END
     */

    public static Path elementInsertAtTheLastNextOfList(Path elementToAdd, Path list) {
        if (elementToAdd != null) {
            if (list != null) {
                Path current = list;
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

    public static Path listInsertAtTheLastNextOfList(Path listToAdd, Path list) {
        if (listToAdd != null) {
            if (list != null) {
                Path current = list;
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
