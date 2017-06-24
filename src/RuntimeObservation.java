import AdditionalCode.Bitmap;
import AdditionalCode.BitmapTranslater;
import AdditionalCode.Input.JSONDeEncoder;
import org.json.simple.parser.ParseException;
import potraceOriginal.Param;
import potraceOriginal.Path;
import potraceOriginal.PotraceLib;

import java.io.IOException;

/**
 * Created by andreydelany on 21.06.17.
 */
public class RuntimeObservation {

    static int amountOfRuns = 1000000;
    static String bitmapFileName = "01.json";
    static String bitMapFileFolder = "testPictures";
    static Bitmap bitmap;
    static double[] msPerRun = new double[100];

    public static void main(String args[]){
        loadBitmap();
        long totalRunTime = 0;
        printGeneralInformationAboutRun();
        for (int i = 0; i < amountOfRuns; i++) {
            totalRunTime += getTimeForOneRun();
            saveCurrentAverageRunTime(totalRunTime,i);
            showProgress(i);
        }
        showResults();
    }

    public static void loadBitmap(){
        try {
            bitmap = JSONDeEncoder.readBitmapFromJSon(bitmapFileName, bitMapFileFolder);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void printGeneralInformationAboutRun() {
        System.out.println("Test starts with "+ amountOfRuns + " Rounds:");
        System.out.print("Progress : ||||||||||||||||||||\n           ");
    }

    private static long getTimeForOneRun() {
        long startTime = System.nanoTime();
        Path path = PotraceLib.potrace_trace(new Param(), BitmapTranslater.translatBitmapForOriginalCode(bitmap));
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
}
