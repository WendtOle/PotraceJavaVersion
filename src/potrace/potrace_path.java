package potrace;

/**
 * Created by andreydelany on 04/03/2017.
 */
public class potrace_path {
    public int area;               //area of the bitmap path
    public int sign;               //+ or -, depending on orientation
    public potrace_curve curve;    // this path vector data
    public potrace_path next;      //linked potrace.list structure //TODO: verweist auf sich selbst und bildet so den anfang für einen linked potrace.list
    public potrace_path childlist;  //tree structure //TODO: verweist auf ein Array von kinder
    public potrace_path sibling;  //tree structure //TODO: verweist auf ein Array von geschwistern
    public potrace_privepath priv;  /* private state */


    public potrace_path() {
        this.priv = new potrace_privepath();
    }


}
