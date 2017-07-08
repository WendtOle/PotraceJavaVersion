import General.DecompositionInterface;

/**
 * Created by andreydelany on 08.07.17.
 */
public enum DecompositionEnum {
    ORIGINAL, REFACTORED;

    public DecompositionInterface getDecomposer(){
        switch (this) {
            case ORIGINAL:
                return new original.Decompose();
            case REFACTORED:
                return new refactored.Decompose();
        }
        return null;
    }
}
