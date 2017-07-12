package Potrace.refactored;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 12.07.17.
 */
public class PointShapeTest {

    @Test
    public void testAddingFourPointsToPointShape(){
        PointShape pointShape = getPointShapeWithLength(4);
        validatePointShape(pointShape,4,131);
    }

    @Test
    public void testAdding131PointsToPointshape(){
        PointShape pointShape =  getPointShapeWithLength(131);
        validatePointShape(pointShape,131,131);
    }

    @Test
    public void testAdding132PointsToPointshape(){
        PointShape pointShape =  getPointShapeWithLength(132);
        validatePointShape(pointShape,132,300);
    }

    private void validatePointShape(PointShape pointShape,int indexOfCurrentPoint, int lengthOfArray) {
        assertEquals("amount Of Points",indexOfCurrentPoint,pointShape.getLengthOfPath());
        assertEquals("lengthOfArray",lengthOfArray,pointShape.getPointsOfPath().length);
        assertEquals("index Of current Point",indexOfCurrentPoint,pointShape.indexOfCurrentPoint);
    }

    private PointShape getPointShapeWithLength(int amountOfPoints){
        PointShape pointShape = new PointShape();
        for (int i = 0; i < amountOfPoints; i++)
            pointShape.addPointToPointsOfPath(new Point(1,1));
        return pointShape;

    }

}
