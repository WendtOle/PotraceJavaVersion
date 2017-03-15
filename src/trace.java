import java.awt.*;
import java.util.Arrays;

/**
 * Created by andreydelany on 13/03/2017.
 */
public class trace {

    static int INFTY  = 10000000;	//it suffices that this is longer than any path; it need not be really infinite
    static double COS179 = -0.999847695156;	 /* the cosine of 179 degrees */
    static int POTRACE_CURVETO = 1;
    static int POTRACE_CORNER = 2;

    /* calculate point of a bezier curve */
    static potrace_dpoint bezier(double t, potrace_dpoint p0, potrace_dpoint p1, potrace_dpoint p2, potrace_dpoint p3) {
        double s = 1-t;
        potrace_dpoint res = null;

  /* Note: a good optimizing compiler (such as gcc-3) reduces the
     following to 16 multiplications, using common subexpression
     elimination. */

        res.x = s*s*s*p0.x + 3*(s*s*t)*p1.x + 3*(t*t*s)*p2.x + t*t*t*p3.x;
        res.y = s*s*s*p0.y + 3*(s*s*t)*p1.y + 3*(t*t*s)*p2.y + t*t*t*p3.y;

        return res;
    }

    /* calculate the point t in [0..1] on the (convex) bezier curve
   (p0,p1,p2,p3) which is tangent to q1-q0. Return -1.0 if there is no
   solution in [0..1]. */
    static double tangent(potrace_dpoint p0, potrace_dpoint p1, potrace_dpoint p2, potrace_dpoint p3, potrace_dpoint q0, potrace_dpoint q1) {
        double A, B, C;   /* (1-t)^2 A + 2(1-t)t B + t^2 C = 0 */
        double a, b, c;   /* a t^2 + b t + c = 0 */
        double d, s, r1, r2;

        A = cprod(p0, p1, q0, q1);
        B = cprod(p1, p2, q0, q1);
        C = cprod(p2, p3, q0, q1);

        a = A - 2*B + C;
        b = -2*A + 2*B;
        c = A;

        d = b*b - 4*a*c;

        if (a==0 || d<0) {
            return -1.0;
        }

        s = Math.sqrt(d);

        r1 = (-b + s) / (2 * a);
        r2 = (-b - s) / (2 * a);

        if (r1 >= 0 && r1 <= 1) {
            return r1;
        } else if (r2 >= 0 && r2 <= 1) {
            return r2;
        } else {
            return -1.0;
        }
    }

    /* calculate (p1-p0)*(p2-p0) */
    static double iprod(potrace_dpoint p0, potrace_dpoint p1, potrace_dpoint p2) {
        double x1, y1, x2, y2;

        x1 = p1.x - p0.x;
        y1 = p1.y - p0.y;
        x2 = p2.x - p0.x;
        y2 = p2.y - p0.y;

        return x1*x2 + y1*y2;
    }

    /* calculate (p1-p0)*(p3-p2) */
    static double iprod1(potrace_dpoint p0, potrace_dpoint p1, potrace_dpoint p2, potrace_dpoint p3) {
        double x1, y1, x2, y2;

        x1 = p1.x - p0.x;
        y1 = p1.y - p0.y;
        x2 = p3.x - p2.x;
        y2 = p3.y - p2.y;

        return x1*x2 + y1*y2;
    }

    /* calculate (p1-p0)x(p3-p2) */
    static double cprod(potrace_dpoint p0, potrace_dpoint p1, potrace_dpoint p2, potrace_dpoint p3) {
        double x1, y1, x2, y2;

        x1 = p1.x - p0.x;
        y1 = p1.y - p0.y;
        x2 = p3.x - p2.x;
        y2 = p3.y - p2.y;

        return x1*y2 - x2*y1;
    }

    /* calculate distance between two points */
    static double ddist(potrace_dpoint p, potrace_dpoint q) {
        return Math.sqrt((p.x-q.x)*(p.x-q.x)+(p.y-q.y)*(p.y-q.y));
    }

    /* calculate best fit from i+.5 to j+.5.  Assume i<j (cyclically).
   Return 0 and set badness and parameters (alpha, beta), if
   possible. Return 1 if impossible. */
    static potrace_privepath opti_penalty(potrace_privepath pp, int i, int j, potrace_opti res, double opttolerance, int[] convc, double[] areac) {
        int m = pp.curve.n;
        int conv;
        int k, k1, k2, i1;
        double area, alpha, d, d1, d2;
        potrace_dpoint p0, p1, p2, p3, pt;
        double A, R, A1, A2, A3, A4;
        double s, t;

        //check convexity, corner-freeness, and maximum bend < 179 degrees */

        if (i==j) {  /* sanity - a full loop can never be an opticurve */
            return null;
        }

        k = i;
        i1 = auxiliary.mod(i+1, m);
        k1 = auxiliary.mod(k+1, m);
        conv = convc[k1];
        if (conv == 0) {
            return null;
        }
        d = ddist(pp.curve.vertex[i], pp.curve.vertex[i1]);
        for (k=k1; k!=j; k=k1) {
            k1 = auxiliary.mod(k+1, m);
            k2 = auxiliary.mod(k+2, m);
            if (convc[k1] != conv) {
                return null;
            }
            if (auxiliary.sign(cprod(pp.curve.vertex[i], pp.curve.vertex[i1], pp.curve.vertex[k1], pp.curve.vertex[k2])) != conv) {
                return null;
            }
            if (iprod1(pp.curve.vertex[i], pp.curve.vertex[i1], pp.curve.vertex[k1], pp.curve.vertex[k2]) < d * ddist(pp.curve.vertex[k1], pp.curve.vertex[k2]) * COS179) {
                return null;
            }
        }

        //the curve we're working in:
        p0 = pp.curve.c[auxiliary.mod(i,m)][2];
        p1 = pp.curve.vertex[auxiliary.mod(i+1,m)];
        p2 = pp.curve.vertex[auxiliary.mod(j,m)];
        p3 = pp.curve.c[auxiliary.mod(j,m)][2];

        //determine its area
        area = areac[j] - areac[i];
        area -= dpara(pp.curve.vertex[0], pp.curve.c[i][2], pp.curve.c[j][2])/2;
        if (i>=j) {
            area += areac[m];
        }

        //find intersection o of p0p1 and p2p3. Let t,s such that o =
        //interval(t,p0,p1) = interval(s,p3,p2). Let A be the area of the
        //triangle (p0,o,p3).

        A1 = dpara(p0, p1, p2);
        A2 = dpara(p0, p1, p3);
        A3 = dpara(p0, p2, p3);
        //A4 = dpara(p1, p2, p3); */
        A4 = A1+A3-A2;

        if (A2 == A1) {  //this should never happen
            return null;
        }

        t = A3/(A3-A4);
        s = A2/(A2-A1);
        A = A2 * t / 2.0;

        if (A == 0.0) {  //this should never happen
            return null;
        }

        R = area / A;	 //relative area
        alpha = 2 - Math.sqrt(4 - R / 0.3);  // overall alpha for p0-o-p3 curve

        res.c[0] = auxiliary.interval(t * alpha, p0, p1);
        res.c[1] = auxiliary.interval(s * alpha, p3, p2);
        res.alpha = alpha;
        res.t = t;
        res.s = s;

        p1 = res.c[0];
        p2 = res.c[1];  // the proposed curve is now (p0,p1,p2,p3)

        res.pen = 0;

        //calculate penalty
        //check tangency with edges
        for (k=auxiliary.mod(i+1,m); k!=j; k=k1) {
            k1 = auxiliary.mod(k+1,m);
            t = tangent(p0, p1, p2, p3, pp.curve.vertex[k], pp.curve.vertex[k1]);
            if (t<-.5) {
                return null;
            }
            pt = bezier(t, p0, p1, p2, p3);
            d = ddist(pp.curve.vertex[k], pp.curve.vertex[k1]);
            if (d == 0.0) {  //this should never happen
                return null;
            }
            d1 = dpara(pp.curve.vertex[k], pp.curve.vertex[k1], pt) / d;
            if (Math.abs(d1) > opttolerance) {
                return null;
            }
            if (iprod(pp.curve.vertex[k], pp.curve.vertex[k1], pt) < 0 || iprod(pp.curve.vertex[k1], pp.curve.vertex[k], pt) < 0) {
                return null;
            }
            res.pen += (d1)*(d1);
        }

        //check corners
        for (k=i; k!=j; k=k1) {
            k1 = auxiliary.mod(k+1,m);
            t = tangent(p0, p1, p2, p3, pp.curve.c[k][2], pp.curve.c[k1][2]);
            if (t<-.5) {
                return null;
            }
            pt = bezier(t, p0, p1, p2, p3);
            d = ddist(pp.curve.c[k][2], pp.curve.c[k1][2]);
            if (d == 0.0) {  /* this should never happen */
                return null;
            }
            d1 = dpara(pp.curve.c[k][2], pp.curve.c[k1][2], pt) / d;
            d2 = dpara(pp.curve.c[k][2], pp.curve.c[k1][2], pp.curve.vertex[k1]) / d;
            d2 *= 0.75 * pp.curve.alpha[k1];
            if (d2 < 0) {
                d1 = -d1;
                d2 = -d2;
            }
            if (d1 < d2 - opttolerance) {
                return null;
            }
            if (d1 < d2) {
                res.pen += (d1 - d2)*(d1 - d2);
            }
        }

        return pp;
    }

    /* optimize the path p, replacing sequences of Bezier segments by a
       single segment when possible. Return 0 on success, 1 with errno set
       on failure. */
    static potrace_privepath opticurve(potrace_privepath pp, double opttolerance) {
        int m = pp.curve.n;
        int[] pt = new int[m+1];            //pt[m+1]
        double[]  pen = new double[m+1];       //pen[m+1]
        int[] len = new int [m+1];           //len[m+1]
        potrace_opti[]  opt = new potrace_opti[m+1];         //opt[m+1]
        int om;
        int i,j,r;
        potrace_opti o = new potrace_opti();
        potrace_dpoint p0;
        int i1;
        double area;
        double alpha;
        double[]  s = null;
        double[]  t = null;

        int[]  convc = new int[m]; /* conv[m]: pre-computed convexities */
        double[]  areac = new double[m+1]; /* cumarea[m+1]: cache for fast area computation */

        /* pre-calculate convexity: +1 = right turn, -1 = left turn, 0 = corner */
        for (i=0; i<m; i++) {
            if (pp.curve.tag[i] == POTRACE_CURVETO) {
                convc[i] = auxiliary.sign(dpara(pp.curve.vertex[auxiliary.mod(i-1,m)], pp.curve.vertex[i], pp.curve.vertex[auxiliary.mod(i+1,m)]));
            } else {
                convc[i] = 0;
            }
        }

        /* pre-calculate areas */
        area = 0.0;
        areac[0] = 0.0;
        p0 = pp.curve.vertex[0];
        for (i=0; i<m; i++) {
            i1 = auxiliary.mod(i+1, m);
            if (pp.curve.tag[i1] == POTRACE_CURVETO) {
                alpha = pp.curve.alpha[i1];
                area += 0.3*alpha*(4-alpha)*dpara(pp.curve.c[i][2], pp.curve.vertex[i1], pp.curve.c[i1][2])/2;
                area += dpara(p0, pp.curve.c[i][2], pp.curve.c[i1][2])/2;
            }
            areac[i+1] = area;
        }

        pt[0] = -1;
        pen[0] = 0;
        len[0] = 0;

        /* Fixme: we always start from a fixed point -- should find the best curve cyclically */

        for (j=1; j<=m; j++) {
            /* calculate best path from 0 to j */
            pt[j] = j-1;
            pen[j] = pen[j-1];
            len[j] = len[j-1]+1;

            for (i=j-2; i>=0; i--) {
                 potrace_privepath testpp = opti_penalty(pp, i, auxiliary.mod(j,m), o, opttolerance, convc, areac);
                if (testpp == null) {
                    break;
                } else {
                    pp = testpp;
                }
                if (len[j] > len[i]+1 || (len[j] == len[i]+1 && pen[j] > pen[i] + o.pen)) {
                    pt[j] = i;
                    pen[j] = pen[i] + o.pen;
                    len[j] = len[i] + 1;
                    opt[j] = o;
                }
            }
        }
        om = len[m];
        pp.ocurve = new privcurve(om);

        s = new double[om];
        t = new double[om];

        j = m;
        for (i=om-1; i>=0; i--) {
            if (pt[j]==j-1) {
                pp.ocurve.tag[i]     = pp.curve.tag[auxiliary.mod(j,m)];
                pp.ocurve.c[i][0]    = pp.curve.c[auxiliary.mod(j,m)][0];
                pp.ocurve.c[i][1]    = pp.curve.c[auxiliary.mod(j,m)][1];
                pp.ocurve.c[i][2]    = pp.curve.c[auxiliary.mod(j,m)][2];
                pp.ocurve.vertex[i]  = pp.curve.vertex[auxiliary.mod(j,m)];
                pp.ocurve.alpha[i]   = pp.curve.alpha[auxiliary.mod(j,m)];
                pp.ocurve.alpha0[i]  = pp.curve.alpha0[auxiliary.mod(j,m)];
                pp.ocurve.beta[i]    = pp.curve.beta[auxiliary.mod(j,m)];
                s[i] = t[i] = 1.0;
            } else {
                pp.ocurve.tag[i] = POTRACE_CURVETO;
                pp.ocurve.c[i][0] = opt[j].c[0];
                pp.ocurve.c[i][1] = opt[j].c[1];
                pp.ocurve.c[i][2] = pp.curve.c[auxiliary.mod(j,m)][2];
                pp.ocurve.vertex[i] = auxiliary.interval(opt[j].s, pp.curve.c[auxiliary.mod(j,m)][2], pp.curve.vertex[auxiliary.mod(j,m)]);
                pp.ocurve.alpha[i] = opt[j].alpha;
                pp.ocurve.alpha0[i] = opt[j].alpha;
                s[i] = opt[j].s;
                t[i] = opt[j].t;
            }
            j = pt[j];
        }

        /* calculate beta parameters */
        for (i=0; i<om; i++) {
            i1 = auxiliary.mod(i+1,om);
            pp.ocurve.beta[i] = s[i] / (s[i] + t[i1]);
        }
        pp.ocurve.alphacurve = 1;
        return pp;
    }

    /* return a direction that is 90 degrees counterclockwise from p2-p0,
       but then restricted to one of the major wind directions (n, nw, w, etc) */
    static Point dorth_infty(potrace_dpoint p0, potrace_dpoint p2) {
        Point r = new Point();

        r.y = auxiliary.sign(p2.x-p0.x);
        r.x = -auxiliary.sign(p2.y-p0.y);

        return r;
    }

    static double ddenom(potrace_dpoint p0, potrace_dpoint p2) {
        Point r = dorth_infty(p0, p2);

        return r.y*(p2.x-p0.x) - r.x*(p2.y-p0.y);
    }


    /* return (p1-p0)x(p2-p0), the area of the parallelogram */
    static double dpara(potrace_dpoint p0, potrace_dpoint p1, potrace_dpoint p2) {
        double x1, y1, x2, y2;

        x1 = p1.x-p0.x;
        y1 = p1.y-p0.y;
        x2 = p2.x-p0.x;
        y2 = p2.y-p0.y;

        return x1*y2 - x2*y1;
    }

    /* Always succeeds */
    static privcurve smooth(privcurve curve, double alphamax) {
        int m = curve.n;

        int i, j, k;
        double dd, denom, alpha;
        potrace_dpoint p2, p3, p4;

        //examine each vertex and find its best fit
        for (i=0; i<m; i++) {
            j = auxiliary.mod(i+1, m);
            k = auxiliary.mod(i+2, m);
            p4 = auxiliary.interval(1/2.0, curve.vertex[k], curve.vertex[j]);

            denom = ddenom(curve.vertex[i], curve.vertex[k]);
            if (denom != 0.0) {
                dd = dpara(curve.vertex[i], curve.vertex[j], curve.vertex[k]) / denom;
                dd = Math.abs(dd);
                alpha = dd>1 ? (1 - 1.0/dd) : 0;
                alpha = alpha / 0.75;
            } else {
                alpha = 4/3.0;
            }
            curve.alpha0[j] = alpha;	 /* remember "original" value of alpha */

            if (alpha >= alphamax) {  /* pointed corner */
                curve.tag[j] = POTRACE_CORNER;
                curve.c[j][1] = curve.vertex[j];
                curve.c[j][2] = p4;
            } else {
                if (alpha < 0.55) {
                    alpha = 0.55;
                } else if (alpha > 1) {
                    alpha = 1;
                }
                p2 = auxiliary.interval(.5+.5*alpha, curve.vertex[i], curve.vertex[j]);
                p3 = auxiliary.interval(.5+.5*alpha, curve.vertex[k], curve.vertex[j]);
                curve.tag[j] = POTRACE_CURVETO;
                curve.c[j][0] = p2;
                curve.c[j][1] = p3;
                curve.c[j][2] = p4;
            }
            curve.alpha[j] = alpha;	/* store the "cropped" value of alpha */
            curve.beta[j] = 0.5;
        }
        curve.alphacurve = 1;

        return curve;
    }

    static privcurve reverse(privcurve curve) {
        int m = curve.n;
        int i, j;
        potrace_dpoint tmp;

        for (i=0, j=m-1; i<j; i++, j--) {
            tmp = curve.vertex[i];
            curve.vertex[i] = curve.vertex[j];
            curve.vertex[j] = tmp;
        }
        return curve;
    }

    //Apply quadratic form Q to vector w = (w.x,w.y)
    static double quadform(potrace_quadform Q, potrace_dpoint w) {
        double[] v = new double[3];
        int i, j;
        double sum;

        v[0] = w.x;
        v[1] = w.y;
        v[2] = 1;
        sum = 0.0;

        for (i=0; i<3; i++) {
            for (j=0; j<3; j++) {
                sum += v[i] * Q.content[i][j] * v[j];
            }
        }
        return sum;
    }

    //determine the center and slope of the line i..j. Assume i<j. Needs
    //"sum" components of p to be set.
    static potrace_dpoint[] pointslope(potrace_privepath pp, int i, int j) {
    //assume i<j

        potrace_dpoint ctr = new potrace_dpoint();
        potrace_dpoint dir = new potrace_dpoint();

        int n = pp.len;
        sums[] sums = pp.sums;

        double x, y, x2, xy, y2;
        double k;
        double a, b, c, lambda2, l;
        int r=0; // rotations from i to j

        while (j>=n) {
            j-=n;
            r+=1;
        }
        while (i>=n) {
            i-=n;
            r-=1;
        }
        while (j<0) {
            j+=n;
            r-=1;
        }
        while (i<0) {
            i+=n;
            r+=1;
        }

        x = sums[j+1].x-sums[i].x+r*sums[n].x;
        y = sums[j+1].y-sums[i].y+r*sums[n].y;
        x2 = sums[j+1].x2-sums[i].x2+r*sums[n].x2;
        xy = sums[j+1].xy-sums[i].xy+r*sums[n].xy;
        y2 = sums[j+1].y2-sums[i].y2+r*sums[n].y2;
        k = j+1-i+r*n;

        ctr.x = x/k;
        ctr.y = y/k;

        a = (x2-(double)x*x/k)/k;
        b = (xy-(double)x*y/k)/k;
        c = (y2-(double)y*y/k)/k;

        lambda2 = (a+c+Math.sqrt((a-c)*(a-c)+4*b*b))/2; // larger e.value

        //now find e.vector for lambda2
        a -= lambda2;
        c -= lambda2;

        if (Math.abs(a) >= Math.abs(c)) {
            l = Math.sqrt(a*a+b*b);
            if (l!=0) {
                dir.x = -b/l;
                dir.y = a/l;
            }
        } else {
            l = Math.sqrt(c*c+b*b);
            if (l!=0) {
                dir.x = -c/l;
                dir.y = b/l;
            }
        }
        if (l==0) {
            dir.x = dir.y = 0;   //sometimes this can happen when k=4: the two eigenvalues coincide
        }
        potrace_dpoint[] output = {ctr,dir};
        return output;
    }

    /* Adjust vertices of optimal polygon: calculate the intersection of
   the two "optimal" line segments, then move it into the unit square
   if it lies outside. Return 1 with errno set on error; 0 on
   success. */

    static potrace_privepath adjust_vertices(potrace_privepath pp) {
        int m = pp.m;
        int[] po = pp.po;
        int n = pp.len;
        Point[] pt = pp.pt;
        int x0 = pp.x0;
        int y0 = pp.y0;

        potrace_dpoint[] ctr = new potrace_dpoint[m];      /* ctr[m] */
        potrace_dpoint[] dir = new potrace_dpoint[m];      /* dir[m] */
        potrace_quadform[] q = new potrace_quadform[m];     /* q[m] */

        for(int i = 0; i < q.length; i++) {
            q[i] = new potrace_quadform();
        }

        double[] v = new double[3];
        double d;
        int i, j, k, l;
        potrace_dpoint s = new potrace_dpoint();

        pp.curve = new privcurve(m);    //Fixme: check wether it works correct

        //calculate "optimal" point-slope representation for each line segment
        for (i=0; i<m; i++) {
            j = po[auxiliary.mod(i+1,m)];
            j = auxiliary.mod(j-po[i],n)+po[i];
            potrace_dpoint[] output = pointslope(pp, po[i], j);
            ctr[i] = output[0];
            dir[i] = output[1];
        }

        //represent each line segment as a singular quadratic form; the
        //distance of a point (x,y) from the line segment will be
        //(x,y,1)Q(x,y,1)^t, where Q=q[i]. */
        for (i=0; i<m; i++) {
            d = ((dir[i].x)*(dir[i].x)) + (dir[i].y)*(dir[i].y);
            if (d == 0.0) {
                for (j=0; j<3; j++) {
                    for (k=0; k<3; k++) {
                        q[i].content[j][k] = 0;
                    }
                }
            } else {
                v[0] = dir[i].y;
                v[1] = -dir[i].x;
                v[2] = - v[1] * ctr[i].y - v[0] * ctr[i].x;
                for (l=0; l<3; l++) {
                    for (k=0; k<3; k++) {
                        q[i].content[l][k] = v[l] * v[k] / d;
                    }
                }
            }
        }

        //now calculate the "intersections" of consecutive segments.
        //Instead of using the actual intersection, we find the point
        //within a given unit square which minimizes the square distance to
        //the two lines.
        for (i=0; i<m; i++) {
            potrace_quadform Q = new potrace_quadform();
            potrace_dpoint w = new potrace_dpoint();
            double dx, dy;
            double det;
            double min, cand;   //minimum and candidate for minimum of quad. form */
            double xmin, ymin;	//coordinates of minimum */
            int z;

            //let s be the vertex, in coordinates relative to x0/y0 */
            s.x = pt[po[i]].x-x0;
            s.y = pt[po[i]].y-y0;

            //intersect segments i-1 and i */

            j = auxiliary.mod(i-1,m);

            //add quadratic forms */
            for (l=0; l<3; l++) {
                for (k=0; k<3; k++) {
                    Q.content[l][k] = q[j].content[l][k] + q[i].content[l][k];
                }
            }

            while(true) {
            //minimize the quadratic form Q on the unit square
            //find intersection

                det = Q.content[0][0]*Q.content[1][1] - Q.content[0][1]*Q.content[1][0];
                if (det != 0.0) {
                    w.x = (-Q.content[0][2]*Q.content[1][1] + Q.content[1][2]*Q.content[0][1]) / det;
                    w.y = ( Q.content[0][2]*Q.content[1][0] - Q.content[1][2]*Q.content[0][0]) / det;
                    break;
                }

            //matrix is singular - lines are parallel. Add another,
	        //orthogonal axis, through the center of the unit square */
                if (Q.content[0][0]>Q.content[1][1]) {
                    v[0] = -Q.content[0][1];
                    v[1] = Q.content[0][0];
                } else if (Q.content[1][1] != 0) { //fixme: check wehter you really get only 1 and zero back
                    v[0] = -Q.content[1][1];
                    v[1] = Q.content[1][0];
                } else {
                    v[0] = 1;
                    v[1] = 0;
                }
                d = (v[0])*(v[0]) + (v[1])*(v[1]);
                v[2] = - v[1] * s.y - v[0] * s.x;
                for (l=0; l<3; l++) {
                    for (k=0; k<3; k++) {
                        Q.content[l][k] += v[l] * v[k] / d;
                    }
                }
            }
            dx = Math.abs(w.x-s.x);
            dy = Math.abs(w.y-s.y);
            if (dx <= .5 && dy <= .5) {
                pp.curve.vertex[i].x = w.x+x0;
                pp.curve.vertex[i].y = w.y+y0;
                continue;
            }

            //the minimum was not in the unit square; now minimize quadratic
            //on boundary of square
            min = quadform(Q, s);
            xmin = s.x;
            ymin = s.y;

            if (Q.content[0][0] != 0.0) { //fixme: checken ob das mit den jumpmarcs wirklich funktioniert hat.
                for (z = 0; z < 2; z++) {   //value of the y-coordinate
                    w.y = s.y - 0.5 + z;
                    w.x = -(Q.content[0][1] * w.y + Q.content[0][2]) / Q.content[0][0];
                    dx = Math.abs(w.x - s.x);
                    cand = quadform(Q, w);
                    if (dx <= .5 && cand < min) {
                        min = cand;
                        xmin = w.x;
                        ymin = w.y;
                    }
                }
            }

            if (Q.content[1][1] != 0.0) {
                for (z = 0; z < 2; z++) {   //value of the x-coordinate
                    w.x = s.x - 0.5 + z;
                    w.y = -(Q.content[1][0] * w.x + Q.content[1][2]) / Q.content[1][1];
                    dy = Math.abs(w.y - s.y);
                    cand = quadform(Q, w);
                    if (dy <= .5 && cand < min) {
                        min = cand;
                        xmin = w.x;
                        ymin = w.y;
                    }
                }
            }

            //check four corners
            for (l=0; l<2; l++) {
                for (k=0; k<2; k++) {
                    w.x = s.x-0.5+l;
                    w.y = s.y-0.5+k;
                    cand = quadform(Q, w);
                    if (cand < min) {
                        min = cand;
                        xmin = w.x;
                        ymin = w.y;
                    }
                }
            }

            pp.curve.vertex[i].x = xmin + x0;
            pp.curve.vertex[i].y = ymin + y0;
            continue;
        }

        return pp;
    }

    /* Auxiliary function: calculate the penalty of an edge from i to j in
   the given path. This needs the "lon" and "sum*" data. */

    static double penalty3(potrace_privepath pp, int i, int j) {
        int n = pp.len;
        Point[] pt = pp.pt;
        sums[] sums = pp.sums;

        //assume 0<=i<j<=n
        double x, y, x2, xy, y2;
        double k;
        double a, b, c, s;
        double px, py, ex, ey;

        int r = 0; // rotations from i to j

        if (j>=n) {
            j -= n;
            r = 1;
        }

        //critical inner loop: the "if" gives a 4.6 percent speedup
        if (r == 0) {
            x = sums[j+1].x - sums[i].x;
            y = sums[j+1].y - sums[i].y;
            x2 = sums[j+1].x2 - sums[i].x2;
            xy = sums[j+1].xy - sums[i].xy;
            y2 = sums[j+1].y2 - sums[i].y2;
            k = j+1 - i;
        } else {
            x = sums[j+1].x - sums[i].x + sums[n].x;
            y = sums[j+1].y - sums[i].y + sums[n].y;
            x2 = sums[j+1].x2 - sums[i].x2 + sums[n].x2;
            xy = sums[j+1].xy - sums[i].xy + sums[n].xy;
            y2 = sums[j+1].y2 - sums[i].y2 + sums[n].y2;
            k = j+1 - i + n;
        }

        px = (pt[i].x + pt[j].x) / 2.0 - pt[0].x;
        py = (pt[i].y + pt[j].y) / 2.0 - pt[0].y;
        ey = (pt[j].x - pt[i].x);
        ex = -(pt[j].y - pt[i].y);

        a = ((x2 - 2*x*px) / k + px*px);
        b = ((xy - x*py - y*px) / k + px*py);
        c = ((y2 - 2*y*py) / k + py*py);

        s = ex*ex*a + 2*ex*ey*b + ey*ey*c;

        return Math.sqrt(s);
    }

    //find the optimal polygon. Fill in the m and po components. Return 1
    //on failure with errno set, else 0. Non-cyclic version: assumes i=0
    //is in the polygon. Fixme: implement cyclic version. */
    static potrace_privepath bestpolygon(potrace_privepath pp) {
        int i, j, m, k;
        int n = pp.len;
        double[] pen = new double[n+1]; /* pen[n+1]: penalty vector */
        int[] prev = new int[n+1];   /* prev[n+1]: best path pointer vector */
        int[] clip0 = new int[n];  /* clip0[n]: longest segment pointer, non-cyclic */
        int[] clip1 = new int[n+1];  /* clip1[n+1]: backwards segment pointer, non-cyclic */
        int[] seg0 = new int[n+1];    /* seg0[m+1]: forward segment bounds, m<=n */
        int[] seg1 = new int[n+1];   /* seg1[m+1]: backward segment bounds, m<=n */
        double thispen;
        double best;
        int c;

        //calculate clipped paths
        for (i=0; i<n; i++) {
            c = auxiliary.mod(pp.lon[auxiliary.mod(i-1,n)]-1,n);
            if (c == i) {
                c = auxiliary.mod(i+1,n);
            }
            if (c < i) {
                clip0[i] = n;
            } else {
                clip0[i] = c;
            }
        }

        //calculate backwards path clipping, non-cyclic. j <= clip0[i] iff
        //clip1[j] <= i, for i,j=0..n.
        j = 1;
        for (i=0; i<n; i++) {
            while (j <= clip0[i]) {
                clip1[j] = i;
                j++;
            }
        }

        //calculate seg0[j] = longest path from 0 with j segments
        i = 0;
        for (j=0; i<n; j++) {
            seg0[j] = i;
            i = clip0[i];
        }
        seg0[j] = n;
        m = j;

        //calculate seg1[j] = longest path to n with m-j segments
        i = n;
        for (j=m; j>0; j--) {
            seg1[j] = i;
            i = clip1[i];
        }
        seg1[0] = 0;

        //now find the shortest path with m segments, based on penalty3
        //note: the outer 2 loops jointly have at most n iterations, thus
        //the worst-case behavior here is quadratic. In practice, it is
        //close to linear since the inner loop tends to be short.
        pen[0]=0;
        for (j=1; j<=m; j++) {
            for (i=seg1[j]; i<=seg0[j]; i++) {
                best = -1;
                for (k=seg0[j-1]; k>=clip1[i]; k--) {
                    thispen = penalty3(pp, k, i) + pen[k];
                    if (best < 0 || thispen < best) {
                        prev[i] = k;
                        best = thispen;
                    }
                }
                pen[i] = best;
            }
        }

        pp.m = m;
        pp.po = new int[m];

        /* read off shortest path */
        for (i=n, j=m-1; i>0; j--) {
            i = prev[i];
            pp.po[j] = i;
        }

        return pp;
    }

    /* calculate p1 x p2 */
    static int xprod(Point p1, Point p2) {
        return p1.x*p2.y - p1.y*p2.x;
    }

    /* return 1 if a <= b < c < a, in a cyclic sense (mod n) */
    static boolean cyclic(int a, int b, int c) {
        if (a<=c) {
            return (a<=b && b<c);
        } else {
            return (a<=b || b<c);
        }
    }

    /* returns 0 on success, 1 on error with errno set */
    static potrace_privepath calc_lon(potrace_privepath pp) {
        Point[] pt = pp.pt;
        int n = pp.len;
        int i, j, k, k1;
        int[] ct = new int[4];
        int dir;
        Point[] constraint = new Point[2];
        Point cur = new Point();
        Point off = new Point();
        int[] pivk = new int[n];   //pivk[n]
        int[] nc = new int[n];   //nc[n]: next corner
        Point dk = new Point();           //direction of k-k1
        int a, b, c, d;

        //initialize the nc data structure. Point from each point to the
        //furthest future point to which it is connected by a vertical or
        //horizontal segment. We take advantage of the fact that there is
        //always a direction change at 0 (due to the path decomposition
        //algorithm). But even if this were not so, there is no harm, as
        //in practice, correctness does not depend on the word "furthest"
        //above.

        k = 0;
        for (i=n-1; i>=0; i--) {
            if (pt[i].x != pt[k].x && pt[i].y != pt[k].y) {
                k = i+1;  /* necessarily i<n-1 in this case */
            }
            nc[i] = k;
        }

        pp.lon = new int[n];

        //determine pivot points: for each i, let pivk[i] be the furthest k
        //such that all j with i<j<k lie on a line connecting i,k.

        outerloop:
        for (i=n-1; i>=0; i--) {
            ct[0] = ct[1] = ct[2] = ct[3] = 0;

            //keep track of "directions" that have occurred

            dir = (3+3*(pt[auxiliary.mod(i+1,n)].x-pt[i].x)+(pt[auxiliary.mod(i+1,n)].y-pt[i].y))/2;
            ct[dir]++;

            constraint[0] = new Point(0,0);
            constraint[1] = new Point(0,0);

            //find the next k such that no straight line from i to k
            k = nc[i];
            k1 = i;
            while (true) {

                dir = (3+3*auxiliary.sign(pt[k].x-pt[k1].x)+auxiliary.sign(pt[k].y-pt[k1].y))/2;
                ct[dir]++;

                //if all four "directions" have occurred, cut this path
                if (ct[0]!=0 && ct[1]!=0 && ct[2]!=0 && ct[3]!=0) {
                    pivk[i] = k1;
	                continue outerloop;
                }

                cur.x = pt[k].x - pt[i].x;
                cur.y = pt[k].y - pt[i].y;

                //see if current constraint is violated
                if (xprod(constraint[0], cur) < 0 || xprod(constraint[1], cur) > 0) {

                    //k1 was the last "corner" satisfying the current constraint, and
                    //k is the first one violating it. We now need to find the last
                    //point along k1..k which satisfied the constraint. */
                    dk.x = auxiliary.sign(pt[k].x-pt[k1].x);
                    dk.y = auxiliary.sign(pt[k].y-pt[k1].y);
                    cur.x = pt[k1].x - pt[i].x;
                    cur.y = pt[k1].y - pt[i].y;

                    //find largest integer j such that xprod(constraint[0], cur+j*dk)
                    //>= 0 and xprod(constraint[1], cur+j*dk) <= 0. Use bilinearity
                    //of xprod. */
                    a = xprod(constraint[0], cur);
                    b = xprod(constraint[0], dk);
                    c = xprod(constraint[1], cur);
                    d = xprod(constraint[1], dk);

                    //find largest integer j such that a+j*b>=0 and c+j*d<=0. This
                    //can be solved with integer arithmetic. */
                    j = INFTY;
                    if (b<0) {
                        j = auxiliary.floordiv(a,-b);
                    }
                    if (d>0) {
                        j = auxiliary.min(j, auxiliary.floordiv(-c,d));
                    }
                    pivk[i] = auxiliary.mod(k1+j,n);
                    continue outerloop;
                }

                //else, update constraint
                if (auxiliary.abs(cur.x) <= 1 && auxiliary.abs(cur.y) <= 1) {
	                //constraint
                } else {
                    off.x = cur.x + ((cur.y>=0 && (cur.y>0 || cur.x<0)) ? 1 : -1);
                    off.y = cur.y + ((cur.x<=0 && (cur.x<0 || cur.y<0)) ? 1 : -1);
                    if (xprod(constraint[0], off) >= 0) {
                        constraint[0] = new Point(off.x, off.y);
                    }
                    off.x = cur.x + ((cur.y<=0 && (cur.y<0 || cur.x<0)) ? 1 : -1);
                    off.y = cur.y + ((cur.x>=0 && (cur.x>0 || cur.y<0)) ? 1 : -1);
                    if (xprod(constraint[1], off) <= 0) {
                        constraint[1] = new Point(off.x, off.y);
                    }
                }
                k1 = k;
                k = nc[k1];
                if (!cyclic(k,i,k1)) {
                    break;
                }
            }
        } // for i == 1

        //clean up: for each i, let lon[i] be the largest k such that for
        //all i' with i<=i'<k, i'<k<=pivk[i']. */

        j=pivk[n-1];
        pp.lon[n-1]=j;
        for (i=n-2; i>=0; i--) {
            if (cyclic(i+1,pivk[i],j)) {
                j=pivk[i];
            }
            pp.lon[i]=j;
        }

        for (i=n-1; cyclic(auxiliary.mod(i+1,n),j,pp.lon[i]); i--) {
            pp.lon[i] = j;
        }

        return pp;
    }

    /* Preparation: fill in the sum* fields of a path (used for later
   rapid summing). Return 0 on success, 1 with errno set on
   failure. */
    static potrace_privepath calc_sums(potrace_privepath pp) {
        int i, x, y;
        int n = pp.len;

        pp.sums = new sums[pp.len+1];

        //origin
        pp.x0 = pp.pt[0].x;
        pp.y0 = pp.pt[0].y;

        //preparatory computation for later fast summing
        sums startSum = new sums();
        startSum.x2 = startSum.xy = startSum.y2 = startSum.x = startSum.y = 0;
        pp.sums[0] = startSum;
        for (i=0; i<n; i++) {
            pp.sums[i+1] = new sums();
            x = pp.pt[i].x - pp.x0;
            y = pp.pt[i].y - pp.y0;
            pp.sums[i+1].x = pp.sums[i].x + x;
            pp.sums[i+1].y = pp.sums[i].y + y;
            pp.sums[i+1].x2 = pp.sums[i].x2 + x*x;
            pp.sums[i+1].xy = pp.sums[i].xy + x*y;
            pp.sums[i+1].y2 = pp.sums[i].y2 + y*y;
        }
        return pp;
    }

    /* return 0 on success, 1 on error with errno set. */
    static potrace_path process_path(potrace_path plist,  potrace_param param) {
        /* call downstream function with each path */

        for (potrace_path p=plist; p!=null; p=p.next) {
            p.priv = calc_sums(p.priv);
            p.priv = calc_lon(p.priv);
            p.priv = bestpolygon(p.priv);
            p.priv = adjust_vertices(p.priv);
            if (p.sign == '-') {   /* reverse orientation of negative paths */
                reverse(p.priv.curve);
            }
            p.priv.curve= smooth(p.priv.curve, param.alphamax);
            if (param.opticurve != 0) {
                opticurve(p.priv, param.opttolerance);
                p.priv.fcurve = p.priv.ocurve;
            } else {
                p.priv.fcurve = p.priv.curve;
            }
            p.curve = potrace_curve.privcurve_to_curve(p.priv.fcurve);
        }
        return plist;

    }
}
