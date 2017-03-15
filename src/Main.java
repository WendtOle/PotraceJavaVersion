import java.awt.*;

/**
 * Created by andreydelany on 06/03/2017.
 */
public class Main {
    public static void main(String [] args)
    {
        potrace_param param = new potrace_param();
        potrace_bitmap bm = potrace_bitmap.default_bitmap_normalThird();
        potrace_path result = PotraceLib.potrace_trace(param,bm);

        System.out.println("-----------resultCurveData---------------");
        potrace_path current = null;
        do {
            if (current != null) {
                current = current.next;
            } else {
                current = result;
            }

            for(int i = 0; i < current.curve.n;i++) {
                System.out.print(i + ": ");
                for(int j = 0; j < 3; j ++) {
                    System.out.print("("+  current.curve.c[i][j].x+",");
                    System.out.print(current.curve.c[i][j].y + "), ");
                }
                System.out.print("\n");
            }
            System.out.print("\n");

        } while (current.next!= null);
        int nonsens = 9;
    }
}
