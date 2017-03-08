/**
 * Created by andreydelany on 04/03/2017.
 */
public class potrace_curve {
    int n;      //number of segments
    int tag;    //tag[n] : Potrace_Curveto or potrace_corner //TODO: Array
    potrace_dpoint[] c = new potrace_dpoint[3];
}
