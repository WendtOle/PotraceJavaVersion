package potrace;

/**
 * Created by andreydelany on 14/04/2017.
 */
public class TestDataPicture02 extends TestData {

    String getName() {
        return "02.bmp";
    }

    int getAmountOfPathes(){
        return 2;
    }

    int[] getArrayWithAreaOfPathes(){
        return new int[]{90,28};
    }

    int[] getArrayWithSignOfPathes(){
        return new int[]{43,45};
    }

    int[] getArrayWithLengthOfPathes(){
        return new int[]{4,4};
    }

    int[][] getArrayTagsOfCurvesOfPathes(){
        return new int[][]{
                {1, 1, 1, 1},
                {1, 1, 1}};
    }

    boolean[][] getArrayOfChildSiblingRelationOfPathes() {
        return new boolean[][]{
                {true, false},
                {false,false}
        };
    }

    double [][][][] getPointsOfCurvesOfPahtes () {
        return new double[][][][]{
                {
                        {{48.000000,0.233333}, {50.333333,0.000000}, {63.000000,0.000000}},
                        {{75.666667,0.000000}, {78.000000,0.233333}, {78.000000,1.500000}},
                        {{78.000000,2.766667}, {75.666667,3.000000}, {63.000000,3.000000}},
                        {{50.333333,3.000000}, {48.000000,2.766667}, {48.000000,1.500000}}
                },
                {
                        {{66.262500,1.067682}, {59.737500,1.067682}, {55.750000,1.257493}},
                        {{51.762500,1.447305}, {55.025000,1.602606}, {63.000000,1.602606}},
                        {{70.975000,1.602606}, {74.237500,1.447305}, {70.250000,1.257493}}
                }};
    }

    int[][] getInformationsAboutPrivePath() {
        return new int[][]{
                {66,48,3,4},
                {58,49,2,3}
        };
    }
}
