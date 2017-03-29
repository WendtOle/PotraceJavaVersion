package potrace;

import java.awt.*;

/**
 * Created by andreydelany on 04/03/2017.
 */

    //Entwickler:
    /* the path structure is filled in with information about a given path
    as it is accumulated and passed through the different stages of the
    Potrace algorithm. Backends only need to read the fcurve and fm
    fields of this data structure, but debugging backends may read
    other fields. */

    //Ole:
    /* Ursprünglich aus curve.h */

public class potrace_privepath {
    int len;
    public Point[] pt;
    int[] lon;
    int x0,y0;

    sums[] sums;

    int m;
    int[] po;

    //Ole:
    /* In den Kommentaren sieht es sehr danach aus als ob die folgenden Drei Felder Arrays sein sollten.
    Beim Debuggen bin ich allerdings nicht darauf gestoßen,
    dass jemals mehrere privecurves auf eines dieser Felder abgelegt werden sollen */

    //TODO: Eventuell zu Arrays machen

    privcurve curve;    /* curve[m]: array of curve elements */
    privcurve ocurve;   /* ocurve[om]: array of curve elements */
    privcurve fcurve;   /* final curve: this points to either curve or
		                ocurve. Do not free this separately. */

}
