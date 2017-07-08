import AdditionalCode.Input.JSONDeEncoder;
import General.BitmapInterface;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by andreydelany on 21.06.17.
 */
public class RuntimeObservation {

    static int maxAmountOfRuns = 100000;
    static String bitmapFileName = "01.json";
    static String bitMapFileFolder = "testPictures";

    int amountOfRuns;
    BitmapInterface bitmap;
    Thread[] threads;
    RunTimeObserver[] oberserver;
    double msPerRun[][];

    public static void main(String args[]) throws InterruptedException {
        RuntimeObservation observation = new RuntimeObservation(bitmapFileName,bitMapFileFolder,maxAmountOfRuns);
        observation.runRunTimeOberservation();
    }

    public RuntimeObservation(String bitmapFileName, String bitmapFolderName, int amountOfRuns) {
        setBitmap(bitmapFileName,bitmapFolderName);
        this.amountOfRuns = amountOfRuns;
        createDifferentThreads();
        msPerRun = new double[DecompositionEnum.values().length][amountOfRuns];
    }

    public void runRunTimeOberservation(){
        startAllThreads();
        waitThatAllThreadsFinish();
        getResultsFromObservers();
        stopThreads();
        showResults();
    }

    private void getResultsFromObservers() {
        for(int i = 0; i < threads.length; i++){
            msPerRun[i] = oberserver[i].getResults();
        }
    }

    private void startAllThreads() {
        for(Thread currentThread:threads)
            currentThread.start();
    }

    private void waitThatAllThreadsFinish(){
        for(Thread currentThread:threads){
            try {
                currentThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopThreads() {
        for(Thread currentThread:threads)
            currentThread.interrupt();
    }

    private void createDifferentThreads() {
        threads = new Thread[DecompositionEnum.values().length];
        oberserver = new RunTimeObserver[DecompositionEnum.values().length];
        for (int i = 0; i < threads.length; i++) {
            oberserver[i] = new RunTimeObserver(amountOfRuns, DecompositionEnum.values()[i], bitmap);
            threads[i] = new Thread(oberserver[i]);
        }
    }

    private void setBitmap(String bitmapFileName, String bitmapFolderName){
        try {
            bitmap = JSONDeEncoder.readBitmapFromJSon(bitmapFileName,bitmapFolderName);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String printRoundNumberWithPoints (int i) {
        return NumberFormat.getNumberInstance(Locale.GERMAN).format(i);
    }

    private void showResults() {
        System.out.println("Average Amount of MS Needed for One Run");
        for(int i = 0; i < msPerRun.length; i++){
            double lastValue = msPerRun[i][amountOfRuns-1];
            System.out.println(DecompositionEnum.values()[i] + ": " + lastValue + " ms");
        }
        PlotterRunTime.plot(amountOfRuns, msPerRun,bitmapFileName);
    }
}
