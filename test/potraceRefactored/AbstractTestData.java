package potraceRefactored;

/**
 * Created by andreydelany on 13/04/2017.
 */
public abstract class AbstractTestData {

    public Object[] getTestParameters(){
        return new Object[] {
                getName(),
                getAmountOfPathes(),
                getArrayWithAreaOfPathes(),
                getArrayWithSignOfPathes(),
                getArrayWithLengthOfPathes(),
                getArrayTagsOfCurvesOfPathes(),
                getArrayOfChildSiblingRelationOfPathes(),
                getPointsOfCurvesOfPathes(),
                getInformationsAboutPrivePath()
        };
    }

    abstract String getName();

    abstract int getAmountOfPathes();

    abstract int[] getArrayWithAreaOfPathes();

    abstract int[] getArrayWithSignOfPathes();

    abstract int[] getArrayWithLengthOfPathes();

    abstract int[][] getArrayTagsOfCurvesOfPathes();

    abstract boolean[][] getArrayOfChildSiblingRelationOfPathes();

    abstract double[][][][] getPointsOfCurvesOfPathes();

    abstract int[][] getInformationsAboutPrivePath();
}
