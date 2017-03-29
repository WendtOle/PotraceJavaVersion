package potrace;

/**
 * Created by andreydelany on 14/03/2017.
 */

    //Ole:
    /* Urpsrünglich war die Typdefinition zu potrace_opti im File trace.c */

public class potrace_opti {
    double pen;	                                    /*  penalty */
    potrace_dpoint[] c = new potrace_dpoint[2];     /* curve parameters */
    double t, s;	                                /* curve parameters */
    double alpha;

    public potrace_opti () {
        for (int i = 0; i < c.length;i++) {
            c[i] = new potrace_dpoint();
        }
    }

    //Ole:
    /* Methode habe ich neu geschrieben, da es einen Fehler gab,
    da z.b. im  File trace.c Zeile 1135 zwar in C die Werte kopiert werden,
    aber in Java einfach die Referenz und dadurch ein Fehler entsteht, deshalb war es nötig, nur die Werte zu kopieren */

    //TODO: Eleganteren Weg finden, die Werte aber nicht die Referenz zu kopieren

    static potrace_opti copy(potrace_opti input) {
        potrace_opti result = new potrace_opti();
        result.pen = input.pen;
        result.t = input.t;
        result.s = input.s;
        result.alpha = input.alpha;
        result.c[0]= new potrace_dpoint(input.c[0].x,input.c[0].y);
        result.c[1]= new potrace_dpoint(input.c[1].x,input.c[1].y);
        return result;
    }
}
