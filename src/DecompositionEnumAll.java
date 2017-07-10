import General.DecompositionInterface;

/**
 * Created by andreydelany on 08.07.17.
 */
public enum DecompositionEnumAll {
  /*  original,
    refactored,
    refactored_origPathListToTree,
    refactored_origPathListToTree_origPathInverter,
    refactored_origFindNextFilledPixel,
    refactored_origFindPath,
    refactored_origFindNextFilledPixel_origFindPath,
    refactored_withAllOrigMethods;

    public DecompositionInterface getDecomposer(){
        switch (this) {
            case original:
                return new original.Decompose();
            case refactored:
                return new refactored.Decompose();
            case refactored_origPathListToTree:
                return new DecomposeWithOriginalPathListToTree();
            case refactored_origPathListToTree_origPathInverter:
                return new OrigPathListToTreeOrigPathInverterInDecomp();
            case refactored_origFindNextFilledPixel:
                return new OrigFindNextFilledPixel();
            case refactored_origFindPath:
                return new OriginalFindPath();
            case refactored_origFindNextFilledPixel_origFindPath:
                return new OriginFindFilledPixelAndFindPath();
            case refactored_withAllOrigMethods:
                return new FakeOriginal();
        }
        return null;
    }
*/

    original,
    refactored;

    public DecompositionInterface getDecomposer(){
        switch (this) {
            case original:
                return new original.Decompose();
            case refactored:
                return new refactored.Decompose();
        }
        return null;
    }




    public static String getDecompositionMethodsAsString() {
        String allMethods = "";
        for(DecompositionEnumAll currentDecompositionKind : DecompositionEnumAll.values()){
            allMethods += "- " + currentDecompositionKind.toString() + "\n";
        }
        return allMethods;
    }
}
