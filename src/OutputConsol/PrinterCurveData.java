package OutputConsol;

import potrace.Path;

/**
 * Created by andreydelany on 21/03/2017.
 */
public class PrinterCurveData {

    Path path;

    public PrinterCurveData(Path path) {
        this.path = path;
    }

    public void print() {
        printHeader();
        recursiveCallToPrintNextCurve(path);
    }

    private void recursiveCallToPrintNextCurve(Path path) {
        printCurrentCurve(path);
        if (hasNext(path)) {
            recursiveCallToPrintNextCurve(path.next);
        }
    }

    private void printCurrentCurve(Path path) {
        for(int i = 0; i < path.curve.n;i++) {
            printCurrentBezierCurve(path, i);
        }
        newLine();
    }

    private void printCurrentBezierCurve(Path path, int i) {
        System.out.print(i + ": ");
        for(int j = 0; j < 3; j ++) {
            printCurrentCurvePoint(path, i,j);
        }
        newLine();
    }

    private void printCurrentCurvePoint(Path path, int i, int j){
        System.out.printf("( %.6f,", path.curve.c[i][j].x);
        System.out.printf("%.6f), ", path.curve.c[i][j].y );
    }

    private boolean hasNext(Path path) {
        return path.next != null;
    }

    private void printHeader() {
        System.out.println("-----------resultCurveData---------------");
    }

    private void newLine() {
        System.out.print("\n");
    }
}
