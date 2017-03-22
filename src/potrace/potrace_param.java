package potrace;

/**
 * Created by andreydelany on 04/03/2017.
 */
public class potrace_param {

    static int POTRACE_TURNPOLICY_BLACK = 0;
    static int  POTRACE_TURNPOLICY_WHITE = 1;
    static int POTRACE_TURNPOLICY_LEFT = 2;
    static int  POTRACE_TURNPOLICY_RIGHT = 3;
    static int  POTRACE_TURNPOLICY_MINORITY = 4;
    static int  POTRACE_TURNPOLICY_MAJORITY = 5;
    static int  POTRACE_TURNPOLICY_RANDOM = 6;

    int turdsize;        /* area of largest path to be ignored */
    int turnpolicy;      /* resolves ambiguous turns in path decomposition */
    double alphamax;     /* corner threshold */
    int opticurve;       /* use curve optimization? */
    double opttolerance; /* curve optimization tolerance */

    public potrace_param() {
        param_default();
    }

    void param_default() {
            this.turdsize = 1; //TODO normal value was 2 but i use small pictures so 1 is okay   /* turdsize */
            this.turnpolicy = POTRACE_TURNPOLICY_MINORITY;   /* turnpolicy */
            this.alphamax = 1.0;                           /* alphamax */
            this.opticurve= 1;                             /* opticurve */
            this.opttolerance = 0.2;                          /* opttolerance */
    };

}
