package Bencharking;

import Potrace.General.Bitmap;
import Potrace.refactored.Decompose;

/**
 * Created by andreydelany on 10.07.17.
 */
public class DecomposeHelperForTreeStructureTransformation extends Decompose {
    @Override
    protected void structurePathListAsTree(){
    }

    public Bitmap getWorkCopy() {
        return workCopy;
    }
}
