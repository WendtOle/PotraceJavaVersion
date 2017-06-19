package potraceOriginal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 21/03/2017.
 */
public class privcurveTest {
    @Test
    public void testPriveCurveInitialization() throws Exception {
        PrivCurve testPrivCurve = new PrivCurve(10);
        assertEquals(10,testPrivCurve.n);
        assertEquals(10,testPrivCurve.tag.length);
        assertEquals(10,testPrivCurve.alpha.length);
        assertEquals(10,testPrivCurve.alpha0.length);
        assertEquals(10,testPrivCurve.beta.length);
        assertEquals(10,testPrivCurve.vertex.length);
        for(int i = 0; i < 10; i ++) {
            assertEquals(false, testPrivCurve.vertex[i] == null);
        }
        for(int i = 0; i < 10; i ++) {
            for(int j = 0; j < 3; j ++) {
                assertEquals(false, testPrivCurve.c[i][j] == null);
            }
        }
    }
}
