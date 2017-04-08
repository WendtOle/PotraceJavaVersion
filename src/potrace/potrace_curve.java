package potrace;

/**
 * Created by andreydelany on 04/03/2017.
 */

    //Ole:
    /* Im wesentlichen ist die Klasse privCurve und potrace_curve, das gleiche.
    Sie repräsentiert das gleiche. Allerdings repräsentiert potrace_curve ein öffentliche Curve,
    mit weniger Feldern die jeder einsehen darf. Und privCurve,
    enthält sehr viel mehr Felder und wird bei der Erstellung eine Curve genutz */


public class potrace_curve {
    public int n;                   //number of segments
    public int[] tag;                      //tag[n] : Potrace_Curveto or potrace_corner //TODO: Array
    public potrace_dpoint[][] c;

    /* copy private to public curve structure */
    static potrace_curve privcurve_to_curve(privcurve pc) {
        potrace_curve publicCurve = new potrace_curve();
        publicCurve.n = pc.n;
        publicCurve.tag = pc.tag;
        publicCurve.c = pc.c;
        return publicCurve;
    }

}
