/**
 * Created by andreydelany on 06/03/2017.
 */
public class privcurve {
    int n;            /* number of segments */
    int tag;         /* tag[n]: POTRACE_CORNER or POTRACE_CURVETO */
    //dpoint (*c)[3]; /* c[n][i]: control points. //TODO check ich nicht
		       /*c[n][0] is unused for tag[n]=POTRACE_CORNER */
  /* the remainder of this structure is special to privcurve, and is
     used in EPS debug output and special EPS "short coding". These
     fields are valid only if "alphacurve" is set. */
    int alphacurve;   /* have the following fields been initialized? */
    potrace_dpoint vertex; /* for POTRACE_CORNER, this equals c[1] */
    double alpha;    /* only for POTRACE_CURVETO */
    double alpha0;   /* "uncropped" alpha parameter - for debug output only */
    double beta;
}
