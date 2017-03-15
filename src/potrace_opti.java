/**
 * Created by andreydelany on 14/03/2017.
 */
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
