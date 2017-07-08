import AdditionalCode.Input.JSONDeEncoder;
import General.BitmapInterface;
import General.DecompositionInterface;
import General.Param;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by andreydelany on 21.06.17.
 */
public class RuntimeObservation {

    static String type = "Refactored";
    static int maxAmountOfRuns = 20000;
    static String bitmapFileName = "02.json";
    static String bitMapFileFolder = "testPictures";
    static BitmapInterface bitmap;
    static double[][] msPerRun = new double[2][100];

    public static void main(String args[]){
        loadBitmap();
        long totalRefactoredRunTime = 0;
        long totalOriginalRunTime = 0;
        printGeneralInformationAboutRun();
        for (int i = 0; i < maxAmountOfRuns; i++) {
            totalRefactoredRunTime += getTimeForOneRefactoredRun();
            totalOriginalRunTime += getTimeForOneOriginalRun();
            saveCurrentAverageRunTime(totalRefactoredRunTime,totalOriginalRunTime,i);
            showProgress(i);
        }
        showResults();
    }

    public static void loadBitmap(){
        try {
            bitmap = JSONDeEncoder.readBitmapFromJSon(bitmapFileName, bitMapFileFolder);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printGeneralInformationAboutRun() {
        System.out.println("Test starts with "+ printRoundNumberWithPoints(maxAmountOfRuns) + " Rounds:");
        System.out.print("Progress : ||||||||||||||||||||\n           ");
    }

    private static String printRoundNumberWithPoints (int i) {
        return NumberFormat.getNumberInstance(Locale.GERMAN).format(i);
    }

    private static long getTimeForOneRefactoredRun() {
        DecompositionInterface decomposer = new refactored.Decompose();
        Param params = new Param();

        long startTime = System.nanoTime();
        decomposer.getPathList(bitmap,params);
        long endTime = System.nanoTime();
        return (endTime - startTime);
    }

    private static long getTimeForOneOriginalRun() {
        DecompositionInterface decomposer = new original.Decompose();
        Param params = new Param();

        long startTime = System.nanoTime();
        decomposer.getPathList(bitmap,params);
        long endTime = System.nanoTime();
        return (endTime - startTime);
    }

    private static void saveCurrentAverageRunTime(long refactoredRunTimes, long originalRunTimes, int currentRunIndex) {
        if(currentRunIndex % (maxAmountOfRuns /100) == 0) {
            int identifier = currentRunIndex / (maxAmountOfRuns / 100);
            msPerRun[0][identifier] = refactoredRunTimes / ((double) (currentRunIndex + 1) * 1000000);
            msPerRun[1][identifier] = originalRunTimes / ((double) (currentRunIndex + 1) * 1000000);
        }
    }

    private static void showProgress(int currentRunIndex) {
        if (currentRunIndex % (maxAmountOfRuns /20) == 0) {
            System.out.print("|");
        }
    }

    private static void showResults() {
        System.out.println("\nAverage Amount of MS Needed for One Run");
        System.out.println("Refactored: " + msPerRun[0][99] + " ms");
        System.out.println("Original: " + msPerRun[1][99] + " ms");
        PlotterRunTime.plot(maxAmountOfRuns / 100, msPerRun, type, bitmapFileName);
    }
}
