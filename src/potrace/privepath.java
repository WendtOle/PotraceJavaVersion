package potrace;

import java.awt.*;

    /* the path structure is filled in with information about a given path
    as it is accumulated and passed through the different stages of the
    Potrace algorithm. Backends only need to read the fcurve and fm
    fields of this data structure, but debugging backends may read
    other fields. */

public class privepath {
    public int len;
    public Point[] pt;
    int[] lon;
    int x0,y0;

    sums[] sums;

    int m;
    int[] po;

    privcurve curve;    /* curve[m]: array of curve elements */
    privcurve ocurve;   /* ocurve[om]: array of curve elements */
    privcurve fcurve;   /* final curve: this points to either curve or
		                ocurve. Do not free this separately. */
}
