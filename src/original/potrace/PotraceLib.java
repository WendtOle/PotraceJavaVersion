package original.potrace;

public class PotraceLib {

    static int POTRACE_TURNPOLICY_BLACK = 0;
    static int  POTRACE_TURNPOLICY_WHITE = 1;
    static int POTRACE_TURNPOLICY_LEFT = 2;
    static int  POTRACE_TURNPOLICY_RIGHT = 3;
    static int  POTRACE_TURNPOLICY_MINORITY = 4;
    static int  POTRACE_TURNPOLICY_MAJORITY = 5;
    static int  POTRACE_TURNPOLICY_RANDOM = 6;

    static int POTRACE_CURVETO = 1;
    static int POTRACE_CORNER = 2;

    /* On success, returns a Potrace state st with st->status ==
    POTRACE_STATUS_OK. On failure, returns NULL if no Potrace state
    could be created (with errno set), or returns an incomplete Potrace
    state (with st->status == POTRACE_STATUS_INCOMPLETE, and with errno
    set). Complete or incomplete Potrace state can be freed with
    potrace_state_free(). */

    public static Path potrace_trace(Param param, Bitmap bm) {
        Path plist = Decompose.bm_to_pathlist(bm, param);
        plist = Trace.process_path(plist, param);
        return plist;
    }
}
