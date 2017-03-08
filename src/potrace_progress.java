/**
 * Created by andreydelany on 04/03/2017.
 */
public class potrace_progress {

    //void (*callback)(double progress, void *privdata); /* callback fn */ //TODO here should be a funtion
    //void *data;          /* callback function's private data */ //TODO here should be a funtion

    double min, max;     /* desired range of progress, e.g. 0.0 to 1.0 */
    double epsilon;

   /* private void progress_update(double d) {
        double d_scaled;

        if (this != null && prog.callback != NULL) {
            d_scaled = prog->min * (1-d) + prog->max * d;
            if (d == 1.0 || d_scaled >= prog->d_prev + prog->epsilon) {
                prog->callback(prog->min * (1-d) + prog->max * d, prog->data);
                prog->d_prev = d_scaled;
            }
        }
    }*/

}
