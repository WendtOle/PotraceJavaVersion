package General;

/**
 * Created by andreydelany on 07.07.17.
 */
public class PotraceLibrary {

    public static int POTRACE_TURNPOLICY_BLACK = 0;
    public static int  POTRACE_TURNPOLICY_WHITE = 1;
    public static int POTRACE_TURNPOLICY_LEFT = 2;
    public static int  POTRACE_TURNPOLICY_RIGHT = 3;
    public static int  POTRACE_TURNPOLICY_MINORITY = 4;
    public static int  POTRACE_TURNPOLICY_MAJORITY = 5;
    public static int  POTRACE_TURNPOLICY_RANDOM = 6;

    static int POTRACE_CURVETO = 1;
    static int POTRACE_CORNER = 2;

    public static Path potrace_trace(Param param, Bitmap bm) {
        //DecompositionInterface decomposer = new refactored.Decompose();
        DecompositionInterface decomposer = new original.Decompose();
        Path plist = decomposer.getPathList(bm, param);
        plist = Trace.process_path(plist, param);
        return plist;
    }
}
