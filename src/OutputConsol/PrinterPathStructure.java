package OutputConsol;

import potrace.Path;

/**
 * Created by andreydelany on 21/03/2017.
 */
public class PrinterPathStructure {

    Path origin;

    public PrinterPathStructure(Path path) {
        this.origin = path;
    }

    public void print() {
        printHeader();
        recursiveCallToPrintNextPolygon(origin,0,false, BranchTypEnum.NULL);
        System.out.println();
    }

    private void printHeader() {
        System.out.println();
        System.out.println("-------Architecture of Pathes in Picture---------");
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

    public void recursiveCallToPrintNextPolygon(Path current, int ebene, boolean isTheSame, BranchTypEnum follower) {
        printIntroduction(follower);
        printActualPathData(current);
        callFollowingPolygon(current,ebene, isTheSame,follower);
    }

    private void callFollowingPolygon(Path path, int ebene, boolean isTheSame, BranchTypEnum follower) {
        if (isTheSame) {
            printReferenceToPreviousePolygon(path);
        } else {
            System.out.print("\n");
            if (hasNext(path)) {
                printConnectionLineBetweenLevels(ebene);
                recursiveCallToPrintNextPolygon(path.next, ebene + 1, false, BranchTypEnum.NEXT);
            }
            if (hasChild(path)) {
                printConnectionLineBetweenLevels(ebene);
                boolean isNextTheSame = path.next == path.childlist;
                recursiveCallToPrintNextPolygon(path.childlist, ebene + 1, isNextTheSame, BranchTypEnum.CHILD);
            }
            if (hasSibling(path)) {
                printConnectionLineBetweenLevels(ebene);
                boolean isNextTheSame = path.next == path.sibling;
                recursiveCallToPrintNextPolygon(path.sibling, ebene + 1, isNextTheSame, BranchTypEnum.SIBLING);
            }
        }
    }

    private void printReferenceToPreviousePolygon (Path path) {
        if (hasAtMinimumOneFollower(path)) {
            System.out.print(" siehe oben");
        }
        System.out.print("\n");
    }

    private void printActualPathData (Path path) {
        System.out.print(path.area + "-(" +  path.priv.pt[0].x + " " + path.priv.pt[0].y + ")");
    }

    private void printConnectionLineBetweenLevels(int level) {
        printFiller(level);
        System.out.print("|\n");
        printFiller(level);
    }

    private boolean hasAtMinimumOneFollower(Path path) {
        return (path.next != null || path.childlist != null || path.sibling != null);
    }

    private boolean hasNext(Path path) {
        return path.next != null;
    }

    private boolean hasChild(Path path) {
        return path.childlist != null;
    }

    private boolean hasSibling(Path path) {
        return path.sibling != null;
    }

    private void printIntroduction(BranchTypEnum follower) {
        String introduction = "";
        switch(follower) {
            case NEXT: introduction = "|-next-  ";
                break;
            case CHILD: introduction = "|-child- ";
                break;
            case SIBLING: introduction = "|-sibl-  ";
                break;
            case NULL: introduction = "";
        }
        System.out.print(introduction);
    }

}
