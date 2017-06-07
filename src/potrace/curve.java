package potrace;

/* closed curve segment */
public class curve {
    public int n;                   //number of segments
    public int[] tag;               //tag[n] : Potrace_Curveto or potrace_corner
    public dpoint[][] c;    /* c[n][3]: control points. c[n][0] is unused for tag[n]=POTRACE_CORNER */

    /* copy private to public curve structure */
    static curve privcurve_to_curve(privcurve pc) {
        curve publicCurve = new curve();
        publicCurve.n = pc.n;
        publicCurve.tag = pc.tag;
        publicCurve.c = pc.c;
        return publicCurve;
    }

}
