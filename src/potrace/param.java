package potrace;

public class param {

    public int turdsize;        /* area of largest path to be ignored */
    public int turnpolicy;      /* resolves ambiguous turns in path decomposition */
    double alphamax;            /* corner threshold */
    int opticurve;              /* use curve optimization? */
    double opttolerance;        /* curve optimization tolerance */

    public param() {
        param_default();
    }

    void param_default() {
            this.turdsize = 1;                                  //TODO normal value was 2 but i use small pictures so 1 is okay
            this.turnpolicy = potraceLib.POTRACE_TURNPOLICY_MINORITY;      /* turnpolicy */
            this.alphamax = 1.0;                                /* alphamax */
            this.opticurve= 1;                                  /* opticurve */
            this.opttolerance = 0.2;                            /* opttolerance */
    };

}
