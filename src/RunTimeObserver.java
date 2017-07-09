import General.Bitmap;
import General.DecompositionInterface;
import General.Param;

/**
 * Created by andreydelany on 08.07.17.
 */
public class RunTimeObserver implements Runnable {

    int amountOfRuns;
    DecompositionEnum decomposerIdentifier;
    Bitmap bitmap;
    double[] msPerRun;
    int currentRun;
    long totalRunTime = 0;

    public RunTimeObserver(int amountOfRuns, DecompositionEnum decomposer, Bitmap bitmap){
        this.amountOfRuns = amountOfRuns;
        this.decomposerIdentifier = decomposer;
        this.bitmap = bitmap;
        msPerRun= new double[amountOfRuns];
    }
    @Override
    public void run() {
        Param params = new Param();
        for (currentRun = 0; currentRun < amountOfRuns; currentRun++) {
            DecompositionInterface decomposer = decomposerIdentifier.getDecomposer();

            long startTime = System.nanoTime();
            decomposer.getPathList(bitmap,params);
            long endTime = System.nanoTime();

            saveRunTime(startTime,endTime);
        }
    }

    private void saveRunTime(long startTime,long endTime) {
        totalRunTime += (endTime - startTime);
        msPerRun[currentRun] = totalRunTime / ((double) (currentRun + 1) * 1000000);
    }

    public double[] getResults() {
        return msPerRun;
    }

    public double getProcentageToFinish() {
        return (double)currentRun / (double)amountOfRuns;
    }
}
