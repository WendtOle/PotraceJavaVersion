package AdditionalCode;

import java.awt.geom.Point2D;

/**
 * Created by andreydelany on 24.06.17.
 */
public class Curve {
    public int n;
    public int[] tag;
    public Point2D.Double[][] c;

    public Curve(int n, int[] tag, Point2D.Double[][] c){
        this.n = n;
        this.tag = tag;
        this.c = c;
    }
}
