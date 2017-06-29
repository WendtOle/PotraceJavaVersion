package refactored.potrace;

public class PotraceLib {

    static int POTRACE_CURVETO = 1;
    static int POTRACE_CORNER = 2;

    public static Path potrace_trace(Param param, Bitmap bm) {
        Decompose decomposer = new Decompose(bm, param);
        Path plist = decomposer.getPathList();
        plist = Trace.process_path(plist, param);
        return plist;
    }
}