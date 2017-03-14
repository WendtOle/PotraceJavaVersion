/**
 * Created by andreydelany on 04/03/2017.
 */
public class potrace_curve {
    int n;      //number of segments
    int[] tag;    //tag[n] : Potrace_Curveto or potrace_corner //TODO: Array
    potrace_dpoint[][] c;

    //initialize the members of the given curve structure to size m.
    //Return 0 on success, 1 on error with errno set.

    /* copy private to public curve structure */
    static potrace_curve privcurve_to_curve(privcurve pc, potrace_curve c) {
        c.n = pc.n;
        c.tag = pc.tag;
        c.c = pc.c;
        return c;
    }

}
