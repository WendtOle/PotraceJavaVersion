import java.awt.*;

/**
 * Created by andreydelany on 04/03/2017.
 */
public class potrace_privepath {
    int len;
    Point[] pt;       //TODO: Potential Array
    int[] lon;
    int x0,y0;

    sums[] sums;

    int m;
    int[] po;

    privcurve curve;   /* curve[m]: array of curve elements */      //TODO: Potential Array
    privcurve ocurve;  /* ocurve[om]: array of curve elements */    //TODO: Potential Array
    privcurve fcurve;  /* final curve: this points to either curve or
		       ocurve. Do not free this separately. */

}
