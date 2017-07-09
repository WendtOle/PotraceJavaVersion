import DecompositionKindsForTesting.*;
import General.DecompositionInterface;

/**
 * Created by andreydelany on 08.07.17.
 */
public enum DecompositionEnum {
    ORIGINAL,
    REFACTORED,
    REF_ORIG_PATHLISTTOTREE,
    //REF_ORIG_PATHINVERTER_INDECOMP,
    OrigPathListToTree_OrigPathInverterInDecomp,
    ORIGFINDNEXTFILLEDPIXEL,
    OriginalFindPath,
    ORIG_FINDNEXTFILLEDPIXEL_FINDPATH,
    FAKEORIGINAL;

    public DecompositionInterface getDecomposer(){
        switch (this) {
            case ORIGINAL:
                return new original.Decompose();
            case REFACTORED:
                return new refactored.Decompose();
            case REF_ORIG_PATHLISTTOTREE:
                return new DecomposeWithOriginalPathListToTree();
            /*case REF_ORIG_PATHINVERTER_INDECOMP:
                return new DecomposeWithOriginalPathInverterOnlyInDecompose();*/
            case OrigPathListToTree_OrigPathInverterInDecomp:
                return new OrigPathListToTreeOrigPathInverterInDecomp();
            case ORIGFINDNEXTFILLEDPIXEL:
                return new OrigFindNextFilledPixel();
            case OriginalFindPath:
                return new OriginalFindPath();
            case ORIG_FINDNEXTFILLEDPIXEL_FINDPATH:
                return new OriginFindFilledPixelAndFindPath();
            case FAKEORIGINAL:
                return new FakeOriginal();
        }
        return null;
    }

    public static String getDecompositionMethodsAsString() {
        String allMethods = "";
        for(DecompositionEnum currentDecompositionKind : DecompositionEnum.values()){
            allMethods += "- " + currentDecompositionKind.toString() + "\n";
        }
        return allMethods;
    }
}
