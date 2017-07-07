package refactored.potrace.BitmapDecomposition;

import refactored.potrace.Path;

/**
 * Created by andreydelany on 06.07.17.
 */
public interface PathQueueInterface {

    public Path[] getNextPathes();

    public void updateQueue(Path path);

    public boolean stillNeedToProcessPathes();
}
