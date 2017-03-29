package potrace;

/**
 * Created by andreydelany on 13/03/2017.
 */

    //Ole:
    /* Ich habe an dieser Klasse soweit nichts verändert,
    außer das in dieser Klasse außerdem die Typdefinition für point gemacht wurde,
    die ich allerdings weggelassen habe, da die Java.Point genau die gleiche Funktion besitz
    Desweiteren habe ich eine Funktion weggelassen, die einen normalen Punkt in einen dPoint verwandelt,
    da sie niemals verwendet wurde
    Ansonsten habe ich (ab Zeile 49) die im originalen als Marco vorkommenden Sachen in Funktionen verwandeln,
    die allerdings identisch funktionieren. */

public class auxiliary {

    //Entwickler:
    /* range over the straight line segment [a,b] when lambda ranges over [0,1] */

    static potrace_dpoint interval(double lambda, potrace_dpoint a, potrace_dpoint b) {
        potrace_dpoint res = new potrace_dpoint();

        res.x = a.x + lambda * (b.x - a.x);
        res.y = a.y + lambda * (b.y - a.y);
        return res;
    }

    //Entwickler:
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
