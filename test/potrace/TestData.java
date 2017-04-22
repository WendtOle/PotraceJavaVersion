package potrace;

/**
 * Created by andreydelany on 13/04/2017.
 */
public abstract class TestData{

    public Object[] getTestParameters(){
        return new Object[] {
                getName(),
                getAmountOfPathes(),
                getArrayWithAreaOfPathes(),
                getArrayWithSignOfPathes(),
                getArrayWithLengthOfPathes(),
                getArrayTagsOfCurvesOfPathes(),
                getArrayOfChildSiblingRelationOfPathes(),
                getPointsOfCurvesOfPahtes(),
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

    abstract double[][][][] getPointsOfCurvesOfPahtes();

    abstract int[][] getInformationsAboutPrivePath();
}
