/**
 * Created by andreydelany on 04/03/2017.
 */
public class PotraceLib {

    static int POTRACE_STATUS_OK = 0;
    static int POTRACE_STATUS_INCOMPLETE = 1;

    static potrace_state potrace_trace(potrace_param param, potrace_bitmap bm) {
        potrace_state st = new potrace_state();             //TODO: Potential Array

        /* process the image */
        potrace_path plist = decompose.bm_to_pathlist(bm, param);

        st.status = POTRACE_STATUS_OK;
        //st.plist = plist;

        return st;

    }
}
