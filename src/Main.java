import Input.BitmapImporter;
import OutputConsol.PrinterCurveData;
import OutputConsol.PrinterPathStructure;
import OutputGraphical.Plotter;
import potraceOriginal.Bitmap;
import potraceOriginal.Param;
import potraceOriginal.Path;
import potraceOriginal.PotraceLib;

public class Main {

    static String folderName = "testPictures";
    static String fileName = "01.bmp";
    static int amountOfRuns = 1000000;
    static Bitmap bitmap;
    static double[] msPerRun = new double[100];

    public static void main(String [] args){
        bitmap = BitmapImporter.importBitmap(fileName,folderName);
        //runWithFocusOnOutput();
        runWithTimeObservation();
    }

    public static void runWithTimeObservation() {
        long totalRunTime = 0;
        printGeneralInformationAboutRun();
        for (int i = 0; i < amountOfRuns; i++) {
            totalRunTime += getTimeForOneRun();
            saveCurrentAverageRunTime(totalRunTime,i);
            showProgress(i);
        }
        showResults();
    }

    private static void printGeneralInformationAboutRun() {
        System.out.println("Test starts with "+ amountOfRuns + " Rounds:");
        System.out.print("Progress : ||||||||||||||||||||\n           ");
    }

    private static long getTimeForOneRun() {
        long startTime = System.nanoTime();
        Path path = PotraceLib.potrace_trace(new Param(),bitmap);
        long endTime = System.nanoTime();
        return (endTime - startTime);
    }

    private static void saveCurrentAverageRunTime(long currentTotalRunTime, int currentRunIndex) {
        if(currentRunIndex % (amountOfRuns/100) == 0)
            msPerRun[currentRunIndex / (amountOfRuns / 100)] = currentTotalRunTime/((double)(currentRunIndex+1)*1000000);
    }

    private static void showProgress(int currentRunIndex) {
        if (currentRunIndex % (amountOfRuns/20) == 0) {
            System.out.print("|");
        }
    }

    private static void showResults() {
        System.out.println("\n Average Amount of MS Needed for One Run: " + msPerRun[99] + " ms");
        PlotterRunTime.plot(amountOfRuns/100,msPerRun);
    }

    public static void runWithFocusOnOutput() {
        Bitmap bitmap = BitmapImporter.importBitmap(fileName,folderName);
        Path path = PotraceLib.potrace_trace(new Param(),bitmap);

        PrinterPathStructure printer = new PrinterPathStructure(path);
        printer.print();

        PrinterCurveData curvePrinter = new PrinterCurveData(path);
        curvePrinter.print();

        Plotter plotter = new Plotter();
        plotter.showBitmap(bitmap);
        //plotter.showPathAndBitmap(path,bitmap);
        //plotter.showPath(path);
    }
}
