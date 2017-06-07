package potrace;

/* a private type for the result of opti_penalty */
public class opti {
    double pen;	                    /*  penalty */
    dpoint[] c = new dpoint[2];     /* curve parameters */
    double t, s;	                /* curve parameters */
    double alpha;

    public opti() {
        for (int i = 0; i < c.length;i++) {
            c[i] = new dpoint();
        }
    }

    static opti copy(opti input) {
        opti result = new opti();
        result.pen = input.pen;
        result.t = input.t;
        result.s = input.s;
        result.alpha = input.alpha;
        result.c[0]= new dpoint(input.c[0].x,input.c[0].y);
        result.c[1]= new dpoint(input.c[1].x,input.c[1].y);
        return result;
    }
}
