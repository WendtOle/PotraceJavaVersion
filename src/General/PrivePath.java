package General;

import java.awt.*;

    /* the Path structure is filled in with information about a given Path
    as it is accumulated and passed through the different stages of the
    Potrace algorithm. Backends only need to read the fcurve and fm
    fields of this data structure, but debugging backends may read
    other fields. */

public class PrivePath {
    public int len;
    public Point[] pt;
    int[] lon;
    public int x0,y0;

    Sums[] sums;

    int m;
    int[] po;

    PrivCurve curve;    /* Curve[m]: array of Curve elements */
    PrivCurve ocurve;   /* ocurve[om]: array of Curve elements */
    PrivCurve fcurve;   /* final Curve: this pointsOfPath to either Curve or
		                ocurve. Do not free this separately. */
}
