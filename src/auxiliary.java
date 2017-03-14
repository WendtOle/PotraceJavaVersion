/**
 * Created by andreydelany on 13/03/2017.
 */
public class auxiliary {

    static int mod(int a, int n) {
        return a>=n ? a%n : a>=0 ? a : n-1-(-1-a)%n;
    }

    static int sign(int x) {
        return (x)>0 ? 1 : ((x)<0 ? -1 : 0);
    }

    static int sign(double x) {
        return (x)>0 ? 1 : ((x)<0 ? -1 : 0);
    }

    static int abs(int a) {
        return (a)>0 ? (a) : -(a);
    }

    static int floordiv(int a, int n) {
        return a>=0 ? a/n : -1-(-1-a)/n;
    }

    static int min(int a, int b) {
        return (a)<(b) ? (a) : (b);
    }

    /* range over the straight line segment [a,b] when lambda ranges over [0,1] */
    static potrace_dpoint interval(double lambda, potrace_dpoint a, potrace_dpoint b) {
        potrace_dpoint res = null;

        res.x = a.x + lambda * (b.x - a.x);
        res.y = a.y + lambda * (b.y - a.y);
        return res;
    }
}
