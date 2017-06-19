package CharacterizationTests;

import potraceOriginal.Bitmap;
import potraceOriginal.Path;

/**
 * Created by andreydelany on 13/04/2017.
 */
public abstract class AbstractTestData {

    public Object[] getTestParameters(){
        return new Object[] {
                getBitmap(),
                getArrayOfAllPathes()
        };
    }

    abstract Bitmap getBitmap();

    abstract MockupPath[] getArrayOfAllPathes();
}
