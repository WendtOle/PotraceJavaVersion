import General.BitmapInterface;
import General.DecompositionInterface;
import General.Param;

/**
 * Created by andreydelany on 08.07.17.
 */
public class RunTimeObserver implements Runnable {

    int amountOfRuns;
    DecompositionEnum decomposerIdentifier;
    BitmapInterface bitmapInterface;
    double[] msPerRun;

    public RunTimeObserver(int amountOfRuns, DecompositionEnum decomposer, BitmapInterface bitmapInterface){
        this.amountOfRuns = amountOfRuns;
        this.decomposerIdentifier = decomposer;
        this.bitmapInterface = bitmapInterface;
        msPerRun= new double[amountOfRuns];
    }
    @Override
    public void run() {
        long totalRunTime = 0;
        Param params = new Param();
        for (int currentRun = 0; currentRun < amountOfRuns; currentRun++) {
            DecompositionInterface decomposer = decomposerIdentifier.getDecomposer();
            long startTime = System.nanoTime();
            decomposer.getPathList(bitmapInterface,params);
            long endTime = System.nanoTime();
            totalRunTime += (endTime - startTime);
            msPerRun[currentRun] = totalRunTime / ((double) (currentRun + 1) * 1000000);
        }
    }

    public double[] getResults() {
        return msPerRun;
    }
}
