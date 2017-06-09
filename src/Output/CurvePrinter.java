package Output;

/**
 * Created by andreydelany on 21/03/2017.
 */
public class CurvePrinter {

    potrace.path path;

    public CurvePrinter (potrace.path path) {
        this.path = path;
    }

    public void print() {
        printHeader();
        recursiveCallToPrintNextCurve(path);
    }

    private void recursiveCallToPrintNextCurve(potrace.path path) {
        printCurrentCurve(path);
        if (hasNext(path)) {
            recursiveCallToPrintNextCurve(path.next);
        }
    }

    private void printCurrentCurve(potrace.path path) {
        for(int i = 0; i < path.curve.n;i++) {
            printCurrentBezierCurve(path, i);
        }
        newLine();
    }

    private void printCurrentBezierCurve(potrace.path path, int i) {
        System.out.print(i + ": ");
        for(int j = 0; j < 3; j ++) {
            printCurrentCurvePoint(path, i,j);
        }
        newLine();
    }

    private void printCurrentCurvePoint(potrace.path path, int i, int j){
        System.out.printf("( %.6f,", path.curve.c[i][j].x);
        System.out.printf("%.6f), ", path.curve.c[i][j].y );
    }

    private boolean hasNext(potrace.path path) {
        return path.next != null;
    }

    private void printHeader() {
        System.out.println("-----------resultCurveData---------------");
    }

    private void newLine() {
        System.out.print("\n");
    }
}
