package potrace;

/**
 * Created by andreydelany on 04/03/2017.
 */

    //Ole:
    /* Ursprünglich ist extram viel in PotraceLib passiert.
    Parameter, Bitmap, Dpoint, Curve, Path, state und als einzige jetzt noch wichtige Methode die Methode:
    potrace_trace, welche das Bild entgegennimmt, und die parameter nach dem das bild analysiert werden soll,
    und dann einen Pfad zurückgibt.
    Ursprünglich gab es noch sehr viel mit einen Status, der genau Festhalt woman im Process ist.
    Allerdings gab es massive Probleme mit den callbacks,
    deswegen habe ich mich gegen diesen Status entschieden und alle seine Sachen, die auf ihn folgen und habe ihn weggelassen.
    Außerdem denke ich auch nicht, dass es so wichtig ist.
     */

public class PotraceLib {

    //Entwickler:
    /* On success, returns a Potrace state st with st->status ==
    POTRACE_STATUS_OK. On failure, returns NULL if no Potrace state
    could be created (with errno set), or returns an incomplete Potrace
    state (with st->status == POTRACE_STATUS_INCOMPLETE, and with errno
    set). Complete or incomplete Potrace state can be freed with
    potrace_state_free(). */

    public static potrace_path potrace_trace(potrace_param param, potrace_bitmap bm) {
        potrace_path plist = decompose.bm_to_pathlist(bm, param);
        plist = trace.process_path(plist, param);
        return plist;
    }
}
