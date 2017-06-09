package potrace;

/* a private type for the result of opti_penalty */
public class Opti {
    double pen;	                    /*  penalty */
    DPoint[] c = new DPoint[2];     /* Curve parameters */
    double t, s;	                /* Curve parameters */
    double alpha;

    public Opti() {
        for (int i = 0; i < c.length;i++) {
            c[i] = new DPoint();
        }
    }

    static Opti copy(Opti input) {
        Opti result = new Opti();
        result.pen = input.pen;
        result.t = input.t;
        result.s = input.s;
        result.alpha = input.alpha;
        result.c[0]= new DPoint(input.c[0].x,input.c[0].y);
        result.c[1]= new DPoint(input.c[1].x,input.c[1].y);
        return result;
    }
}
