package potraceRefactored;

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
            this.turdsize = 1;                                  //TODO normal value was 2 but i use small pictures so 1 is okay
            this.turnpolicy = PotraceLib.POTRACE_TURNPOLICY_MINORITY;      /* turnpolicy */
            this.alphamax = 1.0;                                /* alphamax */
            this.opticurve= true;                                  /* opticurve */
            this.opttolerance = 0.2;                            /* opttolerance */
    };

}
