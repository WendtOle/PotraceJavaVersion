package DecompositionKindsForTesting;

import General.DecompositionInterface;
import refactored.Decompose;

/**
 * Created by andreydelany on 09.07.17.
 */
public class DecomposeWithOriginalPathListToTree extends Decompose implements DecompositionInterface{

    @Override
    protected void structurePathlistAsTree(){
        original.Decompose.pathlist_to_tree(pathList,workCopy);
    }

}
