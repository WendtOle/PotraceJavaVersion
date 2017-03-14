/**
 * Created by andreydelany on 04/03/2017.
 */
public class PotraceLib {

    static int POTRACE_STATUS_OK = 0;
    static int POTRACE_STATUS_INCOMPLETE = 1;

    static potrace_path potrace_trace(potrace_param param, potrace_bitmap bm) {

        potrace_path plist = decompose.bm_to_pathlist(bm, param);

        plist = trace.process_path(plist, param);

        return plist;

    }
}
