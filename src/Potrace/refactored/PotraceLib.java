package Potrace.refactored;

public class PotraceLib {

    static int POTRACE_CURVETO = 1;
    static int POTRACE_CORNER = 2;
/*
    public Path potrace_trace(Param param, BitmapManipulator bm) {
        Decompose decomposer = new Decompose(bm, param);
        Path plist = decomposer.getPathList();
        plist = Potrace.General.Trace.process_path(plist, param);
        return plist;
    }
    */
}