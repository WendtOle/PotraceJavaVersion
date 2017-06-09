package potrace;

/* the type of (affine) quadratic forms, represented as symmetric 3x3
   matrices.  The value of the quadratic form at a vector (x,y) is v^t
   Q v, where v = (x,y,1)^t. */

public class quadform {
    double[][] content = new double[3][3];
}
