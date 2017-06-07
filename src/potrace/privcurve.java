package potrace;

public class privcurve {
    int n;                                          //number of segments
    int[] tag;                                      //tag[n]: POTRACE_CORNER or POTRACE_CURVETO
    dpoint[][] c;                                   //c[n][i]: control points.
                                                    //[n][0] is unused for tag[n]=POTRACE_CORNER
                                                    //the remainder of this structure is special to potrace.privcurve, and is
                                                    //used in EPS debug output and special EPS "short coding". These
                                                    //fields are valid only if "alphacurve" is set.
    int alphacurve;                                 //have the following fields been initialized?
    dpoint[] vertex;                                //for POTRACE_CORNER, this equals c[1]
    double[] alpha;                                 //only for POTRACE_CURVETO
    double[] alpha0;                                //"uncropped" alpha parameter - for debug output only
    double[] beta;

    public privcurve(int n) {
        this.n = n;

        tag = new int[n];

        vertex = new dpoint[n];
        for (int i = 0; i < vertex.length; i++) {
            vertex[i] = new dpoint();
        }

        c = new dpoint[n][3];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < 3; j ++)
                c[i][j] = new dpoint();

        alpha = new double[n];
        alpha0 = new double[n];
        beta = new double[n];

    }
}