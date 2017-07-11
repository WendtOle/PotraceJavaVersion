package Potrace.General;

public class Param {

    public int turdsize;        /* area of largest Path to be ignored */
    public int turnpolicy;      /* resolves ambiguous turns in Path decomposition */
    double alphamax;            /* corner threshold */
    boolean opticurve;              /* use Curve optimization? */
    double opttolerance;        /* Curve optimization tolerance */

    public Param() {
        param_default();
    }

    void param_default() {
            this.turdsize = 2;
            this.turnpolicy = 4;      /* turnpolicy */
            this.alphamax = 1.0;                                /* alphamax */
            this.opticurve= true;                                  /* opticurve */
            this.opttolerance = 0.2;                            /* opttolerance */
    };

}
