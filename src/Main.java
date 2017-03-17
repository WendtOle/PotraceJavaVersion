import java.awt.*;

/**
 * Created by andreydelany on 06/03/2017.
 */
public class Main {

    public static void printCurveData(potrace_path result) {
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
                    System.out.printf("( %.6f,", current.curve.c[i][j].x);
                    System.out.printf("%.6f), ", current.curve.c[i][j].y );
                }
                System.out.print("\n");
            }
            System.out.print("\n");

        } while (current.next!= null);
    }

    private static void printFiller(int ebene) {
        int amount;
        if (ebene == 0)
            amount = 2;
        else {
            amount = 14 * (ebene);
        }
        for(int i = 0; i < amount; i ++) {
            System.out.print(" ");
        }
    }

    public static void printNextPolygon(potrace_path current, int ebene, boolean isTheSame) {
        System.out.print(current.area + "-(" +  current.priv.pt[0].x + " " + current.priv.pt[0].y + ")");


        if (isTheSame) {
            if (current.next != null || current.childlist != null || current.sibling != null)
                System.out.print(" siehe oben");
            System.out.print("\n");
        } else {
            System.out.print("\n");
            if (current.next != null) {
                printFiller(ebene);
                System.out.print("|\n");
                printFiller(ebene);
                System.out.print("|-next-  ");
                printNextPolygon(current.next, ebene + 1, false);
            }
            if (current.childlist != null) {
                printFiller(ebene);
                System.out.print("|\n");
                printFiller(ebene);
                System.out.print("|-child- ");
                printNextPolygon(current.childlist, ebene + 1, current.next == current.childlist);
            }
            if (current.sibling != null) {
                printFiller(ebene);
                System.out.print("|\n");
                printFiller(ebene);
                System.out.print("|-sibl-  ");
                printNextPolygon(current.sibling, ebene + 1, current.next == current.sibling);
            }
        }

    }

    public static void printArchitectureOfPolygons(potrace_path result) {
        System.out.println();
        System.out.println("-------Architecture of Pathes in Picture---------");
        printNextPolygon(result,0,false);
        System.out.println();
    }

    public static void main(String [] args)
    {
        potrace_param param = new potrace_param();
        potrace_bitmap bm = potrace_bitmap.default_bitmap_Sixth();
        long startTime = System.currentTimeMillis();
        potrace_path result = PotraceLib.potrace_trace(param,bm);
        long  endTime = System.currentTimeMillis();

        System.out.println("Excecution needed: " + (endTime - startTime) + "ms");

        printArchitectureOfPolygons(result);

        printCurveData(result);
    }
}
