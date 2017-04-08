package potrace;

/**
 * Created by andreydelany on 07/03/2017.
 */
public class list {

    //Ole:
    /* Ich habe die Implementation dieser Funktion geändert:
    die original methode hat mit hooks gearbeitet, die immer auf das letzte Element der Liste gezeigt haben,
    dass war leicht zu realisieren mit pointern, aber eher schwer zu machen in java.
    Diese Implementierung ist langsamer und basiert darauf, dass man sich einfach seinen Weg durch die liste bahnt bis,
    man am Ende angekommen ist. */


    //Original Macro:
    /*
    #define list_insert_beforehook(elt, hook) \
    MACRO_BEGIN elt->next = *hook; *hook = elt; hook=&elt->next; MACRO_END
     */

    public static potrace_path unefficient_list_insert_beforehook(potrace_path elt, potrace_path list) {
        if (elt != null) {
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
        return list;
    }
}
