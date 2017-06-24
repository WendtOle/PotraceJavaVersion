package refactored.potrace;

import java.awt.geom.Point2D;

/* a private type for the result of opti_penalty */
public class Opti {
    double pen;	                    /*  penalty */
    Point2D.Double[] c = new Point2D.Double[2];     /* Curve parameters */
    double t, s;	                /* Curve parameters */
    double alpha;

    public Opti() {
        for (int i = 0; i < c.length;i++) {
            c[i] = new Point2D.Double();
        }
    }

    static Opti copy(Opti input) {
        Opti result = new Opti();
        result.pen = input.pen;
        result.t = input.t;
        result.s = input.s;
        result.alpha = input.alpha;
        result.c[0]= new Point2D.Double(input.c[0].x,input.c[0].y);
        result.c[1]= new Point2D.Double(input.c[1].x,input.c[1].y);
        return result;
    }
}
