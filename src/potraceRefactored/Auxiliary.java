package potraceRefactored;

public class Auxiliary {

    /* range over the straight line segment [a,b] when lambda ranges over [0,1] */

    static DPoint interval(double lambda, DPoint a, DPoint b) {
        DPoint res = new DPoint();

        res.x = a.x + lambda * (b.x - a.x);
        res.y = a.y + lambda * (b.y - a.y);
        return res;
    }

    /* some useful macros. Note: the "mod" macro works correctly for
    negative a. Also note that the test for a>=n, while redundant,
    speeds up the mod function by 70% in the average case (significant
    since the program spends about 16% of its time here - or 40%
    without the test). The "floordiv" macro returns the largest integer
    <= a/n, and again this works correctly for negative a, as long as
    a,n are integers and n>0. */

    /* integer arithmetic */

    static int mod(int a, int n) {
        return a>=n ? a%n : a>=0 ? a : n-1-(-1-a)%n;
    }

    static int floordiv(int a, int n) {
        return a>=0 ? a/n : -1-(-1-a)/n;
    }

    /* Note: the following work for integers and other numeric types. */

    static int sign(int x) {
        return (x)>0 ? 1 : ((x)<0 ? -1 : 0);
    }

    static int sign(double x) {
        return (x)>0 ? 1 : ((x)<0 ? -1 : 0);
    }

    static int abs(int a) {
        return (a)>0 ? (a) : -(a);
    }

    static int min(int a, int b) {
        return (a)<(b) ? (a) : (b);
    }
}
