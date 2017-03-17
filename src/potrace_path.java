/**
 * Created by andreydelany on 04/03/2017.
 */
public class potrace_path {
    int area;               //area of the bitmap path
    int sign;               //+ or -, depending on orientation
    potrace_curve curve;    // this path vector data
    potrace_path next;      //linked list structure //TODO: verweist auf sich selbst und bildet so den anfang für einen linked list
    potrace_path childlist;  //tree structure //TODO: verweist auf ein Array von kinder
    potrace_path sibling;  //tree structure //TODO: verweist auf ein Array von geschwistern
    potrace_privepath priv;  /* private state */


    public potrace_path() {
        this.priv = new potrace_privepath();
    }

}
