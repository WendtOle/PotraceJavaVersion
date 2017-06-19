package potraceOriginal;

/* closed Curve segment */
public class Curve {
    public int n;                   //number of segments
    public int[] tag;               //tag[n] : Potrace_Curveto or potrace_corner
    public DPoint[][] c;    /* c[n][3]: control points. c[n][0] is unused for tag[n]=POTRACE_CORNER */

    /* copy private to public Curve structure */
    static Curve privcurve_to_curve(PrivCurve pc) {
        Curve publicCurve = new Curve();
        publicCurve.n = pc.n;
        publicCurve.tag = pc.tag;
        publicCurve.c = pc.c;
        return publicCurve;
    }

}
