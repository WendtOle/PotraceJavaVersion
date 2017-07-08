package General;

import refactored.potrace.Decompose;

/**
 * Created by andreydelany on 07.07.17.
 */
public class PotraceLibrary {

    static int POTRACE_CURVETO = 1;
    static int POTRACE_CORNER = 2;

    public static Path potrace_trace(Param param, BitmapInterface bm) {
        DecompositionInterface decomposer = new Decompose();
        //DecompositionInterface decomposer = new original.potrace.Decompose();
        Path plist = decomposer.getPathList(bm, param);
        plist = Trace.process_path(plist, param);
        return plist;
    }
}
