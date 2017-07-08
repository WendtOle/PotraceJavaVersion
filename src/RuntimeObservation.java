import AdditionalCode.Input.JSONDeEncoder;
import General.BitmapInterface;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by andreydelany on 21.06.17.
 */
public class RuntimeObservation {

    static int maxAmountOfRuns = 1000000;
    static String bitmapFileName = "02.json";
    static String bitMapFileFolder = "testPictures";

    int amountOfRuns;
    BitmapInterface bitmap;
    Thread[] threads;
    Thread progressPrinter;
    RunTimeObserver[] observer;
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
        printGeneralInformationAboutTimeObservation();
    }

    private void printGeneralInformationAboutTimeObservation() {
        String status = "RunTimeObservation of Decomposition: \n";
        status += DecompositionEnum.getDecompositionMethodsAsString();
        status += "\nAmountOfRuns: " + printRoundNumberWithPoints(amountOfRuns);
        System.out.println(status);
    }

    public void runRunTimeOberservation(){
        startAllThreads();
        startProgressPrinter();
        waitThatAllThreadsFinish();
        getResultsFromObservers();
        stopThreads();
        showResults();
    }

    private void startProgressPrinter() {
        Thread thread = new Thread(){

            int secondIntervall = 1;

            @Override
            public void run() {
                System.out.println("\nProgress:");
                for(int i = 1; isObservationStillRunning(); i++) {
                    sleep();
                    printStatus(i);
                }
            }

            private void printStatus(int i) {
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMaximumFractionDigits(2);
                numberFormat.setMinimumFractionDigits(2);
                String procentage = numberFormat.format(getProgress() * 100);
                int stillNeededTime = (int) (((1. - getProgress()) * secondIntervall * i) / getProgress());
                String status = procentage + "% done - still needs " + stillNeededTime + " sec";
                System.out.println(status);
            }

            private void sleep() {
                try {
                    sleep(secondIntervall * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.run();
    }

    private double getProgress() {
        double allTogether = 0;
        for(RunTimeObserver currentObserver : observer) {
            allTogether += currentObserver.getProcentageToFinish();
        }
        return allTogether/observer.length;
    }

    private void getResultsFromObservers() {
        for(int i = 0; i < threads.length; i++){
            msPerRun[i] = observer[i].getResults();
        }
    }

    private boolean isObservationStillRunning() {
        for(RunTimeObserver observer :observer) {
            if (observer.getProcentageToFinish() < 1.)
                return true;
        }
        return false;
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
        System.out.println("\nRunTimeObservation finished\n");
    }

    private void stopThreads() {
        for(Thread currentThread:threads)
            currentThread.interrupt();
        progressPrinter.interrupt();
    }

    private void createDifferentThreads() {
        threads = new Thread[DecompositionEnum.values().length];
        observer = new RunTimeObserver[DecompositionEnum.values().length];
        for (int i = 0; i < threads.length; i++) {
            observer[i] = new RunTimeObserver(amountOfRuns, DecompositionEnum.values()[i], bitmap);
            threads[i] = new Thread(observer[i]);
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

    private String printRoundNumberWithPoints (int i) {
        return NumberFormat.getNumberInstance(Locale.GERMAN).format(i);
    }

    private void showResults() {
        System.out.println("Average Amount of MS Needed for One Run:");
        for(int i = 0; i < msPerRun.length; i++){
            double lastValue = msPerRun[i][amountOfRuns-1];
            System.out.println(DecompositionEnum.values()[i] + ": " + lastValue + " ms");
        }
        PlotterRunTime.plot(amountOfRuns, msPerRun,bitmapFileName);
    }
}